package com.jilag.croesus_survey.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jilag.croesus_survey.R;
import com.jilag.croesus_survey.activities.Main2Activity;
import com.jilag.croesus_survey.models.Surveys;

import java.util.ArrayList;

public class SectionListDataAdapter_Survey extends RecyclerView.Adapter<SectionListDataAdapter_Survey.SingleItemRowHolder> {

    private ArrayList<Surveys> itemsList;
    private Context mContext;

    public SectionListDataAdapter_Survey(Context context, ArrayList<Surveys> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card_survey, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Surveys singleItem = itemsList.get(i);

        holder.text1.setText(singleItem.getName());

        String desc = singleItem.getDescription();

        if (desc.length() > 45)
        {
            desc = desc.substring(0,45) + "..";
        }

        holder.text2.setText(desc);
        holder.text3.setText(String.valueOf(singleItem.getId()));
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView text1;
        protected TextView text2;
        protected TextView text3;


        public SingleItemRowHolder(View view) {
            super(view);

            this.text1 = (TextView) view.findViewById(R.id.tvtext1);
            this.text2 = (TextView) view.findViewById(R.id.tvtext2);
            this.text3 = (TextView) view.findViewById(R.id.tvtext3);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String surv_id = text3.getText().toString();
                    String surv_name = text1.getText().toString();
                    String surv_desc = text2.getText().toString();

                    Bundle survey = new Bundle();
                    survey.putString("surv_id", surv_id);
                    survey.putString("surv_name", surv_name);
                    survey.putString("surv_desc", surv_desc);

                    Intent i = null;
                    i = new Intent(v.getContext(), Main2Activity.class);
                    i.putExtras(survey);
                    v.getContext().startActivity(i);
                }
            });


        }

    }

}