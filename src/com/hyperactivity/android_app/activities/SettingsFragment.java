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
import android.widget.*;

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
    private TextView caption;
    private Button sendButton;
    private CheckBox ageCheckBox;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_fragment, null);

        caption = (TextView) view.findViewById(R.id.caption)
                .findViewById(R.id.caption_text);

        caption.setText("Profilinställningar");

        profileNameField = (TextView) view.findViewById(R.id.profile_name_field);
        profileDescriptionField = (EditText) view.findViewById(R.id.profile_description_field);
        ageCheckBox = (CheckBox) view.findViewById(R.id.showAge);

        sendButton = (Button) view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        return view;
    }

    public void save() {
        Exception ex;
        try {
            final Activity mainActivity = getActivity();
            ((Engine) getActivity().getApplication()).getServerLink().updateAccount(
                    profileDescriptionField.getText().toString(),
                    ageCheckBox.isChecked(),
                    null,
                    true,
                    new NetworkCallback() {
                @Override
                public Activity getActivity() {
                    return mainActivity;

                }
            });
        } catch (Exception e) {

        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {

        super.onResume();

        try {
            currentAccount = ((Engine) getActivity().getApplication()).getClientInfo().getAccount();

           // ageCheckBox.setChecked(currentAccount.isShowBirthDate());
            profileNameField.setText(currentAccount.getUsername());
            profileDescriptionField.setText(currentAccount.getProfileDescription());

        } catch (Exception e) {
        }
    }

}

