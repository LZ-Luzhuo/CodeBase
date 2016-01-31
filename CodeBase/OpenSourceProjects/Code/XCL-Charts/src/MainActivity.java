package com.example.chart;

import java.util.ArrayList;
import java.util.Random;

import org.xclcharts.chart.PieData;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.chart.bean.DataCount;
import com.example.chart.utils.Utils;
import com.example.chart.widget.piechart.PieChartView;
import com.example.chart.widget.piechart.PieChartView.OnClickItem;

public class MainActivity extends Activity {

	private View menu_statistics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		setContentView(menu_statistics);
	}

	private void initView() {
		menu_statistics = View.inflate(this, R.layout.menu_dialog_statistics, null);
		PieChartView piechart_statistics = (PieChartView) menu_statistics.findViewById(R.id.piechart_statistics);
		TextView hint_statistics = (TextView) menu_statistics.findViewById(R.id.hint_statistics);
		
		// 初始化圆饼数据
		initPieChartData(piechart_statistics,hint_statistics);
	}

	private void initPieChartData(PieChartView piechart_statistics, final TextView hint_statistics) {
		// 饼图数据
		ArrayList<PieData> chartData = new ArrayList<PieData>();
		// 统计数据
		final ArrayList<DataCount> dataCount = Utils.getDataCount();
		// 总数据量
		final int sum = 100;
		
		// 设置PieChartView
		hint_statistics.setText("全部数据总量:".concat(String.valueOf(sum).concat("条")));
		int coclorssum = PieChartView.cocors.length;
		int residue = coclorssum;
		Random random = new Random();
		//设置图表数据源	
		for (DataCount data : dataCount) {
			if(residue>0){
				chartData.add(new PieData(data.classify,"", ((float)data.count/(float)sum)*100f,PieChartView.cocors[residue-1]));
			}else{
				// 如果颜色不够,随机取一种颜色
				int nextInt = random.nextInt(coclorssum);
				chartData.add(new PieData(data.classify,"", ((float)data.count/(float)sum)*100f,PieChartView.cocors[nextInt]));
			}
			residue --;
		}
		piechart_statistics.setData(chartData);
		
		// 饼页点击监听
		piechart_statistics.setOnClickItemLinstener(new OnClickItem() {
			@Override
			public void onClick(int position, boolean isOpen) {
				if(isOpen){
					DataCount count = dataCount.get(position);
					hint_statistics.setText(count.classify+": "+count.count+"条");
				}else{
					hint_statistics.setText("全部数据总量:".concat(String.valueOf(sum).concat("条")));
				}
			}
		});
		piechart_statistics.show();
	}
}
