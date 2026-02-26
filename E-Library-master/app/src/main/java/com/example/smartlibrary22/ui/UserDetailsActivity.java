package com.example.smartlibrary22.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary22.R;
import com.example.smartlibrary22.adapter.UserAdapter;
import com.example.smartlibrary22.database.DatabaseHelper;
import com.example.smartlibrary22.model.User;

import java.util.List;

public class UserDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        databaseHelper = new DatabaseHelper(this);
        userList = databaseHelper.getAllUsers();

        recyclerView = findViewById(R.id.recycler_view_users);

        userAdapter = new UserAdapter(userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
    }
}
