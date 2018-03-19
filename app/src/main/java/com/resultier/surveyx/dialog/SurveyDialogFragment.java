package com.resultier.surveyx.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;


public class SurveyDialogFragment extends DialogFragment {
    String time = "";
    int button1 = 0;
    int button2 = 0;
    int button3 = 0;
    int button4 = 0;
    TextView tvSubmit;
    ProgressDialog progressDialog;
    ArrayList<String> cbAnswer7aList = new ArrayList<> ();
    String answer1 = "";
    String answer2 = "";
    String answer3 = "";
    String answer4 = "";
    String answer5 = "";
    String answer5a = "";
    String answer5b = "";
    String answer5c = "";
    String answer5d = "";
    String answer6 = "";
    String answer6a = "";
    String answer6b = "";
    String answer6c = "";
    String answer6d = "";
    String answer7 = "";
    String answer7a = "";
    String answer7b = "";
    String answer7c = "";
    String answer8 = "";
    String answer8a = "";
    String answer8b = "";
    AppDetailsPref appDetailsPref;
    private ImageView ivCancel;
    private EditText etAnswer1;
    private EditText etAnswer2;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6;
    private RadioButton rb7, rb8, rb9;
    private EditText etAnswer5;
    private LinearLayout llQuestion5;
    private EditText etAnswer5a;
    private EditText etAnswer5b;
    private EditText etAnswer5c;
    private EditText etAnswer5d;
    private EditText etAnswer6;
    private LinearLayout llQuestion6;
    private EditText etAnswer6a;
    private RadioGroup rgAnswer6b;
    private EditText etAnswer6c;
    private EditText etAnswer6d;
    private EditText etAnswer7;
    private LinearLayout llQuestion7;
    private CheckBox cbAnswer7a1;
    private CheckBox cbAnswer7a2;
    private CheckBox cbAnswer7a3;
    private CheckBox cbAnswer7a4;
    private CheckBox cbAnswer7a5;
    private EditText etAnswer7b;
    private EditText etAnswer7c;
    private EditText etAnswer8;
    private LinearLayout llQuestion8;
    private EditText etAnswer8a;
    private EditText etAnswer8b;
    private int mHour, mMinute;
    private LinearLayout llAnswer1;
    private LinearLayout llAnswer2;
    private LinearLayout llAnswer3;
    private LinearLayout llAnswer4;
    private LinearLayout llAnswer5;
    private LinearLayout llAnswer5a;
    private LinearLayout llAnswer5b;
    private LinearLayout llAnswer5c;
    private LinearLayout llAnswer5d;
    private LinearLayout llAnswer6;
    private LinearLayout llAnswer6a;
    private LinearLayout llAnswer6b;
    private LinearLayout llAnswer6c;
    private LinearLayout llAnswer6d;
    private LinearLayout llAnswer7;
    private LinearLayout llAnswer7a;
    private LinearLayout llAnswer7b;
    private LinearLayout llAnswer7c;
    private LinearLayout llAnswer8;
    private LinearLayout llAnswer8a;
    private LinearLayout llAnswer8b;
    
    public static SurveyDialogFragment newInstance (int button1, int button2, int button3, int button4) {
        SurveyDialogFragment f = new SurveyDialogFragment ();
        Bundle args = new Bundle ();
        args.putInt ("Button 1", button1);
        args.putInt ("Button 2", button2);
        args.putInt ("Button 3", button3);
        args.putInt ("Button 4", button4);
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
        View root = inflater.inflate (R.layout.fd_end_day, container, false);
        initView (root);
        initBundle ();
        initData ();
        initListener ();
        return root;
    }
    
    private void initView (View root) {
        ivCancel = (ImageView) root.findViewById (R.id.ivCancel);
        etAnswer1 = (EditText) root.findViewById (R.id.etAnswer1);
        etAnswer2 = (EditText) root.findViewById (R.id.etAnswer2);
        rb1 = (RadioButton) root.findViewById (R.id.rb1);
        rb2 = (RadioButton) root.findViewById (R.id.rb2);
        rb3 = (RadioButton) root.findViewById (R.id.rb3);
        rb4 = (RadioButton) root.findViewById (R.id.rb4);
        rb5 = (RadioButton) root.findViewById (R.id.rb5);
        rb6 = (RadioButton) root.findViewById (R.id.rb6);
        rb7 = (RadioButton) root.findViewById (R.id.rb7);
        rb8 = (RadioButton) root.findViewById (R.id.rb8);
        rb9 = (RadioButton) root.findViewById (R.id.rb9);
    
        etAnswer5 = (EditText) root.findViewById (R.id.etAnswer5);
        llQuestion5 = (LinearLayout) root.findViewById (R.id.llQuestion5);
        etAnswer5a = (EditText) root.findViewById (R.id.etAnswer5a);
        etAnswer5b = (EditText) root.findViewById (R.id.etAnswer5b);
        etAnswer5c = (EditText) root.findViewById (R.id.etAnswer5c);
        etAnswer5d = (EditText) root.findViewById (R.id.etAnswer5d);
    
        etAnswer6 = (EditText) root.findViewById (R.id.etAnswer6);
        llQuestion6 = (LinearLayout) root.findViewById (R.id.llQuestion6);
        etAnswer6a = (EditText) root.findViewById (R.id.etAnswer6a);
        rgAnswer6b = (RadioGroup) root.findViewById (R.id.rgAnswer6b);
        etAnswer6c = (EditText) root.findViewById (R.id.etAnswer6c);
        etAnswer6d = (EditText) root.findViewById (R.id.etAnswer6d);
    
        etAnswer7 = (EditText) root.findViewById (R.id.etAnswer7);
        llQuestion7 = (LinearLayout) root.findViewById (R.id.llQuestion7);
        cbAnswer7a1 = (CheckBox) root.findViewById (R.id.cb7a1);
        cbAnswer7a2 = (CheckBox) root.findViewById (R.id.cb7a2);
        cbAnswer7a3 = (CheckBox) root.findViewById (R.id.cb7a3);
        cbAnswer7a4 = (CheckBox) root.findViewById (R.id.cb7a4);
        cbAnswer7a5 = (CheckBox) root.findViewById (R.id.cb7a5);
        etAnswer7b = (EditText) root.findViewById (R.id.etAnswer7b);
        etAnswer7c = (EditText) root.findViewById (R.id.etAnswer7c);
    
        etAnswer8 = (EditText) root.findViewById (R.id.etAnswer8);
        llQuestion8 = (LinearLayout) root.findViewById (R.id.llQuestion8);
        etAnswer8a = (EditText) root.findViewById (R.id.etAnswer8a);
        etAnswer8b = (EditText) root.findViewById (R.id.etAnswer8b);
    
    
        llAnswer1 = (LinearLayout) root.findViewById (R.id.llAnswer1);
        llAnswer2 = (LinearLayout) root.findViewById (R.id.llAnswer2);
        llAnswer3 = (LinearLayout) root.findViewById (R.id.llAnswer3);
        llAnswer4 = (LinearLayout) root.findViewById (R.id.llAnswer4);
        llAnswer5 = (LinearLayout) root.findViewById (R.id.llAnswer5);
        llAnswer5a = (LinearLayout) root.findViewById (R.id.llAnswer5a);
        llAnswer5b = (LinearLayout) root.findViewById (R.id.llAnswer5b);
        llAnswer5c = (LinearLayout) root.findViewById (R.id.llAnswer5c);
        llAnswer5d = (LinearLayout) root.findViewById (R.id.llAnswer5d);
        llAnswer6 = (LinearLayout) root.findViewById (R.id.llAnswer6);
        llAnswer6a = (LinearLayout) root.findViewById (R.id.llAnswer6a);
        llAnswer6b = (LinearLayout) root.findViewById (R.id.llAnswer6b);
        llAnswer6c = (LinearLayout) root.findViewById (R.id.llAnswer6c);
        llAnswer6d = (LinearLayout) root.findViewById (R.id.llAnswer6d);
        llAnswer7 = (LinearLayout) root.findViewById (R.id.llAnswer7);
        llAnswer7a = (LinearLayout) root.findViewById (R.id.llAnswer7a);
        llAnswer7b = (LinearLayout) root.findViewById (R.id.llAnswer7b);
        llAnswer7c = (LinearLayout) root.findViewById (R.id.llAnswer7c);
        llAnswer8 = (LinearLayout) root.findViewById (R.id.llAnswer8);
        llAnswer8a = (LinearLayout) root.findViewById (R.id.llAnswer8a);
        llAnswer8b = (LinearLayout) root.findViewById (R.id.llAnswer8b);
    
        tvSubmit = (TextView) root.findViewById (R.id.tvSubmit);
    }
    
    private void initBundle () {
        Bundle bundle = this.getArguments ();
        button1 = bundle.getInt ("Button 1", 0);
        button2 = bundle.getInt ("Button 2", 0);
        button3 = bundle.getInt ("Button 3", 0);
        button4 = bundle.getInt ("Button 4", 0);
    }
    
    private void initData () {
        Utils.setTypefaceToAllViews (getActivity (), etAnswer1);
        progressDialog = new ProgressDialog (getActivity ());
        appDetailsPref = AppDetailsPref.getInstance ();
        if (button1 > 0) {
            answer5 = "YES";
            llQuestion5.setVisibility (View.VISIBLE);
            answer5a = String.valueOf (button1);
        } else {
            answer5 = "NO";
            llQuestion5.setVisibility (View.GONE);
        }
    
        if (button2 > 0) {
            answer6 = "YES";
            llQuestion6.setVisibility (View.VISIBLE);
            answer6a = String.valueOf (button2);
        } else {
            answer6 = "NO";
            llQuestion6.setVisibility (View.GONE);
        }
    
        if (button3 > 0) {
            answer7 = "YES";
            llQuestion7.setVisibility (View.VISIBLE);
            answer7b = String.valueOf (button3);
        } else {
            answer7 = "NO";
            llQuestion7.setVisibility (View.GONE);
        }
    
        if (button4 > 0) {
            answer8 = "YES";
            llQuestion8.setVisibility (View.VISIBLE);
            answer8a = String.valueOf (button4);
        } else {
            answer8 = "NO";
            llQuestion8.setVisibility (View.GONE);
        }
    
        etAnswer5.setText (answer5);
        etAnswer5a.setText (answer5a);
        etAnswer6.setText (answer6);
        etAnswer6a.setText (answer6a);
        etAnswer7.setText (answer7);
        etAnswer7b.setText (answer7b);
        etAnswer8.setText (answer8);
        etAnswer8a.setText (answer8a);
    }
    
    private void initListener () {
        etAnswer1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                final Calendar c = Calendar.getInstance ();
                mHour = c.get (Calendar.HOUR_OF_DAY);
                mMinute = c.get (Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog (getActivity (), new TimePickerDialog.OnTimeSetListener () {
                    @Override
                    public void onTimeSet (TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            if (hourOfDay == 12) {
                                answer1 = String.format (Locale.US, "%02d", hourOfDay) + ":" + String.format (Locale.US, "%02d", minute) + " PM";
                            } else {
                                answer1 = String.format (Locale.US, "%02d", hourOfDay - 12) + ":" + String.format (Locale.US, "%02d", minute) + " PM";
                            }
                        } else {
                            answer1 = String.format (Locale.US, "%02d", hourOfDay) + ":" + String.format (Locale.US, "%02d", minute) + " AM";
                        }
                        etAnswer1.setText (answer1);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show ();
            }
        });
    
        etAnswer2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                final Calendar c = Calendar.getInstance ();
                mHour = c.get (Calendar.HOUR_OF_DAY);
                mMinute = c.get (Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog (getActivity (), new TimePickerDialog.OnTimeSetListener () {
                    @Override
                    public void onTimeSet (TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            if (hourOfDay == 12) {
                                answer2 = String.format (Locale.US, "%02d", hourOfDay) + ":" + String.format (Locale.US, "%02d", minute) + " PM";
                            } else {
                                answer2 = String.format (Locale.US, "%02d", hourOfDay - 12) + ":" + String.format (Locale.US, "%02d", minute) + " PM";
                            }
                        } else {
                            answer2 = String.format (Locale.US, "%02d", hourOfDay) + ":" + String.format (Locale.US, "%02d", minute) + " AM";
                        }
                        etAnswer2.setText (answer2);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show ();
                
            }
        });
    
        rb1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer3 = rb1.getText ().toString ().trim ();
                rb6.setChecked (false);
                rb2.setChecked (false);
                rb3.setChecked (false);
                rb4.setChecked (false);
                rb5.setChecked (false);
                llAnswer4.setVisibility (View.GONE);
            }
        });
        rb2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer3 = rb2.getText ().toString ().trim ();
                rb1.setChecked (false);
                rb6.setChecked (false);
                rb3.setChecked (false);
                rb4.setChecked (false);
                rb5.setChecked (false);
                llAnswer4.setVisibility (View.GONE);
            }
        });
        rb3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer3 = rb3.getText ().toString ().trim ();
                rb1.setChecked (false);
                rb2.setChecked (false);
                rb6.setChecked (false);
                rb4.setChecked (false);
                rb5.setChecked (false);
                llAnswer4.setVisibility (View.GONE);
            }
        });
        rb4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer3 = rb4.getText ().toString ().trim ();
                rb1.setChecked (false);
                rb2.setChecked (false);
                rb3.setChecked (false);
                rb6.setChecked (false);
                rb5.setChecked (false);
                llAnswer4.setVisibility (View.GONE);
            }
        });
        rb5.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer3 = rb5.getText ().toString ().trim ();
                rb1.setChecked (false);
                rb2.setChecked (false);
                rb3.setChecked (false);
                rb4.setChecked (false);
                rb6.setChecked (false);
                llAnswer4.setVisibility (View.GONE);
            }
        });
        rb6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer3 = rb6.getText ().toString ().trim ();
                rb1.setChecked (false);
                rb2.setChecked (false);
                rb3.setChecked (false);
                rb4.setChecked (false);
                rb5.setChecked (false);
                llAnswer4.setVisibility (View.VISIBLE);
            }
        });
    
        rb7.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer4 = rb7.getText ().toString ().trim ();
                rb8.setChecked (false);
                rb9.setChecked (false);
            }
        });
        rb8.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer4 = rb8.getText ().toString ().trim ();
                rb7.setChecked (false);
                rb9.setChecked (false);
            }
        });
        rb9.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer4 = rb9.getText ().toString ().trim ();
                rb7.setChecked (false);
                rb8.setChecked (false);
            }
        });
        
        ivCancel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                getDialog ().dismiss ();
            }
        });
    
        rgAnswer6b.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById (i);
                answer6b = radioButton.getText ().toString ().trim ();
            }
        });
    
    
        cbAnswer7a1.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    cbAnswer7aList.add (cbAnswer7a1.getText ().toString ());
                } else {
                    cbAnswer7aList.remove (cbAnswer7a1.getText ().toString ());
                }
            }
        });
    
        cbAnswer7a2.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    cbAnswer7aList.add (cbAnswer7a2.getText ().toString ());
                } else {
                    cbAnswer7aList.remove (cbAnswer7a2.getText ().toString ());
                }
            }
        });
    
        cbAnswer7a3.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    cbAnswer7aList.add (cbAnswer7a3.getText ().toString ());
                } else {
                    cbAnswer7aList.remove (cbAnswer7a3.getText ().toString ());
                }
            }
        });
    
        cbAnswer7a4.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    cbAnswer7aList.add (cbAnswer7a4.getText ().toString ());
                } else {
                    cbAnswer7aList.remove (cbAnswer7a4.getText ().toString ());
                }
            }
        });
    
        cbAnswer7a5.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    cbAnswer7aList.add (cbAnswer7a5.getText ().toString ());
                } else {
                    cbAnswer7aList.remove (cbAnswer7a5.getText ().toString ());
                }
            }
        });
    
    
        tvSubmit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                answer5b = etAnswer5b.getText ().toString ().trim ();
                answer5c = etAnswer5c.getText ().toString ().trim ();
                answer5d = etAnswer5d.getText ().toString ().trim ();
                answer6c = etAnswer6c.getText ().toString ().trim ();
                answer6d = etAnswer6d.getText ().toString ().trim ();
                answer7c = etAnswer7c.getText ().toString ().trim ();
                answer8b = etAnswer8b.getText ().toString ().trim ();
    
                if (cbAnswer7aList.size () == 1) {
                    answer7a = cbAnswer7aList.get (0);
                } else {
                    for (int i = 0; i < cbAnswer7aList.size (); i++) {
                        if (i == 0) {
                            answer7a = cbAnswer7aList.get (i);
                        } else {
                            answer7a = answer7a + ", " + cbAnswer7aList.get (i);
                        }
                    }
                }
    
    
                if (answer1.length () == 0) {
                    llAnswer1.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer1.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer2.length () == 0) {
                    llAnswer2.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer2.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer3.length () == 0) {
                    llAnswer3.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer3.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer4.length () == 0) {
                    llAnswer4.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer4.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer5.length () == 0) {
                    llAnswer5.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer5.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer5a.length () == 0) {
                    llAnswer5a.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer5a.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer5b.length () == 0) {
                    llAnswer5b.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer5b.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer5c.length () == 0) {
                    llAnswer5c.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer5c.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer5d.length () == 0) {
                    llAnswer5d.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer5d.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer6.length () == 0) {
                    llAnswer6.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer6.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer6a.length () == 0) {
                    llAnswer6a.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer6a.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer6b.length () == 0) {
                    llAnswer6b.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer6b.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer6c.length () == 0) {
                    llAnswer6c.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer6c.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer6d.length () == 0) {
                    llAnswer6d.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer6d.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer7.length () == 0) {
                    llAnswer7.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer7.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer7a.length () == 0) {
                    llAnswer7a.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer7a.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer7b.length () == 0) {
                    llAnswer7b.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer7b.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer7c.length () == 0) {
                    llAnswer7c.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer7c.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer8.length () == 0) {
                    llAnswer8.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer8.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer8a.length () == 0) {
                    llAnswer8a.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer8a.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
                if (answer8b.length () == 0) {
                    llAnswer8b.setBackground (getResources ().getDrawable (R.drawable.bg_question_red));
                } else {
                    llAnswer8b.setBackground (getResources ().getDrawable (R.drawable.bg_question));
                }
    
                int bt1 = 0;
                int bt2 = 0;
                int bt3 = 0;
                int bt4 = 0;
    
                if (button1 > 0) {
                    if (answer5.length () > 0 &&
                            answer5a.length () > 0 &&
                            answer5b.length () > 0 &&
                            answer5c.length () > 0 &&
                            answer5d.length () > 0) {
                        bt1 = 1;
                    } else {
                        bt1 = 2;
                    }
                }
    
                if (button2 > 0) {
                    if (answer6.length () > 0 &&
                            answer6a.length () > 0 &&
                            answer6b.length () > 0 &&
                            answer6c.length () > 0 &&
                            answer6d.length () > 0) {
                        bt2 = 1;
                    } else {
                        bt2 = 2;
                    }
                }
    
                if (button3 > 0) {
                    if (answer7.length () > 0 &&
                            answer7a.length () > 0 &&
                            answer7b.length () > 0 &&
                            answer7c.length () > 0) {
                        bt3 = 1;
                    } else {
                        bt3 = 2;
                    }
                }
    
                if (button4 > 0) {
                    if (answer8.length () > 0 &&
                            answer8a.length () > 0 &&
                            answer8b.length () > 0) {
                        bt4 = 1;
                    } else {
                        bt4 = 2;
                    }
                }
    
                if (answer1.length () > 0 &&
                        answer2.length () > 0 &&
                        answer3.length () > 0) {
                    if (answer3.equalsIgnoreCase (getResources ().getString (R.string.question3f))) {
                        if (answer4.length () > 0) {
                            if (bt1 != 2 && bt2 != 2 && bt3 != 2 && bt4 != 2) {
                                sendSurveyDetailsToServer (answer1, answer2, answer3, answer4, answer5, answer5a, answer5b, answer5c, answer5d, answer6, answer6a, answer6b, answer6c, answer6d, answer7, answer7a, answer7b, answer7c, answer8, answer8a, answer8b);
                            }
                        }
                    } else {
                        if (bt1 != 2 && bt2 != 2 && bt3 != 2 && bt4 != 2) {
                            sendSurveyDetailsToServer (answer1, answer2, answer3, answer4, answer5, answer5a, answer5b, answer5c, answer5d, answer6, answer6a, answer6b, answer6c, answer6d, answer7, answer7a, answer7b, answer7c, answer8, answer8a, answer8b);
                        }
                    }
                }

//                Log.e ("answer1", answer1);
//                Log.e ("answer2", answer2);
//                Log.e ("answer3", answer3);
//                Log.e ("answer4", answer4);
//                Log.e ("answer5", answer5);
//                Log.e ("answer5a", answer5a);
//                Log.e ("answer5b", answer5b);
//                Log.e ("answer5c", answer5c);
//                Log.e ("answer5d", answer5d);
//                Log.e ("answer6", answer6);
//                Log.e ("answer6a", answer6a);
//                Log.e ("answer6b", answer6b);
//                Log.e ("answer6c", answer6c);
//                Log.e ("answer6d", answer6d);
//                Log.e ("answer7", answer7);
//                Log.e ("answer7a", answer7a);
//                Log.e ("answer7b", answer7b);
//                Log.e ("answer7c", answer7c);
//                Log.e ("answer8", answer8);
//                Log.e ("answer8a", answer8a);
//                Log.e ("answer8b", answer8b);
            }
        });
    }
    
    private void sendSurveyDetailsToServer (final String answer1, final String answer2,
                                            final String answer3, final String answer4,
                                            final String answer5, final String answer5a,
                                            final String answer5b, final String answer5c,
                                            final String answer5d, final String answer6,
                                            final String answer6a, final String answer6b,
                                            final String answer6c, final String answer6d,
                                            final String answer7, final String answer7a,
                                            final String answer7b, final String answer7c,
                                            final String answer8, final String answer8a,
                                            final String answer8b) {
        if (NetworkConnection.isNetworkAvailable (getActivity ())) {
            Utils.showProgressDialog (getActivity (), progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), true);
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.DAILY_SURVEY, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.DAILY_SURVEY,
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
                                        progressDialog.dismiss ();
                                        getDialog ().dismiss ();
    
                                        appDetailsPref.putIntPref (getActivity (), AppDetailsPref.SURVEY_STATUS, jsonObj.getInt (AppConfigTags.SURVEY_STATUS));
                                        appDetailsPref.putIntPref (getActivity (), AppDetailsPref.BUTTON1, 0);
                                        appDetailsPref.putIntPref (getActivity (), AppDetailsPref.BUTTON2, 0);
                                        appDetailsPref.putIntPref (getActivity (), AppDetailsPref.BUTTON3, 0);
                                        appDetailsPref.putIntPref (getActivity (), AppDetailsPref.BUTTON4, 0);
    
                                        Intent newIntent = new Intent (getActivity (), MainActivity.class);
                                        newIntent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        newIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity (newIntent);
                                    } else {
//                                        Utils.showSnackBar (getActivity (), clMain, message, Snackbar.LENGTH_LONG, null, null);
                                    }
                                    progressDialog.dismiss ();
                                } catch (Exception e) {
                                    progressDialog.dismiss ();
//                                    Utils.showSnackBar (getActivity (), clMain, getResources ().getString (R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace ();
                                }
                            } else {
//                                Utils.showSnackBar (getActivity (), clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
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
//                            Utils.showSnackBar (getActivity (), clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                            progressDialog.dismiss ();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<> ();
                    Calendar c = Calendar.getInstance ();
                    SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd", Locale.US);
                    
                    params.put (AppConfigTags.ANSWER_1, answer1);
                    params.put (AppConfigTags.ANSWER_2, answer2);
                    params.put (AppConfigTags.ANSWER_3, answer3);
                    params.put (AppConfigTags.ANSWER_4, answer4);
                    params.put (AppConfigTags.ANSWER_5, answer5);
                    params.put (AppConfigTags.ANSWER_5A, answer5a);
                    params.put (AppConfigTags.ANSWER_5B, answer5b);
                    params.put (AppConfigTags.answer_5C, answer5c);
                    params.put (AppConfigTags.ANSWER_5D, answer5d);
                    params.put (AppConfigTags.ANSWER_6, answer6);
                    params.put (AppConfigTags.ANSWER_6A, answer6a);
                    params.put (AppConfigTags.ANSWER_6B, answer6b);
                    params.put (AppConfigTags.ANSWER_6C, answer6c);
                    params.put (AppConfigTags.ANSWER_6D, answer6d);
                    params.put (AppConfigTags.ANSWER_7, answer7);
                    params.put (AppConfigTags.ANSWER_7A, answer7a);
                    params.put (AppConfigTags.ANSWER_7B, answer7b);
                    params.put (AppConfigTags.ANSWER_7C, answer7c);
                    params.put (AppConfigTags.ANSWER_8, answer8);
                    params.put (AppConfigTags.ANSWER_8A, answer8a);
                    params.put (AppConfigTags.ANSWER_8B, answer8b);
                    params.put (AppConfigTags.SURVEY_ID, String.valueOf (appDetailsPref.getIntPref (getActivity (), AppDetailsPref.SURVEY_ID)));
                    params.put (AppConfigTags.DAY_NUMBER, String.valueOf (appDetailsPref.getIntPref (getActivity (), AppDetailsPref.SURVEY_DAY_ELAPSED) + 1));
                    params.put (AppConfigTags.DATE, df.format (c.getTime ()));
                    
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
//            Utils.showSnackBar (getActivity (), clMain, getResources ().getString (R.string.snackbar_text_no_internet_connection_available), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_go_to_settings), new View.OnClickListener () {
//                @Override
//                public void onClick (View v) {
//                    Intent dialogIntent = new Intent (Settings.ACTION_SETTINGS);
//                    dialogIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity (dialogIntent);
//                }
//            });
        }
    }
    
}