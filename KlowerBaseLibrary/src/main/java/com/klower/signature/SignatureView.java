/**
 * All rights reserved
 */
package com.klower.signature;

import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Path.Direction;
import android.view.MotionEvent;
import android.view.View;

/**
 * Please describe
 * 
 * @version 1.0
 * @createDate 2012-7-16
 */
public class SignatureView extends View {
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;
	boolean isSigned = false;
	
	int mPaintcolor;

	public SignatureView(Context c, int paintcolor) {
		super(c);

		mPath = new Path();
		this.mPaintcolor = paintcolor;
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		mBitmapPaint.setAntiAlias(true);
		// mPaint = new Paint();
		mBitmapPaint.setDither(true);
//		mBitmapPaint.setColor(Color.BLACK);
		mBitmapPaint.setColor(mPaintcolor);
		mBitmapPaint.setStyle(Paint.Style.STROKE);
		mBitmapPaint.setStrokeJoin(Paint.Join.ROUND);
		mBitmapPaint.setStrokeCap(Paint.Cap.ROUND);
		// mPaint.setTypeface(Typeface.SANS_SERIF);
		mBitmapPaint.setStrokeWidth(4);
	}
	
	

	
	public int getmPaintcolor() {
		return mPaintcolor;
	}




	public void setmPaintcolor(int mPaintcolor) {
		this.mPaintcolor = mPaintcolor;
	}




	public boolean isSigned() {
		return isSigned;
	}


	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
	}

	public void Undo() {
		mCanvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xFFFFFFFF);
		// canvas.drawColor(Color.TRANSPARENT);

		mBitmapPaint.setColor(mPaintcolor);
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

		canvas.drawPath(mPath, mBitmapPaint);
	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	private long touchdowntime = 0;
	private long touchuptime = 0;
	private boolean moveflag = false;

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
		moveflag = true;
	}

	private void touch_up(float x, float y) {

		if (mX == x && mY == y) {
			long interval = (touchuptime - touchdowntime);
			if (interval > 50 && !moveflag)
				mPath.addCircle(x, y, 1.0f, Direction.CW);
		} else
			mPath.lineTo(mX, mY);

		moveflag = false;
		// commit the path to our offscreen
		mBitmapPaint.setColor(mPaintcolor);
		mCanvas.drawPath(mPath, mBitmapPaint);
		// kill this so we don't double draw
		mPath.reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchdowntime = new Date().getTime();
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			isSigned = true;
			break;
		case MotionEvent.ACTION_UP:
			touchuptime = new Date().getTime();
			touch_up(x, y);
			invalidate();
			break;
		}
		return true;
	}
}
