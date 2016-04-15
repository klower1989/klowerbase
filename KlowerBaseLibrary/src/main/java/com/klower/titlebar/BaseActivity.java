package com.klower.titlebar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.klower.R;
import com.klower.interfaces.OnActionbarListener;

public class BaseActivity extends FragmentActivity implements
		OnActionbarListener {

	/**
	 * abstract method onCreateEqually
	 * 
	 * @param savedInstanceState
	 * @throws BaseException
	 */
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
	}

	/**
	 * abstract method onStartEqually
	 * 
	 * @throws BaseException
	 */
	public void onStartEqually() throws BaseException {

	}

	/**
	 * abstract method onResumeEqually
	 * 
	 * @throws BaseException
	 */
	protected void onResumeEqually() throws BaseException {
	}

	/**
	 * @Description: abstract method onNewIntentEqually
	 * @throws BaseException
	 * @return void
	 */
	public void onNewIntentEqually(Intent intent) throws BaseException {

	}

	/**
	 * @Description: ConfigurationChanged
	 * @param newConfig
	 * @throws BLException
	 * @return void
	 */
	protected void onConfigurationChangedEqually(Configuration newConfig)
			throws BaseException {
	}

	/**
	 * abstract method onPauseEqually
	 * 
	 * @throws BaseException
	 */
	public void onPauseEqually() throws BaseException {

	}

	public void onSaveInstanceStateEqually(Bundle outState)
			throws BaseException {

	}

	/**
	 * abstract method onDestroyEqually
	 * 
	 * @throws BaseException
	 */
	public void onDestroyEqually() throws BaseException {
	}

	/**
	 * abstract method onRestartEqually
	 * 
	 * @throws BaseException
	 */
	public void onRestartEqually() throws BaseException {

	}

	/**
	 * abstract method onStopEqually
	 * 
	 * @throws BaseException
	 */
	public void onStopEqually() throws BaseException {

	}

	/**
	 * abstract method onStopEqually
	 * 
	 * @throws BaseException
	 */
	public void onBackPressedEqually() throws BaseException {
		finish();
	}

	/**
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @throws BaseException
	 */
	public void onActivityResultEqually(int requestCode, int resultCode,
			Intent data) throws BaseException {

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		try {
			setContentView(R.layout.ui_baselayout_container);
			onCreateEqually(savedInstanceState);

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}

		// Check if Internet present
	}

	protected void setCustomContentView(int id) {
		FrameLayout content = (FrameLayout) findViewById(R.id.content_container);
		content.removeAllViews();
		content.addView(LayoutInflater.from(this).inflate(id, null));
	}
	
	protected void setCustomContentView(View view) {
		FrameLayout content = (FrameLayout) findViewById(R.id.content_container);
		content.removeAllViews();
		content.addView(view);
	}
	
	public TitleBar getTitleBar(){
		return (TitleBar) findViewById(R.id.title_container);
	}
	
	public void hideTitleBar(){
		getTitleBar().setVisibility(View.GONE);
	}

	public void showTitleBar(){
		getTitleBar().setVisibility(View.VISIBLE);
	}

	public View getTopView(){
		return findViewById(R.id.title_top_line);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {

		super.onRestart();

		try {

			onRestartEqually();

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();

		try {

			onStartEqually();

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {

		super.onResume();

		try {
			onResumeEqually();

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		try {

			onSaveInstanceStateEqually(outState);

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		try {

			onDestroyEqually();

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	@Override
	public void onBackPressed() {
		try {

			onBackPressedEqually();

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {

		super.onPause();

		try {
			onPauseEqually();

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

		try {

			onConfigurationChangedEqually(newConfig);

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {

		super.onStop();

		try {

			onStopEqually();

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	/**
	 * (non-Javadoc) Description:
	 * 
	 * @param intent
	 * @see android.app.Activity#onNewIntent(Intent)
	 */
	@Override
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);

		try {

			onNewIntentEqually(intent);

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		try {

			onActivityResultEqually(requestCode, resultCode, data);

		} catch (BaseException e) {

			handleException(e);

		} catch (Exception e) {

			handleException(e);
		}
	}

	protected void toast(String msg) {
		if (msg != null) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * estimate exception type
	 * 
	 * @param e
	 */
	public void handleException(Exception exEXCP) {

		exEXCP.printStackTrace();
	}

	protected void onRefreshData(int type, String json) {

	}

	protected void onErrorCodes(int errorCode) {

	}

	protected void onHttpErrors(int errorCode) {

	}

	@Override
	public void onLeftTitleClick() {
		finish();
	}

	@Override
	public void onRightTitleClick() {
	}

	@Override
	public void onRightSecondTitleClick() {

	}

}
