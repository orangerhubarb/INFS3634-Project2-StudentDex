package com.example.infs3634project2.views;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3634project2.R;
import com.example.infs3634project2.model.Student;
import com.example.infs3634project2.model.Tutorial;
import com.example.infs3634project2.recyclerviews.StudentsAdapter;
import com.example.infs3634project2.recyclerviews.TutorialsAdapter;
import com.example.infs3634project2.storage.DBOpenHelper;
import com.example.infs3634project2.storage.StudentsContract;
import com.example.infs3634project2.storage.TutorialsContract;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class TutorialsActivity extends AppCompatActivity implements StudentListFragment.OnFragmentInteractionListener{

    //The only way to get to the list of tutorials/students has to be through a button otherwise the list doesn't refresh
    private RecyclerView mRecyclerView;
    private TutorialsAdapter mAdapter;
    private StudentsAdapter studentsAdapter;
    private ArrayList<Tutorial> tutorialsList;
    private ArrayList<Student> studentsList;
    private LinearLayoutManager mLinearLayoutManager;
    private int currentTutorialID;
    private RecyclerView getmRecyclerViewStudents;
    private EditText studentsSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Classes");
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        currentTutorialID = (int) getIntent().getSerializableExtra("TutorialID");
        Log.d("CURRENTTUTID", String.valueOf(currentTutorialID));

        Button newTutorialButton = (Button) findViewById(R.id.newTutorialButton);
        assert newTutorialButton != null;
        newTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This should redirect to a blank list of the students but working out Tutorials right now
                Intent showClasses = new Intent(TutorialsActivity.this, NewTutorial.class);
                startActivity(showClasses);
            }
        });

        Button newStudentButton = (Button) findViewById(R.id.newStudentButton);
        if(currentTutorialID != 0) {
            newStudentButton.setVisibility(VISIBLE);
        }

        newStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newStudent = new Intent(TutorialsActivity.this, NewStudent.class);

                if(currentTutorialID != 0) {
                    newStudent.putExtra("TutorialID", currentTutorialID);
                    Log.d("StartingNewStudent", String.valueOf(currentTutorialID));
                    startActivity(newStudent);
                }
                else {
                    Toast.makeText(TutorialsActivity.this, "You have not selected a Tutorial",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        DBOpenHelper helper = new DBOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        TutorialsContract tutorialsContract = new TutorialsContract(helper);

        tutorialsList = tutorialsContract.getTutorials();

        mRecyclerView = (RecyclerView) findViewById(R.id.tutorialsRecylerView);
        mAdapter = new TutorialsAdapter(tutorialsList, this);
        mRecyclerView.setAdapter(mAdapter);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        studentsSearch = (EditText) findViewById(R.id.searchStudents);

        getmRecyclerViewStudents = (RecyclerView) findViewById(R.id.studentsRecyclerViewFragment);

        Log.d("CurrentTutID", String.valueOf(currentTutorialID));
        if (currentTutorialID != 0) {
            updateFragmentList(currentTutorialID);
        }
    }

    public void updateFragmentList(int tutorialID) {

        DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
        StudentsContract studentsContract = new StudentsContract(dbOpenHelper);

        studentsList = studentsContract.getStudentsList(tutorialID);
        currentTutorialID = tutorialID;

        getmRecyclerViewStudents.setLayoutManager(new LinearLayoutManager(TutorialsActivity.this));
        studentsAdapter = new StudentsAdapter(studentsList);
        Log.d("STULIST", studentsList.toString());
        getmRecyclerViewStudents.setAdapter(studentsAdapter);
        studentsAdapter.notifyDataSetChanged();

        searchStudentsTextListener();

    }


    @Override
    public void onResume() {
        super.onResume();
        int backTutorialID = (int) getIntent().getSerializableExtra("TutorialID");
        Log.d("BackID", String.valueOf(backTutorialID));
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
        TutorialsContract tutorialsContract = new TutorialsContract(dbOpenHelper);

        tutorialsList = tutorialsContract.getTutorials();

        mRecyclerView = (RecyclerView) findViewById(R.id.tutorialsRecylerView);
        mAdapter = new TutorialsAdapter(tutorialsList, this, backTutorialID);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void searchStudentsTextListener() {
        studentsSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().toLowerCase();

                final ArrayList<Student> newList  = new ArrayList<>();

                for(int i = 0 ; i < studentsList.size() ; i++) {
                    final String pokemonSearch = studentsList.get(i).getFirstName().toLowerCase() + " " + studentsList.get(i).getLastName().toLowerCase() ;
                    if(pokemonSearch.contains(s)) {
                        newList.add(studentsList.get(i));
                    }
                }

                getmRecyclerViewStudents.setLayoutManager(new LinearLayoutManager(TutorialsActivity.this));
                studentsAdapter = new StudentsAdapter(newList);
                getmRecyclerViewStudents.setAdapter(studentsAdapter);
                studentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
