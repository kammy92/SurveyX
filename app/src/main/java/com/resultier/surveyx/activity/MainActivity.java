package com.resultier.surveyx.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resultier.surveyx.R;
import com.resultier.surveyx.dialog.FinalSurveyDialogFragment;
import com.resultier.surveyx.dialog.SurveyDialogFragment;
import com.resultier.surveyx.utils.UserDetailsPref;
import com.resultier.surveyx.utils.Utils;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_LOGIN_SCREEN_RESULT = 2;
    UserDetailsPref userDetailsPref;
    TextView tvDay;
    TextView tvDate;
    TextView tvProductCode;
    TextView tvEndDay;
    
    int button1 = 0; // Pouch Product Provided
    int button2 = 0; // Your Own Loose Product
    int button3 = 0; // Different Tobacco Product
    int button4 = 0; // Your Own Pouch Product
    
    
    RelativeLayout rlButton1, rlButton2, rlButton3, rlButton4;
    TextView tvButton1, tvButton2, tvButton3, tvButton4;
    
    
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
        rlButton1 = (RelativeLayout) findViewById (R.id.rlButton1);
        rlButton2 = (RelativeLayout) findViewById (R.id.rlButton2);
        rlButton3 = (RelativeLayout) findViewById (R.id.rlButton3);
        rlButton4 = (RelativeLayout) findViewById (R.id.rlButton4);
    
        tvButton1 = (TextView) findViewById (R.id.tvNumber1);
        tvButton2 = (TextView) findViewById (R.id.tvNumber2);
        tvButton3 = (TextView) findViewById (R.id.tvNumber3);
        tvButton4 = (TextView) findViewById (R.id.tvNumber4);
    
    }
    
    private void initData () {
        Utils.setTypefaceToAllViews (this, tvDay);
    }
    
    private void initListener () {
        tvProductCode.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                FragmentTransaction ft = getFragmentManager ().beginTransaction ();
                FinalSurveyDialogFragment frag = FinalSurveyDialogFragment.newInstance ();
                frag.show (ft, "4");
            }
        });
        
        rlButton1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button1++;
                tvButton1.setText (String.valueOf (button1));
                tvButton1.setVisibility (View.VISIBLE);
            }
        });
    
        rlButton2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button2++;
                tvButton2.setText (String.valueOf (button2));
                tvButton2.setVisibility (View.VISIBLE);
            }
        });
    
        rlButton3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button3++;
                tvButton3.setText (String.valueOf (button3));
                tvButton3.setVisibility (View.VISIBLE);
            }
        });
    
        rlButton4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button4++;
                tvButton4.setText (String.valueOf (button4));
                tvButton4.setVisibility (View.VISIBLE);
            }
        });
    
        tvEndDay.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                FragmentTransaction ft = getFragmentManager ().beginTransaction ();
                SurveyDialogFragment frag = SurveyDialogFragment.newInstance (button1, button2, button3, button4);
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