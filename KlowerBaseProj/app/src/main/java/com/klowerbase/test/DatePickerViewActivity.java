package com.klowerbase.test;

import static android.widget.Toast.LENGTH_SHORT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.klower.calendarview.CalendarCellDecorator;
import com.klower.calendarview.CalendarPickerView;
import com.klower.calendarview.CalendarPickerView.SelectionMode;
import com.klower.titlebar.BaseException;
import com.klowerbase.test.helper.SampleDecorator;

public class DatePickerViewActivity extends ActionBarActivity {
	private static final String TAG = "SampleTimesSquareActivity";
	private CalendarPickerView calendar;
	private AlertDialog theDialog;
	private CalendarPickerView dialogView;
	private final Set<Button> modeButtons = new LinkedHashSet<Button>();

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.sample_calendar_picker);

		final Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);

		final Calendar lastYear = Calendar.getInstance();
		lastYear.add(Calendar.YEAR, -1);

		calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
		calendar.init(lastYear.getTime(), nextYear.getTime()) //
				.inMode(SelectionMode.SINGLE) //
				.withSelectedDate(new Date());

		initButtonListeners(nextYear, lastYear);
	}

	private void initButtonListeners(final Calendar nextYear,
			final Calendar lastYear) {
		final Button single = (Button) findViewById(R.id.button_single);
		final Button multi = (Button) findViewById(R.id.button_multi);
		final Button range = (Button) findViewById(R.id.button_range);
		final Button displayOnly = (Button) findViewById(R.id.button_display_only);
		final Button dialog = (Button) findViewById(R.id.button_dialog);
		final Button customized = (Button) findViewById(R.id.button_customized);
		final Button decorator = (Button) findViewById(R.id.button_decorator);
		final Button rtl = (Button) findViewById(R.id.button_rtl);

		modeButtons.addAll(Arrays.asList(single, multi, range, displayOnly,
				decorator));

		single.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setButtonsEnabled(single);

				calendar.setDecorators(Collections
						.<CalendarCellDecorator> emptyList());
				calendar.init(lastYear.getTime(), nextYear.getTime()) //
						.inMode(SelectionMode.SINGLE) //
						.withSelectedDate(new Date());
			}
		});

		multi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setButtonsEnabled(multi);

				Calendar today = Calendar.getInstance();
				ArrayList<Date> dates = new ArrayList<Date>();
				for (int i = 0; i < 5; i++) {
					today.add(Calendar.DAY_OF_MONTH, 3);
					dates.add(today.getTime());
				}
				calendar.setDecorators(Collections
						.<CalendarCellDecorator> emptyList());
				calendar.init(new Date(), nextYear.getTime()) //
						.inMode(SelectionMode.MULTIPLE) //
						.withSelectedDates(dates);
			}
		});

		range.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setButtonsEnabled(range);

				Calendar today = Calendar.getInstance();
				ArrayList<Date> dates = new ArrayList<Date>();
				today.add(Calendar.DATE, 3);
				dates.add(today.getTime());
				today.add(Calendar.DATE, 5);
				dates.add(today.getTime());
				calendar.setDecorators(Collections
						.<CalendarCellDecorator> emptyList());
				calendar.init(new Date(), nextYear.getTime()) //
						.inMode(SelectionMode.RANGE) //
						.withSelectedDates(dates);
			}
		});

		displayOnly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setButtonsEnabled(displayOnly);

				calendar.setDecorators(Collections
						.<CalendarCellDecorator> emptyList());
				calendar.init(new Date(), nextYear.getTime()) //
						.inMode(SelectionMode.SINGLE) //
						.withSelectedDate(new Date()) //
						.displayOnly();
			}
		});

		dialog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String title = "I'm a dialog!";
				showCalendarInDialog(title, R.layout.dialog);
				dialogView.init(lastYear.getTime(), nextYear.getTime()) //
						.withSelectedDate(new Date());
			}
		});

		customized.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				showCalendarInDialog("Pimp my calendar!",
						R.layout.dialog_customized);
				dialogView.init(lastYear.getTime(), nextYear.getTime())
						.withSelectedDate(new Date());
			}
		});

		decorator.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setButtonsEnabled(decorator);

				calendar.setDecorators(Arrays
						.<CalendarCellDecorator> asList(new SampleDecorator()));
				calendar.init(lastYear.getTime(), nextYear.getTime()) //
						.inMode(SelectionMode.SINGLE) //
						.withSelectedDate(new Date());
			}
		});

		rtl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				showCalendarInDialog("I'm right-to-left!", R.layout.dialog);
				dialogView.init(lastYear.getTime(), nextYear.getTime(),
						new Locale("iw", "IL")) //
						.withSelectedDate(new Date());
			}
		});

		findViewById(R.id.done_button).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View view) {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						String toast = "Selected: "
								+ calendar.getSelectedDate().getTime();
						if (calendar.getSelectedDates() != null
								&& calendar.getSelectedDates().size() > 1) {
							toast = dateFormat.format(calendar
									.getSelectedDates().get(0))
									+ "---"
									+ dateFormat.format(calendar
											.getSelectedDates().get(
													calendar.getSelectedDates()
															.size() - 1));
						}
						Log.d(TAG, "Selected time in millis: "
								+ calendar.getSelectedDate().getTime());

						Toast.makeText(DatePickerViewActivity.this, toast,
								LENGTH_SHORT).show();
					}
				});
	}

	private void showCalendarInDialog(String title, int layoutResId) {
		dialogView = (CalendarPickerView) getLayoutInflater().inflate(
				layoutResId, null, false);
		theDialog = new AlertDialog.Builder(this)
				//
				.setTitle(title)
				.setView(dialogView)
				.setNeutralButton("Dismiss",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialogInterface, int i) {
								dialogInterface.dismiss();
							}
						}).create();
		theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {
				Log.d(TAG, "onShow: fix the dimens!");
				dialogView.fixDialogDimens();
			}
		});
		theDialog.show();
	}

	private void setButtonsEnabled(Button currentButton) {
		for (Button modeButton : modeButtons) {
			modeButton.setEnabled(modeButton != currentButton);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		boolean applyFixes = theDialog != null && theDialog.isShowing();
		if (applyFixes) {
			Log.d(TAG,
					"Config change: unfix the dimens so I'll get remeasured!");
			dialogView.unfixDialogDimens();
		}
		super.onConfigurationChanged(newConfig);
		if (applyFixes) {
			dialogView.post(new Runnable() {
				@Override
				public void run() {
					Log.d(TAG, "Config change done: re-fix the dimens!");
					dialogView.fixDialogDimens();
				}
			});
		}
	}
}
