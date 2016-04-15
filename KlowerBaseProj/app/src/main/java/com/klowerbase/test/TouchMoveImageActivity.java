package com.klowerbase.test;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.klower.titlebar.BaseException;

public class TouchMoveImageActivity extends ActionBarActivity {
	private int screenWidth;
	private int screenHeight;
	int lastX, lastY;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.touch);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels - 50;
		final ImageView imageView = (ImageView) findViewById(R.id.touch_img);
		 imageView.setOnTouchListener(new OnTouchListener() {
		
		
		 @Override
		 public boolean onTouch(View v, MotionEvent event) {
		 switch (event.getAction()) {
		 case MotionEvent.ACTION_DOWN:
		 lastX = (int) event.getRawX();
		 lastY = (int) event.getRawY();
		 break;
		 case MotionEvent.ACTION_MOVE:
		 int dx = (int) event.getRawX() - lastX;
		 int dy = (int) event.getRawY() - lastY;
		
		 int left = imageView.getLeft() + dx;
		 int right = imageView.getRight()+ dx;
		 int top = imageView.getTop() + dy;
		 int bottom = imageView.getBottom()+ dy;
		 // 设置不能出界
		 if (left < 0) {
		 left = 0;
		 right = left + v.getWidth();
		 }
		
		 if (right > screenWidth) {
		 right = screenWidth;
		 left = right - v.getWidth();
		 }
		
		 if (top < 0) {
		 top = 0;
		 bottom = top + v.getHeight();
		 }
		
		 if (bottom > screenHeight) {
		 bottom = screenHeight;
		 top = bottom - v.getHeight();
		 }
		
		 v.layout((int)left, (int)top, (int)right, (int)bottom);
		
		 lastX = (int) event.getRawX();
		 lastY = (int) event.getRawY();
		 break;
		 case MotionEvent.ACTION_UP:
		 break;
		 }
		 return true;
		 }
		 });

//		imageView.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					lastX = (int) event.getX();
//					lastY = (int) event.getY();
//					break;
//				case MotionEvent.ACTION_MOVE:
//					int dx = (int) event.getX();
//					int dy = (int) event.getY();
//					int mx = dx - lastX;
//					int my = dy - lastY;
//					if (Math.abs(mx) > 0 || Math.abs(my) > 0) {
//
//					} else {
//						return true;
//					}
//
//					int left = imageView.getLeft() + dx;
//					int right = imageView.getRight() + dx;
//					int top = imageView.getTop() + dy;
//					int bottom = imageView.getBottom() + dy;
//					// 设置不能出界
//					if (left < 0) {
//						left = 0;
//						right = left + v.getWidth();
//					}
//
//					if (right > screenWidth) {
//						right = screenWidth;
//						left = right - v.getWidth();
//					}
//
//					if (top < 0) {
//						top = 0;
//						bottom = top + v.getHeight();
//					}
//
//					if (bottom > screenHeight) {
//						bottom = screenHeight;
//						top = bottom - v.getHeight();
//					}
//
//					v.layout((int) left, (int) top, (int) right, (int) bottom);
//
//					lastX = (int) event.getRawX();
//					lastY = (int) event.getRawY();
//					break;
//				case MotionEvent.ACTION_UP:
//					break;
//				}
//				return true;
//			}
//		});
	}
}
