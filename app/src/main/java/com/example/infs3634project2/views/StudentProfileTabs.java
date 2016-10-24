package com.example.infs3634project2.views;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3634project2.R;
import com.example.infs3634project2.model.Student;
import com.example.infs3634project2.storage.DBOpenHelper;
import com.example.infs3634project2.storage.StudentsContract;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StudentProfileTabs extends AppCompatActivity {

    private DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
    private StudentsContract studentsContract = new StudentsContract(dbOpenHelper);
    private Student student;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionButton editStudentButton;

    private int studentID;
    private int tutorialID;

    private ImageView studentPicture;
    private Bitmap studentPictureBitmap;
    private TextView studentName;
    private ImageButton backButton;
    private ImageButton takePhoto;

    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent showStudents = new Intent(StudentProfileTabs.this, StudentsActivity.class);
                showStudents.putExtra("TutorialID", tutorialID);
                Log.d("DEBUG SPROFILE TID", String.valueOf(tutorialID));
                startActivity(showStudents);

                Intent backToTutorialList = new Intent(StudentProfileTabs.this, TutorialsActivity.class);
                backToTutorialList.putExtra("TutorialID", tutorialID);
                startActivity(backToTutorialList);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        studentID = (int) getIntent().getSerializableExtra("StudentID");
        tutorialID = (int) getIntent().getSerializableExtra("TutorialID");
        student = studentsContract.getStudent(studentID);
        Log.d("STUDENTIDPASSED", String.valueOf(studentID));

        editStudentButton = (FloatingActionButton) findViewById(R.id.editStudentButton);
        editStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showEdit = new Intent(StudentProfileTabs.this, EditStudent.class);
                Bundle extras = new Bundle();
                extras.putInt("STUDENT_ID", studentID);
                extras.putString("FNAME", student.getFirstName());
                extras.putString("LNAME", student.getLastName());
                extras.putInt("TUTORIAL_ID", student.getTutorialID());
                extras.putString("ZID", student.getzID());
                extras.putInt("YEAR_OF_DEGREE", student.getYearOfDegree());
                extras.putString("DEGREE", student.getDegree());
                extras.putString("GITHUB_USER", student.getGithubUsername());
                extras.putString("STRENGTHS", student.getStrengths());
                extras.putString("WEAKNESSES", student.getWeaknesses());
                extras.putString("PHONE_NUMBER", student.getPhoneNumber());
                extras.putString("EMAIL", student.getEmail());

                if(student.getStudentPicture() != null) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    student.getStudentPicture().compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                    showEdit.putExtra("STUDENT_PICTURE", outputStream.toByteArray());
                }
                showEdit.putExtras(extras);
                startActivity(showEdit);
            }
        });
        studentPicture = (ImageView) findViewById(R.id.studentPictureProfile);
        studentName = (TextView) findViewById(R.id.studentNameTextView);
        studentName.setText(student.getFirstName() + " " + student.getLastName());


        if(student.getStudentPicture() != null) {
            studentPicture.setImageBitmap(student.getStudentPicture());
        }

        takePhoto = (ImageButton) findViewById(R.id.takephotoImageButton);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }

                else {
                    Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    Log.d("IMAGE", "IMAGE SNAPPED");
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                Log.d("IMAGE", "IMAGE SNAPPED");

            }
            else {

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                Log.d("ImageTaken", "ImageTaken");

                Bundle extras = data.getExtras();

                studentPictureBitmap = (Bitmap) extras.get("data");

                studentPicture.setImageBitmap(studentPictureBitmap);
                student.setStudentPicture(studentPictureBitmap);

                Log.d("ImageTaken", studentPictureBitmap.toString());


                SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();

                byte[] img = studentsContract.getBitmapAsByteArray(student.getStudentPicture());
                cv.put("studentpicture", img);

                Log.d("Debug", "Content Values put method has been used");

                db.update("students", cv, "_id=" + student.getStudentID(), null);

                Log.d("Debug", student.getStudentID() + student.getStudentPicture().toString());
                db.close();


            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
            }
        }


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentOne(), "Basic Info");
        adapter.addFragment(new FragmentTwo(), "Student Skills");
        adapter.addFragment(new FragmentThree(), "To-do List");
        viewPager.setAdapter(adapter);
    }

    public Student getStudent() {
        return this.student;
    }

   public int getID() {
       Log.d("ID", String.valueOf(studentID));
       return studentID;
   }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
