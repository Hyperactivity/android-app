package com.hyperactivity.android_app.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hyperactivity.android_app.Constants;
import com.hyperactivity.android_app.R;
import com.hyperactivity.android_app.core.Engine;
import com.hyperactivity.android_app.core.ScrollPicker;
import com.hyperactivity.android_app.forum.models.Category;

import java.util.Iterator;

public class CreateNoteFragment extends Fragment {
    private ScrollPicker scrollPicker;
    private EditText headlineEditText;
    private EditText textEditText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_note_fragment, null);

        headlineEditText = (EditText) view.findViewById(R.id.note_headline_textfield);
        textEditText = (EditText) view.findViewById(R.id.note_text_textfield);

        scrollPicker = (ScrollPicker) view.findViewById(R.id.note_categories_surface_view);
        scrollPicker.getThread().setState(ScrollPicker.ScrollPickerThread.STATE_READY);
        scrollPicker.setZOrderOnTop(true);
        scrollPicker.getThread().setBackgroundColor(R.color.black);

        Engine engine = ((Engine) getActivity().getApplication());
        scrollPicker.getItemManager().addCategories(getActivity(), engine.getPrivateForum().getCategories(getActivity()), Color.WHITE);

        final EditText headline = headlineEditText;
        final EditText text = textEditText;
        final Button button = (Button) view.findViewById(R.id.create_note_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String h = headline.getText().toString().trim();
                String t = text.getText().toString().trim();
                if (h.length() > 0 && t.length() > 0 && h.length()<=35) {
                    ((Engine) getActivity().getApplication()).getPrivateForum().createNote(getActivity(), scrollPicker.getThread().getItemManager().getSelectedItem().getCategory().getId(), headlineEditText.getText().toString().trim(), textEditText.getText().toString().trim(), true);
                    clearFields();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setMessage(getString(R.string.headline_or_text_empty));
                    alert.setPositiveButton("Ok", null);
                    alert.show();
                }
            }
        });

        TextView caption = (TextView)view.findViewById(R.id.caption).findViewById(R.id.caption_text);
        caption.setText((String)getResources().getText(R.string.new_note));
        caption.setTextColor(Color.parseColor("#f7eded"));

        return view;
    }

    public void updateCategories() {
        if (scrollPicker != null && scrollPicker.getThread().isReady()) {
            scrollPicker.reset();

            Iterator<Category> it = ((Engine) getActivity().getApplication()).getPrivateForum().getCategories().iterator();

            while (it.hasNext()) {
                Category category = it.next();
                scrollPicker.getItemManager().addItem(category.getImage(getActivity()), category.getHeadLine(), Color.WHITE, category);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        scrollPicker.getThread().pause();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(headlineEditText.getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();

        scrollPicker.getThread().unpause();
    }

    public void clearFields() {
        headlineEditText.setText("");
        textEditText.setText("");
    }
}
