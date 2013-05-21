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

    //final EditText send_text = (EditText) getView().findViewById(R.id.send_text);
    //final Button   send_button = (Button) getView().findViewById(R.id.send_button);




    private List<HashMap<String, String>> data;

    public void updateChatList() {
        data = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> row = new HashMap<String, String>();
        row.put("chat_item_pic", Integer.toString(R.drawable.default_profile_picture));
        row.put("chat_item_text", "Hej hej");
        data.add(row);

        HashMap<String, String> row2 = new HashMap<String, String>();
        row2.put("chat_item_pic", Integer.toString(R.drawable.default_profile_picture));
        row2.put("chat_item_text", "What up homie?");
        data.add(row2);

        HashMap<String, String> row3 = new HashMap<String, String>();
        row3.put("chat_item_pic", Integer.toString(R.drawable.default_profile_picture));
        row3.put("chat_item_text", "Vad betyder homie?");
        data.add(row3);

        HashMap<String, String> row4 = new HashMap<String, String>();
        row4.put("chat_item_pic", Integer.toString(R.drawable.default_profile_picture));
        row4.put("chat_item_text", "Got med frid.");
        data.add(row4);

        HashMap<String, String> row5 = new HashMap<String, String>();
        row5.put("chat_item_pic", Integer.toString(R.drawable.default_profile_picture));
        row5.put("chat_item_text", "Hörde ni om Gottried??");
        data.add(row5);


        if (getActivity() != null) {
            load();
        }





        /*
        send_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //send_text.setText("");
            }
        });
        */
    }

    @Override
    public void onResume() {
        super.onResume();
        if (data != null) {
            load();
        }
    }

    private void load() {
        setListAdapter(new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.chat_list_item, from, to));
        data = null;
        getListView().setDivider(null);
    }

}