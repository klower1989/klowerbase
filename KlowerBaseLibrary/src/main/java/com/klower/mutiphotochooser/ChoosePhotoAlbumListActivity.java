package com.klower.mutiphotochooser;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.klower.R;
import com.klower.adapter.ChoosePhotoAlbumAdapter;
import com.klower.titlebar.BaseActivity;
import com.klower.titlebar.BaseException;

public class ChoosePhotoAlbumListActivity extends BaseActivity {

	private ListView mListView;

	private ChoosePhotoAlbumAdapter mAdapter;

	private PhotoSelectorDomain mPhotoSelectorDomain;

	/**
	 * 1: Single, 2:Muti
	 */
	private int choosePhotoType = 1;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		if (getIntent() != null) {
			choosePhotoType = getIntent().getIntExtra("choosePhotoType", 1);
		}
		Intent intent = new Intent(ChoosePhotoAlbumListActivity.this,
				ChoosePhotoListActivity.class);
		intent.putExtra("choosePhotoType", choosePhotoType);
		startActivityForResult(intent, 1);
		
		getTitleBar().setCustomTitleColor(Color.WHITE);
		getTitleBar().setCustomTitle("选择相册");
		setCustomContentView(R.layout.choosephoto_album);
		initViews();
	}

	private void initViews() {
		mPhotoSelectorDomain = new PhotoSelectorDomain(this);
		mListView = (ListView) findViewById(R.id.lv_ablum_ar);
		mAdapter = new ChoosePhotoAlbumAdapter(this);
		mListView.setDivider(null);
		mListView.setAdapter(mAdapter);

		mPhotoSelectorDomain.updateAlbum(new OnLocalAlbumListener() {

			@Override
			public void onAlbumLoaded(List<AlbumModel> albums) {
				mAdapter.setData((ArrayList<AlbumModel>) albums);
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(ChoosePhotoAlbumListActivity.this,
						ChoosePhotoListActivity.class);
				if(arg2 != 0){
					intent.putExtra("albumName", mAdapter.getData().get(arg2)
							.getName());
				}
				intent.putExtra("choosePhotoType", choosePhotoType);
				startActivityForResult(intent, 1);
			}
		});
	}

	@Override
	public void onActivityResultEqually(int requestCode, int resultCode,
			Intent data) throws BaseException {
		if (resultCode == RESULT_OK) {
			if (data != null) {
				ArrayList<String> photos = data
						.getStringArrayListExtra("photos");
				// for (int i = 0; i < photos.size(); i++) {
				// LogTool.d("Photos", "photo: " + photos.get(i));
				// }
				Intent intent = new Intent();
				intent.putExtra("photos", photos);
				setResult(RESULT_OK, data);
				finish();
			}else{
				finish();
			}
		}
	}
}
