package com.resultier.surveyx.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.resultier.surveyx.R;
import com.resultier.surveyx.activity.MainActivity;
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


public class FinalSurveyDialogFragment extends DialogFragment {
    ImageView ivCancel;
    LinearLayout llAnswer9;
    LinearLayout llAnswer9a;
    LinearLayout llAnswer9b;
    LinearLayout llAnswer9c;
    LinearLayout llAnswer9d;
    LinearLayout llAnswer9e;
    LinearLayout llAnswer9f;
    String answer9 = "";
    String answer9a = "";
    String answer9b = "";
    String answer9c = "";
    String answer9d = "";
    String answer9e = "";
    String answer9f = "";
    AppDetailsPref appDetailsPref;
    ProgressDialog progressDialog;
    private RadioButton rbNotAtAll;
    private RadioButton rbVeryLittle;
    private RadioButton rbALittle;
    private RadioButton rbModerately;
    private RadioButton rbALot;
    private RadioButton rbQuiteALot;
    private RadioButton rbExtremely;
    private EditText etAnswer9a;
    private EditText etAnswer9b;
    private EditText etAnswer9c;
    private EditText etAnswer9d;
    private EditText etAnswer9e;
    private EditText etAnswer9f;
    private TextView tvSubmit;
    
    public static FinalSurveyDialogFragment newInstance () {
        FinalSurveyDialogFragment f = new FinalSurveyDialogFragment ();
        Bundle args = new Bundle ();
        f.setArguments (args);
        return f;
    }
    
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setStyle (DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }
    
    @Override
    public void onActivityCreated (Bundle arg0) {
        super.onActivityCreated (arg0);
        Window window = getDialog ().getWindow ();
        window.getAttributes ().windowAnimations = R.style.DialogAnimation;
    }
    
    @Override
    public void onStart () {
        super.onStart ();
        Dialog d = getDialog ();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow ().setLayout (width, height);
        }
    }
    
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate (R.layout.fd_final_survey, container, false);
        initView (root);
        initBundle ();
        initData ();
        initListener ();
        return root;
    }
    
    private void initView (View root) {
        ivCancel = (ImageView) root.findViewById (R.id.ivCancel);
        rbNotAtAll = (RadioButton) root.findViewById (R.id.rbNotAtAll);
        rbVeryLittle = (RadioButton) root.findViewById (R.id.rbVeryLittle);
        rbALittle = (RadioButton) root.findViewById (R.id.rbALittle);
        rbModerately = (RadioButton) root.findViewById (R.id.rbModerately);
        rbALot = (RadioButton) root.findViewById (R.id.rbALot);
        rbQuiteALot = (RadioButton) root.findViewById (R.id.rbQuiteALot);
        rbExtremely = (RadioButton) root.findViewById (R.id.rbExtremely);
        etAnswer9a = (EditText) root.findViewById (R.id.etAnswer9a);
        etAnswer9b = (EditText) root.findViewById (R.id.etAnswer9b);
        etAnswer9c = (EditText) root.findViewById (R.id.etAnswer9c);
        etAnswer9d = (EditText) root.findViewById (R.id.etAnswer9d);
        etAnswer9e = (EditText) root.findViewById (R.id.etAnswer9e);
        etAnswer9f = (EditText) root.findViewById (R.id.etAnswer9f);
    
        llAnswer9 = (LinearLayout) root.findViewById (R.id.llAnswer9);
        llAnswer9a = (LinearLayout) root.findViewById (R.id.llAnswer9a);
        llAnswer9b = (LinearLayout) root.findViewById (R.id.llAnswer9b);
        llAnswer9c = (LinearLayout) root.findViewById (R.id.llAnswer9c);
        llAnswer9d = (LinearLayout) root.findViewById (R.id.llAnswer9d);
        llAnswer9e = (LinearLayout) root.findViewById (R.id.llAnswer9e);
        llAnswer9f = (LinearLayout) root.findViewById (R.id.llAnswer9f);
    
    
        tvSubmit = (TextView) root.findViewById (R.id.tvSubmit);
    }
    
    private void initBundle () {
        Bundle bundle = this.getArguments ();
    }
    
    private void initData () {
        appDetailsPref = AppDetailsPref.getInstance ();
        progressDialog = new ProgressDialog (getActivity ());
        Utils.setTypefaceToAllViews (getActivity (), ivCancel);
    }
    
    private void initListener () {
        ivCancel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                getDialog ().dismiss ();
            }
        });
        
        rbNotAtAll.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer9 = rbNotAtAll.getText ().toString ().trim ();
                rbVeryLittle.setChecked (false);
                rbALittle.setChecked (false);
                rbModerately.setChecked (false);
                rbALot.setChecked (false);
                rbQuiteALot.setChecked (false);
                rbExtremely.setChecked (false);
            }
        });
        rbVeryLittle.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer9 = rbVeryLittle.getText ().toString ().trim ();
                rbNotAtAll.setChecked (false);
                rbALittle.setChecked (false);
                rbModerately.setChecked (false);
                rbALot.setChecked (false);
                rbQuiteALot.setChecked (false);
                rbExtremely.setChecked (false);
            }
        });
        rbALittle.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer9 = rbALittle.getText ().toString ().trim ();
                rbNotAtAll.setChecked (false);
                rbVeryLittle.setChecked (false);
                rbModerately.setChecked (false);
                rbALot.setChecked (false);
                rbQuiteALot.setChecked (false);
                rbExtremely.setChecked (false);
            }
        });
        rbModerately.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer9 = rbModerately.getText ().toString ().trim ();
                rbNotAtAll.setChecked (false);
                rbVeryLittle.setChecked (false);
                rbALittle.setChecked (false);
                rbALot.setChecked (false);
                rbQuiteALot.setChecked (false);
                rbExtremely.setChecked (false);
            }
        });
        rbALot.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer9 = rbALot.getText ().toString ().trim ();
                rbNotAtAll.setChecked (false);
                rbVeryLittle.setChecked (false);
                rbALittle.setChecked (false);
                rbModerately.setChecked (false);
                rbQuiteALot.setChecked (false);
                rbExtremely.setChecked (false);
            }
        });
        rbQuiteALot.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer9 = rbQuiteALot.getText ().toString ().trim ();
                rbNotAtAll.setChecked (false);
                rbVeryLittle.setChecked (false);
                rbALittle.setChecked (false);
                rbModerately.setChecked (false);
                rbALot.setChecked (false);
                rbExtremely.setChecked (false);
            }
        });
        
        
        rbExtremely.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer9 = rbExtremely.getText ().toString ().trim ();
                rbNotAtAll.setChecked (false);
                rbVeryLittle.setChecked (false);
                rbALittle.setChecked (false);
                rbModerately.setChecked (false);
                rbALot.setChecked (false);
                rbQuiteALot.setChecked (false);
                
            }
        });
        
        tvSubmit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (answer9.length () == 0) {
                    llAnswer9.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer9.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                
                if (etAnswer9a.getText ().length () == 0) {
                    llAnswer9a.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer9a.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                    answer9a = etAnswer9a.getText ().toString ().trim ();
                }
                
                if (etAnswer9b.getText ().length () == 0) {
                    llAnswer9b.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer9b.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                    answer9b = etAnswer9b.getText ().toString ().trim ();
                }
                if (etAnswer9c.getText ().length () == 0) {
                    llAnswer9c.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer9c.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                    answer9c = etAnswer9c.getText ().toString ().trim ();
                }
                if (etAnswer9d.getText ().length () == 0) {
                    llAnswer9d.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer9d.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                    answer9d = etAnswer9d.getText ().toString ().trim ();
                }
                if (etAnswer9e.getText ().length () == 0) {
                    llAnswer9e.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer9e.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                    answer9e = etAnswer9e.getText ().toString ().trim ();
                }
                if (etAnswer9f.getText ().length () == 0) {
                    llAnswer9f.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer9f.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                    answer9f = etAnswer9f.getText ().toString ().trim ();
                }
                
                if (answer9.length () > 0 &&
                        answer9a.length () > 0 &&
                        answer9b.length () > 0 &&
                        answer9c.length () > 0 &&
                        answer9d.length () > 0 &&
                        answer9e.length () > 0 &&
                        answer9f.length () > 0) {
                    submitConclusionSurvey (answer9, answer9a, answer9b, answer9c, answer9d, answer9e, answer9f);
                }
            }
        });
    }
    
    private void submitConclusionSurvey (final String answer9, final String answer9a, final String answer9b, final String answer9c, final String answer9d, final String answer9e, final String answer9f) {
        if (NetworkConnection.isNetworkAvailable (getActivity ())) {
            Utils.showProgressDialog (getActivity (), progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), false);
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.CONCLUSION_SURVEY, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.CONCLUSION_SURVEY,
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
                                        progressDialog.dismiss ();
                                        getDialog ().dismiss ();
    
                                        appDetailsPref.putIntPref (getActivity (), AppDetailsPref.SURVEY_STATUS, jsonObj.getInt (AppConfigTags.SURVEY_STATUS));
    
                                        Intent newIntent = new Intent (getActivity (), MainActivity.class);
                                        newIntent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        newIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity (newIntent);
                                    }
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
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
                            progressDialog.dismiss ();
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<> ();
                    Calendar c = Calendar.getInstance ();
                    SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd", Locale.US);
                    
                    params.put (AppConfigTags.SURVEY_ID, String.valueOf (appDetailsPref.getIntPref (getActivity (), AppDetailsPref.SURVEY_ID)));
                    params.put (AppConfigTags.DATE, df.format (c.getTime ()));
                    params.put (AppConfigTags.ANSWER_9, answer9);
                    params.put (AppConfigTags.ANSWER_9A, answer9a);
                    params.put (AppConfigTags.ANSWER_9B, answer9b);
                    params.put (AppConfigTags.ANSWER_9C, answer9c);
                    params.put (AppConfigTags.ANSWER_9D, answer9d);
                    params.put (AppConfigTags.ANSWER_9E, answer9e);
                    params.put (AppConfigTags.ANSWER_9F, answer9f);
                    
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }
                
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    params.put (AppConfigTags.HEADER_USER_LOGIN_KEY, appDetailsPref.getStringPref (getActivity (), AppDetailsPref.USER_LOGIN_KEY));
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            strRequest.setRetryPolicy (new DefaultRetryPolicy (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Utils.sendRequest (strRequest, 30);
        } else {
        }
    }
}