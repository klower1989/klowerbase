package com.klowerbase.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.klower.colorpicker.ColorPickerDialog;
import com.klower.colorpicker.ColorPickerDialog.OnColorChangedListener;
import com.klower.utilities.LogTool;

public class ColorPickerActivity extends ActionBarActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ColorPickerDialog pickerDialog = new ColorPickerDialog(this,
				Color.BLACK);
		pickerDialog.setOnColorChangedListener(new OnColorChangedListener() {
			
			@Override
			public void onColorChanged(int color) {
				LogTool.d("Color", "Color: "+color);
				Intent intent = new Intent();
				intent.putExtra("color",color);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		pickerDialog.show();
	}
}
