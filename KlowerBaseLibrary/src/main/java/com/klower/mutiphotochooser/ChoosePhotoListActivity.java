package com.klower.mutiphotochooser;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.klower.R;
import com.klower.adapter.ChoosePhotoAdapter;
import com.klower.titlebar.BaseActivity;
import com.klower.titlebar.BaseException;

public class ChoosePhotoListActivity extends BaseActivity implements
        OnClickListener {

    private GridView mGridView;

    private TextView mScan, mConfirm;

    private ChoosePhotoAdapter mPhotoAdapter;

    private PhotoSelectorDomain mPhotoSelectorDomain;

    private String albumName;

    private RelativeLayout mScanRelativeLayout;

    /**
     * 1: Single, 2:Muti
     */
    private int choosePhotoType = 1;

    @Override
    protected void onCreateEqually(Bundle savedInstanceState)
            throws BaseException {
        super.onCreateEqually(savedInstanceState);
        if (getIntent() != null) {
            albumName = getIntent().getStringExtra("albumName");
            choosePhotoType = getIntent().getIntExtra("choosePhotoType", 1);
        }
        getTitleBar().setCustomTitleColor(Color.WHITE);
        if (albumName != null) {
            getTitleBar().setCustomTitle(albumName);
        } else {
            getTitleBar().setCustomTitle("最近照片");
        }
        getTitleBar().setRightTitle("取消");
        setCustomContentView(R.layout.choosephoto_list);
        initView();
    }

    @Override
    public void onRightTitleClick() {
        setResult(RESULT_OK);
        finish();
    }

    private void initView() {
        mScanRelativeLayout = (RelativeLayout) findViewById(R.id.choosephoto_scan_layout);
//        if(choosePhotoType == 1){
//            mScanRelativeLayout.setVisibility(View.GONE);
//        }
        mScan = (TextView) findViewById(R.id.choosephoto_scan);
        mConfirm = (TextView) findViewById(R.id.choosephoto_confirm);
        mScan.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mScan.setClickable(false);
        mConfirm.setClickable(false);

        mPhotoSelectorDomain = new PhotoSelectorDomain(this);
        mPhotoAdapter = new ChoosePhotoAdapter(this);
        mPhotoAdapter.setChoosePhotoType(choosePhotoType);
        mGridView = (GridView) findViewById(R.id.gv_photos_ar);
        mGridView.setAdapter(mPhotoAdapter);

        if (albumName != null) {
            mPhotoSelectorDomain.getAlbum(albumName,
                    new OnLocalReccentListener() {

                        @Override
                        public void onPhotoLoaded(List<PhotoModel> photos) {

                            if (photos != null) {
                                mPhotoAdapter
                                        .setData((ArrayList<PhotoModel>) photos);
                            }

                        }
                    });
        } else {
            mPhotoSelectorDomain.getReccent(new OnLocalReccentListener() {

                @Override
                public void onPhotoLoaded(List<PhotoModel> photos) {
                    if (photos != null) {
                        mPhotoAdapter.setData((ArrayList<PhotoModel>) photos);
                    }
                }
            });
        }
        mPhotoAdapter.setOnPhotoChooseListener(new OnPhotoChooseListener() {

            @Override
            public void onChoose(ArrayList<PhotoModel> selectedItems) {
//                if (choosePhotoType == 1) {
//                    Intent intent = new Intent();
//                    intent.putExtra("photos", mPhotoAdapter.getSelectedPhotos());
//                    setResult(RESULT_OK, intent);
//                    finish();
//                    return;
//                }
                if (selectedItems.size() > 0) {
                    mScan.setTextColor(Color.parseColor("#4CACFF"));
                    mScan.setClickable(true);

                    mConfirm.setTextColor(Color.WHITE);
                    mConfirm.setBackgroundResource(R.drawable.blue_bg_selector);
                    mConfirm.setClickable(true);
                    mConfirm.setText("确定(" + selectedItems.size() + ")");
                } else {
                    mScan.setTextColor(getResources().getColor(R.color.TextGray));
                    mScan.setClickable(false);

                    mConfirm.setTextColor(getResources().getColor(R.color.TextGray));
                    mConfirm.setBackgroundResource(R.drawable.grey_bg_selector);
                    mConfirm.setClickable(false);
                    mConfirm.setText("确定");

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (R.id.choosephoto_confirm == v.getId()) {
            if (mPhotoAdapter.getSelectedPhotos() != null
                    && mPhotoAdapter.getSelectedPhotos().size() > 0) {
                Intent intent = new Intent();
                intent.putExtra("photos", mPhotoAdapter.getSelectedPhotos());
                setResult(RESULT_OK, intent);
                finish();
            }
        } else if (R.id.choosephoto_scan == v.getId()) {
            Intent intent = new Intent(ChoosePhotoListActivity.this,
                    ChoosePhotoScanActivity.class);
//			ArrayList<String> newPhotos = new ArrayList<String>();
//			for (int i = 0; i < mPhotoAdapter.getSelectedPhotos().size(); i++) {
//				newPhotos.add("file://" +mPhotoAdapter.getSelectedPhotos().get(i));
//			}
            intent.putExtra("imgs", mPhotoAdapter.getSelectedImgs());
            intent.putExtra("position", mPhotoAdapter.getSelectedPhotos()
                    .size() - 1);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onActivityResultEqually(int requestCode, int resultCode,
                                        Intent data) throws BaseException {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> photos = data
                        .getStringArrayListExtra("photos");
                Intent intent = new Intent();
                intent.putExtra("photos", photos);
                setResult(RESULT_OK, data);
                finish();

            }
        }
    }
}
