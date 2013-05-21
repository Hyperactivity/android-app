package com.hyperactivity.android_app.activities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import com.hyperactivity.android_app.R;

public class ChatListFragment extends ListFragment {

    private String[] from = new String[]{"chat_item_pic", "chat_item_text"};
    private int[] to = new int[]{R.id.chat_item_pic, R.id.chat_item_text};

    private List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();;

    public void updateChatList() {
        if (getActivity() != null) {
            setListAdapter(new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.chat_list_item, from, to));
        }
    }

    public void addChatLine(String picId, String text) {
        HashMap<String, String> row = new HashMap<String, String>();
        row.put("chat_item_pic", picId);
        row.put("chat_item_text", text);
        data.add(row);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (data != null) {
            updateChatList();
        }
    }
}