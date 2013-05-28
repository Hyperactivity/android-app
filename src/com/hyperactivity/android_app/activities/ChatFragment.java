package com.hyperactivity.android_app.activities;

import java.util.*;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.*;
import com.hyperactivity.android_app.forum.models.Account;
import com.hyperactivity.android_app.network.NetworkCallback;
import net.minidev.json.JSONObject;

public class ChatFragment extends Fragment {

    private List<Shout> currentShoutBox;
    ChatListFragment chatListFragment = new ChatListFragment();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // THIS WILL BE REMOVED, IT'S HERE SO THAT THE PAGE WILL NOT BE EMPTY
        View view = inflater.inflate(R.layout.chat_fragment, null);

        final EditText send_text = (EditText)view.findViewById(R.id.send_text);
        final Button   send_button = (Button)view.findViewById(R.id.send_button);

        TextView caption = (TextView)view.findViewById(R.id.caption).findViewById(R.id.caption_text);
        //  caption.setText((String)getResources().getText(R.string.profile));
        caption.setText(getResources().getText(R.string.chat));
        chatListFragment = new ChatListFragment();
        chatListFragment.updateData(new ArrayList<Shout>());
        send_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(send_text.getText().length() < 1 || send_text.getText().length() > Constants.Transfer.MAX_SHOUT_LENGTH){
                    Toast.makeText(getActivity(), "Text must be over 0 and max " + Constants.Transfer.MAX_SHOUT_LENGTH + " in length!", Toast.LENGTH_SHORT).show();
                    return;
                }
                createShout(getActivity(), send_text.getText().toString());
                send_text.setText("");
            }
        });

        if(currentShoutBox == null){
            loadShoutBox(getActivity(), true);
        }

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        loadShoutBox(getActivity(), false);
        if(currentShoutBox != null){
            chatListFragment.updateData(currentShoutBox);
        }
        getFragmentManager().beginTransaction().replace(R.id.chat_list_container, chatListFragment).commit();
    }

    private void createShout(final Activity activity, String text){
        ((Engine) activity.getApplicationContext()).getServerLink().createShout(text, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);
                loadShoutBox(activity, false);
            }

            @Override
            public Activity getActivity() {
                return activity;
            }
        });
    }

    private void loadShoutBox(final Activity activity, boolean lockWithLoadingScreen) {

                ((Engine) activity.getApplicationContext()).getServerLink().getShoutBox(lockWithLoadingScreen, new NetworkCallback() {
            @Override
            public void onSuccess(JSONObject result, int userId) throws Exception {
                super.onSuccess(result, userId);

                boolean newData = false;

                try {
                    List<Shout> resultShoutBox = (LinkedList<Shout>) (deSerialize(LinkedList.class, (String) result.get(Constants.Transfer.SHOUT_BOX)));

                    if (!resultShoutBox.equals(currentShoutBox)) {
                        currentShoutBox = resultShoutBox;
                        Set<Account> profilePicUpdateList = new HashSet<Account>();

                        for(Shout shout: currentShoutBox){
                            shout.getAccount().setProfilePicture(MainActivity.cachedAccounts.get(shout.getAccount().getId()));
                            if(shout.getAccount().getProfilePicture() == null){
                                profilePicUpdateList.add(shout.getAccount());
                            }
                        }

                        if(!profilePicUpdateList.isEmpty()){
                            ((Engine) activity.getApplicationContext()).getServerLink().loadAvatars(MainActivity.cachedAccounts,profilePicUpdateList, new NetworkCallback() {
                                @Override
                                public void onSuccess(JSONObject result, int userId) throws Exception {
                                    chatListFragment.updateData(currentShoutBox);
                                    chatListFragment.setSelection(currentShoutBox.size()-1);
                                }

                                @Override
                                public Activity getActivity() {
                                    return activity;
                                }
                            });
                        }else{

                        }}
                        newData = true;
//                    }

                } catch (Exception e) {
                    Log.e(Constants.Log.TAG, "exception", e);
                    return;
                }

                if (newData) {
                    Log.d(Constants.Log.TAG, "New shout box");
                    chatListFragment.updateData(currentShoutBox);
                    if(getFragment().isVisible()){
                        chatListFragment.setSelection(currentShoutBox.size()-1);
                    }
                }
            }

            @Override
            public Activity getActivity() {
                return activity;
            }
        });
    }

    private Fragment getFragment(){
        return this;
    }

}
