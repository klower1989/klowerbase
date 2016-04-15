package com.klowerbase.test;

import android.os.Bundle;

import com.klower.titlebar.BaseException;

public class ChartPieActivity extends ActionBarActivity {

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
//		initChart();
	}

//	private void initChart() {
//		// 饼图分层5块,每块代表的数值
//		double[] values = new double[] { 12, 14, 11, 10, 19 };
//		// 每块饼图的颜色
//		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA,
//				Color.YELLOW, Color.CYAN };
//		DefaultRenderer renderer = new DefaultRenderer();
//		
//		renderer.setLabelsTextSize(25);
//		// 设置底部说明文字字体大小
//		renderer.setLegendTextSize(40);
//		renderer.setMargins(new int[] { 50, 40, 30, 20 });
//		renderer.setChartTitle("Pie Chart");
//		for (int color : colors) {
//			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
//			r.setColor(color);
//			renderer.addSeriesRenderer(r);
//		}
//		// 设置显示放大缩小按钮
//		renderer.setZoomButtonsVisible(true);
//		// 设置允许放大缩小.
//		renderer.setZoomEnabled(true);
//		// 设置图表标题的文字大小
//		renderer.setChartTitleTextSize(25);
//		View view = ChartFactory.getPieChartView(this,
//				buildCategoryDataset("Project budget", values), renderer);
//		view.setBackgroundColor(Color.BLACK);
//
//		setContentView(view);
//	}
//
//	/**
//	 * 饼状图文字信息
//	 *
//	 * @param title
//	 * @param values
//	 * @return
//	 */
//	protected CategorySeries buildCategoryDataset(String title, double[] values
//			) {
//		CategorySeries series = new CategorySeries(title);
//		// 根据list值分配视图 颜色
//		for (int i = 0; i < values.length; i++) {
//			series.add("pie("+values[i]+")", values[i]);
//		}
//		return series;
//	}
}
