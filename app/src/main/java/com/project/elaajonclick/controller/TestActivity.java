package com.project.elaajonclick.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.elaajonclick.R;

import com.project.elaajonclick.model.Common.Common;
import com.project.elaajonclick.model.Common.NonSwipeViewPager;
import com.project.elaajonclick.model.adapter.MyViewPagerAdapter;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Unbinder;

import static com.project.elaajonclick.model.Common.Common.step;
import static com.project.elaajonclick.model.fragment.BookingStep1Fragment.spinner;

public class TestActivity extends AppCompatActivity {

    StepView stepView;
    NonSwipeViewPager viewPager;
    Button btn_previous_step;
    Button btn_next_step;
    LocalBroadcastManager localBroadcastManager;
    private final BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (step == 2) {
                Common.currentTimeSlot = intent.getIntExtra(Common.KEY_TIME_SLOT, -1);
            }
            btn_next_step.setEnabled(true);
            setColorButton();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        stepView = findViewById(R.id.step_view);
        viewPager = findViewById(R.id.view_pager);
        btn_next_step = findViewById(R.id.btn_next_step);
        btn_previous_step = findViewById(R.id.btn_previous_step);

        setupStepView();
        setColorButton();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver, new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setColorButton();

            }

            @Override
            public void onPageSelected(int position) {
                stepView.go(position, true);
                btn_previous_step.setEnabled(position != 0);
                btn_next_step.setEnabled(position != 2);


                setColorButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btn_next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (step < 3 || step == 0) {
                    step++;
                    Common.currentAppointmentType = spinner.getSelectedItem().toString();
                    Log.e("Spinner", Common.currentAppointmentType);

                    if (step == 1) {
                        if (Common.currentDoctor != null) {
                            Common.currentTimeSlot = -1;
                            Common.currentDate = Calendar.getInstance();
                            loadTimeSlotOfDoctor(Common.currentDoctor);
                        }
                    } else if (step == 2) {
                        // if(Common.currentTimeSlot != -1)
                        confirmBooking();
                    }
                    viewPager.setCurrentItem(step);
                }


            }
        });
        btn_previous_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (step == 3 || step > 0) {
                    step--;
                    viewPager.setCurrentItem(step);
                }
            }
        });

        loadTimeSlotOfDoctor("testdoc@testdoc.com");
    }


    private void confirmBooking() {

        Intent intent = new Intent(Common.KEY_CONFIRM_BOOKING);
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        step = 0;
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }

    private void loadTimeSlotOfDoctor(String doctorId) {
        Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void setColorButton() {
        if (btn_previous_step.isEnabled()) {
            btn_previous_step.setBackgroundResource(R.color.design_default_color_primary_dark);
        } else {
            btn_previous_step.setBackgroundResource(R.color.colorAccent);
        }
        if (btn_next_step.isEnabled()) {
            btn_next_step.setBackgroundResource(R.color.design_default_color_primary_dark);
        } else {
            btn_next_step.setBackgroundResource(R.color.colorAccent);
        }
    }

    private void setupStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Purpose");
        stepList.add("Time and Date");
        stepList.add("Finish");
        stepView.setSteps(stepList);

    }

}
