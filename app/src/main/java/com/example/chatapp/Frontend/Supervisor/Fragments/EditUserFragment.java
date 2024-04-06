package com.example.chatapp.Frontend.Supervisor.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chatapp.Frontend.Supervisor.SupervisorLoginActivity;
import com.example.chatapp.Frontend.UserAdapter;
import com.example.chatapp.Frontend.Users;
import com.example.chatapp.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditUserFragment extends Fragment {
    RecyclerView editUserRecyclerView;
    DatabaseReference database;
    UserAdapter adapter;
    ArrayList<Users> usersArrayList;
    Button logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        editUserRecyclerView = view.findViewById(R.id.edit_user_recycler_view);
        logoutButton = view.findViewById(R.id.action_logout);
        database=FirebaseDatabase.getInstance().getReference("user");
        editUserRecyclerView.setHasFixedSize(true);
        editUserRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usersArrayList = new ArrayList<>();

        adapter = new UserAdapter(getContext(), usersArrayList);
        editUserRecyclerView.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users user = dataSnapshot.getValue(Users.class);
                    usersArrayList.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(view.getContext(), SupervisorLoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}