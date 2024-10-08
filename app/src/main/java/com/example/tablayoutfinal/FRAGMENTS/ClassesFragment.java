package com.example.tablayoutfinal.FRAGMENTS;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tablayoutfinal.Note;
import com.example.tablayoutfinal.NoteAdapter;
import com.example.tablayoutfinal.R;

import java.util.ArrayList;
import java.util.Map;

public class ClassesFragment extends Fragment {
    //values added below
    public static ArrayList<Note> items;
    private NoteAdapter adapter;
    private ListView mListView;

    private Button add;
    private EditText classNameEditText;
    private EditText locationEditText;
    private EditText daysOfWeekEditText;
    private EditText profEditText;
    Note item;
    private int indexValue;
    String courseName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.activity_main_classes, container, false);

        //from onCreate()
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main); redline
        Log.d("ClassesTab", "onCreate: Started.");


        mListView = rootView.findViewById(R.id.listView);
        add= rootView.findViewById(R.id.button);

        items = new ArrayList<>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        adapter = new NoteAdapter(getContext(), R.layout.listview, items);
        mListView.setAdapter(adapter);
        setUpListViewListener();

        //end from onCreate()

        return rootView;
    }

    //methods taken below:

    private void setUpListViewListener() {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getContext(); //this line produces error "cannot resolve method"
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

                items.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = adapter.getItem(position);
                indexValue = position;
                showDialog(item.getClassName(), item.getLocation(), item.getProfessor(), item.getDaysOfWeek());

            }
        });
    }

    private void addItem(View view) {
        String className = classNameEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String professor = profEditText.getText().toString();
        String daysOfWeek = daysOfWeekEditText.getText().toString();

        if (!className.isEmpty() && !location.isEmpty() && !professor.isEmpty() && !daysOfWeek.isEmpty()) {
            Note notes = new Note(className, location, professor, daysOfWeek);
            items.add(notes);
            adapter.notifyDataSetChanged();


            classNameEditText.getText().clear();
            locationEditText.getText().clear();
            profEditText.getText().clear();
            daysOfWeekEditText.getText().clear();
        }
    }

    private void button2Click(View view) {
        String className = classNameEditText.getText().toString();
        if (!className.isEmpty()) {
            items.get(indexValue).setClassName(className);
        }
        String location = locationEditText.getText().toString();
        if (!location.isEmpty()) {
            items.get(indexValue).setLocation(location);
        }
        String professor = profEditText.getText().toString();
        if (!professor.isEmpty()) {
            items.get(indexValue).setProfessor(professor);
        }
        String daysOfWeek = daysOfWeekEditText.getText().toString();
        if (!daysOfWeek.isEmpty()) {
            items.get(indexValue).setDaysOfWeek(daysOfWeek);
        }
        adapter.notifyDataSetChanged();
        classNameEditText.getText().clear();
        locationEditText.getText().clear();
        profEditText.getText().clear();
        daysOfWeekEditText.getText().clear();
    }
    //end methods taken
    protected void showDialog(String name, String location, String prof, String time){

        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.dialog, null);
        dialog.setContentView(view);

        Button add = view.findViewById(R.id.addButton);

        classNameEditText = view.findViewById(R.id.editTextName);
        locationEditText = view.findViewById(R.id.editTextLocation);
        profEditText = view.findViewById(R.id.editTextProf);
        daysOfWeekEditText = view.findViewById(R.id.editTextDays);

        classNameEditText.setText(name);
        locationEditText.setText(location);
        profEditText.setText(prof);
        daysOfWeekEditText.setText(time);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button2Click(view);

            }
        });


        dialog.show();
    };
    protected void showDialog(){

        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.dialog, null);
        dialog.setContentView(view);

        Button add = view.findViewById(R.id.addButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);

            }
        });


        dialog.show();
    };
}
