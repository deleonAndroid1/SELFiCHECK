package com.training.android.selficheck.TeacherSide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.training.android.selficheck.Datas.AttendanceData;
import com.training.android.selficheck.Datas.SubjectsData;
import com.training.android.selficheck.R;

public class AddSubjectActivity extends AppCompatActivity {

    private Button mBtnSubmit;
    private EditText mEtCourseName, mEtCourseCode, mEtTime, mEtDay, mEtRoom, mEtCoursePassword;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mSubjReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        mBtnSubmit = (Button) findViewById(R.id.btnSubmit);
        mEtCourseName = (EditText) findViewById(R.id.etCourseName);
        mEtCourseCode = (EditText) findViewById(R.id.etCourseCode);
        mEtTime = (EditText) findViewById(R.id.etTime);
        mEtRoom = (EditText) findViewById(R.id.etRoom);
        mEtCoursePassword = (EditText) findViewById(R.id.etCoursePassword);

        //Database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mSubjReference = mFirebaseDatabase.getReference().child("Subjects");

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cName = mEtCourseName.getText().toString();
                String cCode = mEtCourseCode.getText().toString();
                String time = mEtTime.getText().toString();
                String room = mEtRoom.getText().toString();
                String password = mEtCoursePassword.getText().toString();

                //Temporary ID
                String id = "123123";

                SubjectsData subjdata = new SubjectsData(cCode,cName,time, room, password, Long.parseLong(id));
                mSubjReference.child(cCode).setValue(subjdata);
            }
        });
    }
}
