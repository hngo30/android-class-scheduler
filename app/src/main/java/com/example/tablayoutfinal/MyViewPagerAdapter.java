package com.example.tablayoutfinal;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tablayoutfinal.FRAGMENTS.ExamsFragment;
import com.example.tablayoutfinal.FRAGMENTS.ClassesFragment;
import com.example.tablayoutfinal.FRAGMENTS.AssignmentsTabFragment;
import com.example.tablayoutfinal.FRAGMENTS.ToDoFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ClassesFragment();
            case 1:
                return new ToDoFragment();
            case 2:
                return new ExamsFragment();
            case 3:
                return new AssignmentsTabFragment();
            default:
                return new ClassesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
