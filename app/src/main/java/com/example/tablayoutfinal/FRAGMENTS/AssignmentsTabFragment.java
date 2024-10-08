package com.example.tablayoutfinal.FRAGMENTS;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

// Add this import for your custom AssignmentAdapter
import com.example.tablayoutfinal.AssignmentAdapter;

import com.example.tablayoutfinal.Note;
import com.example.tablayoutfinal.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AssignmentsTabFragment extends Fragment {

    ListView listView;
    List<Map<String, String>> assignmentsList;

    AssignmentAdapter myArrayAdapter;

    Button buttonADD;
    EditText editText;
    private int editingPosition = -1;


    //sorting:
    private enum SortingOption {
        BY_DUE_DATE,
        BY_TITLE,
        BY_COURSE
    }

    private SortingOption currentSortingOption = SortingOption.BY_DUE_DATE;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_assigments_tab, container, false);

        listView = rootView.findViewById(R.id.listview);
        buttonADD = rootView.findViewById(R.id.buttonADD);
        editText = rootView.findViewById(R.id.assignment_name);


        // Add these lines in your onCreateView method, after initializing buttonADD

        Button buttonSortByDueDate = rootView.findViewById(R.id.buttonSortByDueDate);
        Button buttonSortByTitle = rootView.findViewById(R.id.buttonSortByTitle);


        buttonSortByDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSortingOption = SortingOption.BY_DUE_DATE;
                sortAssignments();
            }
        });

        buttonSortByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSortingOption = SortingOption.BY_TITLE;
                sortAssignments();
            }
        });


        assignmentsList = new ArrayList<>();
        myArrayAdapter = new AssignmentAdapter(requireContext(), assignmentsList, android.R.layout.simple_list_item_2, new String[]{"title", "dueDate"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(myArrayAdapter);


        buttonADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText.getText().toString();
                String dueDate = ""; // Default value, you can modify this based on user input
                addAssignment(title, dueDate);
                sortAssignments();
                myArrayAdapter.notifyDataSetChanged();
                editText.getText().clear();
            }

        });

        registerForContextMenu(listView);



        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");

        menu.add(Menu.NONE, 0, Menu.NONE, "Assignment Progress");
        menu.add(Menu.NONE, 1, Menu.NONE, "Edit Assignment");
        menu.add(Menu.NONE, 2, Menu.NONE, "Delete Assignment");

    }

    private void showProgressDiaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Progress");

        View view = getLayoutInflater().inflate(R.layout.edit_assignment_progress, null);
        builder.setView(view);

        EditText editProgress = view.findViewById(R.id.editProgress);
        TextView progressTextView = view.findViewById(R.id.progressTextView);

        // Retrieve progress value from SharedPreferences and display it
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("progress", Context.MODE_PRIVATE);
        String savedProgress = sharedPreferences.getString("progress", "");
        progressTextView.setText(savedProgress);

        editProgress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is changed
                String progress = s.toString();

                // Set the text of the TextView to the input text
                progressTextView.setText(progress);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called after the text is changed
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Save the edited details
                String progress = editProgress.getText().toString();
                // Save progress to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("progress", progress);
                editor.apply();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Reset editing position
                editingPosition = -1;
            }
        });

        // Show the dialog
        builder.show();
    }

    private void showEditDialogAssignment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Assignment");


        // Inflate the layout for the dialog
        View view = getLayoutInflater().inflate(R.layout.edit_assignment_dialog, null);
        builder.setView(view);


        // Add UI elements for editing assignment details
        EditText editTitle = view.findViewById(R.id.editTitle);
        EditText editDueDate = view.findViewById(R.id.editDueDate);

        // ... (add more EditText for other assignment details if needed)

        // Pre-fill the EditText fields with existing assignment details
        Map<String, String> existingAssignment = assignmentsList.get(editingPosition);
        editTitle.setText(existingAssignment.get("title"));
        editDueDate.setText(existingAssignment.get("dueDate"));

        // Add positive and negative buttons to the dialog
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Save the edited details
                String editedTitle = editTitle.getText().toString();
                String editedDueDate = editDueDate.getText().toString();
                // Update the assignment details in the list
                updateAssignment(editingPosition, editedTitle, editedDueDate);
                sortAssignments();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Reset editing position
                editingPosition = -1;
            }
        });

        // Show the dialog
        builder.show();
    }

// ... (other methods and variables in AssignmentsTabFragment)

    // For example, call this method when a long-press occurs
    private void handleLongPress(int position) {
        // Perform any other actions needed for long-press, then call the showEditDialog
        editingPosition = position;
        showEditDialogAssignment();
    }

    private void updateAssignment(int position, String title, String dueDate) {
        Map<String, String> assignment = assignmentsList.get(position);
        assignment.put("title", title);
        assignment.put("dueDate", dueDate);
        myArrayAdapter.notifyDataSetChanged();
    }


    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (item.getItemId()) {
            case 0:
                //assignment progress
                editingPosition = position;
                showProgressDiaglog(); //need a edit dialogue
                return true;
            case 1:
                // Edit Assignment
                // Save the position being edited
                editingPosition = position;
                // Show the edit dialog
                showEditDialogAssignment();
                return true;
            case 2:
                // Delete Assignment
                // Implement the logic to delete the assignment at the selected position
                deleteAssignment(position);
                return true;
            case 3:
                // Set Due Date
                handleSetDueDate(position);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void handleSetDueDate(int position) {
        // You can implement a date picker or any other input method to get the due date
        // For simplicity, let's assume you have a method to show a date picker dialog
        showDatePickerDialog(position);
    }

    private void showDatePickerDialog(int position) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Handle the selected date (you can format it as needed)
                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                updateAssignmentDueDate(position, selectedDate);
            }
        };

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }


    private void updateAssignmentDueDate(int position, String dueDate) {
        Map<String, String> assignment = assignmentsList.get(position);
        assignment.put("dueDate", dueDate);
        myArrayAdapter.notifyDataSetChanged();
    }


    private void deleteAssignment(int position) {
        // Implement the logic to delete the assignment at the specified position
        assignmentsList.remove(position);
        myArrayAdapter.notifyDataSetChanged();
    }

    private void addAssignment(String title, String dueDate) {
        Map<String, String> assignment = new HashMap<>();
        assignment.put("title", title);
        assignment.put("dueDate", dueDate);
        assignmentsList.add(assignment);
    }


    private class AssignmentComparator implements Comparator<Map<String, String>> {
        @Override
        public int compare(Map<String, String> assignment1, Map<String, String> assignment2) {
            switch (currentSortingOption) {
                case BY_DUE_DATE:
                    // Implement comparison logic by due date (you'll need due date information for this)
                    // For now, let's compare alphabetically for demonstration purposes
                    return assignment1.get("dueDate").compareToIgnoreCase(assignment2.get("dueDate"));
                case BY_TITLE:
                    // Implement comparison logic by title
                    return assignment1.get("title").compareToIgnoreCase(assignment2.get("title"));
                default:
                    return 0;
            }
        }
    }

    private AssignmentComparator assignmentComparator = new AssignmentComparator();

    //method for sorting assignments
    private void sortAssignments() {
        Collections.sort(assignmentsList, assignmentComparator); //cannot resolve
        //course?
        myArrayAdapter.notifyDataSetChanged();
    }


}
