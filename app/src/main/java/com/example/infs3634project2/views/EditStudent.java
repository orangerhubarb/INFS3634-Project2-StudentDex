package com.example.infs3634project2.views;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.infs3634project2.R;
import com.example.infs3634project2.model.Student;
import com.example.infs3634project2.storage.DBOpenHelper;
import com.example.infs3634project2.storage.StudentsContract;

public class EditStudent extends AppCompatActivity {

    private EditText firstNameEditText;
    private TextInputLayout firstNameError;

    private EditText lastNameEditText;
    private TextInputLayout lastNameError;

    private EditText zIDEditText;
    private TextInputLayout zIDError;


    private EditText degreeEditText;
    private EditText githubUsernameEditText;
    private EditText strengths;
    private EditText weaknesses;
    private EditText phonenumber;
    private EditText email;
    private Spinner yearOfDegreeSpinner;
    private Button confirmStudentSaveButton;
    private Button deleteStudentButton;

    private String firstNameText;
    private String lastNameText;
    private String zIDText;
    private int yearOfDegreeText;
    private String degreeText;
    private String githubUsernameText;
    private String strengthsText;
    private String weaknessesText;
    private String phonenumberText;
    private String emailText;

    private int tutorialID;
    private int studentID;

    private ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent backToProfile = new Intent(EditStudent.this, StudentProfileTabs.class);
                backToProfile.putExtra("StudentID", studentID);
                backToProfile.putExtra("TutorialID", tutorialID);
                startActivity(backToProfile);
            }
        });

        studentID = (int) getIntent().getSerializableExtra("STUDENT_ID");
        firstNameText = (String) getIntent().getSerializableExtra("FNAME");
        lastNameText = (String) getIntent().getSerializableExtra("LNAME");
        zIDText = (String) getIntent().getSerializableExtra("ZID");
        yearOfDegreeText = (int) getIntent().getSerializableExtra("YEAR_OF_DEGREE");
        degreeText = (String) getIntent().getSerializableExtra("DEGREE");
        githubUsernameText = (String) getIntent().getSerializableExtra("GITHUB_USER");
        strengthsText = (String) getIntent().getSerializableExtra("STRENGTHS");
        weaknessesText = (String) getIntent().getSerializableExtra("WEAKNESSES");
        tutorialID = (int) getIntent().getSerializableExtra("TUTORIAL_ID");
        phonenumberText = (String) getIntent().getSerializableExtra("PHONE_NUMBER");
        emailText = (String) getIntent().getSerializableExtra("EMAIL");


        Log.d("STUDENT DETAILS DEBUG", firstNameText + lastNameText + zIDText + yearOfDegreeText + degreeText + githubUsernameText + strengthsText + weaknessesText + tutorialID);

        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        firstNameEditText.setText(firstNameText);
        firstNameError = (TextInputLayout) findViewById(R.id.firstNameTextInput);

        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        lastNameEditText.setText(lastNameText);
        lastNameError = (TextInputLayout) findViewById(R.id.lastNameTextInput);

        zIDEditText = (EditText) findViewById(R.id.zIDEditText);
        zIDEditText.setText(zIDText);
        zIDError = (TextInputLayout) findViewById(R.id.zIDTextInput);

        yearOfDegreeSpinner = (Spinner) findViewById(R.id.yearOfDegreeSpinnerEdit);
        ArrayAdapter<CharSequence> yearOfDegreeAdapter = ArrayAdapter.createFromResource(this,
                R.array.yearOfDegree_array, android.R.layout.simple_spinner_item);
        yearOfDegreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearOfDegreeSpinner.setAdapter(yearOfDegreeAdapter);

        int spinnerPosition = yearOfDegreeAdapter.getPosition(String.valueOf(yearOfDegreeText));
        yearOfDegreeSpinner.setSelection(spinnerPosition);

        degreeEditText = (EditText) findViewById(R.id.degreeEditText);
        degreeEditText.setText(degreeText);
        githubUsernameEditText = (EditText) findViewById(R.id.githubUserEditText);
        githubUsernameEditText.setText(githubUsernameText);
        strengths = (EditText) findViewById(R.id.strengthsEditText);
        strengths.setText(strengthsText);
        weaknesses = (EditText) findViewById(R.id.weaknessesEditText);
        weaknesses.setText(weaknessesText);

        phonenumber = (EditText) findViewById(R.id.phonenumberEditText);
        phonenumber.setText(phonenumberText);

        email = (EditText) findViewById(R.id.emailEditText);
        email.setText(emailText);


        confirmStudentSaveButton = (Button) findViewById(R.id.confirmStudentSaveButton);

        confirmStudentSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean noError = true;

                String fName = firstNameEditText.getText().toString();

                String lName = lastNameEditText.getText().toString();

                String zID = zIDEditText.getText().toString();
                String degree = degreeEditText.getText().toString();
                int yearOfDegree = Integer.parseInt(yearOfDegreeSpinner.getSelectedItem().toString());
                String githubUsername = githubUsernameEditText.getText().toString();

                String strength = strengths.getText().toString();
                String weakness = weaknesses.getText().toString();

                if (fName.matches("")) {
                    firstNameError.setErrorEnabled(true);
                    firstNameError.setError("You have not provided a first name.");
                    noError = false;
                }

                if (lName.matches("")) {
                    lastNameError.setErrorEnabled(true);
                    lastNameError.setError("You have not provided a last name.");
                    noError = false;
                }

                if (zID.matches("") || !zID.matches("z[0-9]{7}")) {
                    zIDError.setErrorEnabled(true);
                    zIDError.setError("You have not entered a valid zID.");
                    noError = false;
                }

                if (noError == true) {

                    Student student = new Student(fName, lName, tutorialID, zID, yearOfDegree, degree, githubUsername, strength, weakness);


                    student.setPhoneNumber(phonenumber.getText().toString());
                    student.setEmail(email.getText().toString());

                    DBOpenHelper helper = new DBOpenHelper(EditStudent.this);
                    StudentsContract studentsContract = new StudentsContract(helper);

                    studentsContract.updateEditStudent(student, studentID);

                    Intent showStudentProfile = new Intent(EditStudent.this, StudentProfileTabs.class);
                    showStudentProfile.putExtra("StudentID", studentID);
                    showStudentProfile.putExtra("TutorialID", tutorialID);
                    startActivity(showStudentProfile);
                    finish();
                }
            }
        });

        deleteStudentButton = (Button) findViewById(R.id.deleteStudentButton);
        deleteStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditStudent.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Student")
                        .setMessage("Are you sure you want to delete this student?")
                        .setPositiveButton("Delete Student", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBOpenHelper helper = new DBOpenHelper(EditStudent.this);
                                StudentsContract studentsContract = new StudentsContract(helper);
                                studentsContract.deleteStudent(studentID);
                                Intent intent = new Intent(EditStudent.this, TutorialsActivity.class);
                                intent.putExtra("TutorialID", tutorialID);
                                startActivity(intent);
                                finish();
                                Toast.makeText(EditStudent.this, "Student successfully deleted.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

}