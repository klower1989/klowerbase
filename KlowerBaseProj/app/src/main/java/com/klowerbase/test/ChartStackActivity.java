package com.klowerbase.test;

import android.os.Bundle;

import com.klower.titlebar.BaseException;

public class ChartStackActivity extends ActionBarActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
//		initChart();
	}

//	private void initChart() {
//		String[] titles = new String[] { "2008", "2007" };// 图例
//		List<double[]> values = new ArrayList<double[]>();
//		values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
//				22030, 21200, 19500, 15500, 12600, 14000 });// 第一种柱子的数值
//		values.add(new double[] { 5230, 7300, 9240, 10540, 7900, 9200, 12030,
//				11200, 9500, 10500, 11600, 13500 });// 第二中柱子的数值
//		int[] colors = new int[] { Color.BLUE, Color.CYAN };// 两种柱子的颜色
//
//		// 调用AbstractDemoChart中的方法构建renderer.
//		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
//		SimpleSeriesRenderer r;
//		for (int i = 0; i < colors.length; i++) {
//			r = new SimpleSeriesRenderer();
//			r.setColor(colors[i]); // 定义柱状图的颜色
//			renderer.addSeriesRenderer(r);
//		}
//
//		// renderer.setChartTitle("个人收支表");// 设置柱图名称
//		renderer.setXTitle("月份");// 设置X轴名称
//		renderer.setYTitle("收益");// 设置Y轴名称
//
////		// 设置柱子上是否显示数量值
////		renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
////		// 设置柱子上是否显示数量值
////		renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
//		renderer.setDisplayChartValues(true);//
//
//		// 自动增长的X值
//		// renderer.setXAxisMin(0.5);// 设置X轴的最小值为0.5
//		// renderer.setXAxisMax(12.5);// 设置X轴的最大值为
//		// renderer.setXLabels(12);// 设置X轴显示的刻度标签的个数
//
//		// 如果想要在X轴显示自定义的标签，那么首先要设置renderer.setXLabels(0);
//		// 如果不设置为0，那么所设置的Labels会与原X坐标轴labels重叠
//		renderer.setXLabels(0);
//		for (int i = 0; i < 13; i++) {
//			renderer.addXTextLabel(i, i + "月");
//		}
//
//		// 设定坐标轴上标题的字体大小
//		renderer.setAxisTitleTextSize(30);
//		renderer.setYAxisMin(0);// 设置Y轴的最小值为0
//		renderer.setYAxisMax(24000);// 设置Y轴最大值为
//		// renderer.setDisplayChartValues(true); // 设置是否在柱体上方显示值
//		renderer.setShowGrid(true);// 设置是否在图表中显示网格
//		renderer.setGridColor(Color.GREEN);
//		// 设置底部说明文字字体大小
//		renderer.setShowLegend(false);
//		renderer.setLegendTextSize(30);
////		renderer.setLegendHeight(1);
////		renderer.setFitLegend(true);// 调整合适的位置
//		// 设定刻度的字体大小
//		renderer.setLabelsTextSize(30);
//		// 设定柱子上数值字体大小
//		renderer.setChartValuesTextSize(25);
//		renderer.setBarSpacing(0.5); // 柱状间的间隔
//		renderer.setZoomButtonsVisible(false);
//		renderer.setPanEnabled(true, false);// 允许左右拖动,但不允许上下拖动.
//		// Type: Type.DEFAULT柱子会分开显示 Type.STACKED柱子会重叠显示
//		View view = ChartFactory.getBarChartView(this,
//				getBarDataset(titles, values), renderer, Type.DEFAULT); // Type.STACKED
//		view.setBackgroundColor(Color.BLACK);
//		setContentView(view);
//
//	}
//
//	private XYMultipleSeriesDataset getBarDataset(String[] titles,
//			List<double[]> values) {
//
//		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
//		CategorySeries series = null;
//		for (int i = 0; i < titles.length; i++) {
//			series = new CategorySeries(titles[i]);
//			double[] v = values.get(i);
//			for (int j = 0; j < v.length; j++) {
//				series.add(v[j]);
//			}
//			dataset.addSeries(series.toXYSeries());// 添加该柱形图到数据设置列表
//		}
//		return dataset;
//	}
}
