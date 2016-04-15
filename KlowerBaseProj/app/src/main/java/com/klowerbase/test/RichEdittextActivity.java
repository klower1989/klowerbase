package com.klowerbase.test;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.klower.ambilwarna.AmbilWarnaDialog;
import com.klower.datasave.Hawk;
import com.klower.richedittext.CustomEditText;


public class RichEdittextActivity extends Activity implements AmbilWarnaDialog.OnAmbilWarnaListener {
    private LinearLayout lnl;
    private CustomEditText customEditor;
    private AmbilWarnaDialog colorPickerDialog;
    private ImageView imgChangeColor;

    private int selectionStart;
    private int selectionEnd;

//	private CustomEditText.EventBack eventBack = new CustomEditText.EventBack() {
//
//		@Override
//		public void close() {
//			lnl.setVisibility(View.GONE);
//		}
//
//		@Override
//		public void show() {
//			lnl.setVisibility(View.VISIBLE);
//		}
//	};
//	private OnClickListener clickListener = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			if (customEditor.isFocused()) {
//				lnl.setVisibility(View.VISIBLE);
//			}
//		}
//	};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        colorPickerDialog = new AmbilWarnaDialog(this, Color.BLACK, this);
        ToggleButton boldToggle = (ToggleButton) findViewById(R.id.btnBold);
        ToggleButton italicsToggle = (ToggleButton) findViewById(R.id.btnItalics);
        ToggleButton underlinedToggle = (ToggleButton) findViewById(R.id.btnUnderline);
        imgChangeColor = (ImageView) findViewById(R.id.btnChangeTextColor);
        lnl = (LinearLayout) findViewById(R.id.lnlAction);
        lnl.setVisibility(View.VISIBLE);

        customEditor = (CustomEditText) findViewById(R.id.CustomEditor);
        customEditor.setSingleLine(false);
        customEditor.setMinLines(10);
        customEditor.setBoldToggleButton(boldToggle);
        customEditor.setItalicsToggleButton(italicsToggle);
        customEditor.setUnderlineToggleButton(underlinedToggle);
//		customEditor.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus) {
//					lnl.setVisibility(View.VISIBLE);
//				} else {
//					lnl.setVisibility(View.GONE);
//
//				}
//			}
//		});
        findViewById(R.id.showbtn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.d("customEditor",
                        "customEditor: " + Html.toHtml(customEditor.getSpannedText()));
                String htmlStr = Html
                        .toHtml(customEditor.getSpannedText());
                htmlStr = htmlStr.replace(getString(R.string.size_nomal), (String.format("style=\"font-size:%dpx;\"", 15 * 2)));
                htmlStr = htmlStr.replace(getString(R.string.size_large), (String.format("style=\"font-size:%dpx;\"", 20 * 2)));
                Log.d("customEditor",
                        "customEditor: " + htmlStr);
//				((TextView) findViewById(R.id.show)).setText(Html.fromHtml(htmlStr));
                ((TextView) findViewById(R.id.show)).setText(customEditor.getSpannedText());

//				String htmlStr = Html.toHtml(customEditor.getSpannedText());
//				ImageSpan[] spans = customEditor.getText().getSpans(0,
//						customEditor.getText().length(), ImageSpan.class);
//				Log.d("customEditor", "spans size:" + spans.length);
//				for (int i = 0; i < spans.length; i++) {
//					htmlStr = htmlStr.replaceFirst(getString(R.string.src),
//							getString(R.string.src).replace("null", "hello"+i));
//				}
//				Log.d("customEditor", "htmlStr: "+htmlStr);
//				String str = "dasabcdefgabddd";
//				boolean isContains = true;
//				while (isContains) {
//					if (str.indexOf("ab") != -1) {
//						str = str.replaceFirst("ab", (int) (Math.random() * 10) + "");
//					} else {
//						isContains = false;
//					}
//				}
//				Log.d("customEditor", str);
            }
        });
        customEditor.setTextSize(18, customEditor.getSelectionStart(), customEditor
                .getSelectionEnd());
//		customEditor.setEventBack(eventBack);
//		customEditor.setOnClickListener(clickListener);
        imgChangeColor.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectionStart = customEditor.getSelectionStart();
                selectionEnd = customEditor.getSelectionEnd();
                colorPickerDialog.show();
            }
        });

        ((TextView) findViewById(R.id.show))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Drawable drawable = getResources().getDrawable(
                                R.drawable.empty_photo);
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                                drawable.getIntrinsicHeight());
                        // Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
                        customEditor.inSertImage(drawable, "path");
                    }
                });

        final ImageView testSizeBtn = (ImageView) findViewById(R.id.btnChangeTextSize);
        testSizeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionStart = customEditor.getSelectionStart();
                selectionEnd = customEditor.getSelectionEnd();
                if (testSizeBtn.isSelected()) {
                    testSizeBtn.setImageResource(R.drawable.font_normal_size);
                    testSizeBtn.setSelected(false);
                    customEditor.setTextSize(18, selectionStart, selectionEnd);
                } else {
                    testSizeBtn.setImageResource(R.drawable.font_focus_size);
                    testSizeBtn.setSelected(true);
                    customEditor.setTextSize(25, selectionStart, selectionEnd);
                }

            }
        });

    }

    @Override
    public void onCancel(AmbilWarnaDialog dialog) {

    }

    @Override
    public void onOk(AmbilWarnaDialog dialog, int color) {
        customEditor.setColor(color, selectionStart, selectionEnd);
        imgChangeColor.setBackgroundColor(color);

    }
}
