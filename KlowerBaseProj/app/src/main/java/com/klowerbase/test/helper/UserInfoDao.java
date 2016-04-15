package com.klowerbase.test.helper;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.klower.model.UserInfo;

public class UserInfoDao extends BaseDao<UserInfo, Integer> {

	public UserInfoDao(Context context) {
		super(context);
	}

	@Override
	public Dao<UserInfo, Integer> getDao() throws SQLException {
		return getHelper().getDao(UserInfo.class);
	}

}
