package com.klowerbase.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.os.Bundle;
import android.widget.ListView;

import com.klower.model.SortModel;
import com.klower.sort.CharacterParser;
import com.klower.sort.SideBar;
import com.klower.sort.SideBar.OnTouchingLetterChangedListener;
import com.klower.titlebar.BaseException;
import com.klowerbase.test.adapter.MMAdapter;

public class SortListActivity extends ActionBarActivity {

	ArrayList<SortModel> models = new ArrayList<SortModel>();

	CharacterParser mCharacterParser;
	SideBar mSideBar;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.sort);

		mCharacterParser = CharacterParser.getInstance();

		SortModel model1 = new SortModel();
		model1.setName("张三");
		SortModel model2 = new SortModel();
		model2.setName("李三");
		SortModel model3 = new SortModel();
		model3.setName("王三");
		SortModel model4 = new SortModel();
		model4.setName("aaa");
		SortModel model5 = new SortModel();
		model5.setName("张三");
		SortModel model6 = new SortModel();
		model6.setName("bbb");
		SortModel model7 = new SortModel();
		model7.setName("dddd");
		SortModel model8 = new SortModel();
		model8.setName("eeee");
		SortModel model9 = new SortModel();
		model9.setName("ffff");
		SortModel mode20 = new SortModel();
		mode20.setName("fsasd");
		SortModel mode21 = new SortModel();
		mode21.setName("asdasd");
		SortModel mode22 = new SortModel();
		mode22.setName("dasdasd");

		models.add(model1);
		models.add(model2);
		models.add(model3);
		models.add(model4);
		models.add(model5);
		models.add(model6);
		models.add(model7);
		models.add(model8);
		models.add(model9);
		models.add(mode20);
		models.add(mode21);
		models.add(mode22);

		final ListView listView = (ListView) findViewById(R.id.sort_listview);
		final MMAdapter adapter = new MMAdapter(this);
		listView.setAdapter(adapter);
		ArrayList<SortModel> newList = reSortList(models);
		Collections.sort(newList, new PinyinComparator());
		adapter.setData(newList);
		mSideBar = (SideBar) findViewById(R.id.sidrbar);
		mSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					listView.setSelection(position);
				}

			}

			@Override
			public void onTouchingLetterChange(String s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

	private ArrayList<SortModel> reSortList(ArrayList<SortModel> models) {
		ArrayList<SortModel> newModels = new ArrayList<SortModel>();
		for (int i = 0; i < models.size(); i++) {
			SortModel sortModel = models.get(i);
			String pinyin = mCharacterParser.getSelling(sortModel.getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			newModels.add(sortModel);
		}
		return newModels;
	}

	public class PinyinComparator implements Comparator<SortModel> {

		public int compare(SortModel o1, SortModel o2) {
			if (o1.getSortLetters().equals("@")
					|| o2.getSortLetters().equals("#")) {
				return -1;
			} else if (o1.getSortLetters().equals("#")
					|| o2.getSortLetters().equals("@")) {
				return 1;
			} else {
				return o1.getSortLetters().compareTo(o2.getSortLetters());
			}
		}
	}

}
