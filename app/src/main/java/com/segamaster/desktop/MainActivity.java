package com.segamaster.desktop;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends Activity {
    private final Handler clockHandler = new Handler(Looper.getMainLooper());
    private final String[] zoneIds = {
            TimeZone.getDefault().getID(),
            "UTC",
            "America/New_York",
            "Europe/London",
            "Asia/Tokyo",
            "Australia/Sydney"
    };
    private final String[] zoneNames = {
            "LOCAL TIME",
            "UTC",
            "NEW YORK",
            "LONDON",
            "TOKYO",
            "SYDNEY"
    };
    private final TextView[] timeViews = new TextView[zoneIds.length];
    private final TextView[] dateViews = new TextView[zoneIds.length];
    private final Runnable ticker = new Runnable() {
        @Override
        public void run() {
            updateClocks();
            clockHandler.postDelayed(this, 1000L);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(11, 11, 11));
        setContentView(buildClockScreen());
    }

    @Override
    protected void onStart() {
        super.onStart();
        clockHandler.post(ticker);
    }

    @Override
    protected void onStop() {
        clockHandler.removeCallbacks(ticker);
        super.onStop();
    }

    private View buildClockScreen() {
        int padding = dp(20);
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(padding, dp(28), padding, padding);
        content.setBackgroundColor(Color.rgb(11, 11, 11));

        TextView title = label("WORLD CLOCK", 28, Color.WHITE);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        content.addView(title, new LinearLayout.LayoutParams(-1, -2));

        TextView subtitle = label("CURRENT TIME AROUND THE WORLD", 12, Color.rgb(145, 145, 145));
        LinearLayout.LayoutParams subtitleParams = new LinearLayout.LayoutParams(-1, -2);
        subtitleParams.topMargin = dp(6);
        subtitleParams.bottomMargin = dp(22);
        content.addView(subtitle, subtitleParams);

        for (int i = 0; i < zoneIds.length; i++) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(dp(18), dp(14), dp(18), dp(14));
            card.setBackgroundColor(Color.rgb(28, 28, 28));

            TextView name = label(zoneNames[i], 13, Color.rgb(170, 170, 170));
            name.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            card.addView(name, new LinearLayout.LayoutParams(-1, -2));

            timeViews[i] = label("--:--:--", 38, Color.rgb(255, 196, 0));
            timeViews[i].setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(-1, -2);
            timeParams.topMargin = dp(3);
            card.addView(timeViews[i], timeParams);

            dateViews[i] = label("", 13, Color.rgb(145, 145, 145));
            card.addView(dateViews[i], new LinearLayout.LayoutParams(-1, -2));

            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(-1, -2);
            cardParams.bottomMargin = dp(12);
            content.addView(card, cardParams);
        }

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.addView(content);
        return scrollView;
    }

    private void updateClocks() {
        Date now = new Date();
        for (int i = 0; i < zoneIds.length; i++) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
            timeFormat.setTimeZone(TimeZone.getTimeZone(zoneIds[i]));
            timeViews[i].setText(timeFormat.format(now));

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d yyyy  z", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone(zoneIds[i]));
            dateViews[i].setText(dateFormat.format(now));
        }
    }

    private TextView label(String text, int sizeSp, int color) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(sizeSp);
        view.setTextColor(color);
        view.setGravity(Gravity.START);
        return view;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
