package com.example.smartlibrary22.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary22.R;
import com.example.smartlibrary22.adapter.AssignmentAdapter;
import com.example.smartlibrary22.database.DatabaseHelper;
import com.example.smartlibrary22.model.Assignment;
import com.example.smartlibrary22.model.Book;
import com.example.smartlibrary22.model.User;

import java.util.ArrayList;
import java.util.List;

public class ViewAssignmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AssignmentAdapter assignmentAdapter;
    private List<Assignment> assignmentList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignments);

        databaseHelper = new DatabaseHelper(this);
        assignmentList = databaseHelper.getAllAssignments();

        // Filter out invalid assignments
        List<Assignment> validAssignments = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            Book book = databaseHelper.getBook(assignment.getBookId());
            User user = databaseHelper.getUser(assignment.getUserId());
            if (book != null && user != null) {
                validAssignments.add(assignment);
            }
        }

        recyclerView = findViewById(R.id.recycler_view_assignments);

        assignmentAdapter = new AssignmentAdapter(databaseHelper, validAssignments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assignmentAdapter);
    }
}
