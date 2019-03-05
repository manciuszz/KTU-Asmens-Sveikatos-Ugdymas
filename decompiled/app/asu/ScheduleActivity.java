package app.asu;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import app.asu.IcsDownloader.OnScheduleDownloaded;
import app.asu.Models.CalendarModel;
import app.asu.Models.LectureModel;
import app.asu.Models.WeekModel;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ScheduleActivity extends NavigationActivity implements OnScheduleDownloaded {
    private CalendarModel cModel;

    @SuppressLint({"ValidFragment"})
    public class ScheduleFragment extends Fragment {
        private WeekModel week;

        public ScheduleFragment(WeekModel week) {
            this.week = week;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_schedule, container, false);
            RelativeLayout tuesday = (RelativeLayout) view.findViewById(R.id.tuesday);
            RelativeLayout wednesday = (RelativeLayout) view.findViewById(R.id.wednesday);
            RelativeLayout thursday = (RelativeLayout) view.findViewById(R.id.thursday);
            RelativeLayout friday = (RelativeLayout) view.findViewById(R.id.friday);
            ScheduleActivity.this.addLectures(this.week.getMonday_lectures(), (RelativeLayout) view.findViewById(R.id.monday));
            ScheduleActivity.this.addLectures(this.week.getTuesday_lectures(), tuesday);
            ScheduleActivity.this.addLectures(this.week.getWednesday_lectures(), wednesday);
            ScheduleActivity.this.addLectures(this.week.getThursday_lectures(), thursday);
            ScheduleActivity.this.addLectures(this.week.getFriday_lectures(), friday);
            return view;
        }
    }

    private class CustomPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments = new ArrayList();
        private List<Long> lectures_weeks;

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
            this.lectures_weeks = ScheduleActivity.this.cModel.getSemester_weeks();
            Collections.sort(this.lectures_weeks);
            long week_start = ScheduleActivity.this.cModel.getFirstSemesterWeek();
            for (int i = 0; i < ScheduleActivity.this.cModel.getSemesterWeeksCount(); i++) {
                this.fragments.add(new ScheduleFragment(new WeekModel((((long) i) * StaticMethods.WEEK_LONG) + week_start, ScheduleActivity.this.cModel)));
            }
        }

        public Fragment getItem(int position) {
            return (Fragment) this.fragments.get(position);
        }

        public int getCount() {
            return this.fragments.size();
        }

        public CharSequence getPageTitle(int position) {
            return formatPagerTitle(((Long) this.lectures_weeks.get(position)).longValue());
        }

        private String formatPagerTitle(long week_start) {
            Date start;
            Date end;
            if (Calendar.getInstance().getFirstDayOfWeek() == 2) {
                start = new Date(week_start - StaticMethods.DAY_LONG);
                end = new Date(259200000 + week_start);
            } else {
                start = new Date(week_start);
                end = new Date(345600000 + week_start);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
            sdf.setTimeZone(TimeZone.getTimeZone("EET"));
            Log.e("ff", "pos: " + week_start + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + sdf.format(start) + " - " + sdf.format(end));
            return sdf.format(start) + " - " + sdf.format(end);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(5);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        this.cModel = new CalendarModel(this);
        if (isScheduleDownloaded()) {
            createCalendarView();
        } else if (StaticMethods.isNetworkConnected(this)) {
            new IcsDownloader(this, this).execute(new Void[0]);
        } else {
            notConnectedDialog();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update /*2131361984*/:
                if (StaticMethods.isNetworkConnected(this)) {
                    setProgressBarIndeterminate(true);
                    setProgressBarIndeterminateVisibility(true);
                    new IcsDownloader(this, this).execute(new Void[0]);
                    return true;
                }
                notConnectedDialog();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onScheduleDownloaded() {
        createCalendarView();
    }

    public void onScheduleDownloadError() {
        Toast.makeText(this, getString(R.string.schedule_download_error), 1).show();
        setProgressBarIndeterminate(false);
        setProgressBarIndeterminateVisibility(false);
    }

    private void createCalendarView() {
        setProgressBarIndeterminateVisibility(true);
        new Thread(new Runnable() {
            public void run() {
                ScheduleActivity.this.cModel.parseCalendar();
                ScheduleActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        ViewPager mViewPager = (ViewPager) ScheduleActivity.this.findViewById(R.id.pager);
                        mViewPager.setAdapter(new CustomPagerAdapter(ScheduleActivity.this.getFragmentManager()));
                        mViewPager.setCurrentItem(ScheduleActivity.this.getThisWeekNumber(ScheduleActivity.this.cModel.getFirstSemesterWeek()));
                        if (ScheduleActivity.this.cModel.getSemester_weeks().size() == 0) {
                            ((TextView) ScheduleActivity.this.findViewById(R.id.empty_schedule)).setText(ScheduleActivity.this.getString(R.string.schedule_empty));
                        }
                        ScheduleActivity.this.setProgressBarIndeterminate(false);
                        ScheduleActivity.this.setProgressBarIndeterminateVisibility(false);
                    }
                });
            }
        }).start();
    }

    private boolean isScheduleDownloaded() {
        return new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/schedule.ics").exists();
    }

    private View makeLectureGridView(final LectureModel lecture) {
        TextView lecture_view = new TextView(this);
        lecture_view.setText(Html.fromHtml("<small>" + lecture.getLecture() + "</small>"));
        lecture_view.setGravity(17);
        LayoutParams params = new LayoutParams(-1, StaticMethods.toDp(this, lecture.getDuration()));
        params.topMargin = startTimeInDp(lecture.getStartTime());
        lecture_view.setLayoutParams(params);
        lecture_view.setClickable(true);
        lecture_view.setBackgroundResource(getLectureColor(lecture.getCategory()));
        lecture_view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ScheduleActivity.this);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.dialog_grid_popup);
                TextView lecture_code = (TextView) dialog.findViewById(R.id.lecture_code);
                TextView category = (TextView) dialog.findViewById(R.id.category);
                TextView auditorium = (TextView) dialog.findViewById(R.id.auditorium);
                TextView lecture_time = (TextView) dialog.findViewById(R.id.lecture_time);
                TextView lecturer = (TextView) dialog.findViewById(R.id.lecturer);
                ((TextView) dialog.findViewById(R.id.lecture)).setText(lecture.getLecture());
                lecture_code.setText(lecture.getLecture_code());
                category.setText(lecture.getCategory());
                auditorium.setText(lecture.getPlace());
                lecture_time.setText(lecture.getLectureTime());
                if (!ScheduleActivity.this.getString(R.string.lecturer).equals(lecture.getLecturer().trim())) {
                    dialog.findViewById(R.id.lecturer_title).setVisibility(0);
                    lecturer.setVisibility(0);
                    lecturer.setText(lecture.getLecturer().substring(13));
                }
                ((Button) dialog.findViewById(R.id.back)).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return lecture_view;
    }

    private int getLectureColor(String category) {
        if (category.equals(getString(R.string.theory))) {
            return R.drawable.lecture_theory;
        }
        if (category.equals(getString(R.string.practice))) {
            return R.drawable.lecture_practice;
        }
        if (category.equals(getString(R.string.laboratory))) {
            return R.drawable.lecture_laboratory;
        }
        if (category.equals(getString(R.string.exam)) || category.equals(getString(R.string.examRetake))) {
            return R.drawable.lecture_exam;
        }
        return R.drawable.lecture_selector;
    }

    private void addLectures(List<LectureModel> lectures, RelativeLayout parentView) {
        for (LectureModel lecture : lectures) {
            parentView.addView(makeLectureGridView(lecture));
        }
    }

    private int startTimeInDp(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("EET"));
        String[] splitedDate = sdf.format(date).split(":");
        int hours = Integer.parseInt(splitedDate[0]);
        return StaticMethods.toDp(this, ((hours * 60) + Integer.parseInt(splitedDate[1])) - 510);
    }

    private long getThisWeekStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.clear(12);
        cal.clear(13);
        cal.clear(14);
        cal.setFirstDayOfWeek(2);
        return cal.getTimeInMillis();
    }

    private int getThisWeekNumber(long start_week) {
        int number = (int) ((getThisWeekStartTime() - start_week) / StaticMethods.WEEK_LONG);
        return number >= 0 ? number : 0;
    }

    private void notConnectedDialog() {
        new Builder(this).setTitle(getString(R.string.error)).setMessage(getString(R.string.schedule_not_downloaded)).setNeutralButton(17039379, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setIcon(17301543).show();
    }
}
