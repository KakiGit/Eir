package hackton.health.eir;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by wanghongkuan on 2019/3/29.
 */

public class TodoView extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    private CalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mEvents;

    private Date startDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        mEvents = new ArrayList<>();
        mEvents.add("check head");
        mEvents.add("check body");

        startDate = new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime();

        mCalendarView = findViewById(R.id.calendarView);
        mRecyclerView = findViewById(R.id.recycler_view);
        final TextView popup = findViewById(R.id.popup_window);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + i2 + "/" + i;

                Date myDate = parseDate(i + "-" + (i1 + 1) +"-" + i2);
                Long chosenDate = myDate.getTime();
                Long startTime = startDate.getTime();
//                Log.d(TAG, "" + chosenDate);
//                Log.d(TAG, "" + startTime);
//                Log.d(TAG, "" + chooseGroupBefore(chosenDate, startTime));

                int index;
                popup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popup.getVisibility() == TextView.VISIBLE) {
                            popup.setVisibility(TextView.INVISIBLE);
                        }
                    }
                });

                if (chosenDate < startTime + (long) (40.0*7*24*3600*1000)) {
                    index = chooseGroupBefore(chosenDate, startTime);
                    if (index > -1) {
                        popup.setText(TestData.healthCheck[index]);
                        popup.setVisibility(TextView.VISIBLE);
//                        Toast.makeText(mCalendarView.getContext(), TestData.healthCheck[index], Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(TodoView.this, Popup.class);
//                        intent.putExtra("info", TestData.healthCheck[index]);
//                        startActivity(intent);
                    }
                } else {
                    index = chooseGroupAfter(chosenDate, startTime);
                    if (index > -1) {
//                        Toast.makeText(mCalendarView.getContext(), TestData.healthCheckafter[index], Toast.LENGTH_LONG).show();
                        popup.setText(TestData.healthCheckafter[index]);
                        popup.setVisibility(TextView.VISIBLE);
                    }
                }

            }
        });

        TodoViewPagerAdapter adapter = new TodoViewPagerAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public int chooseGroupBefore(Long str, Long startTime) {
        if (str >= startTime +(long)( 6*7.0*24*3600*1000) &&  str < startTime + (long)( 8*7.0*24*3600*1000)) {
            return 0;
        } else if (str >= startTime +(long)( 8*7.0*24*3600*1000) &&  str < startTime + (long)(10*7.0*24*3600*1000)) {
            return 1;
        } else if (str >= startTime + (long)(13*7.0*24*3600*1000) &&  str < startTime + (long)(18*7.0*24*3600*1000)) {
            return 2;
        } else if (str >= startTime + (long)(22*7.0*24*3600*1000) &&  str < startTime + (long)(24*7.0*24*3600*1000)) {
            return 3;
        } else if (str == startTime + (long)(22*7.0*24*3600*1000)) {
            return 4;
        } else if (str >= startTime + (long)(26*7.0*24*3600*1000) &&  str < startTime + (long)(28*7.0*24*3600*1000)) {
            return 5;
        } else if (str >= startTime + (long)(30*7.0*24*3600*1000) &&  str < startTime + (long)(32*7.0*24*3600*1000)) {
            return 6;
        } else if (str >= startTime + (long)(32*7.0*24*3600*1000) &&  str < startTime + (long)(35*7.0*24*3600*1000)) {
            return 7;
        } else if (str >= startTime + (long)(37*7.0*24*3600*1000) &&  str < startTime + (long)(40*7.0*24*3600*1000)) {
            return 8;
        }
        return -1;
    }

    public int chooseGroupAfter(Long str, Long startTime) {
        if (str >= startTime + (long)(40*7.0*24*3600*1000) &&  str < startTime + (long)(41*7.0*24*3600*1000)) {
            return 0;
        } else if (str >= startTime + (long)(45*7.0*24*3600*1000) &&  str < startTime + (long)(52*7.0*24*3600*1000)) {
            return 1;
        } else if (str >= startTime + (long)(52*7.0*24*3600*1000) &&  str < startTime + (long)(53*7.0*24*3600*1000)) {
            return 2;
        }
        return -1;
    }


}
