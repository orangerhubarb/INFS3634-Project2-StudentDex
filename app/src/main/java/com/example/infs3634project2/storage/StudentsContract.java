package com.example.infs3634project2.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.infs3634project2.model.Student;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Davian on 11/10/16.
 */
public class StudentsContract {
    public static final String TABLE_NAME = "students";
    public final SQLiteOpenHelper dbHelper;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    StudentEntry._ID + " INTEGER PRIMARY KEY," +
                    StudentEntry.COLUMN_TUTORIAL + " INTEGER," +
                    StudentEntry.COLUMN_FNAME + " TEXT," +
                    StudentEntry.COLUMN_LNAME + " TEXT," +
                    StudentEntry.COLUMN_ZID + " TEXT," +
                    StudentEntry.COLUMN_YEAROFDEGREE + " INT," +
                    StudentEntry.COLUMN_DEGREE + " TEXT," +
                    StudentEntry.COLUMN_GITHUBURL + " TEXT," +
                    StudentEntry.COLUMN_STRENGTHS + " TEXT," +
                    StudentEntry.COLUMN_WEAKNESSES + " TEXT," +
                    StudentEntry.COLUMN_TODO + " TEXT," +
                    StudentEntry.COLUMN_STUDENT_PICTURE + " BLOB," +
                    StudentEntry.COLUMN_PHONE_NUMBER + " TEXT, " +
                    StudentEntry.COLUMN_EMAIL + " TEXT" + ")";

    public StudentsContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    public abstract class StudentEntry implements BaseColumns {
        public static final String COLUMN_TUTORIAL = "tutorial";
        public static final String COLUMN_FNAME = "fname";
        public static final String COLUMN_LNAME = "lname";
        public static final String COLUMN_ZID = "zid";
        public static final String COLUMN_YEAROFDEGREE = "yearofdegree";
        public static final String COLUMN_DEGREE = "degree";
        public static final String COLUMN_GITHUBURL = "githuburl";
        public static final String COLUMN_STRENGTHS = "strengths";
        public static final String COLUMN_WEAKNESSES = "weaknesses";
        public static final String COLUMN_TODO = "todo";
        public static final String COLUMN_STUDENT_PICTURE = "studentpicture";
        public static final String COLUMN_PHONE_NUMBER = "phonenumber";
        public static final String COLUMN_EMAIL = "email";


    }

    public long insertNewStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudentEntry.COLUMN_TUTORIAL, student.getTutorialID());
        values.put(StudentEntry.COLUMN_FNAME, student.getFirstName());
        values.put(StudentEntry.COLUMN_LNAME, student.getLastName());
        values.put(StudentEntry.COLUMN_ZID, student.getzID());
        values.put(StudentEntry.COLUMN_YEAROFDEGREE, student.getYearOfDegree());
        values.put(StudentEntry.COLUMN_DEGREE, student.getDegree());
        values.put(StudentEntry.COLUMN_GITHUBURL, student.getGithubUsername());
        values.put(StudentEntry.COLUMN_STRENGTHS, student.getStrengths());
        values.put(StudentEntry.COLUMN_WEAKNESSES, student.getWeaknesses());
        values.put(StudentEntry.COLUMN_PHONE_NUMBER, student.getPhoneNumber());
        values.put(StudentEntry.COLUMN_EMAIL, student.getEmail());

        byte[] studentImage = getBitmapAsByteArray(student.getStudentPicture());
        values.put(StudentEntry.COLUMN_STUDENT_PICTURE, studentImage);


        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public void updateEditStudent(Student student, int studentID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudentEntry.COLUMN_TUTORIAL, student.getTutorialID());
        values.put(StudentEntry.COLUMN_FNAME, student.getFirstName());
        values.put(StudentEntry.COLUMN_LNAME, student.getLastName());
        values.put(StudentEntry.COLUMN_ZID, student.getzID());
        values.put(StudentEntry.COLUMN_YEAROFDEGREE, student.getYearOfDegree());
        values.put(StudentEntry.COLUMN_DEGREE, student.getDegree());
        values.put(StudentEntry.COLUMN_GITHUBURL, student.getGithubUsername());
        values.put(StudentEntry.COLUMN_STRENGTHS, student.getStrengths());
        values.put(StudentEntry.COLUMN_WEAKNESSES, student.getWeaknesses());
        values.put(StudentEntry.COLUMN_PHONE_NUMBER, student.getPhoneNumber());
        values.put(StudentEntry.COLUMN_EMAIL, student.getEmail());

        if (student.getStudentPicture() != null) {
            byte[] studentImage = getBitmapAsByteArray(student.getStudentPicture());
            values.put(StudentEntry.COLUMN_STUDENT_PICTURE, studentImage);
        }

        String selection = StudentEntry._ID + " =" + studentID;

        db.update(TABLE_NAME, values, selection, null);
    }

    public ArrayList<Student> getStudentsList(int tutorialID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                StudentEntry._ID,
                StudentEntry.COLUMN_TUTORIAL,
                StudentEntry.COLUMN_FNAME,
                StudentEntry.COLUMN_LNAME,
                StudentEntry.COLUMN_ZID,
                StudentEntry.COLUMN_YEAROFDEGREE,
                StudentEntry.COLUMN_DEGREE,
                StudentEntry.COLUMN_GITHUBURL,
                StudentEntry.COLUMN_STRENGTHS,
                StudentEntry.COLUMN_WEAKNESSES,
                StudentEntry.COLUMN_TODO
        };

        String sortOrder = StudentEntry.COLUMN_LNAME;

        Cursor cur = db.query(
                TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList<Student> studentList = new ArrayList<>();

        while (cur.moveToNext()) {

            if (cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_TUTORIAL)) == tutorialID) {
                Student student = new Student();
                student.setFirstName(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_FNAME)));
                student.setLastName(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_LNAME)));
                student.setStudentID(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry._ID)));
                student.setTutorialID(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_TUTORIAL)));
                studentList.add(student);
            }
        }
        cur.close();
        db.close();
        return studentList;
    }

    public Student getStudent(int studentID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = StudentEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(studentID)};

        String[] columns = {
                StudentEntry._ID,
                StudentEntry.COLUMN_TUTORIAL,
                StudentEntry.COLUMN_FNAME,
                StudentEntry.COLUMN_LNAME,
                StudentEntry.COLUMN_ZID,
                StudentEntry.COLUMN_YEAROFDEGREE,
                StudentEntry.COLUMN_DEGREE,
                StudentEntry.COLUMN_GITHUBURL,
                StudentEntry.COLUMN_STRENGTHS,
                StudentEntry.COLUMN_WEAKNESSES,
                StudentEntry.COLUMN_TODO,
                StudentEntry.COLUMN_STUDENT_PICTURE,
                StudentEntry.COLUMN_PHONE_NUMBER,
                StudentEntry.COLUMN_EMAIL
        };

        Cursor cur = db.query(
                TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Student student = null;
        if (cur.moveToNext()) {
            student = new Student();
            student.setFirstName(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_FNAME)));
            student.setLastName(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_LNAME)));
            student.setStudentID(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry._ID)));
            Log.d("Debug", "Name obtained");
            student.setTutorialID(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_TUTORIAL)));
            student.setzID(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_ZID)));
            student.setYearOfDegree(cur.getInt(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_YEAROFDEGREE)));
            student.setDegree(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_DEGREE)));
            student.setGithubUsername(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_GITHUBURL)));
            student.setStrengths(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_STRENGTHS)));
            student.setWeaknesses(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_WEAKNESSES)));
            student.setTodoList(convertStringToArray(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_TODO))));

            if (cur.getBlob(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_STUDENT_PICTURE)) != null) {
                byte[] studentPicture = cur.getBlob(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_STUDENT_PICTURE));
                student.setStudentPicture(BitmapFactory.decodeByteArray(studentPicture, 0, studentPicture.length));

            }

            student.setPhoneNumber(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_PHONE_NUMBER)));
            student.setEmail(cur.getString(cur.getColumnIndexOrThrow(StudentEntry.COLUMN_EMAIL)));
        }

        cur.close();
        db.close();
        return student;
    }

    public void deleteStudent(int studentID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("students", "_id = " + studentID, null);
        db.close();
    }

    public void updateTodoList(List<String> newTodoList, int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("todo", convertArrayToString(newTodoList));
        db.update(TABLE_NAME, values, "_id=" + id, null);
        db.close();

    }

    //Converts a String array to string to store it
    public static String convertArrayToString(List<String> array) {

        if (array.size() != 0) {
            String stringArray = array.get(0) + ",";
            for (int i = 1; i < array.size(); i++) {
                stringArray += array.get(i);

                if (array.indexOf(i) != array.size() - 1) {
                    stringArray += ", ";
                }
            }
            return stringArray;
        } else {
            return null;
        }
    }

    //Converts the string back to a String Array
    public static List<String> convertStringToArray(String string) {
        if (string == null || string.equals("null")) {
            List<String> blankArray = new ArrayList<>();
            return blankArray;
        } else {
            List<String> array = new ArrayList<>(Arrays.asList(string.split("\\s*,\\s*")));
            return array;
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}