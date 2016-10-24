package com.example.infs3634project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.infs3634project2.views.NewTutorial;
import com.example.infs3634project2.views.StudentProfileTabs;
import com.example.infs3634project2.views.TutorialsActivity;

public class MainActivity extends AppCompatActivity {

    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        continueButton = (Button) findViewById(R.id.continueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent showClasses = new Intent(MainActivity.this, TutorialsActivity.class);
                showClasses.putExtra("TutorialID", 0);
                startActivity(showClasses);
                finish();
            }
        });


    }
}
