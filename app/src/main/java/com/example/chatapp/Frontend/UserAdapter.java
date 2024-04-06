package com.example.chatapp.Frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.google.android.material.textfield.TextInputEditText;

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
    public void onBindViewHolder(@NonNull UserAdapter.viewHolder holder, int position) {
        Users users = usersArrayList.get(position);
        holder.name.setText("Name: " + users.name);
        holder.password.setText("Password: " + users.password);
        holder.email.setText("Email: " + users.email);
        holder.position.setText("Position: " + users.position);
        holder.department.setText("Department: " + users.department);
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView name, password, email, position, department;
        public viewHolder(@NonNull View itemView) {
            super(itemView); // idek automatically did this
            name = itemView.findViewById(R.id.employeeName);
            password = itemView.findViewById(R.id.employeePassword);
            email = itemView.findViewById(R.id.employeeEmail);
            position = itemView.findViewById(R.id.employeePosition);
            department = itemView.findViewById(R.id.employeeDepartment);
        }
    }
}
