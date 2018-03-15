
package com.resultier.surveyx.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.resultier.surveyx.R;
import com.resultier.surveyx.utils.AppConfigTags;
import com.resultier.surveyx.utils.AppConfigURL;
import com.resultier.surveyx.utils.Constants;
import com.resultier.surveyx.utils.NetworkConnection;
import com.resultier.surveyx.utils.SetTypeFace;
import com.resultier.surveyx.utils.TypefaceSpan;
import com.resultier.surveyx.utils.UserDetailsPref;
import com.resultier.surveyx.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView tvUsername;
    EditText etUserName;
    TextView tvPassword;
    EditText etPassword;
    EditText etSurveyID;
    TextView tvStartSurvey;
    TextView tvShowHide;
    UserDetailsPref userDetailsPref;
    ProgressDialog progressDialog;
    CoordinatorLayout clMain;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        initView ();
        initData ();
        initListener ();
    }
    
    private void initData () {
        userDetailsPref = UserDetailsPref.getInstance ();
        progressDialog = new ProgressDialog (LoginActivity.this);
        Utils.setTypefaceToAllViews (this, tvStartSurvey);
    }
    
    private void initView () {
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
        tvUsername = (TextView) findViewById (R.id.tvUserName);
        tvStartSurvey = (TextView) findViewById (R.id.tvStartSurvey);
        etUserName = (EditText) findViewById (R.id.etUserName);
        tvPassword = (TextView) findViewById (R.id.tvPassword);
        etPassword = (EditText) findViewById (R.id.etPassword);
        etSurveyID = (EditText) findViewById (R.id.etSurveyID);
        tvShowHide = (TextView) findViewById (R.id.tvShowHide);
    }
    
    private void initListener () {
        tvStartSurvey.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                SpannableString s1 = new SpannableString (getResources ().getString (R.string.please_enter_name));
                s1.setSpan (new TypefaceSpan (LoginActivity.this, Constants.font_name), 0, s1.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s2 = new SpannableString (getResources ().getString (R.string.please_enter_valid_password));
                s2.setSpan (new TypefaceSpan (LoginActivity.this, Constants.font_name), 0, s2.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                SpannableString s3 = new SpannableString (getResources ().getString (R.string.please_enter_survey_id));
                s3.setSpan (new TypefaceSpan (LoginActivity.this, Constants.font_name), 0, s3.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    
                if ((etUserName.getText ().toString ().length () == 0) && (etPassword.getText ().toString ().length () == 0) && (etSurveyID.getText ().toString ().length () == 0)) {
                    etUserName.setError (s1);
                    etPassword.setError (s2);
                    etSurveyID.setError (s3);
        
                } else if (etUserName.getText ().toString ().length () == 0) {
                    etUserName.setError (s1);
                } else if (etPassword.getText ().toString ().length () == 0) {
                    etPassword.setError (s2);
                } else if (etSurveyID.getText ().toString ().length () == 0) {
                    etSurveyID.setError (s3);
                } else {
                    sendLoginDetailsToServer (etUserName.getText ().toString (), etPassword.getText ().toString (), etSurveyID.getText ().toString ());

//                    Intent intent = new Intent (LoginActivity.this, MainActivity.class);
//                    userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.USER_LOGIN_KEY, "1");
//                    startActivity (intent);
                }
            }
        });
    
    
        tvShowHide.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (tvShowHide.getText ().toString ().equalsIgnoreCase ("SHOW")) {
                    tvShowHide.setText ("HIDE");
                    etPassword.setInputType (InputType.TYPE_CLASS_TEXT);
                    etPassword.setSelection (etPassword.getText ().length ());
                    etPassword.setTypeface (SetTypeFace.getTypeface (LoginActivity.this));
                } else {
                    etPassword.setInputType (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword.setSelection (etPassword.getText ().length ());
                    etPassword.setTypeface (SetTypeFace.getTypeface (LoginActivity.this));
                    tvShowHide.setText ("SHOW");
                }
            }
        });
        etUserName.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    etUserName.setError (null);
                }
            }
            
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged (Editable s) {
            }
        });
        etPassword.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    etPassword.setError (null);
                }
            }
            
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged (Editable s) {
            }
        });
    }
    
    private void sendLoginDetailsToServer (final String user_name, final String password, final String surveyId) {
        if (NetworkConnection.isNetworkAvailable (LoginActivity.this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), true);
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.LOGIN, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.LOGIN,
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
                                        userDetailsPref.putIntPref (LoginActivity.this, UserDetailsPref.SURVEY_ID, jsonObj.getInt (AppConfigTags.SURVEY_ID));
                                        userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.USER_NAME, jsonObj.getString (AppConfigTags.USER_NAME));
                                        userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.SURVEY_ID, jsonObj.getString (AppConfigTags.SURVEY_ID));
                                        userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.USER_MOBILE, jsonObj.getString (AppConfigTags.USER_MOBILE));
                                        userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.USER_LOGIN_KEY, jsonObj.getString (AppConfigTags.USER_LOGIN_KEY));
                                        userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.SURVEY_UNIQUE_ID, jsonObj.getString (AppConfigTags.SURVEY_UNIQUE_ID));
                                        userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.SURVEY_STATUS, jsonObj.getString (AppConfigTags.SURVEY_STATUS));
                                        userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.SURVEY_DAY_ELAPSED, jsonObj.getString (AppConfigTags.SURVEY_DAY_ELAPSED));
                                        userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.SURVEY_CONCLUSION, jsonObj.getString (AppConfigTags.SURVEY_CONCLUSION));
                                        userDetailsPref.putStringPref (LoginActivity.this, UserDetailsPref.SURVEY_LAB_REPORT, jsonObj.getString (AppConfigTags.SURVEY_LAB_REPORT));
                                        Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                                        startActivity (intent);
                                        finish ();
                                        overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
//                                        Intent intent = new Intent (SwiggyLoginActivity.this, SwiggyMainActivity.class);
//                                        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity (intent);
//                                        overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                                    } else {
                                        Utils.showSnackBar (LoginActivity.this, clMain, message, Snackbar.LENGTH_LONG, null, null);
                                    }
                                    progressDialog.dismiss ();
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
                                    Utils.showSnackBar (LoginActivity.this, clMain, getResources ().getString (R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showSnackBar (LoginActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
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
                            Utils.showSnackBar (LoginActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                            progressDialog.dismiss ();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.LOGIN_ID, user_name);
                    params.put (AppConfigTags.LOGIN_PASSWORD, password);
                    params.put (AppConfigTags.SURVEY_ID, surveyId);
                    
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
    
    
    @Override
    public void onBackPressed () {
//        super.onBackPressed ();
        Intent intent = new Intent ();
        intent.putExtra ("LOGIN", false);
        setResult (MainActivity.REQUEST_LOGIN_SCREEN_RESULT, intent);
        finish ();
        overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
    }
}