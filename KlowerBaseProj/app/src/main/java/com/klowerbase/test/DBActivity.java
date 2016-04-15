package com.klowerbase.test;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.klower.db.FilterCondition;
import com.klower.db.FilterCondition.WhereCondition;
import com.klower.model.UserInfo;
import com.klower.titlebar.BaseException;
import com.klowerbase.test.helper.UserInfoDao;

public class DBActivity extends ActionBarActivity implements OnClickListener {

	TextView result;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.db);

		findViewById(R.id.insert).setOnClickListener(this);
		findViewById(R.id.update).setOnClickListener(this);
		findViewById(R.id.delete).setOnClickListener(this);
		findViewById(R.id.quary).setOnClickListener(this);
		findViewById(R.id.quary_page).setOnClickListener(this);
		result = (TextView) findViewById(R.id.show);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.insert:
			insert();
			break;
		case R.id.update:
			update();
			break;
		case R.id.delete:
			delete();
			break;
		case R.id.quary:
			quaryAll();
			break;
		case R.id.quary_page:
			uquaryPage();
			break;
		}
	}

	private void insert() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_name("Klower");
		userInfo.setUser_sex("男");
		userInfo.setUser_age(26);
		userInfo.setUser_height(180);
		userInfo.setUser_password("123456");
		userInfo.setTime(System.currentTimeMillis());
		UserInfoDao userInfoDao = new UserInfoDao(this);
		try {
			userInfoDao.save(userInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void quaryAll() {
		String str = "";
		UserInfoDao userInfoDao = new UserInfoDao(this);
		try {
			List<UserInfo> infos = userInfoDao.queryAll();
			if (infos != null && infos.size() > 0) {
				for (int i = 0; i < infos.size(); i++) {
					UserInfo info = infos.get(i);
					str = str + info.getUser_name() + " " + info.getUser_age()
							+ " " + info.getUser_height() + " "
							+ info.getTime() + "\n";
				}
				result.setText(str);
			} else {
				toast("No result!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void uquaryPage() {
		// 先根据条件查询
		FilterCondition filterCondition = new FilterCondition();
		filterCondition.addCondition(
				WhereCondition.newEqIntance("user_name", "Klower"))
				.addCondition(WhereCondition.newEqIntance("user_sex", "男"));
		filterCondition.setPageIndex(0);
		filterCondition.setLimit(5l);
		UserInfoDao dao = new UserInfoDao(this);
		String str = "";
		try {
			List<UserInfo> infos = dao.query(filterCondition);
			if (infos != null && infos.size() > 0) {
				for (int i = 0; i < infos.size(); i++) {
					UserInfo info = infos.get(i);
					str = str + info.getUser_name() + " " + info.getUser_age()
							+ " " + info.getUser_height() + " "
							+ info.getTime() + "\n";
				}
				result.setText(str);
			} else {
				toast("No result!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void update() {
		// 先根据条件查询
		FilterCondition filterCondition = new FilterCondition();
		filterCondition.addCondition(
				WhereCondition.newEqIntance("user_name", "Klower"))
				.addCondition(WhereCondition.newEqIntance("user_sex", "男"));
		UserInfoDao dao = new UserInfoDao(this);
		try {
			List<UserInfo> infos = dao.query(filterCondition);
			if (infos != null && infos.size() > 0) {
				// 修改
				UserInfo info = infos.get(0);
				info.setUser_age(27);
				dao.update(info);
			} else {
				toast("No result!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void delete() {
		// 先根据条件查询
		FilterCondition filterCondition = new FilterCondition();
		filterCondition.addCondition(
				WhereCondition.newEqIntance("user_name", "Klower"))
				.addCondition(WhereCondition.newEqIntance("user_age", "27"));
		UserInfoDao dao = new UserInfoDao(this);
		try {
			dao.delete(filterCondition);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
