package com.example.infs3634project2.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.infs3634project2.R;
import com.example.infs3634project2.model.Student;
import com.example.infs3634project2.recyclerviews.TodoAdapter;
import com.example.infs3634project2.storage.DBOpenHelper;
import com.example.infs3634project2.storage.StudentsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class FragmentThree extends Fragment {

    private RecyclerView todoRecyclerView;
    private TodoAdapter todoAdapter;
    private LinearLayoutManager todoLinearLayoutManager;
    private List<String> newTodoList;
    private EditText newTodoEntry;
    private Button todoEntryButton;
    private int studentID;
    private FragmentThree fragment = this;

    private Student mStudent;

    public FragmentThree() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fragment_three, container, false);

        newTodoEntry = (EditText) view.findViewById(R.id.newTodoEntry);
        todoEntryButton = (Button) view.findViewById(R.id.todoEntryButton);

        mStudent = ((StudentProfileTabs) this.getActivity()).getStudent();
        studentID = ((StudentProfileTabs) this.getActivity()).getID();

        newTodoList = mStudent.getTodoList();
        Log.d("Todo List Return", newTodoList.toString());

        todoRecyclerView = (RecyclerView) view.findViewById(R.id.todoRecyclerView);
        todoAdapter =  new TodoAdapter(newTodoList, this, studentID);

        todoRecyclerView.setAdapter(todoAdapter);
        todoLinearLayoutManager = new LinearLayoutManager(this.getContext());
        todoRecyclerView.setLayoutManager(todoLinearLayoutManager);

        todoEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOpenHelper dbOpenHelper = new DBOpenHelper(fragment.getContext());
                StudentsContract studentsContract = new StudentsContract(dbOpenHelper);
                String newEntry = newTodoEntry.getText().toString();
                newTodoList.add(newEntry);
                studentsContract.updateTodoList(newTodoList, studentID);
                Log.d("DEBUG FRAG UPD TODO", studentID + newTodoList.toString());
                todoAdapter.notifyDataSetChanged();
                newTodoEntry.getText().clear();
            }
        });
        return view;
    }

    public void recalculateTodo(List<String> todoList) {
        mStudent.setTodoList(todoList);
    }
}
