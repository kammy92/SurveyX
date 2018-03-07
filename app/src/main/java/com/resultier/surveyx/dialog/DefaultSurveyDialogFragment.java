package com.resultier.surveyx.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.resultier.surveyx.R;
import com.resultier.surveyx.utils.Utils;

import java.util.Calendar;


public class DefaultSurveyDialogFragment extends DialogFragment {
    String time = "";
    int button1 = 0;
    int button2 = 0;
    int button3 = 0;
    int button4 = 0;
    private ImageView ivCancel;
    private EditText etAnswer1;
    private EditText etAnswer2;
    private LinearLayout llQuestion4;
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
    private EditText etAnswer6b;
    private EditText etAnswer6c;
    private EditText etAnswer6d;
    private EditText etAnswer7;
    private LinearLayout llQuestion7;
    private EditText etAnswer7b;
    private EditText etAnswer7c;
    private EditText etAnswer8;
    private LinearLayout llQuestion8;
    private EditText etAnswer8a;
    private EditText etAnswer8b;
    private int mHour, mMinute;
    
    public static DefaultSurveyDialogFragment newInstance (int button1, int button2, int button3, int button4) {
        DefaultSurveyDialogFragment f = new DefaultSurveyDialogFragment ();
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
        View root = inflater.inflate (R.layout.fragment_dialog_default, container, false);
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
        llQuestion4 = (LinearLayout) root.findViewById (R.id.llQuestion4);
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
        etAnswer6c = (EditText) root.findViewById (R.id.etAnswer6c);
        etAnswer6d = (EditText) root.findViewById (R.id.etAnswer6d);
    
        etAnswer7 = (EditText) root.findViewById (R.id.etAnswer7);
        llQuestion7 = (LinearLayout) root.findViewById (R.id.llQuestion7);
        etAnswer7b = (EditText) root.findViewById (R.id.etAnswer7b);
        etAnswer7c = (EditText) root.findViewById (R.id.etAnswer7c);
    
        etAnswer8 = (EditText) root.findViewById (R.id.etAnswer8);
        llQuestion8 = (LinearLayout) root.findViewById (R.id.llQuestion8);
        etAnswer8a = (EditText) root.findViewById (R.id.etAnswer8a);
        etAnswer8b = (EditText) root.findViewById (R.id.etAnswer8b);
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
    
        if (button1 > 0) {
            etAnswer5.setText ("YES");
            llQuestion5.setVisibility (View.VISIBLE);
            etAnswer5a.setText (String.valueOf (button1));
        } else {
            etAnswer5.setText ("NO");
            llQuestion5.setVisibility (View.GONE);
        }
    
        if (button2 > 0) {
            etAnswer6.setText ("YES");
            llQuestion6.setVisibility (View.VISIBLE);
            etAnswer6a.setText (String.valueOf (button2));
        } else {
            etAnswer6.setText ("NO");
            llQuestion6.setVisibility (View.GONE);
        }
    
        if (button3 > 0) {
            etAnswer7.setText ("YES");
            llQuestion7.setVisibility (View.VISIBLE);
            etAnswer7b.setText (String.valueOf (button3));
        } else {
            etAnswer7.setText ("NO");
            llQuestion7.setVisibility (View.GONE);
        }
    
        if (button4 > 0) {
            etAnswer8.setText ("YES");
            llQuestion8.setVisibility (View.VISIBLE);
            etAnswer8a.setText (String.valueOf (button4));
        } else {
            etAnswer8.setText ("NO");
            llQuestion8.setVisibility (View.GONE);
        }
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
                                etAnswer1.setText (String.format ("%02d", hourOfDay) + ":" + String.format ("%02d", minute) + " PM");
                            } else {
                                etAnswer1.setText (String.format ("%02d", hourOfDay - 12) + ":" + String.format ("%02d", minute) + " PM");
                            }
                        } else {
                            etAnswer1.setText (String.format ("%02d", hourOfDay) + ":" + String.format ("%02d", minute) + " AM");
                        }
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
                                etAnswer2.setText (String.format ("%02d", hourOfDay) + ":" + String.format ("%02d", minute) + " PM");
                            } else {
                                etAnswer2.setText (String.format ("%02d", hourOfDay - 12) + ":" + String.format ("%02d", minute) + " PM");
                            }
                        } else {
                            etAnswer2.setText (String.format ("%02d", hourOfDay) + ":" + String.format ("%02d", minute) + " AM");
                        }
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show ();
                
            }
        });
        rb1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                rb6.setChecked (false);
                rb2.setChecked (false);
                rb3.setChecked (false);
                rb4.setChecked (false);
                rb5.setChecked (false);
                llQuestion4.setVisibility (View.GONE);
            }
        });
        rb2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                rb1.setChecked (false);
                rb6.setChecked (false);
                rb3.setChecked (false);
                rb4.setChecked (false);
                rb5.setChecked (false);
                llQuestion4.setVisibility (View.GONE);
            }
        });
        rb3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                rb1.setChecked (false);
                rb2.setChecked (false);
                rb6.setChecked (false);
                rb4.setChecked (false);
                rb5.setChecked (false);
                llQuestion4.setVisibility (View.GONE);
            }
        });
        rb4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                rb1.setChecked (false);
                rb2.setChecked (false);
                rb3.setChecked (false);
                rb6.setChecked (false);
                rb5.setChecked (false);
                llQuestion4.setVisibility (View.GONE);
            }
        });
        rb5.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                rb1.setChecked (false);
                rb2.setChecked (false);
                rb3.setChecked (false);
                rb4.setChecked (false);
                rb6.setChecked (false);
                llQuestion4.setVisibility (View.GONE);
            }
        });
        rb6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                rb1.setChecked (false);
                rb2.setChecked (false);
                rb3.setChecked (false);
                rb4.setChecked (false);
                rb5.setChecked (false);
                llQuestion4.setVisibility (View.VISIBLE);
            }
        });
    
        rb7.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                rb8.setChecked (false);
                rb9.setChecked (false);
            }
        });
        rb8.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                rb7.setChecked (false);
                rb9.setChecked (false);
            }
        });
        rb9.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
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
    }
}