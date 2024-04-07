package com.example.chatapp.Frontend;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


//Note: automatically creates most of this random stuff idek if i need it but wtv
// https://www.youtube.com/watch?v=M8sKwoVjqU0
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

        // https://www.youtube.com/watch?v=ffYB_feohKQ
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(users.getUserId() != null) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
                    ref.child(users.getUserId()).removeValue().addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            if(position < usersArrayList.size()) { // Check if the position is valid
                                usersArrayList.remove(position);
                                notifyDataSetChanged(); // Notify adapter about data set change
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

        // https://www.youtube.com/watch?v=MM55ERxUI-Q
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
                dialogBuilder.setView(dialogView);

                EditText editTextName = dialogView.findViewById(R.id.popupEditTextName);
                editTextName.setText(users.getName());
                EditText editTextEmail = dialogView.findViewById(R.id.popupEditTextEmail);
                editTextEmail.setText(users.getEmail());
                EditText editTextPassword = dialogView.findViewById(R.id.popupEditTextPassword);
                editTextPassword.setText(users.getPassword());
                EditText editTextPosition = dialogView.findViewById(R.id.popupEditTextPosition);
                editTextPosition.setText(users.getPosition());
                EditText editTextDepartment = dialogView.findViewById(R.id.popupEditTextDepartment);
                editTextDepartment.setText(users.getDepartment());

                dialogBuilder.setTitle("Edit User");
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                dialogView.findViewById(R.id.action_update_user).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Call method to update user information
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child(users.getUserId());
                        users.setName(editTextName.getText().toString());
                        users.setEmail(editTextEmail.getText().toString());
                        users.setPassword(editTextPassword.getText().toString());
                        users.setPosition(editTextPosition.getText().toString());
                        users.setDepartment(editTextDepartment.getText().toString());

                        ref.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "Account Updated", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
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
