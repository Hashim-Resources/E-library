package com.example.smartlibrary22.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartlibrary22.R;
import com.example.smartlibrary22.database.DatabaseHelper;
import com.example.smartlibrary22.model.Assignment;
import com.example.smartlibrary22.model.Book;
import com.example.smartlibrary22.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignBookActivity extends AppCompatActivity {

    private Spinner bookSpinner, userSpinner;
    private Button assignButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_book);

        databaseHelper = new DatabaseHelper(this);

        bookSpinner = findViewById(R.id.spinner_book);
        userSpinner = findViewById(R.id.spinner_user);
        assignButton = findViewById(R.id.button_assign);

        // Populate spinners
        List<Book> books = databaseHelper.getAllBooks();
        List<User> users = databaseHelper.getAllUsers();

        ArrayAdapter<Book> bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, books);
        bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookSpinner.setAdapter(bookAdapter);

        ArrayAdapter<User> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book selectedBook = (Book) bookSpinner.getSelectedItem();
                User selectedUser = (User) userSpinner.getSelectedItem();

                if (selectedBook != null && selectedUser != null) {
                    Assignment assignment = new Assignment();
                    assignment.setBookId(selectedBook.getId());
                    assignment.setUserId(selectedUser.getId());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String currentDate = sdf.format(new Date());
                    assignment.setIssueDate(currentDate);

                    databaseHelper.addAssignment(assignment);

                    Toast.makeText(AssignBookActivity.this, "Book assigned successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AssignBookActivity.this, "Please select a book and a user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
