package com.example.infs3634project2.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.infs3634project2.R;
import com.example.infs3634project2.model.Tutorial;
import com.example.infs3634project2.storage.DBOpenHelper;
import com.example.infs3634project2.storage.StudentsContract;
import com.example.infs3634project2.storage.TutorialsContract;

public class EditTutorial extends AppCompatActivity {

    //Need validation checks, so how do we stop people from entering in dodgy course codes or multiple lines of codes

    private Button updateTutorialButton;
    private Button deleteTutorialButton;
    private EditText tutorialNameEditText;
    private Spinner daySpinner;
    private Spinner classStartSpinner;
    private Spinner classEndSpinner;
    private Spinner classAMPMStartSpinner;
    private Spinner classAMPMEndSpinner;

    private String tutorialName;
    private String tutorialDay;
    private String tutorialTime;

    private ImageButton backButton;

    private TextInputLayout tutorialNameError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Tutorial");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final int tutorialID = (int) getIntent().getSerializableExtra("TutorialID");

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent backToTutorialList = new Intent(EditTutorial.this, TutorialsActivity.class);
                backToTutorialList.putExtra("TutorialID", tutorialID);
                startActivity(backToTutorialList);
                finish();
            }
        });

        updateTutorialButton = (Button) findViewById(R.id.saveTutorialButton);
        tutorialNameEditText = (EditText) findViewById(R.id.tutorialNameEditText);
        tutorialName = (String) getIntent().getSerializableExtra("TutorialName");
        tutorialNameEditText.setText(tutorialName);


        tutorialNameError = (TextInputLayout) findViewById(R.id.tutorialNameTextInput);

        daySpinner = (Spinner) findViewById(R.id.day_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
        tutorialDay = (String) getIntent().getSerializableExtra("TutorialDay");
        int spinnerPositionDaySpinner = adapter.getPosition(String.valueOf(tutorialDay));
        daySpinner.setSelection(spinnerPositionDaySpinner);


        tutorialTime = (String) getIntent().getSerializableExtra("TutorialTime");
        String start = tutorialTime.substring(0, (tutorialTime.length()/2));



        classStartSpinner = (Spinner) findViewById(R.id.classStartSpinner);
        ArrayAdapter<CharSequence> startAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classStartSpinner.setAdapter(startAdapter);
        int spinnerPositionStartSpinner = startAdapter.getPosition(findTime(start));
        classStartSpinner.setSelection(spinnerPositionStartSpinner);

                classAMPMStartSpinner = (Spinner) findViewById(R.id.classAMPMStartSpinner);
        ArrayAdapter<CharSequence> startAMPMAdapter = ArrayAdapter.createFromResource(this,
                R.array.timeOfDay_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classAMPMStartSpinner.setAdapter(startAMPMAdapter);
        int spinnerPositionAMPMStartSpinner = startAMPMAdapter.getPosition(findAMPM(start));
        classAMPMStartSpinner.setSelection(spinnerPositionAMPMStartSpinner);


        //END GOES HERE
        String end = tutorialTime.substring(tutorialTime.length()/2);
        classEndSpinner = (Spinner) findViewById(R.id.classEndSpinner);
        ArrayAdapter<CharSequence> endAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classEndSpinner.setAdapter(endAdapter);
        int spinnerPositionEndSpinner = endAdapter.getPosition(findTime(end));
        classEndSpinner.setSelection(spinnerPositionEndSpinner);

        classAMPMEndSpinner = (Spinner) findViewById(R.id.classAMPMEndSpinner);
        ArrayAdapter<CharSequence> endAMPMAdapter = ArrayAdapter.createFromResource(this,
                R.array.timeOfDay_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classAMPMEndSpinner.setAdapter(endAMPMAdapter);
        int spinnerPositionAMPMEndSpinner = endAMPMAdapter.getPosition(findAMPM(end));
        classAMPMEndSpinner.setSelection(spinnerPositionAMPMEndSpinner);

        updateTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Need to regex this
                if (tutorialNameEditText.getText().toString().matches("") || !tutorialNameEditText.getText().toString().matches("[a-zA-Z]{4}[0-9]{4}")) {
                    tutorialNameError.setErrorEnabled(true);
                    tutorialNameError.setError("Please enter a valid tutorial name.");
                } else {

                    String tutorialTime = classStartSpinner.getSelectedItem().toString() + classAMPMStartSpinner.getSelectedItem().toString() + " - "
                            + classEndSpinner.getSelectedItem().toString() + classAMPMEndSpinner.getSelectedItem().toString();

                    Tutorial tutorial = new Tutorial(tutorialNameEditText.getText().toString(), daySpinner.getSelectedItem().toString(), tutorialTime);

                    DBOpenHelper helper = new DBOpenHelper(EditTutorial.this);
                    TutorialsContract tutorialsContract = new TutorialsContract(helper);
                    tutorialsContract.updateTutorial(tutorial, tutorialID);

                    Intent showClasses = new Intent(EditTutorial.this, TutorialsActivity.class);
                    showClasses.putExtra("TutorialID", tutorialID);
                    startActivity(showClasses);
                    finish();
                }
            }
        });

        deleteTutorialButton = (Button) findViewById(R.id.deleteTutorialButton);
        deleteTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditTutorial.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Tutorial")
                        .setMessage("Are you sure you want to delete this tutorial?")
                        .setPositiveButton("Delete Tutorial", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBOpenHelper helper = new DBOpenHelper(EditTutorial.this);
                                TutorialsContract tutorialsContract = new TutorialsContract(helper);
                                tutorialsContract.deleteTutorial(tutorialID);

                                Intent intent = new Intent(EditTutorial.this, TutorialsActivity.class);
                                intent.putExtra("TutorialID", tutorialID);
                                startActivity(intent);
                                finish();
                                Toast.makeText(EditTutorial.this, "Tutorial successfully deleted.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    public String findTime(String input) {
        String classTime = "";
        if(input.contains("11:00")) {
            classTime = "11:00";
        }

        else if (input.contains("12:00")) {
            classTime = "12:00";
        }

        else if (input.contains("3:00")) {
            classTime = "3:00";
        }
        else if (input.contains("4:00")) {
            classTime = "4:00";
        }
        else if (input.contains("5:00")) {
            classTime = "5:00";
        }
        else if (input.contains("6:00")) {
            classTime = "6:00";
        }
        else if (input.contains("7:00")) {
            classTime = "7:00";
        }
        else if (input.contains("8:00")) {
            classTime = "8:00";
        }
        else if (input.contains("9:00")) {
            classTime = "9:00";
        }
        else if (input.contains("10:00")) {
            classTime = "10:00";
        }
        else if (input.contains("11:00")) {
            classTime = "11:00";
        }
        else if (input.contains("1:00")) {
            classTime = "1:00";
        }
        else {
            classTime = "2:00";
        }

        return classTime;
    }

    public String findAMPM(String input) {
        String AMPM = "";
        if(input.contains("AM")) {
            AMPM = "AM";
        }
        else {
            AMPM = "PM";
        }
        return AMPM;
    }
}