package com.jilag.croesus_survey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jilag.croesus_survey.utils.DBHelper;
import com.jilag.croesus_survey.R;
import com.jilag.croesus_survey.models.Questions;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    Button complete;
    String surv_id, surv_name, surv_desc, ans_1, ans_2, ans_3;
    String []qstns;
    int []qstn_id;

    TextView q1,q2,q3;
    EditText ans2,ans3;

    RadioButton yes_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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

        Bundle survey = this.getIntent().getExtras();
        surv_id = survey.getString("surv_id");
        surv_name = survey.getString("surv_name");
        surv_desc = survey.getString("surv_desc");

        toolbar.setTitle(surv_name);

        q1 = (TextView) findViewById(R.id.tvQ1);
        q2 = (TextView) findViewById(R.id.tvQ2);
        q3 = (TextView) findViewById(R.id.tvQ3);

        ans2 = (EditText) findViewById(R.id.etQ2);
        ans3 = (EditText) findViewById(R.id.etQ3);

       // Toast.makeText(getApplicationContext(), "surv_id: " + surv_id, Toast.LENGTH_SHORT).show();

        DBHelper db = new DBHelper(getApplicationContext());
        List<Questions> questions = db.getSurveyQuestion(surv_id);
        int size = questions.size();
        if (size != 0) {
            qstns = new String [size];
            qstn_id = new int [size];

            for (int j=0; j<size; j++) {
                qstns[j] = questions.get(j).getValue();
                qstn_id[j] = questions.get(j).getId();
            }

            q1.setText(qstns[0]);
            q2.setText(qstns[1]);
            q3.setText(qstns[2]);

            // get selected radio button from radioGroup
         /*   int selectedId = radioSexGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            radioSexButton = (RadioButton) findViewById(selectedId);

            Toast.makeText(MyAndroidAppActivity.this,
                    radioSexButton.getText(), Toast.LENGTH_SHORT).show(); */

         ans_1 = "Yes"; // default radio button response

            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioBool);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // checkedId is the RadioButton selected
                   // Toast.makeText(getApplicationContext(), String.valueOf(checkedId), Toast.LENGTH_SHORT).show();
                    yes_no = (RadioButton) findViewById(checkedId);
                    Toast.makeText(getApplicationContext(), yes_no.getText(), Toast.LENGTH_SHORT).show();
                    if (yes_no.getText().equals("Yes"))
                    {
                        ans2.setEnabled(true);
                        ans_1 = "Yes";
                    }
                    else
                    {
                        ans2.setEnabled(false);
                        ans_1 = "No";
                    }

                }
            });

        }

        complete = (Button) findViewById(R.id.btnComplete);

        complete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // get answers
                ans_2 = ans2.getText().toString();
                ans_3 = ans3.getText().toString();

                Bundle answ = new Bundle();
                answ.putString("qstn1", qstns[0]);
                answ.putString("ans_1", ans_1);
                answ.putString("qstn2", qstns[1]);
                answ.putString("ans_2", ans_2);
                answ.putString("qstn3", qstns[2]);
                answ.putString("ans_3", ans_3);
                answ.putString("qstn_id1", String.valueOf(qstn_id[0]));
                answ.putString("qstn_id2", String.valueOf(qstn_id[1]));
                answ.putString("qstn_id3", String.valueOf(qstn_id[2]));

                Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
                intent.putExtras(answ);
                startActivity(intent);

            }
        });
    }
}
