package com.klowerbase.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.klower.signature.SignatureView;
import com.klower.titlebar.BaseException;

public class SignatureActivity extends ActionBarActivity implements
		OnClickListener {

	private LinearLayout mySurface;

	private SignatureView myView;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.signature);

		mySurface = (LinearLayout) findViewById(R.id.drawsurface);
		if (myView != null) {
			myView = null;
		}
		myView = new SignatureView(this, Color.parseColor("#000000"));
		mySurface.addView(myView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mySurface.setDrawingCacheEnabled(true);

		findViewById(R.id.save).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.selectcolor).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			new SaveSign().execute(mySurface.getDrawingCache());
			break;
		case R.id.cancel:
			myView.Undo();
			myView.setSigned(false);
			break;
		case R.id.selectcolor:
			Intent intent = new Intent(SignatureActivity.this,
					ColorPickerActivity.class);
			startActivityForResult(intent, 1);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			int color = data.getIntExtra("color", Color.BLACK);
			myView.setmPaintcolor(color);
		}
	}

	class SaveSign extends AsyncTask<Object, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Object... params) {
			File photoFile = new File(Environment.getExternalStorageDirectory()
					+ "/" + System.currentTimeMillis() + "sign.jpg");
			Bitmap bitmap = (Bitmap) params[0];
			FileOutputStream out;
			try {
				out = new FileOutputStream(photoFile);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				toast("Signature has been saved!");
			} else {
				toast("Failed to save signature!");
			}
		}

	}
}
