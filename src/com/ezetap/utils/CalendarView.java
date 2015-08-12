package com.ezetap.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.ezetap.android.utils.UIUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarView extends Activity {

	public GregorianCalendar month, itemmonth;// calendar instances.

	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker
	ArrayList<String> event;
	LinearLayout rLayout;
	ArrayList<String> date;
	ArrayList<String> desc;
	/**
     * The header with week day names.
     */
    private ViewGroup mDayNamesHeader;

    /**
     * Cached labels for the week names header.
     */
    private String[] mDayLabels;
    /**
     * The number of day per week to be shown.
     */
    private int mDaysPerWeek = 7;

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(getResources().getIdentifier("calendar", "layout", getPackageName()));
		Locale.setDefault(Locale.US);

		rLayout = (LinearLayout) findViewById(getResources().getIdentifier("text", "id", getPackageName()));
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		items = new ArrayList<String>();

		adapter = new CalendarAdapter(this, month);

		GridView gridview = (GridView) findViewById(getResources().getIdentifier("gridview", "id", getPackageName()));
		gridview.setAdapter(adapter);

		handler = new Handler();
		handler.post(calendarUpdater);

		TextView title = (TextView) findViewById(getResources().getIdentifier("title", "id", getPackageName()));
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = (RelativeLayout) findViewById(getResources().getIdentifier("previous", "id", getPackageName()));

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) findViewById(getResources().getIdentifier("next", "id", getPackageName()));
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// removing the previous view if added
				if (((LinearLayout) rLayout).getChildCount() > 0) {
					((LinearLayout) rLayout).removeAllViews();
				}
				desc = new ArrayList<String>();
				date = new ArrayList<String>();
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				final String selectedGridDate = CalendarAdapter.dayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				} else {
					final Dialog dialog = new Dialog(CalendarView.this);
				    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				    dialog.setCancelable(false);
					dialog.setContentView(CalendarView.this.getResources().getIdentifier("custom_alert_dialog", "layout", CalendarView.this.getPackageName()));
					// set the title
					TextView title = (TextView) dialog.findViewById(CalendarView.this.getResources().getIdentifier("dialogTitle", "id", CalendarView.this.getPackageName()));
					title.setVisibility(View.GONE);
					// set the custom dialog components - text, image and button
					MyTextView text = (MyTextView) dialog.findViewById(CalendarView.this.getResources().getIdentifier("dialogText", "id", CalendarView.this.getPackageName()));
					text.setText("Is selected date " + selectedGridDate + "?");
					// if left button is clicked
					Button leftButton = (Button) dialog.findViewById(CalendarView.this.getResources().getIdentifier("leftButton", "id", CalendarView.this.getPackageName()));
					leftButton.setText("No");
					leftButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					
					// if right button is clicked
					Button rightButton = (Button) dialog.findViewById(CalendarView.this.getResources().getIdentifier("rightButton", "id", CalendarView.this.getPackageName()));
					rightButton.setText("Yes");
					rightButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							JSONObject json = new JSONObject();
							try {
								json.put(EzeConstants.KEY_START_DATE, selectedGridDate);
								json.put(EzeConstants.KEY_END_DATE, selectedGridDate);
							} catch (JSONException e) {
							}
							intent.putExtra(EzeConstants.KEY_RESPONSE_DATA, json.toString());
							dialog.dismiss();
							setResult(2001, intent);
							finish();
						}
					});
					dialog.show();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

				for (int i = 0; i < Utility.startDates.size(); i++) {
					if (Utility.startDates.get(i).equals(selectedGridDate)) {
						desc.add(Utility.nameOfEvent.get(i));
					}
				}

				if (desc.size() > 0) {
					for (int i = 0; i < desc.size(); i++) {
						TextView rowTextView = new TextView(CalendarView.this);

						// set some properties of rowTextView or something
						rowTextView.setText("Event:" + desc.get(i));
						rowTextView.setTextColor(Color.BLACK);

						// add the textview to the linearlayout
						rLayout.addView(rowTextView);

					}

				}

				desc = null;

			}

		});
        mDayNamesHeader = (ViewGroup) findViewById(getResources().getIdentifier("cv_day_names", "id", getPackageName()));
        setUpHeader();

	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(getResources().getIdentifier("title", "id", getPackageName()));

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items
		setUpHeader();
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
		
	}
	
	private void setUpHeader() {
		// month start day. ie; sun, mon, etc
        mDayLabels = new String[mDaysPerWeek];
        for (int i = 1, count = mDaysPerWeek; i < count + 1; i++) {
            int calendarDay = (i > Calendar.SATURDAY) ? i - Calendar.SATURDAY : i;
            mDayLabels[i-1] = DateUtils.getDayOfWeekString(calendarDay,
                    DateUtils.LENGTH_SHORTEST);
        }

        TextView label;
        for (int i = 0, count = mDayNamesHeader.getChildCount(); i < count; i++) {
            label = (TextView) mDayNamesHeader.getChildAt(i);
            if (i < mDaysPerWeek + 1) {
                label.setText(mDayLabels[i]);
                label.setVisibility(View.VISIBLE);
            } else {
                label.setVisibility(View.GONE);
            }
        }
        mDayNamesHeader.invalidate();
    }

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String itemvalue;
//			event = Utility.readCalendarEvent(CalendarView.this);
//			Log.d("=====Event====", event.toString());
//			Log.d("=====Date ARRAY====", Utility.startDates.toString());

			for (int i = 0; i < Utility.startDates.size(); i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add(Utility.startDates.get(i).toString());
			}
			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
	
	public void onBackPressed(){
		setResult(3001);
		finish();
	}
}
