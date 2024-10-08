package com.example.tablayoutfinal.FRAGMENTS;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.MenuInflater;


import com.example.tablayoutfinal.R;

import java.util.ArrayList;


public class ToDoFragment extends Fragment {
    private Context context;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;

    private Button editButton;
    private int indexValue1;
    EditText input;

    String item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.to_do, container, false);
        super.onCreate(savedInstanceState);

        listView = rootView.findViewById(R.id.listView);
        button = rootView.findViewById(R.id.button);
        editButton = rootView.findViewById(R.id.editButton);
        input = rootView.findViewById(R.id.editTextText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClick(v);
            }
        });
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

        return rootView;
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getContext();
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = itemsAdapter.getItem(position);
                indexValue1 = position;
                String display = item + "has been selected!";
                Toast.makeText(getContext(), display, Toast.LENGTH_LONG);
                input.setText(item);
            }
        });
    }

    private void addItem(View view) {
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))) {
            // Adding the item with its number
            String numberedItem = "Task " + (items.size() + 1) + ". " + itemText;
            items.add(numberedItem);

            // Notify the adapter that the data set has changed
            itemsAdapter.notifyDataSetChanged();

            // Clear the input field
            input.setText("");
        } else {
            Toast.makeText(getContext(), "Please enter text..", Toast.LENGTH_LONG).show();
        }
    }
    private void editClick(View view) {
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))) {
            String numberedItem = (items.size() + 1) + ". " + itemText;
            items.set(indexValue1,itemText);
            itemsAdapter.notifyDataSetChanged();
            input.setText("");
        } else {
            Toast.makeText(getContext(), "Please enter text..", Toast.LENGTH_LONG).show();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater1 = new MenuInflater(context);
        inflater1.inflate(R.menu.main_menu, menu);
        return true;

    }
}