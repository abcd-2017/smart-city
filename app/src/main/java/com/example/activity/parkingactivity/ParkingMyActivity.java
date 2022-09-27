package com.example.activity.parkingactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;

/**
 * @author kkk
 */
public class ParkingMyActivity extends BaseActivity {

    private Toolbar parkingToolbar;
    private ImageView finishBtn;
    private ConstraintLayout rechargeBtn, integralBtn, myCarBtn, correctionBtn;

    @Override
    protected int initLayout() {
        return R.layout.activity_parking_my;
    }

    @Override
    protected void initView() {
        parkingToolbar = findViewById(R.id.parking_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        rechargeBtn = findViewById(R.id.recharge_btn);
        integralBtn = findViewById(R.id.integral_btn);
        myCarBtn = findViewById(R.id.my_car_btn);
        correctionBtn = findViewById(R.id.correction_btn);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        rechargeBtn.setOnClickListener(view -> {
            jumpPage(ParkingLotReChargeActivity.class);
        });
        integralBtn.setOnClickListener(view -> {
            jumpPage(ParkingIntegralActivity.class);
        });
        myCarBtn.setOnClickListener(view -> {
            jumpPage(ParkingMyCarActivity.class);
        });
        correctionBtn.setOnClickListener(view -> {
            jumpPage(ParkingCorrectionActivity.class);
        });
    }
}