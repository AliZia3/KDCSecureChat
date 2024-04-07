package com.example.chatapp.Frontend;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Frontend.Supervisor.SupervisorActionsActivity;
import com.example.chatapp.Frontend.Supervisor.SupervisorLoginActivity;
import com.example.chatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


//Note: automatically creates most of this random stuff idek if i need it but wtv
// Source (used for some of the logic): https://www.youtube.com/watch?v=M8sKwoVjqU0
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    Context context;
    ArrayList<Users> usersArrayList;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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
        // Set OnClickListener for deleteButton to delete a user
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child(users.userId);
                Task<Void> task = ref.removeValue();
                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "User Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to delete user", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        // https://www.youtube.com/watch?v=MM55ERxUI-Q
        // Set OnClickListener for updateButton (the '+' symbol) to update user info
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating an alert dialog popup and inflating the dialog_edit_user thats defined in xml file
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
                dialogBuilder.setView(dialogView);

                // Populating the popup with existing user info
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

                // Set OnClickListener for updateButton inside the popup to update user info
                dialogView.findViewById(R.id.action_update_user).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newName = editTextName.getText().toString();
                        String newEmail = editTextEmail.getText().toString();
                        String newPassword = editTextPassword.getText().toString();
                        String newPosition = editTextPosition.getText().toString();
                        String newDepartment = editTextDepartment.getText().toString();

                        if (TextUtils.isEmpty(newName) || TextUtils.isEmpty(newEmail) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(newPosition) || TextUtils.isEmpty(newDepartment)) {
                            Toast.makeText(context, "Enter valid information", Toast.LENGTH_SHORT).show();
                        } else if (!newEmail.matches(emailPattern)) {
                            editTextEmail.setError("Incorrect Email Address Format");
                        } else if (newPassword.length() < 6) {
                            editTextPassword.setError("Password must be >= 6 characters");
                        } else {
                            // Call method to update user information
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child(users.getUserId());
                            users.setName(newName);
                            users.setEmail(newEmail);
                            users.setPassword(newPassword);
                            users.setPosition(newPosition);
                            users.setDepartment(newDepartment);

                            ref.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        alertDialog.dismiss();
                                        Toast.makeText(context, "Account Updated", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
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
