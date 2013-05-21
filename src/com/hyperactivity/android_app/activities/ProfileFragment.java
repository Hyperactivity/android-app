package com.hyperactivity.android_app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.forum.models.Account;

public class ProfileFragment extends Fragment {

    private Account currentAccount;
    private ImageView profilePictureView;
    private TextView profileNameField, birthDateField, profileDescriptionField;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, null);

        profilePictureView = (ImageView) view.findViewById(R.id.profile_picture_view);
        profileNameField = (TextView) view.findViewById(R.id.profile_name_field);
        birthDateField = (TextView) view.findViewById(R.id.profile_birth_date_field);
        profileDescriptionField = (TextView) view.findViewById(R.id.profile_description_field);
        
        TextView caption = (TextView)view.findViewById(R.id.caption).findViewById(R.id.caption_text);
      //  caption.setText((String)getResources().getText(R.string.profile));
        caption.setText("Profil");

        return view;
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
        profilePictureView.setImageResource(R.drawable.com_facebook_profile_default_icon);
        profileNameField.setText(currentAccount.getUsername());
        if (currentAccount.isShowBirthDate() && currentAccount.getBirthDate() != null) birthDateField.setText(currentAccount.getBirthDate().toString());
        profileDescriptionField.setText(currentAccount.getProfileDescription());
    }
}
