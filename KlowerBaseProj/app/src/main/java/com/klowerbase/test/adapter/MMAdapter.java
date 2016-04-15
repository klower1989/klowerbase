package com.klowerbase.test.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klower.adapter.BSAdapter;
import com.klower.model.SortModel;
import com.klowerbase.test.R;

public class MMAdapter extends BSAdapter<SortModel> {
	
	
	
	public MMAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoler holer;
		if (convertView == null) {
			holer = new ViewHoler();
			convertView = mLayoutInflater.inflate(R.layout.sort_item, null);
			holer.sortChar = (TextView) convertView
					.findViewById(R.id.sort_char);
			holer.name = (TextView) convertView.findViewById(R.id.sort_name);
			convertView.setTag(holer);
		} else {
			holer = (ViewHoler) convertView.getTag();
		}
		
		
		holer.name.setText(getEntityClass(position).getName());
		int section = getSectionForPosition(position);
		if (position == getPositionForSection(section)) {
			holer.sortChar.setVisibility(View.VISIBLE);
			holer.sortChar.setText(getEntityClass(position).getSortLetters());
		} else {
			holer.sortChar.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	class ViewHoler {
		TextView sortChar, name;

	}
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = getEntityClass(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	public int getSectionForPosition(int position) {
		return getEntityClass(position).getSortLetters().charAt(0);
	}

}
