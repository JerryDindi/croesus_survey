package com.jilag.croesus_survey.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jilag.croesus_survey.models.Answers;
import com.jilag.croesus_survey.models.Questions;
import com.jilag.croesus_survey.models.Surveys;
import com.jilag.croesus_survey.models.Users;

import java.util.LinkedList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Croesus";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE Users ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_num INTEGER, " +
                "fname TEXT, " +
                "lname TEXT, " +
                "p_pic TEXT )";

        // create users table
        db.execSQL(CREATE_USERS_TABLE);

        // SQL statement to create Questions table
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE Questions ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "value TEXT, " +
                "surevy_id INTEGER )";

        // create Questions table
        db.execSQL(CREATE_QUESTIONS_TABLE);

        // SQL statement to create Questions table
        String CREATE_ANSWERS_TABLE = "CREATE TABLE Answers ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "qstn_id INTEGER, " +
                "answer TEXT )";

        // create EventAttendance table
        db.execSQL(CREATE_ANSWERS_TABLE);

        // SQL statement to create surveys table
        String CREATE_SURVEYS_TABLE = "CREATE TABLE Surveys ( " +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "description TEXT )";

        // create EventAttendance table
        db.execSQL(CREATE_SURVEYS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older Users table if existed
        db.execSQL("DROP TABLE IF EXISTS Users");

        // Drop older Questions table if existed
        db.execSQL("DROP TABLE IF EXISTS Questions");

        // Drop older Answers table if existed
        db.execSQL("DROP TABLE IF EXISTS Answers");

        // Drop older Surveys table if existed
        db.execSQL("DROP TABLE IF EXISTS Surveys");

        // create fresh tables
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Users table name
    private static final String TABLE_USERS = "Users";

    // Questions table name
    private static final String TABLE_QUESTIONS = "Questions";

    // Answers table name
    private static final String TABLE_ANSWERS = "Answers";

    // Answers table name
    private static final String TABLE_SURVEYS = "Surveys";

    // Users Table Columns names
    private static final String KEY_USER_ID = "id";
    private static final String KEY_ID_NUM = "id_num";
    private static final String KEY_F_NAME = "fname";
    private static final String KEY_L_NAME = "lname";
    private static final String KEY_P_PIC = "p_pic";

    // Questions Table Columns names
    private static final String KEY_Q_ID = "id";
    private static final String KEY_Q_NAME = "name";
    private static final String KEY_Q_VALUE = "value";
    private static final String KEY_Q_SURVEY_ID = "surevy_id";

    // Answers Table Columns names
    private static final String KEY_ANSW_ID = "id";
    private static final String KEY_ANSW_U_ID = "user_id";
    private static final String KEY_ANSW_Q_ID = "qstn_id";
    private static final String KEY_ANSWER = "answer";

    // Surveys Table Columns names
    private static final String KEY_SURV_ID = "id";
    private static final String KEY_SURV_NAME = "name";
    private static final String KEY_SURV_DESCR = "description";

    public void addUser(Users users){
        Log.d("addUser", users.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID_NUM, users.getId_num());
        values.put(KEY_F_NAME, users.getFname());
        values.put(KEY_L_NAME, users.getLname());
        values.put(KEY_P_PIC, users.getP_pic());

        // 3. insert
        db.insert(TABLE_USERS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void addQuestion(Questions questions){
        Log.d("addQuestion", questions.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_Q_NAME, questions.getName());
        values.put(KEY_Q_VALUE, questions.getValue());
        values.put(KEY_Q_SURVEY_ID, questions.getSurevy_id());

        // 3. insert
        db.insert(TABLE_QUESTIONS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void addAnswer(Answers answers){
        Log.d("addAnswer", answers.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ANSW_U_ID, answers.getUser_id());
        values.put(KEY_ANSW_Q_ID, answers.getUser_id());
        values.put(KEY_ANSWER, answers.getAnswer());

        // 3. insert
        db.insert(TABLE_ANSWERS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void addSurvey(Surveys surveys){
        Log.d("addSurvey", surveys.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_SURV_ID, surveys.getId());
        values.put(KEY_SURV_NAME, surveys.getName());
        values.put(KEY_SURV_DESCR, surveys.getDescription());

        // 3. insert
        db.insert(TABLE_SURVEYS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    // Get All Users
    public List<Users> getAllUsers() {
        List<Users> users = new LinkedList<Users>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USERS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Users user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new Users();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setId_num(Integer.parseInt(cursor.getString(1)));
                user.setFname(cursor.getString(2));
                user.setLname(cursor.getString(3));
                user.setP_pic(cursor.getString(4));

                // Add user to users
                users.add(user);
            } while (cursor.moveToNext());
        }

        // return users
        return users;
    }

    // Get specific user
    public List<Users> getUser(int idnum) {
        List<Users> users = new LinkedList<Users>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USERS + " WHERE " + KEY_ID_NUM + " = ?";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor cursor = db.rawQuery(query, null);
        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(idnum) });

        // 3. go over each row, build user and add it to list
        Users user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new Users();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setId_num(Integer.parseInt(cursor.getString(1)));
                user.setFname(cursor.getString(2));
                user.setLname(cursor.getString(3));
                user.setP_pic(cursor.getString(4));

                // Add user to users
                users.add(user);
            } while (cursor.moveToNext());
        }

        // return users
        return users;
    }

    // Get All Questions
    public List<Questions> getAllQuestions() {
        List<Questions> questions = new LinkedList<Questions>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_QUESTIONS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build question and add it to list
        Questions question = null;
        if (cursor.moveToFirst()) {
            do {
                question = new Questions();
                question.setId(Integer.parseInt(cursor.getString(0)));
                question.setName(cursor.getString(1));
                question.setValue(cursor.getString(2));
                question.setSurevy_id(Integer.parseInt(cursor.getString(3)));

                // Add question to questions
                questions.add(question);
            } while (cursor.moveToNext());
        }

        // return questions
        return questions;
    }

    // Get Survey Questions
    public List<Questions> getSurveyQuestion(String surv_id) {
        List<Questions> questions = new LinkedList<Questions>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_QUESTIONS + " WHERE " + KEY_Q_SURVEY_ID + " = ?";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor cursor = db.rawQuery(query, null);
        Cursor cursor = db.rawQuery(query, new String[] { surv_id });

        // 3. go over each row, build question and add it to list
        Questions question = null;
        if (cursor.moveToFirst()) {
            do {
                question = new Questions();
                question.setId(Integer.parseInt(cursor.getString(0)));
                question.setName(cursor.getString(1));
                question.setValue(cursor.getString(2));
                question.setSurevy_id(Integer.parseInt(cursor.getString(3)));

                // Add question to questions
                questions.add(question);
            } while (cursor.moveToNext());
        }

        // return questions
        return questions;
    }

    // Get All Answers
    public List<Answers> getAllAnswers() {
        List<Answers> answers = new LinkedList<Answers>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_ANSWERS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Answers answer = null;
        if (cursor.moveToFirst()) {
            do {
                answer = new Answers();
                answer.setId(Integer.parseInt(cursor.getString(0)));
                answer.setUser_id(Integer.parseInt(cursor.getString(1)));
                answer.setQstn_id(Integer.parseInt(cursor.getString(2)));
                answer.setAnswer(cursor.getString(3));

                // Add answer to answers
                answers.add(answer);
            } while (cursor.moveToNext());
        }

        // return answers
        return answers;
    }

    // Get All Surveys
    public List<Surveys> getAlSurveys() {
        List<Surveys> surveys = new LinkedList<Surveys>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_SURVEYS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Surveys survey = null;
        if (cursor.moveToFirst()) {
            do {
                survey = new Surveys();
                survey.setId(Integer.parseInt(cursor.getString(0)));
                survey.setName(cursor.getString(1));
                survey.setDescription(cursor.getString(2));

                // Add survey to surveys
                surveys.add(survey);
            } while (cursor.moveToNext());
        }

        // return answers
        return surveys;
    }

    // Deleting users table
    public void deleteUsers() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_USERS,null,null);

        // 3. close
        db.close();

    }

    // Deleting Questions table
    public void deleteQuestions() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_QUESTIONS,null,null);

        // 3. close
        db.close();

    }

    // Deleting Answers table
    public void deleteAnswers() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_ANSWERS,null,null);

        // 3. close
        db.close();

    }

    // Deleting Surveys table
    public void deleteSurveys() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_SURVEYS,null,null);

        // 3. close
        db.close();

    }
}