package com.example.tablayoutfinal.FRAGMENTS;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tablayoutfinal.R;

import com.example.tablayoutfinal.ListItem;

import java.util.ArrayList;

public class ExamsFragment extends Fragment {

    private ArrayList<ListItem> itemList;
    private ArrayAdapter<ListItem> adapter;
    private EditText editTextLine1, editTextLine2, editTextLine3, editTextLine4, editTextLine5;
    private int indexValue;
    private ListItem list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exams, container, false);
        super.onCreate(savedInstanceState);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, itemList);

        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Set up a long-press listener for deleting items
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteItem(position);
                return true; // Return true to consume the long click event
            }

        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTextLine1.setText(itemList.get(indexValue).getLine1());
                editTextLine2.setText(itemList.get(indexValue).getLine2());
                editTextLine3.setText(itemList.get(indexValue).getLine3());
                editTextLine4.setText(itemList.get(indexValue).getLine4());
                editTextLine5.setText(itemList.get(indexValue).getLine5());
                indexValue = position;
                list = adapter.getItem(indexValue);
                String display = itemList.getClass() + "had been selected";
                Toast.makeText(getContext(), display, Toast.LENGTH_LONG);
            }
        });


        editTextLine1 = rootView.findViewById(R.id.editTextLine1);
        editTextLine2 = rootView.findViewById(R.id.editTextLine2);
        editTextLine3 = rootView.findViewById(R.id.editTextLine3);
        editTextLine4 = rootView.findViewById(R.id.editTextLine4);
        editTextLine5 = rootView.findViewById(R.id.editTextLine5);

        Button addButton = rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
        Button editButton = rootView.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem();
            }
        });


        return rootView;
    }
    private void addItem() {
        String line1 = editTextLine1.getText().toString().trim();
        String line2 = editTextLine2.getText().toString().trim();
        String line3 = editTextLine3.getText().toString().trim();
        String line4 = editTextLine4.getText().toString().trim();
        String line5 = editTextLine5.getText().toString().trim();

        if (!line1.isEmpty() && !line2.isEmpty() && !line3.isEmpty() && !line4.isEmpty() && !line5.isEmpty()) {
            ListItem newItem = new ListItem(line1, line2, line3, line4, line5);
            itemList.add(newItem);
            adapter.notifyDataSetChanged();

            // Clear input fields after adding item
            editTextLine1.getText().clear();
            editTextLine2.getText().clear();
            editTextLine3.getText().clear();
            editTextLine4.getText().clear();
            editTextLine5.getText().clear();
        }
    }

    private void deleteItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            itemList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private void editItem() {
        String line1 = editTextLine1.getText().toString().trim();
        if (!line1.isEmpty()) {
            itemList.get(indexValue).setLine1(line1);
        }
        String line2 = editTextLine2.getText().toString().trim();
        if (!line2.isEmpty()) {
            itemList.get(indexValue).setLine2(line2);
        }
        String line3 = editTextLine3.getText().toString().trim();
        if (!line3.isEmpty()) {
            itemList.get(indexValue).setLine3(line3);
        }
        String line4 = editTextLine4.getText().toString().trim();
        if (!line4.isEmpty()) {
            itemList.get(indexValue).setLine4(line4);
        }
        String line5 = editTextLine5.getText().toString().trim();
        if (!line5.isEmpty()) {
            itemList.get(indexValue).setLine5(line5);
        }
        adapter.notifyDataSetChanged();
        editTextLine1.getText().clear();
        editTextLine2.getText().clear();
        editTextLine3.getText().clear();
        editTextLine4.getText().clear();
        editTextLine5.getText().clear();
    }
}