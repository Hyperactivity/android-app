package com.hyperactivity.android_app.activities;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.AdminActionCallback;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.forum.models.Note;
import com.hyperactivity.android_app.forum.models.Thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoteListFragment extends ListFragment {

    private String[] from = new String[]{"note_headline", "note_text"};
    private int[] to = new int[]{R.id.note_headline, R.id.note_text};

    private List<Note> currentNotes;
    private List<HashMap<String, String>> data;
    private AdminActionCallback callback;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TODO: debug only
        if (true || ((Engine) getActivity().getApplication()).getClientInfo().getAccount().isAdmin()) {
            registerForContextMenu(getListView());
        }
    }

    public void updateNoteList(List<Note> noteList) {
        data = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < noteList.size(); i++) {
            Note note = noteList.get(i);
            data.add(noteToMap(note));
        }

        if (getActivity() != null) {
            final SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.note_list_item, from, to);
            data = null;

            setListAdapter(adapter);
            getListView().setDivider(null);
        }
        currentNotes = noteList;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (position >= 0 && position < currentNotes.size()) {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).visitNote(currentNotes.get(position));
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (data != null) {
            SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.note_list_item, from, to);
            setListAdapter(adapter);
            data = null;
        }
    }

    private HashMap<String, String> noteToMap(Note note) {
        String headline = note.getHeadLine();
        String text = note.getText();
        HashMap<String, String> row = new HashMap<String, String>();
        row.put("note_headline", headline);
        row.put("note_text", text);
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
        Note note = currentNotes.get(info.position);

        switch (item.getItemId()) {

            /*
            case R.id.admin_edit:
               callback.editThread(thread);
                return true;
            */

            case R.id.admin_delete:
//                callback.deleteThread(thread);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void setCallback(AdminActionCallback callback) {
        this.callback = callback;
    }
}