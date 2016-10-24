//package com.example.infs3634project2.views;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.example.infs3634project2.Data.GitHubCallback;
//import com.example.infs3634project2.Data.GitHubDataProvider;
//import com.example.infs3634project2.R;
//import com.example.infs3634project2.model.Student;
//import com.example.infs3634project2.recyclerviews.ProjectsAdapter;
//import com.example.infs3634project2.recyclerviews.StudentsAdapter;
//import com.example.infs3634project2.recyclerviews.TodoAdapter;
//import com.example.infs3634project2.storage.DBOpenHelper;
//import com.example.infs3634project2.storage.StudentsContract;
//
//import org.w3c.dom.Text;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class StudentProfile extends AppCompatActivity implements GitHubCallback<ArrayList<String>>{
//
//    private DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
//    private StudentsContract studentsContract = new StudentsContract(dbOpenHelper);
//    private Student student;
//
//    private Button editStudentButton;
//    private TextView studentName;
//
//    private TextView studentZID;
//    private TextView studentDegree;
//    private TextView studentYear;
//    //fragment
//
//    private TextView studentStrength;
//    private TextView studentWeakness;
//    private RecyclerView projectsRecyclerView;
//    private ProjectsAdapter projectsAdapter;
//    private LinearLayoutManager projectsLinearLayoutManager;
//
//    private RecyclerView todoRecyclerView;
//    private TodoAdapter todoAdapter;
//    private LinearLayoutManager todoLinearLayoutManager;
//    private List<String> newTodoList;
//    private EditText newTodoEntry;
//    private Button todoEntryButton;
//
//    private ImageButton backButton;
//
//    private int studentID;
//    private int tutorialID;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_student_profile);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Student");
//        setSupportActionBar(toolbar);
//
//
        //not moved yet
//        backButton = (ImageButton) findViewById(R.id.backButton);
//        backButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent showStudents = new Intent(StudentProfile.this, StudentsActivity.class);
//                showStudents.putExtra("TutorialID", tutorialID);
//                Log.d("DEBUG SPROFILE TID", String.valueOf(tutorialID));
//                startActivity(showStudents);
//
//                Intent backToTutorialList = new Intent(StudentProfile.this, TutorialsActivity.class);
//                backToTutorialList.putExtra("TutorialID", tutorialID);
//                startActivity(backToTutorialList);
//            }
//        });
//
//        editStudentButton = (Button) findViewById(R.id.editStudentButton);
//
//        editStudentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent showEdit = new Intent(StudentProfile.this, EditStudent.class);
//                Bundle extras = new Bundle();
//                extras.putInt("STUDENT_ID", studentID);
//                extras.putString("FNAME", student.getFirstName());
//                extras.putString("LNAME", student.getLastName());
//                extras.putInt("TUTORIAL_ID", student.getTutorialID());
//                extras.putString("ZID", student.getzID());
//                extras.putInt("YEAR_OF_DEGREE", student.getYearOfDegree());
//                extras.putString("DEGREE", student.getDegree());
//                extras.putString("GITHUB_USER", student.getGithubUsername());
//                extras.putString("STRENGTHS", student.getStrengths());
//                extras.putString("WEAKNESSES", student.getWeaknesses());
//                showEdit.putExtras(extras);
//                startActivity(showEdit);
//            }
//        });
//
//        studentID = (int) getIntent().getSerializableExtra("StudentID");
//        tutorialID = (int) getIntent().getSerializableExtra("TutorialID");
//
//        studentName = (TextView) findViewById(R.id.studentName);
//        studentZID = (TextView) findViewById(R.id.zIDTextView);
//        studentDegree = (TextView) findViewById(R.id.degreeTextView);
//        studentYear = (TextView) findViewById(R.id.yearOfDegreeTextView);
//
//        studentStrength = (TextView) findViewById(R.id.strengthsTextView);
//        studentWeakness = (TextView) findViewById(R.id.weaknessesTextView);
//
//        //fragment 3
//        newTodoEntry = (EditText) findViewById(R.id.newTodoEntry);
//        todoEntryButton = (Button) findViewById(R.id.todoEntryButton);
//
//        student = studentsContract.getStudent(studentID);
//
//        studentName.setText(student.getFirstName() + " " + student.getLastName());
//        studentZID.setText("(" + student.getzID() + ")");
//        studentDegree.setText(student.getDegree());
//        studentYear.setText(String.valueOf(student.getYearOfDegree()));
//        studentStrength.setText(student.getStrengths());
//        studentWeakness.setText(student.getWeaknesses());
//
//        //fragments
//
//        String githubUser = student.getGithubUsername();
//        GitHubDataProvider gitHubDataProvider = new GitHubDataProvider(this);
//        gitHubDataProvider.getGitProject(githubUser, this);
//
//        newTodoList = student.getTodoList();
//
//        todoRecyclerView = (RecyclerView) findViewById(R.id.todoRecyclerView);
////        todoAdapter =  new TodoAdapter(newTodoList, this, studentID);
//        todoRecyclerView.setAdapter(todoAdapter);
//        todoLinearLayoutManager = new LinearLayoutManager(this);
//        todoRecyclerView.setLayoutManager(todoLinearLayoutManager);
//        Log.d("Todo List Return", newTodoList.toString());
//
//        todoEntryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newEntry = newTodoEntry.getText().toString();
//                newTodoList.add(newEntry);
//                studentsContract.updateTodoList(newTodoList, studentID);
//                todoAdapter.notifyDataSetChanged();
//                newTodoEntry.getText().clear();
//            }
//        });
//    }
//
//
//    @Override
//    public void onTaskCompleted(ArrayList<String> listOfProjects) {
//        Log.d("Debug", "URL has been passed");
//        //Obviously after this has been obtained, then we set all the layout shit.
//
//        projectsRecyclerView = (RecyclerView) findViewById(R.id.projectsRecyclerView);
//        projectsAdapter =  new ProjectsAdapter(listOfProjects);
//        projectsRecyclerView.setAdapter(projectsAdapter);
//
//        projectsLinearLayoutManager = new LinearLayoutManager(this);
//        projectsRecyclerView.setLayoutManager(projectsLinearLayoutManager);
//        Log.d("Student List Return", listOfProjects.toString());
//    }
//
//    @Override
//    public void onFailure(String fail) {
//
//    }
//}