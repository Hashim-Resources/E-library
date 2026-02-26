package com.example.smartlibrary22.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartlibrary22.R;
import com.example.smartlibrary22.database.DatabaseHelper;
import com.example.smartlibrary22.model.User;

public class AddUserActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText;
    private Button addUserButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        databaseHelper = new DatabaseHelper(this);

        nameEditText = findViewById(R.id.user_name);
        emailEditText = findViewById(R.id.user_email);
        passwordEditText = findViewById(R.id.user_password);
        addUserButton = findViewById(R.id.add_user_button);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AddUserActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);
                    databaseHelper.addUser(user);
                    Toast.makeText(AddUserActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
