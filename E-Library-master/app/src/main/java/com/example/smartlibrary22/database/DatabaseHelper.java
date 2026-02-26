
package com.example.smartlibrary22.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smartlibrary22.model.Assignment;
import com.example.smartlibrary22.model.Book;
import com.example.smartlibrary22.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smartlibrary.db";
    private static final int DATABASE_VERSION = 1;

    // User table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    // Book table
    private static final String TABLE_BOOKS = "books";
    private static final String COLUMN_BOOK_ID = "id";
    private static final String COLUMN_BOOK_TITLE = "title";
    private static final String COLUMN_BOOK_AUTHOR = "author";
    private static final String COLUMN_BOOK_CATEGORY = "category";
    private static final String COLUMN_BOOK_QUANTITY = "quantity";

    // Assignment table
    private static final String TABLE_ASSIGNMENTS = "assignments";
    private static final String COLUMN_ASSIGNMENT_ID = "id";
    private static final String COLUMN_ASSIGNMENT_BOOK_ID = "book_id";
    private static final String COLUMN_ASSIGNMENT_USER_ID = "user_id";
    private static final String COLUMN_ASSIGNMENT_ISSUE_DATE = "issue_date";
    private static final String COLUMN_ASSIGNMENT_RETURN_DATE = "return_date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BOOK_TITLE + " TEXT,"
                + COLUMN_BOOK_AUTHOR + " TEXT,"
                + COLUMN_BOOK_CATEGORY + " TEXT,"
                + COLUMN_BOOK_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);

        String CREATE_ASSIGNMENTS_TABLE = "CREATE TABLE " + TABLE_ASSIGNMENTS + "("
                + COLUMN_ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ASSIGNMENT_BOOK_ID + " INTEGER,"
                + COLUMN_ASSIGNMENT_USER_ID + " INTEGER,"
                + COLUMN_ASSIGNMENT_ISSUE_DATE + " TEXT,"
                + COLUMN_ASSIGNMENT_RETURN_DATE + " TEXT" + ")";
        db.execSQL(CREATE_ASSIGNMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENTS);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USERS, null, values);
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] columns = {COLUMN_USER_ID};
            String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
            String[] selectionArgs = {email, password};
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            int count = cursor.getCount();
            return count > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_TITLE, book.getTitle());
        values.put(COLUMN_BOOK_AUTHOR, book.getAuthor());
        values.put(COLUMN_BOOK_CATEGORY, book.getCategory());
        values.put(COLUMN_BOOK_QUANTITY, book.getQuantity());
        db.insert(TABLE_BOOKS, null, values);
    }

    public void addAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ASSIGNMENT_BOOK_ID, assignment.getBookId());
        values.put(COLUMN_ASSIGNMENT_USER_ID, assignment.getUserId());
        values.put(COLUMN_ASSIGNMENT_ISSUE_DATE, assignment.getIssueDate());
        values.put(COLUMN_ASSIGNMENT_RETURN_DATE, assignment.getReturnDate());
        db.insert(TABLE_ASSIGNMENTS, null, values);
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Book book = new Book();
                    book.setId(Integer.parseInt(cursor.getString(0)));
                    book.setTitle(cursor.getString(1));
                    book.setAuthor(cursor.getString(2));
                    book.setCategory(cursor.getString(3));
                    book.setQuantity(cursor.getInt(4));
                    bookList.add(book);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bookList;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setId(Integer.parseInt(cursor.getString(0)));
                    user.setName(cursor.getString(1));
                    user.setEmail(cursor.getString(2));
                    userList.add(user);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userList;
    }

    public List<Assignment> getAllAssignments() {
        List<Assignment> assignmentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ASSIGNMENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Assignment assignment = new Assignment();
                    assignment.setId(Integer.parseInt(cursor.getString(0)));
                    assignment.setBookId(Integer.parseInt(cursor.getString(1)));
                    assignment.setUserId(Integer.parseInt(cursor.getString(2)));
                    assignment.setIssueDate(cursor.getString(3));
                    assignment.setReturnDate(cursor.getString(4));
                    assignmentList.add(assignment);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return assignmentList;
    }

    public Book getBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_BOOKS, new String[]{COLUMN_BOOK_ID, COLUMN_BOOK_TITLE, COLUMN_BOOK_AUTHOR, COLUMN_BOOK_CATEGORY, COLUMN_BOOK_QUANTITY},
                    COLUMN_BOOK_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                Book book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));
                book.setCategory(cursor.getString(3));
                book.setQuantity(cursor.getInt(4));
                return book;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL},
                    COLUMN_USER_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                return user;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
