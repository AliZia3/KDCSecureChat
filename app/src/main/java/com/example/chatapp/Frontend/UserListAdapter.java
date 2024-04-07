package com.example.chatapp.Frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    Context context;
    static ArrayList<Users> usersArrayList;

    private OnUserClickListener listener;

//    public UserListAdapter(Context context, ArrayList<Users> usersArrayList) {
//        this.context = context;
//        this.usersArrayList = usersArrayList;
//    }

    public interface OnUserClickListener {
        void onUserClick(Users user);
    }

    public UserListAdapter(Context context, ArrayList<Users> usersArrayList, OnUserClickListener listener) {
        this.context = context;
        this.usersArrayList = usersArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder   onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = usersArrayList.get(position);
        holder.name.setText(users.name);
        holder.email.setText("Email: " + users.email);
        holder.department.setText("Department: " + users.department);
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, department;
        public ViewHolder(@NonNull View itemView, OnUserClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.employeeNameList);
            email = itemView.findViewById(R.id.employeeEmail);
            department = itemView.findViewById(R.id.employeeDepartment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        Users user = usersArrayList.get(position); // Assuming you have access to usersArrayList here
                        listener.onUserClick(user);
                    }
                }
            });
        }
    }
}
