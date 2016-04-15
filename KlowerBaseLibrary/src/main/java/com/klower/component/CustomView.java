package com.klower.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {

	Paint mPaint;

	public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CustomView(Context context) {
		super(context);
	}

	private void init(Context context, AttributeSet attrs) {
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setAntiAlias(false);
		// 绘制一个空心矩形
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Style.STROKE);
		canvas.drawRect(5, 5, getWidth() - 5, getHeight() - 5, mPaint);
		
		Path path3 = new Path();
		path3.moveTo(0, getHeight() / 2);
		path3.lineTo(getWidth(), getHeight() / 2);
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		canvas.drawPath(path3, mPaint);

		Path path4 = new Path();
		path4.moveTo(getWidth() / 2, 0);
		path4.lineTo(getWidth() / 2, getHeight());
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		canvas.drawPath(path4, mPaint);

		// 绘制一个实心圆
		mPaint.setColor(Color.parseColor("#50000000"));
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Style.FILL);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, 170, mPaint);

		// 绘制一个空心圆 做边框
		mPaint.setColor(Color.parseColor("#000000"));
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Style.STROKE);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, 170, mPaint);

		// 绘制一个实心圆
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Style.FILL);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, 150, mPaint);

		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(10);
		mPaint.setStyle(Style.STROKE);

		Path path = new Path();
		// 从哪个点开始
		path.moveTo(getWidth() / 2 - 150, getHeight() / 2);
		// 1、2 两个参数表示控制点 也就是顶点 3、4 两个参数为结束点
		path.quadTo(getWidth() / 2, getHeight() / 2 - 150,
				getWidth() / 2 + 150, getHeight() / 2);
		// path.close();
		canvas.drawPath(path, mPaint);

		mPaint.setStyle(Style.FILL);
		mPaint.setTextSize(30);
		// 根据路径绘制文本
		canvas.drawTextOnPath("Hello World", path, 150 / 2, -5, mPaint);

		// 绘制弧线
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Style.FILL);
		Path path2 = new Path();
		RectF rectF = new RectF(20, 20, getWidth() - 20, getHeight() - 20);
		path2.arcTo(rectF,180, 90);
		canvas.drawPath(path2, mPaint);

	}

}
