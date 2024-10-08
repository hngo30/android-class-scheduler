package com.example.tablayoutfinal;
// AssignmentAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.example.tablayoutfinal.FRAGMENTS.AssignmentsTabFragment;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AssignmentAdapter extends SimpleAdapter {
    public AssignmentAdapter(Context context, List<Map<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
}