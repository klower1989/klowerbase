package com.klowerbase.test;

import java.util.ArrayList;
import java.util.List;



import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;

import com.klower.titlebar.BaseException;

public class ChartLineActivity extends ActionBarActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
//		initChart();
	}

//	private void initChart() {
//		// 图标的指示部分 图例
//		String[] titles = new String[] { "Crete", "Corfu", "Thassos",
//				"Skiathos" };// 图例
//
//		// 每条曲线对应的X 坐标
//		List<double[]> x = new ArrayList<double[]>();
//		for (int i = 0; i < titles.length; i++) {
//			x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
//		}
//
//		// 每条曲线对应的Y 坐标
//		List<double[]> values = new ArrayList<double[]>();
//		values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4,
//				26.1, 23.6, 20.3, 17.2, 13.9 });// 序列1中点的y坐标
//		values.add(new double[] { 10, 10, 12, 15, 20, 24, 26, 26, 23, 18, 14,
//				11 });// 序列2中点的Y坐标
//		values.add(new double[] { 5, 5.3, 8, 12, 17, 22, 24.2, 24, 19, 15, 9, 6 });// 序列3中点的Y坐标
//		values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });// 序列4中点的Y坐标
//
//		// 每条曲线对应的颜色值
//		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN,
//				Color.YELLOW };// 每个序列的颜色设置
//
//		// 每条曲线 对应的点的形状
//		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
//				PointStyle.DIAMOND, PointStyle.TRIANGLE, PointStyle.SQUARE };// 每个序列中点的形状设置
//
//		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);// 调用AbstractDemoChart中的方法设置renderer.
//		int length = renderer.getSeriesRendererCount();
//		for (int i = 0; i < length; i++) {
//			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
//					.setFillPoints(true);// 设置图上的点为实心
//		}
//
//		// 调用AbstractDemoChart中的方法设置图表的renderer属性.
//		setChartSettings(renderer, "Average temperature", "Month",
//				"Temperature", 0.5, 12.5, -10, 40, Color.LTGRAY, Color.LTGRAY);
//		// 设置x轴显示12个点,根据setChartSettings的最大值和最小值自动计算点的间隔
//		renderer.setXLabels(12);
//		// 设置y轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔
//		renderer.setYLabels(7);
//		// 是否显示网格
//		renderer.setShowGrid(true);
//		// 刻度线与刻度标注之间的相对位置关系
//		renderer.setXLabelsAlign(Align.RIGHT);
//		// 刻度线与刻度标注之间的相对位置关系
//		renderer.setYLabelsAlign(Align.RIGHT);
//		// 是否显示放大缩小按钮
//		renderer.setZoomButtonsVisible(true);
//		// 设置拖动时X轴Y轴允许的最大值最小值.
//		renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
//		// 设置放大缩小时X轴Y轴允许的最大最小值.
//		renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
//		//消除锯齿
//		renderer.setAntialiasing(true);
//
//		View view = ChartFactory.getLineChartView(this,
//				buildDataset(titles, x, values), renderer);
//		view.setBackgroundColor(Color.BLACK);
//		setCustomContentView(view);
//
//	}
//
//	private XYMultipleSeriesRenderer buildRenderer(int[] colors,
//			PointStyle[] styles) {
//		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
//
//		// 设定坐标轴上标题的字体大小
//		renderer.setAxisTitleTextSize(20);
//		// 设定标题及字体大小
//		renderer.setChartTitle("Chart Line");
//		renderer.setChartTitleTextSize(20);
//		// 设定刻度X Y label的字体大小
//		renderer.setLabelsTextSize(20); // 标注字
//		// 设置底部说明文字字体大小
//		renderer.setLegendTextSize(20);
//		// 设定曲线上每个点的大小
//		renderer.setPointSize(10f);
//		//设置图表的外边框(上/左/下/右)
//		renderer.setMargins(new int[] { 20, 30, 50, 20 });
////		renderer.setFitLegend(true);// 调整合适的位置
//
//		int length = colors.length;
//		for (int i = 0; i < length; i++) {
//			XYSeriesRenderer r = new XYSeriesRenderer();
//			r.setColor(colors[i]);
//			r.setPointStyle(styles[i]);
//			renderer.addSeriesRenderer(r);
//		}
//		// setRenderer(renderer, colors, styles);
//		return renderer;
//	}
//
////	private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
////			PointStyle[] styles) {
////		// 设定Y轴上标题的字体大小
////		renderer.setAxisTitleTextSize(20);
////		// 设定标题及字体大小
////		renderer.setChartTitle("Chart Line");
////		renderer.setChartTitleTextSize(20);
////		// 设定刻度X Y label的字体大小
////		renderer.setLabelsTextSize(20); // 标注字
////		// 设置底部说明文字字体大小
////		renderer.setLegendTextSize(20);
////		// 设定曲线上每个点的大小
////		renderer.setPointSize(10f);
////		renderer.setMargins(new int[] { 20, 30, 15, 20 });
////		int length = colors.length;
////		for (int i = 0; i < length; i++) {
////			XYSeriesRenderer r = new XYSeriesRenderer();
////			r.setColor(colors[i]);
////			r.setPointStyle(styles[i]);
////			renderer.addSeriesRenderer(r);
////		}
////	}
//
//	private void setChartSettings(XYMultipleSeriesRenderer renderer,
//			String title, String xTitle, String yTitle, double xMin,
//			double xMax, double yMin, double yMax, int axesColor,
//			int labelsColor) {
//
//		renderer.setYTitle("数量");// 设置Y轴名称
//
//		renderer.setXAxisMin(xMin);// 设置X轴的最小值
//
//		renderer.setXAxisMax(xMax);// 设置X轴的最大值
//
//		renderer.setYAxisMin(yMin);// 设置Y轴的最小值
//
//		renderer.setYAxisMax(yMax);// 设置Y轴最大值
//
//		// renderer.setDisplayChartValues(true); // 设置是否在柱体上方显示值
//		renderer.setBarSpacing(1);
//
//		// int tempNum = 1;
//		// for (Information information : list)
//		// {
//		// renderer.addXTextLabel(tempNum,information.weidu);
//		// tempNum++;
//		// }
//
//	}
//
//	private XYMultipleSeriesDataset buildDataset(String[] titles,
//			List<double[]> xValues, List<double[]> yValues) {
//		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
//		addXYSeries(dataset, titles, xValues, yValues, 0);
//		return dataset;
//	}
//
//	private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
//			List<double[]> xValues, List<double[]> yValues, int scale) {
//		int length = titles.length;
//		for (int i = 0; i < length; i++) {
//			XYSeries series = new XYSeries(titles[i], scale);
//			double[] xV = xValues.get(i);
//			double[] yV = yValues.get(i);
//			int seriesLength = xV.length;
//			for (int k = 0; k < seriesLength; k++) {
//				series.add(xV[k], yV[k]);
//			}
//			dataset.addSeries(series);
//		}
//	}
}
