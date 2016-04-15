package com.klowerbase.test;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.klower.titlebar.BaseException;
import com.klower.utilities.DialogUtils;

public class DialogActivity extends ActionBarActivity implements
		OnClickListener {

	Dialog myDialog;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		setCustomContentView(R.layout.dialog_view);
		findViewById(R.id.dialog_top).setOnClickListener(this);
		findViewById(R.id.dialog_bootom).setOnClickListener(this);
		findViewById(R.id.dialog_center).setOnClickListener(this);

	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if(myDialog == null){
	// View dialogView = LayoutInflater.from(this).inflate(
	// R.layout.dialog_view_item, null);
	// myDialog = DialogUtils.showViewDialog(this, dialogView, 0,
	// R.style.mypopwindow_anim_style1, true);
	// }else{
	// if(keyCode == KeyEvent.KEYCODE_MENU){
	// if(myDialog.isShowing()){
	// myDialog.dismiss();
	// }else{
	// myDialog.show();
	// }
	// return false;
	// }
	// }
	//
	// return super.onKeyDown(keyCode, event);
	// }

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (myDialog == null) {
			showDialog();
		} else {

			if (myDialog.isShowing()) {
				myDialog.dismiss();
			} else {
				myDialog.show();
			}

		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		View dialogView;
		switch (v.getId()) {
		case R.id.dialog_top:
			//show dialog on the screen top
			showDialog();
			break;
		case R.id.dialog_bootom:
			//show dialog on the screen Bottom
			dialogView = LayoutInflater.from(this).inflate(
					R.layout.dialog_view_item, null);
			DialogUtils.showViewDialog(this, dialogView, 1,
					R.style.mypopwindow_anim_style1, true);
			break;
		case R.id.dialog_center:
			//show dialog on the screen Center
			dialogView = LayoutInflater.from(this).inflate(
					R.layout.dialog_view_item, null);
			DialogUtils.showViewDialog(this, dialogView, 2,
					R.style.mypopwindow_anim_style1, true);
			break;
		}
	}

	private void showDialog() {
		View dialogView = LayoutInflater.from(this).inflate(
				R.layout.dialog_view_item, null);
		myDialog = DialogUtils.showViewDialog(this, dialogView, 0,
				R.style.mypopwindow_anim_style1, true);
		myDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_MENU) {
					if (myDialog.isShowing()) {
						myDialog.dismiss();
					} else {
						myDialog.show();
					}
					return true;
				}
				return false;
			}
		});
	}
}
