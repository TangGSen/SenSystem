package sen.com.senboss.calender;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sen.com.senboss.R;

/**
 * Created by winson on 2015/2/22.
 */
public class CalendarDialog {
    public static final String SHARED_PREFER = "winson_calendar_view";



	/**
	 * 创建一个日历选择对话框
	 *
	 * @param context
	 * @param maxYear
	 *            最大的年份
	 * @param minYear
	 *            最小的年份
	 * @param selectY
	 *            初始化的年
	 * @param selectM
	 *            初始化的月
	 * @param selectD
	 *            初始化的日
	 * @param listener
	 *            回调
	 * @return
	 */
	public AlertDialog getCalendarDialog(final Context context,
			final boolean isShowLunar, final boolean isSelectLunar,
			final int maxYear, final int minYear, final int selectY, final int selectM, int selectD,
			final OnSelectDateListener listener) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		final AlertDialog alertDialog = builder.create();
		alertDialog.setCancelable(false);
		View parentView = View.inflate(context, R.layout.canlendar, null);
		final TextView titleTv = (TextView) parentView
				.findViewById(R.id.calendar_title_tv);
		final ViewPager calendarViewPager = (ViewPager) parentView
				.findViewById(R.id.calendar_viewpager);
		final GridView yearGridView = (GridView) parentView
				.findViewById(R.id.year_gridview);
		final ToggleButton toggleButton = (ToggleButton) parentView
				.findViewById(R.id.calendar_type_switch);
		final Button todayButton = (Button) parentView
				.findViewById(R.id.calendar_today_btn);
		final LinearLayout weekTitleLayout = (LinearLayout) parentView
				.findViewById(R.id.calender_week_title_tv);

		final List<YearAndMonth> timeData = new ArrayList<YearAndMonth>();

		int currentPagerPos = 0;

		// 加入所有范围内的年月
		for (int i = minYear; i <= maxYear; i++) {
			for (int j = 0; j < 12; j++) {
				YearAndMonth ym = new YearAndMonth();
				ym.setMonth(j);
				ym.setYear(i);
				timeData.add(ym);
				if (selectY == i && selectM == j) {
					// 算出本月所在位置
					currentPagerPos = timeData.size() - 1;
				}
				;
			}
		}

		if (!isShowLunar) {
			toggleButton.setVisibility(View.INVISIBLE);
		}

		final SharedPreferences sp = context.getSharedPreferences(
				SHARED_PREFER, Context.MODE_PRIVATE);
		if (isSelectLunar) {
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean("isSelectLunar", isSelectLunar);
			editor.apply();
		}
		toggleButton.setChecked(isSelectLunar);


		// 点击今日按钮
		todayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					boolean isLunar = context.getSharedPreferences(
							SHARED_PREFER, Context.MODE_PRIVATE)
							.getBoolean("isSelectLunar", false);
					Calendar calendar = Calendar.getInstance();
					long time = calendar.getTimeInMillis();
					calendar.setTimeInMillis(time);
					if (isLunar && isShowLunar) {
						int[] date = LunarCalendarUtil.solarToLunar(
								calendar.get(Calendar.YEAR),
								calendar.get(Calendar.MONTH) + 1,
								calendar.get(Calendar.DAY_OF_MONTH));
						listener.onSelectDate(time, date[0], date[1] - 1, date[2], true);
					} else {

						listener.onSelectDate(time,
								calendar.get(Calendar.YEAR),
								calendar.get(Calendar.MONTH),
								calendar.get(Calendar.DAY_OF_MONTH), false);
					}
					alertDialog.dismiss();
				}
			}
		});

		final CalendarPagerAdapter calendarPagerAdapter = new CalendarPagerAdapter(
				context, isShowLunar, isSelectLunar, timeData, selectY,
				selectM, selectD, alertDialog, listener);
		calendarViewPager.setAdapter(calendarPagerAdapter);
		// 切换到所指定的日期所在位置
		calendarViewPager.setCurrentItem(currentPagerPos, false);
		// 显示标题

		YearAndMonth yearAndMonth = timeData.get(calendarViewPager
				.getCurrentItem());
		titleTv.setText(yearAndMonth.getYear() + "年"
				+ (yearAndMonth.getMonth() + 1) + "月");

		if (isShowLunar) {
			calendarViewPager
					.setLayoutParams(new LinearLayout.LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT,
							(int) TypedValue
									.applyDimension(
											TypedValue.COMPLEX_UNIT_DIP, 250,
											context.getResources()
													.getDisplayMetrics())));
		}

		calendarViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {

					}

					@Override
					public void onPageSelected(int position) {
						YearAndMonth yearAndMonth = timeData
								.get(calendarViewPager.getCurrentItem());
						titleTv.setText(yearAndMonth.getYear() + "年"
								+ (yearAndMonth.getMonth() + 1) + "月");
					}

					@Override
					public void onPageScrollStateChanged(int state) {

					}
				});

		// 是否为nongli
		// toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener()
		// {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView, boolean
		// isChecked) {
		// // TODO Auto-generated method stub
		// calendarViewPager.getCal
		// }
		// });

        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isSelectLunar", isChecked);
                editor.commit();
                for(CalendarAdapter adapter : calendarPagerAdapter.getCalendarAdapterList()) {
                    if(adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

		// 年份gridView
		final List<Integer> years = new ArrayList<Integer>();
		final YearAdapter yearAdapter = new YearAdapter(context, years);
		yearGridView.setAdapter(yearAdapter);
		for (int i = maxYear; i >= minYear; i--) {
			years.add(i);
		}
		yearAdapter.notifyDataSetChanged();
		yearGridView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						int movePos = 0;
						YearAndMonth ym = null;
						for (int i = 0; i < timeData.size(); i++) {
							ym = timeData.get(i);
							if (ym.getYear() == years.get(position)
									&& ym.getMonth() == timeData.get(
											calendarViewPager.getCurrentItem())
											.getMonth()) {
								movePos = i;
								break;
							}
						}
						yearGridView.setVisibility(View.GONE);
						calendarViewPager.setVisibility(View.VISIBLE);
						weekTitleLayout.setVisibility(View.VISIBLE);
						calendarViewPager.setCurrentItem(movePos, false);
						titleTv.setText(ym.getYear() + "年"
								+ (ym.getMonth() + 1) + "月");
					}
				});

		// 点击显示
		titleTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (yearGridView.getVisibility() == View.VISIBLE) {
					yearGridView.setVisibility(View.GONE);
					weekTitleLayout.setVisibility(View.VISIBLE);
					calendarViewPager.setVisibility(View.VISIBLE);
				} else {
					yearGridView.setVisibility(View.VISIBLE);
					weekTitleLayout.setVisibility(View.GONE);
					calendarViewPager.setVisibility(View.GONE);
				}
			}
		});

		alertDialog.setView(parentView);
		return alertDialog;
	}

	public interface OnSelectDateListener {
		public void onSelectDate(long time, int year, int month, int day,
								 boolean isLunar);
	}
}
