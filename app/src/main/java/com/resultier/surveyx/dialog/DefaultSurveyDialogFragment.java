package com.resultier.surveyx.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.resultier.surveyx.R;

import java.util.Calendar;


public class DefaultSurveyDialogFragment extends DialogFragment {
    String time = "";
    private ImageView ivCancel;
    private TextView tvTitle;
    private View v1;
    private RelativeLayout rl1;
    private TextView tvQuestion1;
    private EditText etQuestion1Date;
    private TextView tvQuestion2;
    private EditText etQuestion2Date;
    private TextView tvQuestion3;
    private LinearLayout tbCheckBoxes;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;
    private CheckBox cb6;
    private RelativeLayout rlQuestion4;
    private TextView tvQuestion4;
    private LinearLayout llCheckBoxes2;
    private CheckBox cb7;
    private CheckBox cb8;
    private CheckBox cb9;
    private int mHour, mMinute;
    
    public static DefaultSurveyDialogFragment newInstance () {
        DefaultSurveyDialogFragment f = new DefaultSurveyDialogFragment ();
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
        if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor (ContextCompat.getColor (getActivity (), R.color.text_color_white));
        }
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
        tvTitle = (TextView) root.findViewById (R.id.tvTitle);
        v1 = (View) root.findViewById (R.id.v1);
        rl1 = (RelativeLayout) root.findViewById (R.id.rl1);
        tvQuestion1 = (TextView) root.findViewById (R.id.tvQuestion1);
        etQuestion1Date = (EditText) root.findViewById (R.id.etQuestion1Date);
        tvQuestion2 = (TextView) root.findViewById (R.id.tvQuestion2);
        etQuestion2Date = (EditText) root.findViewById (R.id.etQuestion2Date);
        tvQuestion3 = (TextView) root.findViewById (R.id.tvQuestion3);
        tbCheckBoxes = (LinearLayout) root.findViewById (R.id.tbCheckBoxes);
        cb1 = (CheckBox) root.findViewById (R.id.cb1);
        cb2 = (CheckBox) root.findViewById (R.id.cb2);
        cb3 = (CheckBox) root.findViewById (R.id.cb3);
        cb4 = (CheckBox) root.findViewById (R.id.cb4);
        cb5 = (CheckBox) root.findViewById (R.id.cb5);
        cb6 = (CheckBox) root.findViewById (R.id.cb6);
        rlQuestion4 = (RelativeLayout) root.findViewById (R.id.rlQuestion4);
        tvQuestion4 = (TextView) root.findViewById (R.id.tvQuestion4);
        llCheckBoxes2 = (LinearLayout) root.findViewById (R.id.llCheckBoxes2);
        cb7 = (CheckBox) root.findViewById (R.id.cb7);
        cb8 = (CheckBox) root.findViewById (R.id.cb8);
        cb9 = (CheckBox) root.findViewById (R.id.cb9);
    }
    
    private void initBundle () {
        Bundle bundle = this.getArguments ();
    }
    
    private void initData () {
        rlQuestion4.setVisibility (View.GONE);
        
    }
    
    private void initListener () {
        
        etQuestion1Date.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                
                
                final Calendar c = Calendar.getInstance ();
                mHour = c.get (Calendar.HOUR_OF_DAY);
                mMinute = c.get (Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog (getActivity (), new TimePickerDialog.OnTimeSetListener () {
                    @Override
                    public void onTimeSet (TimePicker view, int hourOfDay, int minute) {
                        etQuestion1Date.setText (String.format ("%02d", hourOfDay) + ":" + String.format ("%02d", minute));
                        time = etQuestion1Date.getText ().toString ().trim ();
                        Log.e ("time", time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show ();
                
            }
        });
        
        
        etQuestion2Date.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                
                
                final Calendar c = Calendar.getInstance ();
                mHour = c.get (Calendar.HOUR_OF_DAY);
                mMinute = c.get (Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog (getActivity (), new TimePickerDialog.OnTimeSetListener () {
                    @Override
                    public void onTimeSet (TimePicker view, int hourOfDay, int minute) {
                        etQuestion2Date.setText (String.format ("%02d", hourOfDay) + ":" + String.format ("%02d", minute));
                        time = etQuestion2Date.getText ().toString ().trim ();
                        Log.e ("time", time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show ();
                
            }
        });
        
        ivCancel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                getDialog ().dismiss ();
            }
        });
        
        
        cb6.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                rlQuestion4.setVisibility (View.VISIBLE);
            }
        });
        
    }
    
    
}