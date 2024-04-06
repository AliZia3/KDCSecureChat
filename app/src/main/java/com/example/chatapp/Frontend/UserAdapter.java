package com.example.chatapp.Frontend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


//Note: automatically creates most of this random stuff idek if i need it but wtv
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    Context context;
    ArrayList<Users> usersArrayList;

    public UserAdapter(Context context, ArrayList<Users> usersArrayList) {
        this.context=context;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {
        Users users = usersArrayList.get(position);
        holder.name.setText("Name: " + users.name);
        holder.password.setText("Password: " + users.password);
        holder.email.setText("Email: " + users.email);
        holder.position.setText("Position: " + users.position);
        holder.department.setText("Department: " + users.department);

//        https://www.youtube.com/watch?v=ffYB_feohKQ
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users.getUserId() != null) { // Add this null check
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
                    ref.child(users.getUserId()).removeValue().addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            if(position < usersArrayList.size()) { // Check if the position is valid
                                usersArrayList.remove(position);
                                notifyDataSetChanged(); // Notify the adapter about data set change
                            }
                        } else {
                            Toast.makeText(context, "Failed to delete user: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(context, "Error: User ID is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView name, password, email, position, department;
        ImageView deleteButton, updateButton;
        public viewHolder(@NonNull View itemView) {
            super(itemView); // idek automatically did this
            name = itemView.findViewById(R.id.employeeName);
            password = itemView.findViewById(R.id.employeePassword);
            email = itemView.findViewById(R.id.employeeEmail);
            position = itemView.findViewById(R.id.employeePosition);
            department = itemView.findViewById(R.id.employeeDepartment);

            deleteButton = itemView.findViewById(R.id.employeeDelete);
            updateButton = itemView.findViewById(R.id.employeeUpdate);
        }
    }
}
