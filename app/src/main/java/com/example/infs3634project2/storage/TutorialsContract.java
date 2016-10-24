package com.example.infs3634project2.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.infs3634project2.model.Student;
import com.example.infs3634project2.model.Tutorial;
import com.example.infs3634project2.views.TutorialsActivity;

import java.util.ArrayList;

/**
 * Created by Davian on 6/10/16.
 */
public class TutorialsContract {

    public static final String TABLE_NAME = "tutorials";
    public final SQLiteOpenHelper dbHelper;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    TutorialEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    TutorialEntry.COLUMN_NAME + " TEXT," +
                    TutorialEntry.COLUMN_TIME + " TEXT," +
                    TutorialEntry.COLUMN_DAY + " TEXT" + ")";


    public TutorialsContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    public abstract class TutorialEntry implements BaseColumns {
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_DAY = "day";


    }

    public long insertNewTutorial(Tutorial tutorial) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TutorialEntry.COLUMN_NAME, tutorial.getName());
        values.put(TutorialEntry.COLUMN_TIME, tutorial.getTime());
        values.put(TutorialEntry.COLUMN_DAY, tutorial.getDay());

        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);

        db.close();
        Log.d("Inserted Row", Long.toString(newRowId));
        return newRowId;
    }

    public ArrayList<Tutorial> getTutorials() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                TutorialEntry._ID,
                TutorialEntry.COLUMN_NAME,
                TutorialEntry.COLUMN_TIME,
                TutorialEntry.COLUMN_DAY

        };

        String sortOrder = TutorialEntry._ID;

        Cursor cur = db.query(
                TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList<Tutorial> tutorialsList = new ArrayList<>();

        while (cur.moveToNext()){
            Tutorial tutorial = new Tutorial();
            tutorial.setName(cur.getString(cur.getColumnIndexOrThrow(TutorialEntry.COLUMN_NAME)));
            tutorial.setTutorialID(cur.getInt(cur.getColumnIndexOrThrow(TutorialEntry._ID)));
            tutorial.setTime(cur.getString(cur.getColumnIndexOrThrow(TutorialEntry.COLUMN_TIME)));
            tutorial.setDay(cur.getString(cur.getColumnIndexOrThrow(TutorialEntry.COLUMN_DAY)));

            StudentsContract studentsContract = new StudentsContract(dbHelper);

            ArrayList<Student> studentsList;
            studentsList = studentsContract.getStudentsList(tutorial.getTutorialID());
            if(studentsList.size() > 0 ) {
                tutorial.setStudentCount(studentsList.size());
            }



            Log.d("DEBUG TUTEIDDB", String.valueOf(cur.getInt(cur.getColumnIndexOrThrow(TutorialEntry._ID))));

            tutorialsList.add(tutorial);
        }

        cur.close();
        db.close();
        return tutorialsList;
    }

    public void deleteTutorial(int tutorialID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TABLE_NAME, "_id = " + tutorialID, null);
        db.delete("students", "tutorial = " + tutorialID, null);
        db.close();
    }

    public void updateTutorial(Tutorial tutorial, int tutorialID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TutorialEntry.COLUMN_NAME, tutorial.getName());
        values.put(TutorialEntry.COLUMN_TIME, tutorial.getTime());
        values.put(TutorialEntry.COLUMN_DAY, tutorial.getDay());

        String selection = StudentsContract.StudentEntry._ID + " =" + tutorialID;

        db.update(TABLE_NAME, values, selection, null);
        db.close();
    }


}