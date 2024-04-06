//https://www.youtube.com/watch?v=pIKdHeOjYNw

package com.example.chatapp.Frontend.Supervisor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chatapp.Frontend.Supervisor.Fragments.EditUserFragment;
import com.example.chatapp.Frontend.Supervisor.Fragments.RegisterUserFragment;

public class SupervisorActionsPagerAdapter extends FragmentStateAdapter {

    public SupervisorActionsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new EditUserFragment();
        }
        return new RegisterUserFragment();

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
