package com.resultier.surveyx.activity;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.resultier.surveyx.R;
import com.resultier.surveyx.dialog.FinalSurveyDialogFragment;
import com.resultier.surveyx.dialog.SurveyDialogFragment;
import com.resultier.surveyx.service.AlarmService;
import com.resultier.surveyx.utils.AppConfigTags;
import com.resultier.surveyx.utils.AppConfigURL;
import com.resultier.surveyx.utils.AppDetailsPref;
import com.resultier.surveyx.utils.Constants;
import com.resultier.surveyx.utils.NetworkConnection;
import com.resultier.surveyx.utils.Utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    AppDetailsPref appDetailsPref;
    TextView tvDay;
    TextView tvDate;
    TextView tvProductCode;
    
    RelativeLayout rlMain;
    RelativeLayout rlEndDay;
    
    RelativeLayout rlInstructions;
    RelativeLayout rlStartSurvey;
    
    RelativeLayout rlConclusion;
    RelativeLayout rlStartSurvey2;
    
    RelativeLayout rlLabReport;
    RelativeLayout rlSurveyComplete;
    RelativeLayout rlLogout;
    
    int button1 = 0; // Pouch Product Provided
    int button2 = 0; // Your Own Loose Product
    int button3 = 0; // Different Tobacco Product
    int button4 = 0; // Your Own Pouch Product
    
    RelativeLayout rlButton1, rlButton2, rlButton3, rlButton4;
    TextView tvButton1, tvButton2, tvButton3, tvButton4;
    
    ImageView ivMinus1, ivMinus2, ivMinus3, ivMinus4, ivPlus1, ivPlus2, ivPlus3, ivPlus4;
    
    ProgressDialog progressDialog;
    CoordinatorLayout clMain;
    
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
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
        tvDay = (TextView) findViewById (R.id.tvDay);
        tvDate = (TextView) findViewById (R.id.tvDate);
        tvProductCode = (TextView) findViewById (R.id.tvProductCode);
    
        rlInstructions = (RelativeLayout) findViewById (R.id.rlInstruction);
        rlStartSurvey = (RelativeLayout) findViewById (R.id.rlStartSurvey);
        rlMain = (RelativeLayout) findViewById (R.id.rlMain);
        rlEndDay = (RelativeLayout) findViewById (R.id.rlEndDay);
        rlConclusion = (RelativeLayout) findViewById (R.id.rlConclusion);
        rlStartSurvey2 = (RelativeLayout) findViewById (R.id.rlStartSurvey2);
        rlLabReport = (RelativeLayout) findViewById (R.id.rlLabReport);
        rlSurveyComplete = (RelativeLayout) findViewById (R.id.rlSurveyComplete);
        rlLogout = (RelativeLayout) findViewById (R.id.rlLogout);
    
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
        appDetailsPref = AppDetailsPref.getInstance ();
        progressDialog = new ProgressDialog (this);
        Utils.setTypefaceToAllViews (this, tvDay);
    
        button1 = appDetailsPref.getIntPref (this, AppDetailsPref.BUTTON1);
        button2 = appDetailsPref.getIntPref (this, AppDetailsPref.BUTTON2);
        button3 = appDetailsPref.getIntPref (this, AppDetailsPref.BUTTON3);
        button4 = appDetailsPref.getIntPref (this, AppDetailsPref.BUTTON4);
    
        tvButton1.setText (String.valueOf (button1));
        tvButton2.setText (String.valueOf (button2));
        tvButton3.setText (String.valueOf (button3));
        tvButton4.setText (String.valueOf (button4));
    
        switch (appDetailsPref.getIntPref (this, AppDetailsPref.SURVEY_STATUS)) {
            case 0:
                rlInstructions.setVisibility (View.VISIBLE);
                break;
            case 1:
                rlMain.setVisibility (View.VISIBLE);
                break;
            case 2:
                rlConclusion.setVisibility (View.VISIBLE);
                break;
            case 3:
                rlLabReport.setVisibility (View.VISIBLE);
                break;
            case 4:
                rlSurveyComplete.setVisibility (View.VISIBLE);
                break;
        }
    
        tvProductCode.setText ("Prod # " + appDetailsPref.getStringPref (this, AppDetailsPref.PRODUCT_CODE));
        if (appDetailsPref.getIntPref (this, AppDetailsPref.SURVEY_DAY_ELAPSED) < 7) {
            tvDay.setText ("Day : " + (appDetailsPref.getIntPref (this, AppDetailsPref.SURVEY_DAY_ELAPSED) + 1));
        } else {
            tvDay.setVisibility (View.GONE);
        }
    
        Calendar c = Calendar.getInstance ();
        SimpleDateFormat df = new SimpleDateFormat ("dd/MM/yyyy", Locale.US);
        tvDate.setText (df.format (c.getTime ()));
    }
    
    private void initListener () {
        rlStartSurvey2.setOnClickListener (new View.OnClickListener () {
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
                    appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON1, button1);
                    tvButton1.setText (String.valueOf (button1));
                }
            }
        });
    
        ivMinus2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (button2 > 0) {
                    button2--;
                    appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON2, button2);
                    tvButton2.setText (String.valueOf (button2));
                }
            }
        });
        ivMinus3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (button3 > 0) {
                    button3--;
                    appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON3, button3);
                    tvButton3.setText (String.valueOf (button3));
                }
            }
        });
        ivMinus4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (button4 > 0) {
                    button4--;
                    appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON4, button4);
                    tvButton4.setText (String.valueOf (button4));
                }
            }
        });
    
        ivPlus1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button1++;
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON1, button1);
                tvButton1.setText (String.valueOf (button1));
            }
        });
    
        ivPlus2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button2++;
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON2, button2);
                tvButton2.setText (String.valueOf (button2));
            }
        });
    
        ivPlus3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button3++;
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON3, button3);
                tvButton3.setText (String.valueOf (button3));
            }
        });
    
        ivPlus4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                button4++;
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON4, button4);
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
    
    
        rlStartSurvey.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                startSurvey (appDetailsPref.getIntPref (MainActivity.this, AppDetailsPref.SURVEY_ID));
            }
        });
    
        rlLogout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                appDetailsPref.putStringPref (MainActivity.this, AppDetailsPref.USER_NAME, "");
                appDetailsPref.putStringPref (MainActivity.this, AppDetailsPref.USER_MOBILE, "");
                appDetailsPref.putStringPref (MainActivity.this, AppDetailsPref.USER_LOGIN_KEY, "");
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.SURVEY_ID, 0);
                appDetailsPref.putStringPref (MainActivity.this, AppDetailsPref.SURVEY_NUMBER, "");
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.SURVEY_STATUS, 0);
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.SURVEY_DAY_ELAPSED, 0);
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.PRODUCT_ID, 0);
                appDetailsPref.putStringPref (MainActivity.this, AppDetailsPref.PRODUCT_CODE, "");
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON1, 0);
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON2, 0);
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON3, 0);
                appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.BUTTON4, 0);
                Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                startActivity (intent);
                finish ();
                overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
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
        if (appDetailsPref.getStringPref (MainActivity.this, AppDetailsPref.USER_LOGIN_KEY).length () == 0) {
            Intent intent = new Intent (MainActivity.this, LoginActivity.class);
            startActivity (intent);
            finish ();
            overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            initApplication ();
        }
    }
    
    private void initApplication () {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.INIT_APPLICATION, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.INIT_APPLICATION,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.SURVEY_STATUS, jsonObj.getInt (AppConfigTags.SURVEY_STATUS));
                                        if (jsonObj.getInt (AppConfigTags.SURVEY_DAY_ELAPSED) < 7) {
                                            appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.SURVEY_DAY_ELAPSED, jsonObj.getInt (AppConfigTags.SURVEY_DAY_ELAPSED));
                                            tvDay.setText ("Day : " + (appDetailsPref.getIntPref (MainActivity.this, AppDetailsPref.SURVEY_DAY_ELAPSED) + 1));
                                        }
                                        
                                        
                                        switch (jsonObj.getInt (AppConfigTags.SURVEY_STATUS)) {
                                            case 0:
                                                rlInstructions.setVisibility (View.VISIBLE);
                                                rlMain.setVisibility (View.GONE);
                                                rlConclusion.setVisibility (View.GONE);
                                                rlLabReport.setVisibility (View.GONE);
                                                rlSurveyComplete.setVisibility (View.GONE);
                                                break;
                                            case 1:
                                                rlInstructions.setVisibility (View.GONE);
                                                rlMain.setVisibility (View.VISIBLE);
                                                rlConclusion.setVisibility (View.GONE);
                                                rlLabReport.setVisibility (View.GONE);
                                                rlSurveyComplete.setVisibility (View.GONE);
                                                break;
                                            case 2:
                                                rlInstructions.setVisibility (View.GONE);
                                                rlMain.setVisibility (View.GONE);
                                                rlConclusion.setVisibility (View.VISIBLE);
                                                rlLabReport.setVisibility (View.GONE);
                                                rlSurveyComplete.setVisibility (View.GONE);
                                                break;
                                            case 3:
                                                rlInstructions.setVisibility (View.GONE);
                                                rlMain.setVisibility (View.GONE);
                                                rlConclusion.setVisibility (View.GONE);
                                                rlLabReport.setVisibility (View.VISIBLE);
                                                rlSurveyComplete.setVisibility (View.GONE);
                                                break;
                                            case 4:
                                                rlInstructions.setVisibility (View.GONE);
                                                rlMain.setVisibility (View.GONE);
                                                rlConclusion.setVisibility (View.GONE);
                                                rlLabReport.setVisibility (View.GONE);
                                                rlSurveyComplete.setVisibility (View.VISIBLE);
                                                break;
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<> ();
                    params.put (AppConfigTags.SURVEY_NUMBER, appDetailsPref.getStringPref (MainActivity.this, AppDetailsPref.SURVEY_NUMBER));
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }
                
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_USER_LOGIN_KEY, appDetailsPref.getStringPref (MainActivity.this, AppDetailsPref.USER_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            strRequest.setRetryPolicy (new DefaultRetryPolicy (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Utils.sendRequest (strRequest, 30);
        } else {
        }
    }
    
    private void startSurvey (final int survey_id) {
        if (NetworkConnection.isNetworkAvailable (MainActivity.this)) {
            Utils.showProgressDialog (this, progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), true);
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.START_SURVEY, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.START_SURVEY,
                    new com.android.volley.Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        rlInstructions.setVisibility (View.GONE);
                                        rlMain.setVisibility (View.VISIBLE);
                                        appDetailsPref.putIntPref (MainActivity.this, AppDetailsPref.SURVEY_STATUS, jsonObj.getInt (AppConfigTags.SURVEY_STATUS));
                                    } else {
                                        Utils.showSnackBar (MainActivity.this, clMain, message, Snackbar.LENGTH_LONG, null, null);
                                    }
                                    progressDialog.dismiss ();
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
                                    Utils.showSnackBar (MainActivity.this, clMain, getResources ().getString (R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showSnackBar (MainActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                            progressDialog.dismiss ();
                        }
                    },
                    new com.android.volley.Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            NetworkResponse response = error.networkResponse;
                            if (response != null && response.data != null) {
                                Utils.showLog (Log.ERROR, AppConfigTags.ERROR, new String (response.data), true);
                            }
                            Utils.showSnackBar (MainActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                            progressDialog.dismiss ();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.SURVEY_ID, String.valueOf (survey_id));
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }
                
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest (strRequest1, 60);
        } else {
            Utils.showSnackBar (this, clMain, getResources ().getString (R.string.snackbar_text_no_internet_connection_available), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_go_to_settings), new View.OnClickListener () {
                @Override
                public void onClick (View v) {
                    Intent dialogIntent = new Intent (Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity (dialogIntent);
                }
            });
        }
    }
}