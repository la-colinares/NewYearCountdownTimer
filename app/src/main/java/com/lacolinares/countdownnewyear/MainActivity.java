package com.lacolinares.countdownnewyear;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.lacolinares.dynatime.DynaTime;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txt_countdown)
    TextView txtCountdown;
    @BindView(R.id.txt_days)
    TextView txtDays;
    @BindView(R.id.txt_time)
    TextView txtTime;

    private Context mContext;

    private DynaTime dynaTime;
    private int mNextYear = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        mNextYear = getNextYear();
        txtCountdown.setText(MessageFormat.format("{0} New Year Countdown", mNextYear));
        setCountDownTimer();
    }

    //This method will get the current year and increment it by 1
    private int getNextYear() {
        Calendar calendar = Calendar.getInstance(Locale.US);
        int year = calendar.get(Calendar.YEAR);
        year++;
        return year;
    }

    // Setup Countdown timer and make it start
    private void setCountDownTimer() {
        Calendar start_calendar = Calendar.getInstance();
        Calendar end_calendar = Calendar.getInstance();
        //set new year date
        end_calendar.set(mNextYear, 0, 1, 0, 0, 0);

        long start_millis = start_calendar.getTimeInMillis();
        long end_millis = end_calendar.getTimeInMillis();

        long total_millis = end_millis - start_millis;

        dynaTime = new DynaTime(total_millis, 1000) {
            @Override
            public void onTimerStart(long l) {
                long days = TimeUnit.MILLISECONDS.toDays(l);
                l -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS.toHours(l);
                l -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(l);
                l -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(l);

                String time;
                String finalHours;
                String finalMinutes;
                String finalSeconds;
                if (hours < 10) finalHours = "0" + hours;
                else finalHours = hours + "";

                if (minutes < 10) finalMinutes = "0" + minutes;
                else finalMinutes = minutes + "";

                if (seconds < 10) finalSeconds = "0" + seconds;
                else finalSeconds = seconds + "";

                time = finalHours + ":" + finalMinutes + ":" + finalSeconds;
                txtDays.setText(days + "");
                txtTime.setText(time);
            }

            @Override
            public void onFinish() {
                mNextYear = 0;
                //Toast.makeText(mContext, "Happy New Year", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mContext, HappyNewYear.class));
            }
        };
        dynaTime.startTimer();
    }

    private void reset(){
        dynaTime.stopTimer();
        mNextYear = 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        reset();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
    }

    @Override
    protected void onPause() {
        super.onPause();
        reset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dynaTime.stopTimer();
        mNextYear = getNextYear();
        txtCountdown.setText(mNextYear + " New Year Countdown");
        setCountDownTimer();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
