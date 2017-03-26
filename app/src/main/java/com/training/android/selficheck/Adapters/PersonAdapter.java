package com.training.android.selficheck.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;
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

        final View StudentListLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.teacher_studentslist_layout, null);

        holder = new PersonAdapter.ViewHolder(StudentListLayout);

        return holder;
    }

    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder holder, int position) {

        final PersonData personData = mPerson.get(position);

        if (personData != null){
            holder.mtvId.setText(personData.getId());
            holder.mtvName.setText(personData.getName());
        }

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

        TextView mtvName, mtvId;
        ImageView mIvPicture;

        public ViewHolder(View itemView) {
            super(itemView);

            mtvName = (TextView) itemView.findViewById(R.id.tvName);
            mtvId = (TextView) itemView.findViewById(R.id.tvId);
            mIvPicture = (ImageView) itemView.findViewById(R.id.ivPicture);

        }
    }
}
