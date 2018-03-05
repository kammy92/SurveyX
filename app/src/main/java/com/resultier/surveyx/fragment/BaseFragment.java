package com.resultier.surveyx.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.resultier.surveyx.R;
import com.resultier.surveyx.activity.ViewPagerActivity;
import com.resultier.surveyx.utils.AppConfigTags;
import com.resultier.surveyx.utils.Constants;
import com.resultier.surveyx.utils.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class BaseFragment extends android.support.v4.app.Fragment {
    private int page;
    private Button btNext;
    private Button btPrev;

    public static BaseFragment newInstance(int page) {
        BaseFragment fragmentFirst = new BaseFragment();
        Bundle args = new Bundle();
        args.putInt(AppConfigTags.PAGE_NUMBER, page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(AppConfigTags.PAGE_NUMBER, 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        initListener();


        if (page == 0) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 2.0f);
            param.setMargins(10, 10, 10, 10);
            btPrev.setVisibility(View.GONE);
            btNext.setVisibility(View.VISIBLE);
            btNext.setLayoutParams(param);
        } else {
            btPrev.setVisibility(View.VISIBLE);
            btNext.setVisibility(View.VISIBLE);
        }

        if (page == Constants.questionsList.size() - 1) {
            btNext.setText("SUBMIT");
        } else {
            btNext.setText("NEXT");
        }

        return view;
    }

    public boolean isPackageExists(String targetPackage) {
        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = getActivity().getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView(View view) {

        btNext = (Button) view.findViewById(R.id.btNextInFragment);
        btPrev = (Button) view.findViewById(R.id.btPrevInFragment);


    }

    private void initListener() {
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.hideSoftKeyboard (getActivity ());
                if (page == Constants.questionsList.size() - 1) {
//                        Utils.showToast (getActivity (), "Last page");
                    Utils.showLog(Log.INFO, "Page :", "Last page", true);


                } else {
                    ViewPagerActivity.nextPage();
                }
            }
        });
        btPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Utils.hideSoftKeyboard (getActivity ());
                ViewPagerActivity.prevPage();
            }
        });


    }


}