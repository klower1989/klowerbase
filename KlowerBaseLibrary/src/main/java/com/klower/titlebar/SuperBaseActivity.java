package com.klower.titlebar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.klower.R;

public class SuperBaseActivity extends AppCompatActivity {

    /**
     * abstract method onCreateEqually
     *
     * @param savedInstanceState
     * @throws BaseException
     */
    private View indicatorView;

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
     * @return void
     * @throws BaseException
     * @Description: abstract method onNewIntentEqually
     */
    public void onNewIntentEqually(Intent intent) throws BaseException {

    }

    /**
     * @param newConfig
     * @return void
     * @Description: ConfigurationChanged
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
        if (indicatorView.getVisibility() == View.VISIBLE) {
            indicatorView.setVisibility(View.GONE);
        } else {
            finish();
        }

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
        try {
            setContentView(R.layout.ui_base_container);
            initAllViews();
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


    private void initAllViews() {
        indicatorView = findViewById(R.id.indicator_view);
        //disable behind touch event
        indicatorView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    protected void setTitleView(int id) {
        FrameLayout content = (FrameLayout) findViewById(R.id.title_container);
        content.removeAllViews();
        content.addView(LayoutInflater.from(this).inflate(id, null));
    }

    protected void setTitleView(View view) {
        FrameLayout content = (FrameLayout) findViewById(R.id.title_container);
        content.removeAllViews();
        content.addView(view);
    }

    public void hideTitleBar() {
        findViewById(R.id.title_container).setVisibility(View.GONE);
    }

    public void showTitleBar() {
        findViewById(R.id.title_container).setVisibility(View.VISIBLE);
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


}
