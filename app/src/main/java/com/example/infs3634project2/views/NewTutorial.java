package com.example.infs3634project2.views;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.infs3634project2.R;
import com.example.infs3634project2.model.Tutorial;
import com.example.infs3634project2.storage.DBOpenHelper;
import com.example.infs3634project2.storage.TutorialsContract;

public class NewTutorial extends AppCompatActivity {

    //Need validation checks, so how do we stop people from entering in dodgy course codes or multiple lines of codes

    private Button confirmTutorialAddButton;
    private EditText tutorialName;
    private Spinner daySpinner;
    private Spinner classStartSpinner;
    private Spinner classEndSpinner;
    private Spinner classAMPMStartSpinner;
    private Spinner classAMPMEndSpinner;

    private ImageButton backButton;

    private TextInputLayout tutorialNameError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tutorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("New Tutorial");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent backToTutorialList = new Intent(NewTutorial.this, TutorialsActivity.class);
                backToTutorialList.putExtra("TutorialID", 0);
                startActivity(backToTutorialList);
                finish();
            }
        });

        confirmTutorialAddButton = (Button) findViewById(R.id.confirmTutorialAddButton);
        tutorialName = (EditText) findViewById(R.id.tutorialNameEditText);
        tutorialNameError = (TextInputLayout) findViewById(R.id.tutorialNameTextInput);

        daySpinner = (Spinner) findViewById(R.id.day_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        classStartSpinner = (Spinner) findViewById(R.id.classStartSpinner);
        ArrayAdapter<CharSequence> startAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classStartSpinner.setAdapter(startAdapter);

        classAMPMStartSpinner = (Spinner) findViewById(R.id.classAMPMStartSpinner);
        ArrayAdapter<CharSequence> startAMPMAdapter = ArrayAdapter.createFromResource(this,
                R.array.timeOfDay_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classAMPMStartSpinner.setAdapter(startAMPMAdapter);

        classEndSpinner = (Spinner) findViewById(R.id.classEndSpinner);
        ArrayAdapter<CharSequence> endAdapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classEndSpinner.setAdapter(endAdapter);

        classAMPMEndSpinner = (Spinner) findViewById(R.id.classAMPMEndSpinner);
        ArrayAdapter<CharSequence> endAMPMAdapter = ArrayAdapter.createFromResource(this,
                R.array.timeOfDay_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classAMPMEndSpinner.setAdapter(endAMPMAdapter);

        confirmTutorialAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Need to regex this
                if (tutorialName.getText().toString().matches("") || !tutorialName.getText().toString().matches("[a-zA-Z]{4}[0-9]{4}")) {
                    tutorialNameError.setErrorEnabled(true);
                    tutorialNameError.setError("Please enter a valid tutorial name.");
                } else {

                    String tutorialTime = classStartSpinner.getSelectedItem().toString() + classAMPMStartSpinner.getSelectedItem().toString() + " - "
                    + classEndSpinner.getSelectedItem().toString() + classAMPMEndSpinner.getSelectedItem().toString();

                    Tutorial tutorial = new Tutorial(tutorialName.getText().toString(), daySpinner.getSelectedItem().toString(), tutorialTime);

                    DBOpenHelper helper = new DBOpenHelper(NewTutorial.this);
                    TutorialsContract tutorialsContract = new TutorialsContract(helper);

                    int tutorialID = (int) tutorialsContract.insertNewTutorial(tutorial);

                    Intent showClasses = new Intent(NewTutorial.this, TutorialsActivity.class);
                    showClasses.putExtra("TutorialID", tutorialID);
                    startActivity(showClasses);
                    finish();
                }
            }
        });
    }
}
