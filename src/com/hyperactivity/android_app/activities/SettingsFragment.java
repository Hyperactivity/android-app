package com.hyperactivity.android_app.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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
import com.hyperactivity.android_app.network.NetworkCallback;

@SuppressLint("NewApi")
public class SettingsFragment extends Fragment {

    private Account currentAccount;

    private ImageView profilePictureView;
    private TextView profileNameField, birthDateField;
    private EditText profileDescriptionField;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_fragment, null);

        currentAccount = ((Engine) getActivity().getApplication())
                .getClientInfo().getAccount();

        profilePictureView = (ImageView) view
                .findViewById(R.id.profile_picture_view);
        profileNameField = (TextView) view
                .findViewById(R.id.profile_name_field);
        birthDateField = (TextView) view
                .findViewById(R.id.profile_birth_date_field);
        profileDescriptionField = (EditText) view
                .findViewById(R.id.profile_description_field);
        updateCurrentAccount();
        TextView caption = (TextView) view.findViewById(R.id.caption)
                .findViewById(R.id.caption_text);
        // caption.setText((String)getResources().getText(R.string.profile));
        caption.setText("Profilinställningar");
        updateCurrentAccount();

        Button tv = (Button) view.findViewById(R.id.sendButton);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        return view;
    }

    public void save() {
        final Activity mainActivity = getActivity();
        ((Engine) getActivity().getApplication()).getServerLink()
                .updateAccount(profileDescriptionField.getText().toString(), true, null, true, new NetworkCallback() {
                    @Override
                    public Activity getActivity() {
                        return mainActivity; // To change body of
                        // implemented methods
                        // use File | Settings |
                        // File Templates.
                    }
                });
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        if (currentAccount != null) {
            updateCurrentAccount();
        }
    }

    public void setCurrentAccount(Account account) {
        currentAccount = account;
        if (getActivity() != null) {
            updateCurrentAccount();
        }
    }

    public void updateCurrentAccount() {
        // profilePictureView.setImageResource(R.drawable.com_facebook_profile_default_icon);
        profilePictureView.setBackground(new BitmapDrawable(getResources(),
                currentAccount.getProfilePicture()));
        profileNameField.setText(currentAccount.getUsername());
        if (currentAccount.isShowBirthDate()
                && currentAccount.getBirthDate() != null)
            birthDateField.setText(currentAccount.getBirthDate().toString());
        profileDescriptionField.setText(currentAccount.getProfileDescription());
    }

}

