package com.training.android.selficheck.TeacherSide;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.training.android.selficheck.Adapters.PersonAdapter;
import com.training.android.selficheck.Adapters.SubjectsAdapter;
import com.training.android.selficheck.Datas.AttendanceData;
import com.training.android.selficheck.Datas.PersonData;
import com.training.android.selficheck.Datas.StudentsAttendanceClass;
import com.training.android.selficheck.Datas.Subj_StudentsData;
import com.training.android.selficheck.Fragments.AddAttendanceFragment;
import com.training.android.selficheck.R;
import com.training.android.selficheck.Subject_details;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static com.training.android.selficheck.R.id.btnAttendance;

public class TeacherSubjectActivity extends AppCompatActivity {

    ArrayList<PersonData> personArrayList = new ArrayList<>();
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    DateFormat df = new SimpleDateFormat("HH:mm");
    DateFormat df1 = new SimpleDateFormat("MM-dd-yyyy");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mSubjStudReference, mAttendanceReference, mCheckAttendanceReference, SampleReference;
    private Button mAddAttendance;
    private RecyclerView mrvStudentList;
    private PersonAdapter mPersonAdapter;
    private TextView mTvSubjName, mTvSubjSched, mTvDate, mTvCurrentTime;
    private String uri;
    private String time = df.format(Calendar.getInstance().getTime()), key;
    private String date = df1.format(Calendar.getInstance().getTime()), AttendancePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_subject);

        //initializations
        mrvStudentList = (RecyclerView) findViewById(R.id.rvStudentList);
        mTvSubjName = (TextView) findViewById(R.id.tvCourseName);
        mTvSubjSched = (TextView) findViewById(R.id.tvSched);
        mTvDate = (TextView) findViewById(R.id.tvDate);
        mTvCurrentTime = (TextView) findViewById(R.id.tvCurrentTIme);

        //Database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mSubjStudReference = mFirebaseDatabase.getReference().child("Subj_students");
        SampleReference = mFirebaseDatabase.getReference().child("Sample");

        //Call Attendance function to access Database
        getSubj();

        //Set Datas
        Intent i = getIntent();
        Bundle data = i.getExtras();
        key = data.getString("CourseCode");
        mTvSubjName.setText(data.getString("CourseName"));
        mTvSubjSched.setText(data.getString("CourseSchedule"));
        mTvDate.setText(date);
//        mTvCurrentTime.setText(time);
/*
        mbtnTakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAttendance();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        });*/

    }

    public void getSubj() {

        mSubjStudReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Subj_StudentsData subj_studentsData = postSnapshot.getValue(Subj_StudentsData.class);

                    if (subj_studentsData.getCourseCode().equals(key)) {
                        mAttendanceReference = mSubjStudReference.child(postSnapshot.getKey()).child("Attendance");
                        getAttendance();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getAttendance() {

        mAttendanceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AttendanceData attendanceData = postSnapshot.getValue(AttendanceData.class);

                    if (attendanceData.getDate().equals(date)) {
                        mCheckAttendanceReference = mAttendanceReference.child(date).child("StudentsAttendance");
                        getStudents();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getStudents() {

        mCheckAttendanceReference.addValueEventListener(new ValueEventListener() {
            @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                PersonData personData = postSnapshot.getValue(PersonData.class);
                personArrayList.add(personData);
            }
                mPersonAdapter = new PersonAdapter(getApplicationContext(), personArrayList);
                mrvStudentList.setAdapter(mPersonAdapter);
                mrvStudentList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_attendance, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add_attendance_menu:
                Intent j = getIntent();
                Bundle data = j.getExtras();
                String course = data.getString("CourseCode");
                Intent i = new Intent(TeacherSubjectActivity.this, PasswordActivity.class);
                i.putExtra("CourseCode",course);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
