package com.jilag.croesus_survey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.jilag.croesus_survey.utils.DBHelper;
import com.jilag.croesus_survey.R;
import com.jilag.croesus_survey.adapters.RecyclerViewDataAdapter_Survey;
import com.jilag.croesus_survey.models.SectionDataModel_Survey;
import com.jilag.croesus_survey.models.Surveys;

import java.util.ArrayList;
import java.util.List;

public class SurveysActivity extends AppCompatActivity {

    ArrayList<SectionDataModel_Survey> surveyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitle("Available Surveys");

        surveyData = new ArrayList<SectionDataModel_Survey>();

        getSurveys();

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter_Survey adapter = new RecyclerViewDataAdapter_Survey(getApplicationContext(), surveyData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);
    }

    public void getSurveys()
    {
        DBHelper db = new DBHelper(getApplicationContext());
        List<Surveys> survey = null;

        SectionDataModel_Survey dm = new SectionDataModel_Survey();

        ArrayList<Surveys> singleSurv = new ArrayList<Surveys>();

        survey = db.getAlSurveys();

        int size = survey.size();
        if (size != 0) {
            for (int j = 0; j < size; j++) {
                singleSurv.add(new Surveys(survey.get(j).getId(),survey.get(j).getName(),survey.get(j).getDescription()));
            }

            dm.setAllItemsInSection(singleSurv);

            surveyData.add(dm);
        }
    }
}
