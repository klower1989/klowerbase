/**
 * All rights reserved
 */

package com.klower.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2013-7-5
 */
public class CustomScrollView extends ScrollView {
    private GestureDetector mGestureDetector;

    OnTouchListener mGestureListener;
    
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    @SuppressWarnings("deprecation")
    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mGestureDetector = new GestureDetector(new YScrollDetector());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                
                if(xDistance > yDistance){
                    return false;
                }  
        }

        return super.onInterceptTouchEvent(ev);
    }
    
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        super.onInterceptTouchEvent(ev);
//        return mGestureDetector.onTouchEvent(ev);
//    }
//
//    class YScrollDetector extends SimpleOnGestureListener {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            if (Math.abs(distanceY) > Math.abs(distanceX)) {
//                return true;
//            }
//            return false;
//
//        }
//    }

}
