package com.training.android.selficheck.TeacherSide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.training.android.selficheck.Datas.AttendanceData;
import com.training.android.selficheck.Datas.Subj_StudentsData;
import com.training.android.selficheck.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PasswordActivity extends AppCompatActivity {

    // INIT
    private Button mBtnConfirm;
    private EditText mPassword;
    private FirebaseDatabase mFirebaseDatabase;
    DateFormat df = new SimpleDateFormat("HH:mm");
    DateFormat df1 = new SimpleDateFormat("MM-dd-yyyy");
    private DatabaseReference mSubjStudReference, mAttendanceReference, mTakeAttendanceReference, SampleReference;
    private String time = df.format(Calendar.getInstance().getTime()), key;
    private String date = df1.format(Calendar.getInstance().getTime()), AttendancePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        //Database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mSubjStudReference = mFirebaseDatabase.getReference().child("Subj_students");
        SampleReference = mFirebaseDatabase.getReference().child("Sample");

        //Subject
        getSubj();

        //Set Datas
        Intent i = getIntent();
        Bundle data = i.getExtras();
        key = data.getString("CourseCode");

        mBtnConfirm = (Button) findViewById(R.id.btnConfirm);
        mPassword = (EditText) findViewById(R.id.etCoursePassword);

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = mPassword.getText().toString();
                Toast.makeText(PasswordActivity.this, password, Toast.LENGTH_SHORT).show();
                pushPassword(password);
                Intent i = new Intent(PasswordActivity.this, TeacherMainActivity.class);
                startActivity(i);
            }
        });
    }

    public void pushPassword(String password) {
        mTakeAttendanceReference = mAttendanceReference.child(date);
        AttendanceData attdata = new AttendanceData(password, date);
        mTakeAttendanceReference.setValue(attdata);
    }

    public void getSubj() {

        mSubjStudReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Subj_StudentsData subj_studentsData = postSnapshot.getValue(Subj_StudentsData.class);

                    if (subj_studentsData.getCourseCode().equals(key)) {
                        mAttendanceReference = mSubjStudReference.child(postSnapshot.getKey()).child("Attendance");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
