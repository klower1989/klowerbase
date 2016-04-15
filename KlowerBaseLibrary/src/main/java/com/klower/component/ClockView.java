package com.klower.component;

import com.klower.utilities.LogTool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ClockView extends View {

	private Paint mPaint;

	private int height;

	private int width;

	private int ridus;

	public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public ClockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ClockView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int deta = 100;
		height = getHeight();
		width = getWidth();
		ridus = (width - deta) / 2;
		int centerX = height / 2;
		int centerY = width / 2;

		mPaint = new Paint();
		mPaint.setAntiAlias(false);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(Color.BLACK);

		canvas.drawCircle(centerX, centerY, ridus, mPaint);

		mPaint.setStyle(Style.FILL);
		canvas.drawCircle(centerX, centerY, 10, mPaint);
		mPaint.setTextSize(30);
		// canvas.drawText("1", 150 + 300, 300 - 260, mPaint);

		Path path;
		for (int i = 0; i < 12; i++) {

			path = new Path();
			RectF rectF = new RectF(40, 40, getWidth() - 40, getHeight() - 40);
			path.arcTo(rectF, -60 + 30 * i, 30);
			canvas.drawTextOnPath("" + (i + 1), path, 0, 0, mPaint);
//			LogTool.d("onDraw", "Math.cos(- Math.PI/3 + i * Math.PI/6) : "
//					+ Math.cos(-Math.PI / 3 + i * Math.PI / 6));
//			LogTool.d(
//					"onDraw",
//					"Math.sin(-Math.PI/3 + i * Math.PI/6): "
//							+ Math.sin(-Math.PI / 3 + i * Math.PI / 6));
//			int x = height / 2
//					+ (int) (Math.cos(-Math.PI / 3 + i * Math.PI / 6) * ridus) + (int) (Math.cos(-Math.PI / 3 + i * Math.PI / 6)*50);
//			int y = height / 2
//					+ (int) (Math.sin(-Math.PI / 3 + i * Math.PI / 6) * ridus)+ (int) (Math.sin(-Math.PI / 3 + i * Math.PI / 6) * 30);
//			LogTool.d("onDraw", "x: " + x + " y: " + y);
//			canvas.drawText("" + (i + 1), x, y, mPaint);

		}
	}

}
