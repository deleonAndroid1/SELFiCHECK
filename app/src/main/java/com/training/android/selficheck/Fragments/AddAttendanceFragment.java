package com.training.android.selficheck.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.training.android.selficheck.Datas.AttendanceData;
import com.training.android.selficheck.Datas.StudentsAttendanceClass;
import com.training.android.selficheck.Datas.Subj_StudentsData;
import com.training.android.selficheck.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Maouusama on 3/26/2017.
 */

public class AddAttendanceFragment extends DialogFragment {
    private EditText mPassword;
    private FirebaseDatabase mFirebaseDatabase;
    DateFormat df = new SimpleDateFormat("HH:mm");
    DateFormat df1 = new SimpleDateFormat("MM-dd-yyyy");
    private DatabaseReference mSubjStudReference, mAttendanceReference, mTakeAttendanceReference, SampleReference;
    private String time = df.format(Calendar.getInstance().getTime()), key;
    private String date = df1.format(Calendar.getInstance().getTime()), AttendancePassword;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


    getSubj();

    //Set Data
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.add_attendance_layout, null))
            // Add action buttons
            .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            mPassword = (EditText) getView().findViewById(R.id.etPassword);
            Toast.makeText(mPassword.getContext(), "asdfasdfaf", Toast.LENGTH_SHORT).show();
                        /*String password = String.valueOf(mPassword.getText());
                        pushPassword(password);*/
        }
    })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            AddAttendanceFragment.this.getDialog().cancel();
        }
    });
        return builder.create();
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
