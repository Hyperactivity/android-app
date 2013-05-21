package com.hyperactivity.android_app.activities;

import java.util.ArrayList;
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
import android.widget.TextView;

import android.widget.Toast;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.Category;
import com.hyperactivity.android_app.forum.models.Thread;

public class ChatFragment extends Fragment {

    ThreadListFragment searchResultList;
    TextView noResultsText;
    EditText searchEditText;

    ChatListFragment chatListFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        chatListFragment = new ChatListFragment();
        // THIS WILL BE REMOVED, IT'S HERE SO THAT THE PAGE WILL NOT BE EMPTY
        View view = inflater.inflate(R.layout.chat_fragment, null);

        final EditText send_text = (EditText)view.findViewById(R.id.send_text);
        final Button   send_button = (Button)view.findViewById(R.id.send_button);

        chatListFragment.addChatLine(Integer.toString(R.drawable.default_profile_picture), "Tjaba!");
        chatListFragment.addChatLine(Integer.toString(R.drawable.default_profile_picture), "Läget?");
        chatListFragment.addChatLine(Integer.toString(R.drawable.default_profile_picture), "Sverige vann, jihoo!");

        send_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chatListFragment.addChatLine(Integer.toString(R.drawable.default_profile_picture), send_text.getText().toString());
                chatListFragment.updateChatList();
                send_text.setText("");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        chatListFragment.updateChatList();
        getFragmentManager().beginTransaction().replace(R.id.chat_list_container, chatListFragment).commit();
    }
}
