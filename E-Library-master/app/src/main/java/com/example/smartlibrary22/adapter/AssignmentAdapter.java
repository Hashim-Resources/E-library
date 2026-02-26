package com.example.smartlibrary22.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartlibrary22.R;
import com.example.smartlibrary22.database.DatabaseHelper;
import com.example.smartlibrary22.model.Assignment;
import com.example.smartlibrary22.model.Book;
import com.example.smartlibrary22.model.User;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<Assignment> assignmentList;
    private DatabaseHelper databaseHelper;

    public AssignmentAdapter(DatabaseHelper databaseHelper, List<Assignment> assignmentList) {
        this.databaseHelper = databaseHelper;
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_list_item, parent, false);
        return new AssignmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment assignment = assignmentList.get(position);

        // Get book and user from database
        Book book = databaseHelper.getBook(assignment.getBookId());
        User user = databaseHelper.getUser(assignment.getUserId());

        if (book != null) {
            holder.bookTitle.setText(book.getTitle());
        } else {
            holder.bookTitle.setText("Book not found");
        }

        if (user != null) {
            holder.userName.setText(user.getName());
        } else {
            holder.userName.setText("User not found");
        }

        holder.issueDate.setText(assignment.getIssueDate());
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {
        public TextView bookTitle, userName, issueDate;

        public AssignmentViewHolder(View view) {
            super(view);
            bookTitle = view.findViewById(R.id.text_view_book_title);
            userName = view.findViewById(R.id.text_view_user_name);
            issueDate = view.findViewById(R.id.text_view_issue_date);
        }
    }
}
