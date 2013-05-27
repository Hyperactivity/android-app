package com.hyperactivity.android_app.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.AdminActionCallback;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.Note;
import com.hyperactivity.android_app.forum.models.Reply;
import com.hyperactivity.android_app.forum.models.Thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ViewNoteFragment extends Fragment {

    private Note currentNote;
    private TextView headlineField, textField;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_note_fragment, null);

        headlineField = (TextView) view.findViewById(R.id.note_headline_field);
        textField = (TextView) view.findViewById(R.id.note_text_field);

        return view;
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        if (currentNote != null) {
            updateCurrentNote();
        }
    }

    public void setCurrentNote(Note note) {
        currentNote = note;
        if (getActivity() != null) {
            updateCurrentNote();
        }
    }

    private void updateCurrentNote() {
        headlineField.setText(currentNote.getHeadLine());
        textField.setText(currentNote.getText());
    }


}
