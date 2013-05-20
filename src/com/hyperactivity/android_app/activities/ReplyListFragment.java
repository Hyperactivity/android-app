package com.hyperactivity.android_app.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.AdminActionCallback;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.*;

public class ReplyListFragment extends ListFragment {

    public class SimpleAdapterEx extends SimpleAdapter {
        private int mResource;
        private int[] mTo;
        private String[] mFrom;

        private List<? extends Map<String, ?>> mData;

        public SimpleAdapterEx(Context context, List<? extends Map<String, ?>> data,
                               int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);

            mResource = resource;
            mData = data;
            mTo = to;
            mFrom = from;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            v = createViewFromResourceEx(v, position, convertView, parent, mResource);

            return v;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = super.getDropDownView(position, convertView, parent);

            v = createViewFromResourceEx(v, position, convertView, parent, mResource);

            return v;
        }

        private View createViewFromResourceEx(View v, int position, View convertView,
                                              ViewGroup parent, int resource) {

            bindView(position, v);

            return v;
        }

        private void bindView(int position, View view) {
            final Map dataSet = mData.get(position);
            if (dataSet == null) {
                return;
            }

            final ViewBinder binder = getViewBinder();
            final String[] from = mFrom;
            final int[] to = mTo;
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


    private String[] from = new String[] {"reply_author", "reply_text", "reply_timestamp", "reply_image"};
	private int[] to = new int[] {R.id.reply_author, R.id.reply_text, R.id.reply_timestamp, R.id.reply_image};
	
	private List<HashMap<String, Object>> data;
    private List<Reply> currentReplies;
    private AdminActionCallback callback;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TODO: debug only
        if (true || ((Engine) getActivity().getApplication()).getClientInfo().getAccount().isAdmin()) {
            registerForContextMenu(getListView());
        }
    }

    public void updateReplyList(List<Reply> replyList) {
        currentReplies = replyList;
		data = new ArrayList<HashMap<String, Object>>();


		for(int i = 0; i < replyList.size(); i++) {
			Reply reply= replyList.get(i);
			data.add(replyToMap(reply));
		}
		
		if (getActivity() != null) {
            SimpleAdapterEx adapter = new SimpleAdapterEx(getActivity().getBaseContext(), data, R.layout.reply_list_item, from, to);
	        setListAdapter(adapter);
	        data = null;
		}
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (position >= 0 && position < currentReplies.size()) {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).visitAccount(currentReplies.get(position).getAccount());
            }
        }
    }
	
	@Override
	public void onResume() {
		super.onResume();
		if (data != null) {
            SimpleAdapterEx adapter = new SimpleAdapterEx(getActivity().getBaseContext(), data, R.layout.reply_list_item, from, to);
	        setListAdapter(adapter);
	        data = null;
		}
	}
	
	private HashMap<String, Object> replyToMap(Reply reply) {
		String author = reply.getAccount() != null ? reply.getAccount().getUsername() : null;
		String text = reply.getText();
		HashMap<String, Object> row = new HashMap<String, Object>();
		row.put("reply_author", author == null ? "<Anonymous>" : author);
		row.put("reply_text", text);
		row.put("reply_timestamp", reply.getTime().toString());
        row.put("reply_image", reply.getAccount().getProfilePicture());
		return row;
	}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.admin_edit_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(callback == null) {
            return super.onContextItemSelected(item);
        }

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Reply reply = currentReplies.get(info.position);

        switch (item.getItemId()) {

            case R.id.admin_edit:
                callback.editReply(reply);
                return true;

            case R.id.admin_delete:
                callback.deleteReply(reply);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void setCallback(AdminActionCallback callback) {
        this.callback = callback;
    }
}