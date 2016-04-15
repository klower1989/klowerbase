package com.klowerbase.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.klower.model.ImageInfo;
import com.klower.pla.lib.internal.PLABaseXListView;
import com.klower.pla.lib.internal.PLABaseXListView.IXListViewListener;
import com.klower.pla.lib.internal.PLA_AdapterView;
import com.klower.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.klower.pla.lib.internal.ScaleImageView;
import com.klower.utilities.LogTool;
import com.klower.utilities.Utils;
import com.klowerbase.test.helper.HttpurlConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

public class PullGridviewActivity extends Activity implements
		IXListViewListener, OnItemClickListener {
	private PLABaseXListView mListView;
	protected ImageLoader imageLoader;

	protected DisplayImageOptions options;

	private int width;

	private ImageAdapter mAdapter;

	private int pageIndex = 1;

//	private AsyncHttpClient mAsyncHttpClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LogTool.d("--", "url: " + HttpurlConstants.appendUrl("美女", "可爱", 0, 10));

//		mAsyncHttpClient = new AsyncHttpClient();
		width = Utils.getScreenWidth(this) / 2;
		imageLoader = ImageLoader.getInstance();
		options = Utils.getNormalDisplayImageOptions(R.drawable.empty_photo);
		setContentView(R.layout.act_pull_to_refresh_sample);
		mListView = (PLABaseXListView) findViewById(R.id.list);
		mAdapter = new ImageAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(this);
		mListView.startRefresh();
	}

	public String getWidthAndHeight() {
		String str = "";
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int displayWidth = dm.widthPixels;
		int displayHeight = dm.heightPixels;
		return "width=" + displayWidth + "&height=" + displayHeight;
	}

	public static DisplayImageOptions getNormalDisplayImageOptions(int photo) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = 2;
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(photo).showImageOnFail(photo)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Config.RGB_565).cacheInMemory(true)
				.decodingOptions(opt).cacheOnDisc(true)
				.resetViewBeforeLoading(false).considerExifParams(true)
				.showImageOnLoading(photo)
				// .displayer(new RoundedBitmapDisplayer(20))
				.build();
		return options;
	}

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(PullGridviewActivity.this,
				GestureImageviewActivity.class);
		intent.putExtra("url", mAdapter.getData().get(position - 1)
				.getImage_url());
		startActivity(intent);

	}

	@Override
	public void onRefresh() {
		pageIndex = 1;
//		getImagesRequest();
	}

	@Override
	public void onLoadMore() {
		pageIndex = mAdapter.getCount();
//		getImagesRequest();
	}

//	private void getImagesRequest() {
//		mAsyncHttpClient.get(this, HttpurlConstants.getMusicUrl("同桌的你"), null,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int statusCode, String content) {
//						// TODO Auto-generated method stub
//						super.onSuccess(statusCode, content);
//						try {
//							JSONObject jsonObject = new JSONObject(content);
//							String ss = jsonObject.toString();
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//
//					@Override
//					public void onFailure(Throwable error, String content) {
//						// TODO Auto-generated method stub
//						super.onFailure(error, content);
//					}
//				});
//		// 1733789 动漫， 1733798 衣服， 1733791抽象，1733796文具，1733797家居
//		// "http://www.duitang.com/album/1733798/masn/p/1/10"
//		// String url = "http://www.duitang.com/album/1733798/masn/p/" +
//		// pageIndex
//		// + "/6/";
//
//		String url = HttpurlConstants.appendUrl("美女", "性感", pageIndex, 10);
//		mAsyncHttpClient.get(this, url, new AsyncHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode, String content) {
//				mListView.stopRefresh();
//				mListView.stopLoadMore();
//				// JSONObject jsonObject = new JSONObject(content);
//				ImagePayload imagePayload = new Gson().fromJson(content,
//						ImagePayload.class);
//				if (pageIndex == 1) {
//					mAdapter.setData(imagePayload.getData());
//				} else {
//					mAdapter.addData(imagePayload.getData());
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable error) {
//				// TODO Auto-generated method stub
//				super.onFailure(error);
//				mListView.stopRefresh();
//				mListView.stopLoadMore();
//			}
//		});
//	}

	class ImageAdapter extends BaseAdapter {

		ArrayList<ImageInfo> items = new ArrayList<ImageInfo>();

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int arg0) {
			return items.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ViewHolder holder;
			if (view == null) {
				holder = new ViewHolder();
				view = LayoutInflater.from(PullGridviewActivity.this).inflate(
						R.layout.infos_list, null);
				holder.mImageView = (ScaleImageView) view
						.findViewById(R.id.job_item_photo);
				holder.tx = (TextView) view.findViewById(R.id.text);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			imageLoader.displayImage(items.get(position).getThumbnail_url(),
					holder.mImageView, options);
			holder.mImageView.setImageWidth(items.get(position).getImage_width());
			holder.mImageView.setImageHeight(items.get(position).getImage_height());
//			holder.mImageView.setImageWidth(Utils.getScreenWidth(PullGridviewActivity.this)/3);
//			holder.mImageView.setImageHeight(Utils.getScreenWidth(PullGridviewActivity.this)/3);
			holder.tx.setText(items.get(position).getAbs());
			return view;
		}

		class ViewHolder {
			TextView tx;
			ScaleImageView mImageView;
		}

		private void setData(ArrayList<ImageInfo> imgs) {
			items.clear();
			items.addAll(imgs);
			notifyDataSetChanged();
		}

		private void addData(ArrayList<ImageInfo> imgs) {
			items.addAll(imgs);
			notifyDataSetChanged();
		}

		private ArrayList<ImageInfo> getData() {
			return this.items;
		}
	}

	
	
	
}
