package com.klowerbase.test;

import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.klower.lock.LockPatternView;
import com.klower.lock.LockPatternView.Cell;
import com.klower.lock.LockPatternView.OnPatternListener;
import com.klower.titlebar.BaseException;

public class LockActivity extends ActionBarActivity {
	LockPatternView mLockPatternView;
	SharedPreferences mPreferences;
	String patten;
	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		// TODO Auto-generated method stub
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.lock);
		mPreferences = getSharedPreferences("SharedPreferences", 0);
		if (mPreferences.getString("password", null) != null) {
			Toast.makeText(LockActivity.this, "请绘制手势密码解锁", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(LockActivity.this, "请设置手势密码", Toast.LENGTH_SHORT)
					.show();
		}
		mLockPatternView = (LockPatternView) findViewById(R.id.lp_lock_set_psd);
		mLockPatternView.setOnPatternListener(new OnPatternListener() {

			@Override
			public void onPatternStart() {

			}

			@Override
			public void onPatternDetected(List<Cell> pattern) {
				// TODO Auto-generated method stub
				if (pattern.size() < 4) {
					Toast.makeText(LockActivity.this, "长度不能小于4",
							Toast.LENGTH_SHORT).show();
					mLockPatternView.clearPattern();
				} else {
					if (mPreferences.getString("password", null) != null) {
						if (mPreferences.getString("password", null)
								.equalsIgnoreCase(mLockPatternView.patternToString(pattern))) {
							Toast.makeText(LockActivity.this, "解锁成功！",
									Toast.LENGTH_SHORT).show();
							finish();
						}else{
							mLockPatternView.clearPattern();
							Toast.makeText(LockActivity.this, "解锁失败！",
									Toast.LENGTH_SHORT).show();
						}
						return;
					}
					if (patten != null) {
						if (!patten.equalsIgnoreCase(mLockPatternView.patternToString(pattern))) {
							mLockPatternView.clearPattern();
							Toast.makeText(LockActivity.this, "两次设置不一样",
									Toast.LENGTH_SHORT).show();
						} else {
							mLockPatternView.clearPattern();
							mPreferences.edit().putString("password", patten)
									.commit();
							Toast.makeText(LockActivity.this, "设置成功",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						patten = mLockPatternView.patternToString(pattern);
						mLockPatternView.clearPattern();
					}
				}
			}

			@Override
			public void onPatternCleared() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPatternCellAdded(List<Cell> pattern) {

			}
		});
	}
}
