package com.klowerbase.test;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.klower.model.FileInfo;
import com.klower.titlebar.BaseException;
import com.klowerbase.test.adapter.FileChooserAdapter;

public class FileChooseActivity extends ActionBarActivity {

	private GridView mGridView;
	private TextView mTvPath;

	private String mSdcardRootPath; // sdcard 根路径
	private String mLastFilePath; // 当前显示的路径

	private ArrayList<FileInfo> mFileLists;
	private FileChooserAdapter mAdatper;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.filechooser_show);
		mSdcardRootPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();// �õ�sdcardĿ¼


		mTvPath = (TextView) findViewById(R.id.tvPath);

		mGridView = (GridView) findViewById(R.id.gvFileChooser);
		mGridView.setEmptyView(findViewById(R.id.tvEmptyHint));
		mGridView.setOnItemClickListener(mItemClickListener);
		setGridViewAdapter(mSdcardRootPath);
		
		renameFile();
	}
	
	@Override
	public void onLeftTitleClick() {
		backProcess();
	}

	// 配置适配器
	private void setGridViewAdapter(String filePath) {
		updateFileItems(filePath);
		mAdatper = new FileChooserAdapter(this, mFileLists);
		mGridView.setAdapter(mAdatper);
	}

	// 根据路径更新数据，并且通知Adatper数据改变
	private void updateFileItems(String filePath) {
		mLastFilePath = filePath;
		mTvPath.setText(mLastFilePath);

		if (mFileLists == null)
			mFileLists = new ArrayList<FileInfo>();
		if (!mFileLists.isEmpty())
			mFileLists.clear();

		File[] files = folderScan(filePath);
		if (files == null)
			return;

		for (int i = 0; i < files.length; i++) {
			if (files[i].isHidden()) // 不显示隐藏文件
				continue;

			String fileAbsolutePath = files[i].getAbsolutePath();
			String fileName = files[i].getName();
			boolean isDirectory = false;
			if (files[i].isDirectory()) {
				isDirectory = true;
			}
			FileInfo fileInfo = new FileInfo(fileAbsolutePath, fileName,
					isDirectory);
			mFileLists.add(fileInfo);
		}
		// When first enter , the object of mAdatper don't initialized
		if (mAdatper != null)
			mAdatper.notifyDataSetChanged(); // 重新刷新
	}

	// 获得当前路径的所有文件
	private File[] folderScan(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
	}

	private OnItemClickListener mItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long id) {
			FileInfo fileInfo = (FileInfo) (((FileChooserAdapter) adapterView
					.getAdapter()).getItem(position));
			if (fileInfo.isDirectory()) // 点击项为文件夹, 显示该文件夹下所有文件
				updateFileItems(fileInfo.getFilePath());
			else if (fileInfo.isPPTFile()) { // 是ppt文件 ， 则将该路径通知给调用者
				toast("path: "+fileInfo.getFilePath());
				// Intent intent = new Intent();
				// intent.putExtra(MainActivity.EXTRA_FILE_CHOOSER,
				// fileInfo.getFilePath());
				// setResult(RESULT_OK, intent);
				// finish();
			} else { // 其他文件.....
				toast("path: "+fileInfo.getFilePath());
			}
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			backProcess();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 返回上一层目录的操作
	public void backProcess() {
		// 判断当前路径是不是sdcard路径 ， 如果不是，则返回到上一层。
		if (!mLastFilePath.equals(mSdcardRootPath)) {
			File thisFile = new File(mLastFilePath);
			String parentFilePath = thisFile.getParent();
			updateFileItems(parentFilePath);
		} else { // 是sdcard路径 ，直接结束
			setResult(RESULT_CANCELED);
			finish();
		}
	}
	
	
	private void renameFile(){
		File file = new File(Environment.getExternalStorageDirectory()+"/xxxxx.avi");
		file.renameTo(new File(Environment.getExternalStorageDirectory()+"/gk/xxxxx"));
		
		File file1 = new File(Environment.getExternalStorageDirectory()+"/xxxxxx.avi");
		file1.renameTo(new File(Environment.getExternalStorageDirectory()+"/gk/xxxxxx"));

	}

}
