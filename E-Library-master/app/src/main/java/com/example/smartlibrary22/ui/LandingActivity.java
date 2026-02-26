package com.example.smartlibrary22.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary22.R;
import com.example.smartlibrary22.adapter.BookAdapter;
import com.example.smartlibrary22.database.DatabaseHelper;
import com.example.smartlibrary22.model.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LandingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fab;
    private Button viewUsersButton, addUserButton, assignBookButton, viewAssignmentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        databaseHelper = new DatabaseHelper(this);
        bookList = databaseHelper.getAllBooks();

        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);
        viewUsersButton = findViewById(R.id.view_users_button);
        addUserButton = findViewById(R.id.add_user_button);
        assignBookButton = findViewById(R.id.assign_book_button);
        viewAssignmentsButton = findViewById(R.id.view_assignments_button);

        bookAdapter = new BookAdapter(bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        viewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, UserDetailsActivity.class);
                startActivity(intent);
            }
        });

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        assignBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, AssignBookActivity.class);
                startActivity(intent);
            }
        });

        viewAssignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, ViewAssignmentsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookList.clear();
        bookList.addAll(databaseHelper.getAllBooks());
        bookAdapter.notifyDataSetChanged();
    }
}
