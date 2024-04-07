package com.example.chatapp.Frontend.Supervisor.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.chatapp.Frontend.Supervisor.SupervisorLoginActivity;
import com.example.chatapp.Frontend.Users;
import com.example.chatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserFragment extends Fragment {
    TextInputEditText register_name, register_password, register_email, register_position, register_department;
    Button registerUserButton, clearTextButton, logoutButton;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    // Source (for some of the logic): https://www.youtube.com/watch?v=QAKq8UBv4GI
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_user, container, false);

        // Init ui elements
        registerUserButton = view.findViewById(R.id.action_register_employee);
        clearTextButton = view.findViewById(R.id.action_clear_text);
        logoutButton = view.findViewById(R.id.action_logout);
        register_name = view.findViewById(R.id.register_employeeName);
        register_password = view.findViewById(R.id.register_employeePassword);
        register_email = view.findViewById(R.id.register_employeeEmail);
        register_position = view.findViewById(R.id.register_employeePosition);
        register_department = view.findViewById(R.id.register_employeeDepartment);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Set OnClickListener for registerUserButton
        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String name = register_name.getText().toString();
                String password = register_password.getText().toString();
                String email = register_email.getText().toString();
                String position = register_position.getText().toString();
                String department = register_department.getText().toString();

                // Validate user input
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email) || TextUtils.isEmpty(position) || TextUtils.isEmpty(department)){
                    Toast.makeText(getContext(), "Enter valid information", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)){
                    register_email.setError("Incorrect Email Address Form");
                } else if (password.length()<6){
                    register_password.setError("Password must be >= 6 characters");
                } else {
                    // Create user account in firebase auth (https://www.youtube.com/watch?v=QAKq8UBv4GI)
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                // If account creation is successful, save user details in firebase realtime database
                                String id = task.getResult().getUser().getUid();
                                DatabaseReference reference = database.getReference().child("user").child(id);

                                // Create new user object with user details
                                Users users = new Users(id, name, email, password, position, department);

                                // Set the val of the user node in the database with the Users object
                                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getContext(), "Account Created.", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                // Display error message if accoutn creation fails
                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Set OnClickListener for clearTextButton to clear input fields
        clearTextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                register_name.setText("");
                register_password.setText("");
                register_email.setText("");
                register_position.setText("");
                register_department.setText("");
            }
        });

        // Set OnClickListener for logoutButton to log out user and navigate back to SupervisorLoginActivity
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