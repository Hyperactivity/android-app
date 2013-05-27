package com.hyperactivity.android_app.activities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.forum.models.Shout;

public class ChatListFragment extends ListFragment {
    public class SimpleAdapterEx extends SimpleAdapter {

    int resource;
    private String[] from = new String[]{"chat_item_pic", "chat_item_text"};
    private int[] to = new int[]{R.id.chat_item_pic, R.id.chat_item_text};

    private List<? extends Map<String, ?>> data;

    public SimpleAdapterEx(Context context, List<? extends Map<String, ?>> data,
                           int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        this.resource = resource;
        this.data = data;
        this.to = to;
        this.from = from;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);

        v = createViewFromResourceEx(v, position, convertView, parent, resource);

        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getDropDownView(position, convertView, parent);

        v = createViewFromResourceEx(v, position, convertView, parent, resource);

        return v;
    }

    private View createViewFromResourceEx(View v, int position, View convertView,
                                          ViewGroup parent, int resource) {

        bindView(position, v);

        return v;
    }

    private void bindView(int position, View view) {
        final Map dataSet = data.get(position);
        if (dataSet == null) {
            return;
        }

        final SimpleAdapter.ViewBinder binder = getViewBinder();
        final String[] from = this.from;
        final int[] to = this.to;
        final int count = to.length;

        for (int i = 0; i < count; i++) {
            final View v = view.findViewById(to[i]);
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }

                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, data, text);
                }

                if (!bound) {
                    if (v instanceof ImageView) {
                        if (data instanceof Bitmap) {
                            setViewImage((ImageView) v, (Bitmap)data);
                        }
                    }
                }
            }
        }
    }




        private void setViewImage(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
    private String[] from = new String[]{"chat_item_pic", "chat_item_text"};
    private int[] to = new int[]{R.id.chat_item_pic, R.id.chat_item_text};
    private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

    private void updateChatList() {
        if (getActivity() != null) {
            setListAdapter(new SimpleAdapterEx(getActivity().getBaseContext(), data, R.layout.chat_list_item, from, to));
        }
    }
    public void updateData(List<Shout> shoutBox){
        data = new ArrayList<HashMap<String, Object>>();
        for(Shout shout: shoutBox){
            addChatLine(shout.getAccount().getProfilePicture(), shout.getText());
        }
        updateChatList();
    }

    private void addChatLine(Object pic, String text) {
        if(pic == null){
            pic = Integer.toString(R.drawable.default_profile_picture);
        }
        HashMap<String, Object> row = new HashMap<String, Object>();
        row.put("chat_item_pic", pic);
        row.put("chat_item_text", text);
        data.add(0,row);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (data != null) {
//            updateChatList(currentShoutBox);
//        }
    }


}