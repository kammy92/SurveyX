package com.resultier.surveyx.activity;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resultier.surveyx.R;
import com.resultier.surveyx.dialog.FinalSurveyDialogFragment;
import com.resultier.surveyx.dialog.SurveyDialogFragment;
import com.resultier.surveyx.service.AlarmService;
import com.resultier.surveyx.utils.UserDetailsPref;
import com.resultier.surveyx.utils.Utils;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_LOGIN_SCREEN_RESULT = 2;
    UserDetailsPref userDetailsPref;
    TextView tvDay;
    TextView tvDate;
    TextView tvProductCode;
    RelativeLayout rlEndDay;
    
    int button1 = 0; // Pouch Product Provided
    int button2 = 0; // Your Own Loose Product
    int button3 = 0; // Different Tobacco Product
    int button4 = 0; // Your Own Pouch Product
    
    RelativeLayout rlButton1, rlButton2, rlButton3, rlButton4;
    TextView tvButton1, tvButton2, tvButton3, tvButton4;
    
    ImageView ivMinus1, ivMinus2, ivMinus3, ivMinus4, ivPlus1, ivPlus2, ivPlus3, ivPlus4;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
//        setAlarm ();
        initView ();
        initData ();
        initListener ();
        isLogin ();
    }
    
    private void initView () {
        tvDay = (TextView) findViewById (R.id.tvDay);
        tvDate = (TextView) findViewById (R.id.tvDate);
        tvProductCode = (TextView) findViewById (R.id.tvProductCode);
        rlEndDay = (RelativeLayout) findViewById (R.id.rlEndDay);

        rlButton1 = (RelativeLayout) findViewById (R.id.rlButton1);
        rlButton2 = (RelativeLayout) findViewById (R.id.rlButton2);
        rlButton3 = (RelativeLayout) findViewById (R.id.rlButton3);
        rlButton4 = (RelativeLayout) findViewById (R.id.rlButton4);
    
    
        ivMinus1 = (ImageView) findViewById (R.id.ivMinus1);
        ivMinus2 = (ImageView) findViewById (R.id.ivMinus2);
        ivMinus3 = (ImageView) findViewById (R.id.ivMinus3);
        ivMinus4 = (ImageView) findViewById (R.id.ivMinus4);
        ivPlus1 = (ImageView) findViewById (R.id.ivPlus1);
        ivPlus2 = (ImageView) findViewById (R.id.ivPlus2);
        ivPlus3 = (ImageView) findViewById (R.id.ivPlus3);
        ivPlus4 = (ImageView) findViewById (R.id.ivPlus4);
    
    
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
    
        ivMinus1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (button1 > 0) {
                    button1--;
                    tvButton1.setText (String.valueOf (button1));
                }
            }
        });
    
        ivMinus2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (button2 > 0) {
                    button2--;
                    tvButton2.setText (String.valueOf (button2));
                }
            }
        });
        ivMinus3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (button3 > 0) {
                    button3--;
                    tvButton3.setText (String.valueOf (button3));
                }
            }
        });
        ivMinus4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (button4 > 0) {
                    button4--;
                    tvButton4.setText (String.valueOf (button4));
                }
            }
        });
    
        ivPlus1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button1++;
                tvButton1.setText (String.valueOf (button1));
            }
        });
    
        ivPlus2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button2++;
                tvButton2.setText (String.valueOf (button2));
            }
        });
    
        ivPlus3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button3++;
                tvButton3.setText (String.valueOf (button3));
            }
        });
    
        ivPlus4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button4++;
                tvButton4.setText (String.valueOf (button4));
            }
        });
    
        rlEndDay.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                FragmentTransaction ft = getFragmentManager ().beginTransaction ();
                SurveyDialogFragment frag = SurveyDialogFragment.newInstance (button1, button2, button3, button4);
                frag.show (ft, "4");
            }
        });
    }
    
    public void setAlarm () {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService (this.ALARM_SERVICE);
        long when = System.currentTimeMillis ();         // notification time
        Intent intent = new Intent (this, AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService (this, 0, intent, 0);
        alarmManager.setRepeating (AlarmManager.RTC, when, (AlarmManager.INTERVAL_FIFTEEN_MINUTES / 15), pendingIntent);
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