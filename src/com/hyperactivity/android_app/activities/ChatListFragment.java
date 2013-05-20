package com.hyperactivity.android_app.activities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.support.v4.app.ListFragment;
import android.widget.SimpleAdapter;
import com.hyperactivity.android_app.R;

public class ChatListFragment extends ListFragment {

    private String[] from = new String[]{"chat_item_pic", "chat_item_text"};
    private int[] to = new int[]{R.id.chat_item_pic, R.id.chat_item_text};

    private List<HashMap<String, String>> data;

    public void updateChatList() {
        data = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> row = new HashMap<String, String>();
        row.put("chat_item_pic", Integer.toString(R.drawable.active_house));
        row.put("chat_item_text", "TEST TEXT");
        data.add(row);

        HashMap<String, String> row2 = new HashMap<String, String>();
        row2.put("chat_item_pic", Integer.toString(R.drawable.active_house));
        row2.put("chat_item_text", "HABIB");
        data.add(row2);

        HashMap<String, String> row3 = new HashMap<String, String>();
        row3.put("chat_item_pic", Integer.toString(R.drawable.active_house));
        row3.put("chat_item_text", "HABIB");
        data.add(row3);

        HashMap<String, String> row4 = new HashMap<String, String>();
        row4.put("chat_item_pic", Integer.toString(R.drawable.active_house));
        row4.put("chat_item_text", "HABIB");
        data.add(row4);

        HashMap<String, String> row5 = new HashMap<String, String>();
        row5.put("chat_item_pic", Integer.toString(R.drawable.active_house));
        row5.put("chat_item_text", "HABIB");
        data.add(row5);


        if (getActivity() != null) {
            load();
        }
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