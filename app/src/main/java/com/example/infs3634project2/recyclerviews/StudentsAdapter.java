package com.example.infs3634project2.recyclerviews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infs3634project2.R;
import com.example.infs3634project2.model.Student;
import com.example.infs3634project2.model.Tutorial;
import com.example.infs3634project2.views.StudentProfileTabs;
import com.example.infs3634project2.views.StudentsActivity;

import java.util.ArrayList;

/**
 * Created by Davian on 6/10/16.
 */

    public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentsHolder> {

        private ArrayList<Student> mStudent;

        public StudentsAdapter(ArrayList<Student> studentList) {
            this.mStudent = studentList;
        }

        @Override
        public StudentsAdapter.StudentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.student_item_row, parent, false);

            return new StudentsHolder(inflatedView);
        }

        @Override
        public void onBindViewHolder(StudentsHolder holder, int position) {
            Student itemStudent = mStudent.get(position);
            holder.bindStudent(itemStudent);

        }

        @Override
        public int getItemCount() {
            return mStudent.size();
        }

        public static class StudentsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView studentName;
            private Student studentItem;

            public StudentsHolder(View itemView) {
                super(itemView);
                studentName = (TextView) itemView.findViewById(R.id.student_name);
                itemView.setOnClickListener(this);
            }

            public void bindStudent (Student studentItem) {
                this.studentItem = studentItem;
                String fullName = studentItem.getFirstName() + " " + studentItem.getLastName();
                studentName.setText(fullName);

            }

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent showProfile = new Intent(context, StudentProfileTabs.class);
                showProfile.putExtra("StudentID", studentItem.getStudentID());
                showProfile.putExtra("TutorialID", studentItem.getTutorialID());
                Log.d("DEBUG SADAPT SID TID", String.valueOf(studentItem.getStudentID()) + " " + String.valueOf(studentItem.getTutorialID()));

                context.startActivity(showProfile);
            }
        }
    }


