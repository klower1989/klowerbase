package com.klowerbase.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.klower.arcmenu.ArcMenu;
import com.klower.arcmenu.ArcMenu.OnMenuItemClickListener;
import com.klower.titlebar.BaseException;

public class ArcMenuActivity extends ActionBarActivity {
	private ArcMenu mArcMenuLeftTop;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.arcmenu);
		mArcMenuLeftTop = (ArcMenu) findViewById(R.id.id_arcmenu1);
		// 动态添加一个MenuItem
		ImageView people = new ImageView(this);
		people.setImageResource(R.drawable.composer_with);
		people.setTag("People");
		mArcMenuLeftTop.addView(people);

		mArcMenuLeftTop
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public void onClick(View view, int pos) {
						Toast.makeText(ArcMenuActivity.this,
								pos + ":" + view.getTag(), Toast.LENGTH_SHORT)
								.show();
					}
				});
	}
}
