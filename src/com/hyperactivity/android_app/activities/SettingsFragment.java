package com.hyperactivity.android_app.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.Account;
import com.hyperactivity.android_app.forum.models.Category;
import com.hyperactivity.android_app.forum.models.Thread;

public class SettingsFragment extends Fragment {


    private String[] from = new String[]{"profile_name", "profile_desc"};
    private int[] to = new int[]{R.id.thread_headline, R.id.thread_text};

    private Account currentAccount;

    private EditText profile_name;
    private EditText profile_desc;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_fragment, null);

        currentAccount = ((Engine) getActivity().getApplication()).getClientInfo().getAccount();

        profile_name = (EditText) view.findViewById(R.id.profile_name);
        //profile_name.setText("Hwllo World!");
        //profile_name.setText(currentAccount.getUsername());

        profile_desc = (EditText) view.findViewById(R.id.profile_desc);
        //profile_desc.setText(currentAccount.getProfileDescription());

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


}
