package com.training.android.selficheck.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.training.android.selficheck.Datas.PersonData;
import com.training.android.selficheck.R;
import com.training.android.selficheck.Subject_details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maouusama on 3/27/2017.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private Context mContext;
    private PersonAdapter.ViewHolder holder;
    private List<PersonData> mPerson;

    public PersonAdapter(Context applicationContext, ArrayList<PersonData> mData) {
        mContext = applicationContext;
        mPerson = mData;
    }

    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        final View SubjectListsLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.student_subject_layout, null);

        holder = new PersonAdapter.ViewHolder(SubjectListsLayout);

        return holder;
    }

    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder holder, int position) {

        final PersonData personData = mPerson.get(position);

        if (personData != null){
            holder.mtvName.setText(personData.getName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, Subject_details.class);
                i.putExtra("PersonName", personData.getName());
                i.putExtra("PersonId", personData.getId());
                i.putExtra("PersonEmail", personData.getEmail());
                i.putExtra("PersonRole", personData.getRole());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPerson.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mtvName;
        ImageView mIvPicture;

        public ViewHolder(View itemView) {
            super(itemView);

            mtvName = (TextView) itemView.findViewById(R.id.tvName);
            mIvPicture = (ImageView) itemView.findViewById(R.id.ivPicture);

        }
    }
}
