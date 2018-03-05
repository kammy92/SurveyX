package com.resultier.surveyx.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.resultier.surveyx.R;
import com.resultier.surveyx.utils.UserDetailsPref;

public class MainActivity extends AppCompatActivity {
    UserDetailsPref userDetailsPref;
    public static final int REQUEST_LOGIN_SCREEN_RESULT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLogin();
    }

    private void isLogin() {
        userDetailsPref = UserDetailsPref.getInstance();
        if (userDetailsPref.getStringPref(MainActivity.this, UserDetailsPref.LOGIN_KEY).length() == 0) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }
    }
}
