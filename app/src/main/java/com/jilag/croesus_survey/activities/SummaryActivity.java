package com.jilag.croesus_survey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jilag.croesus_survey.utils.AsyncFunctions;
import com.jilag.croesus_survey.utils.DBHelper;
import com.jilag.croesus_survey.R;
import com.jilag.croesus_survey.models.Answers;
import com.jilag.croesus_survey.utils.Networks;

import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    Button edit, finish;
    TextView q1,q2,q3;
    EditText ans2,ans3;
    String qstn1, qstn2, qstn3, ans_1, ans_2, ans_3,qstn_id1, qstn_id2, qstn_id3;
    RadioButton yes,no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

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

        toolbar.setTitle("Summary");

        Bundle answ = this.getIntent().getExtras();
        qstn1 = answ.getString("qstn1");
        ans_1 = answ.getString("ans_1");
        qstn2 = answ.getString("qstn2");
        ans_2 = answ.getString("ans_2");
        qstn3 = answ.getString("qstn3");
        ans_3 = answ.getString("ans_3");
        qstn_id1 = answ.getString("qstn_id1");
        qstn_id2 = answ.getString("qstn_id2");
        qstn_id3 = answ.getString("qstn_id3");

        q1 = (TextView) findViewById(R.id.tvQ1);
        q2 = (TextView) findViewById(R.id.tvQ2);
        q3 = (TextView) findViewById(R.id.tvQ3);

        ans2 = (EditText) findViewById(R.id.etQ2);
        ans3 = (EditText) findViewById(R.id.etQ3);

        q1.setText(qstn1);
        q2.setText(qstn2);
        q3.setText(qstn3);

        ans2.setText(ans_2);
        ans3.setText(ans_3);

        yes = (RadioButton) findViewById(R.id.radioYes);
        no = (RadioButton) findViewById(R.id.radioNo);

        if (ans_1.equals("Yes"))
        {
            yes.setChecked(true);
            no.setChecked(false);
        }
        else
        {
            yes.setChecked(false);
            no.setChecked(true);
        }

        edit = (Button) findViewById(R.id.btnEdit);

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        finish = (Button) findViewById(R.id.btnFinish);

        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences shared = getSharedPreferences("Croesus", MODE_PRIVATE);
                String id_num_str = (shared.getString("id_num_str", ""));
                int id_num;

                if (id_num_str.equals(""))
                    id_num = 0;
                else
                    id_num = Integer.parseInt(id_num_str);

                // save to db
                DBHelper db = new DBHelper(getApplicationContext());
                List<Answers> answers = db.getAllAnswers();
                int size = answers.size();
                if (size != 0) {
                    db.deleteAnswers();
                }

                db.addAnswer(new Answers(id_num,Integer.parseInt(qstn_id1),ans_1));
                db.addAnswer(new Answers(id_num,Integer.parseInt(qstn_id2),ans_2));
                db.addAnswer(new Answers(id_num,Integer.parseInt(qstn_id3),ans_3));

                Networks networks = new Networks(getApplicationContext());

                if (networks.isNetworkConnected()) { // if internet connection is available
                    AsyncFunctions asyncFunctions = new AsyncFunctions(getApplicationContext());

                    asyncFunctions.updateAnswers();

                }

                finish();
                Intent intent = new Intent(getApplicationContext(),SurveysActivity.class);
                startActivity(intent);
            }
        });
    }
}
