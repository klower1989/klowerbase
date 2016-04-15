package com.klowerbase.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.klower.titlebar.BaseException;
import com.klower.titlebar.SuperBaseActivity;
import com.klowerbase.test.R;

public class TestSuperActivity extends SuperBaseActivity {

    @Override
    protected void onCreateEqually(Bundle savedInstanceState) throws BaseException {
        super.onCreateEqually(savedInstanceState);
        setCustomContentView(R.layout.activity_test_super);
    }

}
