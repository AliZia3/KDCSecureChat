package com.example.chatapp.Frontend.Message;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chatapp.Frontend.UserAdapter;
import com.example.chatapp.Frontend.Users;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.chatapp.Frontend.UserListAdapter;
import java.util.ArrayList;

public class MessageListFragment extends Fragment implements UserListAdapter.OnUserClickListener {
    RecyclerView UserListRecyclerView;
    DatabaseReference database;
    UserListAdapter adapter;
    ArrayList<Users> usersArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);

        UserListRecyclerView = view.findViewById(R.id.user_list_recycler_view);
        database=FirebaseDatabase.getInstance().getReference("user");
        UserListRecyclerView.setHasFixedSize(true);
        UserListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usersArrayList = new ArrayList<>();

        adapter = new UserListAdapter(getContext(), usersArrayList, this);
        UserListRecyclerView.setAdapter(adapter);

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

        return view;

    }

    @Override
    public void onUserClick(Users user) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        System.out.println("=====================================================+++++++++++++++++++");
        System.out.println(user.getName().toString());
        System.out.println(user.getUserId().toString());
        intent.putExtra("Receiver_NAME", user.getName().toString());
        intent.putExtra("Receiver_ID", user.getUserId().toString());
        startActivity(intent);
    }
}

