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

    // create new fragment based on position in viewpager
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            // return an instance of EditUserFragment
            return new EditUserFragment();
        }
        // return an instance of RegisterUserFragment
        return new RegisterUserFragment();

    }

    // #items in viewpager
    @Override
    public int getItemCount() {
        return 2;
    }
}
