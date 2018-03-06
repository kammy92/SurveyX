package com.resultier.surveyx.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.resultier.surveyx.R;
import com.resultier.surveyx.dialog.DefaultSurveyDialogFragment;
import com.resultier.surveyx.utils.UserDetailsPref;
import com.resultier.surveyx.utils.Utils;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_LOGIN_SCREEN_RESULT = 2;
    UserDetailsPref userDetailsPref;
    TextView tvDay;
    TextView tvDate;
    TextView tvProductCode;
    TextView tvEndDay;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
        initData ();
        initListener ();
        isLogin ();
    }
    
    private void initView () {
        tvDay = (TextView) findViewById (R.id.tvDay);
        tvDate = (TextView) findViewById (R.id.tvDate);
        tvProductCode = (TextView) findViewById (R.id.tvProductCode);
        tvEndDay = (TextView) findViewById (R.id.tvEndDay);
    }
    
    private void initData () {
        Utils.setTypefaceToAllViews (this, tvDay);
    }
    
    private void initListener () {
        tvEndDay.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                FragmentTransaction ft = getFragmentManager ().beginTransaction ();
                DefaultSurveyDialogFragment frag = DefaultSurveyDialogFragment.newInstance ();
                frag.show (ft, "4");
            }
        });
    }
    
    private void isLogin () {
        userDetailsPref = UserDetailsPref.getInstance ();
        if (userDetailsPref.getStringPref (MainActivity.this, UserDetailsPref.USER_LOGIN_KEY).length () == 0) {
            Intent intent = new Intent (MainActivity.this, LoginActivity.class);
            startActivityForResult (intent, REQUEST_LOGIN_SCREEN_RESULT);
            overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
    
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN_SCREEN_RESULT) {
            if (data.getBooleanExtra ("LOGIN", false)) {
//                initFirstFragment ();
//                initApplication ();
            } else {
                finish ();
            }
        }
    }
}