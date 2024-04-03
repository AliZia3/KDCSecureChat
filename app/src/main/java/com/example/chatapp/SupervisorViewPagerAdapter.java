package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chatapp.Supervisor.fragments.EditUserFragment;
import com.example.chatapp.Supervisor.fragments.RegisterFragment;

public class SupervisorViewPagerAdapter extends FragmentStateAdapter
{
    public SupervisorViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new EditUserFragment();
        }
        return new RegisterFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
