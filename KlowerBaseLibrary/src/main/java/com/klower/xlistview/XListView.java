/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * 		Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */

package com.klower.xlistview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.klower.R;
import com.klower.utilities.Utils;

public class XListView extends ListView implements OnScrollListener {

    private Context mContext;

    private float mLastY = -1; // save event y

    private Scroller mScroller; // used for scroll back

    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private IXListViewListener mListViewListener;

    // -- header view
    private XListViewHeader mHeaderView;

    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;

    private TextView mHeaderTimeView, mHeaderTextView, mFooterTextView;

    private int mHeaderViewHeight; // header view's height

    private boolean mEnablePullRefresh = true;

    private boolean mPullRefreshing = false; // is refreashing.

    // -- footer view
    private XListViewFooter mFooterView;

    private RelativeLayout mFooterViewContent;

    private boolean mEnablePullLoad;

    private boolean mPullLoading;

    private boolean mIsFooterReady = false;

    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;

    private final static int SCROLLBACK_HEADER = 0;

    private final static int SCROLLBACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 400; // scroll back duration

    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    // at bottom, trigger
    // load more.

    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    // feature.

    int mAutoDistance = 0;

    private boolean isCanRefresh = true;

    private GestureDetector mGestureDetector;

    OnTouchListener mGestureListener;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 1: {
                    int deltaY = (Integer)msg.obj;
                    updateHeaderHeight(deltaY /

                            OFFSET_RADIO);
                    invokeOnScrolling();
                    break;
                }

                case 2: {
                    // mEnablePullRefresh = true;
                    // XListView.this.enablePullLoadEnable();
                    if (mEnablePullRefresh) {
                        mPullRefreshing = true;
                        mHeaderView.setStateRefresh();
                        if (mListViewListener != null) {
                            if (isCanRefresh) {
                                mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                                mListViewListener.onRefresh();
                            }
                            // stopRefresh();
                        }
                    }
                    // resetHeaderHeight();
                    mAutoDistance = 0;
                }
                postInvalidate();
            }
        }
    };

    /**
     * @param context
     */
    public XListView(Context context) {
        super(context);
        mContext = context;
        mGestureDetector = new GestureDetector(new YScrollDetector());
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mGestureDetector = new GestureDetector(new YScrollDetector());
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mGestureDetector = new GestureDetector(new YScrollDetector());
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);

        // init header view
        mHeaderView = new XListViewHeader(context);
        mHeaderViewContent = (RelativeLayout)mHeaderView
                .findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView)mHeaderView.findViewById(R.id.xlistview_header_time);
        mHeaderTextView = (TextView)mHeaderView.findViewById(R.id.xlistview_header_hint_textview);

        addHeaderView(mHeaderView);

        // init footer view
        mFooterView = new XListViewFooter(context);
        mFooterViewContent = (RelativeLayout)mFooterView
                .findViewById(R.id.xlistview_footer_content);
        mFooterTextView = (TextView)mFooterView.findViewById(R.id.xlistview_footer_hint_textview);
        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                mHeaderViewHeight = mHeaderViewContent.getHeight();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }


    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterViewContent.setVisibility(View.INVISIBLE);
            mFooterView.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mFooterViewContent.setVisibility(View.VISIBLE);
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void disablePullLoadEnable() {
        mEnablePullLoad = false;
        if (!mEnablePullLoad) {
            mFooterViewContent.setVisibility(View.VISIBLE);
            mFooterView.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mFooterViewContent.setVisibility(View.VISIBLE);
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void enablePullLoadEnable() {
        mEnablePullLoad = true;
        if (!mEnablePullLoad) {
            mFooterViewContent.setVisibility(View.VISIBLE);
            mFooterView.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mFooterViewContent.setVisibility(View.VISIBLE);
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
            mHeaderView.setState(XListViewFooter.STATE_NORMAL);
        }
        postInvalidate();
    }

    public void startRefresh() {
        updateHeaderHeight(Utils.getDPValue(mContext, 55));
        invokeOnScrolling();
        postInvalidate();
        this.postDelayed(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mHandler.sendEmptyMessage(2);
            }

        }, 100);

        // Thread refreshThread = new Thread() {
        // boolean isStop = false;
        //
        // @Override
        // public void run() {
        // super.run();
        // while (!isStop) {
        // if (mAutoDistance == 30) {
        // isStop = true;
        // mHandler.sendEmptyMessage(2);
        // } else {
        // Message msg = new Message();
        // msg.what = 1;
        // msg.obj = mAutoDistance;
        // mHandler.sendMessage(msg);
        // }
        // mAutoDistance++;
        // try {
        // sleep(48);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }
        // }
        // };
        // refreshThread.start();
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * set last refresh time
     *
     * @param time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setText(time);
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener)mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int)delta + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) {
            // Not in refresh , update arrow
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(XListViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int)delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                // more.
                mFooterView.setState(XListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);

        if(mTotalItemCount > 0) {
            setSelection(mTotalItemCount - 1); // scroll to bottom
        }
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mFooterView.setState(XListViewFooter.STATE_LOADING);
        if (mListViewListener != null && mPullLoading == false) {
            mListViewListener.onLoadMore();
        }
        mPullLoading = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        isCanRefresh = true;
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0
                        && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1
                        && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void setXListViewListener(IXListViewListener l) {
        mListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {
        public void onXScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface IXListViewListener {
        public void onRefresh();

        public void onLoadMore();
    }

    public void setIsRefresh(boolean isRefresh) {
        isCanRefresh = isRefresh;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        return mGestureDetector.onTouchEvent(ev);
    }

    public XListViewFooter operateFoot(){
        return this.mFooterView;
    }

    public String getHeaderText() {
        return mHeaderTextView.getText().toString();
    }

    public void setHeaderText(String text) {
        this.mHeaderTextView.setText(text);
    }

    public String getFooterText() {
        return mFooterTextView.getText().toString();
    }

    public void setFooterText(String text) {
        this.mFooterTextView.setText(text);
    }

    class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;

        }
    }
}
