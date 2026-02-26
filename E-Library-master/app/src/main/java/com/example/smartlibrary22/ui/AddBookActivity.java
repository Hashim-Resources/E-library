package com.example.smartlibrary22.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartlibrary22.R;
import com.example.smartlibrary22.database.DatabaseHelper;
import com.example.smartlibrary22.model.Book;

public class AddBookActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, categoryEditText, quantityEditText;
    private Button addButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        databaseHelper = new DatabaseHelper(this);

        titleEditText = findViewById(R.id.book_title);
        authorEditText = findViewById(R.id.book_author);
        categoryEditText = findViewById(R.id.book_category);
        quantityEditText = findViewById(R.id.book_quantity);
        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String author = authorEditText.getText().toString().trim();
                String category = categoryEditText.getText().toString().trim();
                String quantityStr = quantityEditText.getText().toString().trim();

                if (title.isEmpty() || author.isEmpty() || category.isEmpty() || quantityStr.isEmpty()) {
                    Toast.makeText(AddBookActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    int quantity = Integer.parseInt(quantityStr);
                    Book book = new Book();
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setCategory(category);
                    book.setQuantity(quantity);
                    databaseHelper.addBook(book);
                    Toast.makeText(AddBookActivity.this, "Book added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
