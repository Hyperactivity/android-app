package com.hyperactivity.android_app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hyperactivity.android_app.R;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-05-15
 * Time: 12:55
 */
public class RegisterFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, null);
        return view;
    }

}
