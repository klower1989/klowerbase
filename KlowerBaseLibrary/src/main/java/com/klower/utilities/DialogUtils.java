package com.klower.utilities;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import com.klower.R;
import com.klower.interfaces.OnCusDialogInterface;
import com.klower.interfaces.OnDateChooseListener;
import com.klower.interfaces.OnMutiDialogListener;
import com.klower.interfaces.OnSingleDialogListener;

public class DialogUtils {

	public static Dialog showProgressDialog(Context context) {
		Dialog dialog = new Dialog(context, R.style.dialog);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.ui_progress_dialog);
		dialog.show();
		return dialog;
	}

	public static ProgressDialog showProgressDialog(Context context,
			String title, String message, boolean cancelable) {
		ProgressDialog progressDialog = ProgressDialog.show(context, title,
				message, true, cancelable);
		return progressDialog;
	}

	public static Dialog showViewDialog(Context context, View view) {
		Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
		dialog.setCancelable(true);
		dialog.setContentView(view);
		dialog.show();
		return dialog;
	}

	public static Dialog showViewDialog(Context context, View view,
			String title, boolean isCancel) {
		Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
		dialog.setCancelable(isCancel);
		dialog.setContentView(view);
		dialog.setTitle(title);
		dialog.show();
		return dialog;
	}

	// direction: 0:Top, 1:Bottom, 2:center
	public static Dialog showViewDialog(Context context, View view,
			int direction, int animationStyle, boolean isCancel) {
		Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
//		View v = LayoutInflater.from(context)
//				.inflate(R.layout.dialogview, null);
//		RelativeLayout layout = (RelativeLayout) v
//				.findViewById(R.id.dialog_container);
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//		params.alignWithParent = true;
//		layout.addView(view, params);
		dialog.setCancelable(isCancel);
		dialog.setContentView(view);
		Window window = dialog.getWindow();
		window.setWindowAnimations(animationStyle);
		if (0 == direction) {
//			window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//			 window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); 
			//设定背景透明
//			window.setDimAmount(0f);
			WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
			layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
			layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
			window.setAttributes(layoutParams);
			window.setGravity(Gravity.TOP);
			// params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			// params.addRule(RelativeLayout.CENTER_HORIZONTAL,
			// RelativeLayout.TRUE);
		} else if (1 == direction) {
			window.setGravity(Gravity.BOTTOM);
//			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		} else if (2 == direction) {
			window.setGravity(Gravity.CENTER);
//			params.addRule(RelativeLayout.CENTER_IN_PARENT);
		}
		// layout.removeAllViews();
		// layout.removeAllViewsInLayout();
		dialog.show();
		return dialog;
	}

	public static void showDialog(Context context, String title, String msg,
			String confirm, String cancel, boolean isCancel,
			final OnCusDialogInterface dialogInterface) {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			if (title != null) {
				builder.setTitle(title);
			}
			builder.setCancelable(isCancel);
			builder.setMessage(msg);
			if (confirm != null) {
				builder.setPositiveButton(confirm,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								if (dialogInterface != null) {
									dialogInterface.onConfirmClick();
								}
							}
						});
			}
			if (cancel != null) {
				builder.setNegativeButton(cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								if (dialogInterface != null) {
									dialogInterface.onCancelClick();
								}
							}
						});
			}

			builder.create().show();
		} catch (Exception e) {
		}
	}

	public static void showViewDialog(Context context, View view, String title,
			String msg, String confirm, String cancel, boolean isCancel,
			final OnCusDialogInterface dialogInterface) {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			if (title != null) {
				builder.setTitle(title);
			}
			builder.setView(view);
			builder.setCancelable(isCancel);
			if (msg != null) {
				builder.setMessage(msg);
			}
			if (confirm != null) {
				builder.setPositiveButton(confirm,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// dialog.dismiss();
								if (dialogInterface != null) {
									dialogInterface.onConfirmClick();
								}
							}
						});
			}
			if (cancel != null) {
				builder.setNegativeButton(cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								if (dialogInterface != null) {
									dialogInterface.onCancelClick();
								}
							}
						});
			}

			builder.create().show();
		} catch (Exception e) {
		}
	}

	public static void showSingleChooseDialog(Context context, String Cancel,
			final String items[], final int checkedItem,
			final OnSingleDialogListener listener) {
		new AlertDialog.Builder(context)
				.setSingleChoiceItems(items, checkedItem,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								if (listener != null) {
									listener.onChoose(which, items[which]);
								}
							}
						})
				.setNegativeButton(Cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
							}
						}).show();

	}

	public static void showMutiChooseDialog(Context context, String title,
			String confirm, final String items[], final boolean checkedItems[],
			final OnMutiDialogListener listener) {
		new AlertDialog.Builder(context)
				.setTitle(title)
				.setMultiChoiceItems(items, checkedItems,
						new OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1,
												boolean arg2) {
								arg0.dismiss();
								checkedItems[arg1] = arg2;
							}
						})
				.setNegativeButton(confirm,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								ArrayList<String> selectedItems = new ArrayList<String>();
								arg0.dismiss();
								for (int i = 0; i < checkedItems.length; i++) {
									if (checkedItems[i]) {
										selectedItems.add(items[i]);
									}
								}
								if (listener != null) {
									listener.onChoose(selectedItems);
								}
							}
						}).show();
	}

	public static void showDatePickerDialog(Context context,
			final OnDateChooseListener listener, int offsetDays) {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, offsetDays);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.ui_datepicker, null);
		final Dialog dialog = new Dialog(context, R.style.dialog);
		dialog.setContentView(view);
		dialog.show();

		DatePicker datePicker = (DatePicker) view.findViewById(R.id.datepicker);
		datePicker.setCalendarViewShown(false);
		datePicker
				.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
		datePicker.init(year, month, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int years,
					int monthOfYear, int dayOfMonth) {
				calendar.set(years, monthOfYear, dayOfMonth);
			}
		});
		view.findViewById(R.id.datepicker_confirm).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (dialog != null) {
							dialog.dismiss();
						}
						if (listener != null) {
							listener.onDateChoose(calendar);
						}
					}
				});
	}

	public static Dialog showCopyDialog(final String label,
			final String content, final Context context) {

		Dialog dialog = new AlertDialog.Builder(context)
				.setTitle(label)
				.setNegativeButton("取消", null)
				.setItems(new String[] { content },
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								copy(label, content, context);
								Toast.makeText(context, "文本已复制到粘贴板",
										Toast.LENGTH_SHORT).show();
							}
						}).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		return dialog;

	}

	public static void copy(String label, String content, Context context) {
		// 得到剪贴板管理器
		ClipboardManager clipboard = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		// clipboard.setText(content.trim());
		// Copies the notes URI to the clipboard. In effect, this copies the
		// note itself
		clipboard.setPrimaryClip(ClipData.newPlainText(label, content.trim()));
	}

	public static PopupWindow creatPopupWindow(Context context,
			final View popupView, int animationStyle, int width, int height) {
		// DisplayMetrics metric = new DisplayMetrics();
		// int popupWidth = metric.widthPixels;
		// int popupHeight = metric.heightPixels;
		// int popupWidth =
		// ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
		// int popupHeight =
		// ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
		final PopupWindow puwWindow = new PopupWindow(popupView, width, height,
				true);
		// puwWindow.setWidth(popupWidth);
		puwWindow.setBackgroundDrawable(null);
		puwWindow.setAnimationStyle(animationStyle);
		puwWindow.setFocusable(true);
		popupView.setFocusableInTouchMode(true);
		popupView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					// TODO
					if (puwWindow != null && puwWindow.isShowing()) {
						puwWindow.dismiss();
						return false;
					}
				}
				return false;
			}

		});

		popupView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int x = (int) event.getX();
				final int y = (int) event.getY();

				if ((event.getAction() == MotionEvent.ACTION_DOWN)
						&& ((x < 0) || (x >= popupView.getWidth()) || (y < 0) || (y >= popupView
						.getHeight()))) {
					if (puwWindow != null && puwWindow.isShowing()) {
						puwWindow.dismiss();
						return false;
					}
				} else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					if (puwWindow != null && puwWindow.isShowing()) {
						puwWindow.dismiss();
						return false;
					}
				}
				return false;
			}
		});
		return puwWindow;
	}

	/**
	 *
	 * @param context
	 * @param popupView
	 * @param animationStyle
	 * @param width
	 * @param height
	 * @param direction: 1 top, 2 bottom
	 * @return
	 */
	public static PopupWindow createPopupWindow(Context context,
											   final View popupView, int animationStyle, int
														width, int height, int direction) {
		View view ;
		if(direction == 1){
			view = LayoutInflater.from(context).inflate(R.layout.ui_popup_view_top, null);
		}else{
			view = LayoutInflater.from(context).inflate(R.layout.ui_popup_view_bottom, null);
		}
		LinearLayout container = (LinearLayout) view.findViewById(R.id.view_container);
		container.addView(popupView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams
				.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		final PopupWindow puwWindow = new PopupWindow(view, width, height,
				true);
		view.findViewById(R.id.popup_dismiss_tv).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				puwWindow.dismiss();
			}
		});
		// puwWindow.setWidth(popupWidth);
		puwWindow.setBackgroundDrawable(null);
		puwWindow.setAnimationStyle(animationStyle);
		puwWindow.setFocusable(true);
		popupView.setFocusableInTouchMode(true);
		popupView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					// TODO
					if (puwWindow != null && puwWindow.isShowing()) {
						puwWindow.dismiss();
						return false;
					}
				}
				return false;
			}

		});

		popupView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int x = (int) event.getX();
				final int y = (int) event.getY();

				if ((event.getAction() == MotionEvent.ACTION_DOWN)
						&& ((x < 0) || (x >= popupView.getWidth()) || (y < 0) || (y >= popupView
						.getHeight()))) {
					if (puwWindow != null && puwWindow.isShowing()) {
						puwWindow.dismiss();
						return false;
					}
				} else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					if (puwWindow != null && puwWindow.isShowing()) {
						puwWindow.dismiss();
						return false;
					}
				}
				return false;
			}
		});
		return puwWindow;
	}
}
