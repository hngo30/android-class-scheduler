package com.example.tablayoutfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    int mResource;
    public NoteAdapter(Context context, int resource, ArrayList<Note> objects) { //this method correlates to the adapter problem ClassesFragment.java
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        // Get the current Note object
        Note currentNote = getItem(position);

        if (currentNote != null) {
            // Set the values to the TextView elements
            TextView className = convertView.findViewById(R.id.textView1);
            TextView locationName = convertView.findViewById(R.id.textView2);
            TextView daysOfWeekText = convertView.findViewById(R.id.textView3);
            TextView professorText = convertView.findViewById(R.id.textView4);

            className.setText("Class Name: " + currentNote.getClassName());
            locationName.setText("Location: " + currentNote.getLocation());
            daysOfWeekText.setText("Days of Week: " + currentNote.getDaysOfWeek());
            professorText.setText("Professor: " + currentNote.getProfessor());
        }

        return convertView;
    }

}