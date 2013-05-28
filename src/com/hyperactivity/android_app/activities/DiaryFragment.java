package com.hyperactivity.android_app.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.core.ScrollPicker;
import com.hyperactivity.android_app.core.ScrollPickerEventCallback;
import com.hyperactivity.android_app.core.ScrollPickerItem;
import com.hyperactivity.android_app.forum.models.*;
import com.hyperactivity.android_app.forum.models.Thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DiaryFragment extends Fragment implements ScrollPickerEventCallback {

    private ScrollPicker scrollPicker;
    private NoteListFragment noteList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_fragment, null);
        noteList = new NoteListFragment();

        scrollPicker = (ScrollPicker) view.findViewById(R.id.diary_categories_surface_view);
        scrollPicker.setZOrderOnTop(true);
        scrollPicker.getThread().setState(ScrollPicker.ScrollPickerThread.STATE_READY);
        scrollPicker.getThread().setCallback(this);
        scrollPicker.getThread().setBackgroundColor(R.color.black);

        Button newNoteButton = (Button)view.findViewById(R.id.new_note_button);
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).changeFragment(MainActivity.CREATE_NOTE_FRAGMENT);
            }
        });

        Engine engine = ((Engine) getActivity().getApplication());
        scrollPicker.getItemManager().addCategories(getActivity(), engine.getPrivateForum().getCategories(getActivity()), Color.WHITE);

        TextView caption = (TextView)view.findViewById(R.id.caption).findViewById(R.id.caption_text);
        caption.setText(getResources().getText(R.string.diary_and_notes));
        caption.setTextColor(Color.parseColor("#f7eded"));

        return view;
    }

    public List<Note> updateNoteList() {
        List<Note> notes = ((PrivateCategory) scrollPicker.getItemManager().getSelectedItem().getCategory()).getNotes();
        updateNoteList(notes);
        return notes;
    }

    public void updateNoteList(List<Note> notes) {
        noteList.updateNoteList(notes);
    }

    @Override
    public void selectedItemChanged(final ScrollPickerItem selected) {
        //This callback will be executed as the scrollpicker thread.

        final List<Note> notes;
        if (selected != null) {
            notes = ((Engine) getActivity().getApplication()).getPrivateForum().getNotes(getActivity(), (PrivateCategory) selected.getCategory());
        } else {
            notes = new LinkedList<Note>();
        }

        this.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                updateNoteList(notes);
            }
        });
    }

    @Override
    public void scrollPickerReady() {
        updateCategories();
    }

    public void updateCategories() {
        if (scrollPicker != null && scrollPicker.getThread().isReady()) {
            scrollPicker.reset();

            Iterator<Category> it = ((Engine) getActivity().getApplication()).getPrivateForum().getCategories().iterator();

            while (it.hasNext()) {
                Category category = it.next();

                scrollPicker.getItemManager().addItem(category.getImage(getActivity()), category.getHeadLine(), Color.WHITE, category);
            }

            scrollPicker.getItemManager().recalculateItems();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().beginTransaction().remove(noteList).commit();
        scrollPicker.getThread().pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentManager().beginTransaction().replace(R.id.diary_note_list_container, noteList).commit();
        scrollPicker.getThread().unpause();
    }
}
