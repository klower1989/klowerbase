package com.klowerbase.test.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.klower.model.FileInfo;
import com.klowerbase.test.R;

public class FileChooserAdapter extends BaseAdapter {

	private ArrayList<FileInfo> mFileLists;
	private LayoutInflater mLayoutInflater = null;

	private static ArrayList<String> PPT_SUFFIX = new ArrayList<String>();

	static {
		PPT_SUFFIX.add(".ppt");
		PPT_SUFFIX.add(".pptx");
	}

	public FileChooserAdapter(Context context, ArrayList<FileInfo> fileLists) {
		super();
		mFileLists = fileLists;
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFileLists.size();
	}

	@Override
	public FileInfo getItem(int position) {
		// TODO Auto-generated method stub
		return mFileLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		ViewHolder holder = null;
		if (convertView == null || convertView.getTag() == null) {
			view = mLayoutInflater.inflate(R.layout.filechooser_gridview_item,
					null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) convertView.getTag();
		}

		FileInfo fileInfo = getItem(position);
		// TODO

		holder.tvFileName.setText(fileInfo.getFileName());

		if (fileInfo.isDirectory()) { // 文件夹
			holder.imgFileIcon.setImageResource(R.drawable.ic_folder);
			holder.tvFileName.setTextColor(Color.GRAY);
		} else if (fileInfo.isPPTFile()) { // PPT文件
			holder.imgFileIcon.setImageResource(R.drawable.ic_ppt);
			holder.tvFileName.setTextColor(Color.RED);
		} else { // 未知文件
			holder.imgFileIcon.setImageResource(R.drawable.ic_file_unknown);
			holder.tvFileName.setTextColor(Color.GRAY);
		}
		return view;
	}

	static class ViewHolder {
		ImageView imgFileIcon;
		TextView tvFileName;

		public ViewHolder(View view) {
			imgFileIcon = (ImageView) view.findViewById(R.id.imgFileIcon);
			tvFileName = (TextView) view.findViewById(R.id.tvFileName);
		}
	}
}
