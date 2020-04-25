package com.jilag.croesus_survey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.jilag.croesus_survey.utils.DBHelper;
import com.jilag.croesus_survey.R;
import com.jilag.croesus_survey.models.Questions;
import com.jilag.croesus_survey.models.Surveys;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DBHelper db = new DBHelper(getApplicationContext());
        List<Surveys> surveys = db.getAlSurveys();
        int size = surveys.size();
        if (size != 0) {
            db.deleteSurveys();
        }

        db.addSurvey(new Surveys(1,"Mobile Banking", "How mobile banking has impacted your life"));
        db.addSurvey(new Surveys(2,"Internet Banking", "How internet banking has impacted your life"));
        db.addSurvey(new Surveys(3,"General", "How Croesus has impacted your life"));

        List<Questions> questions = db.getAllQuestions();
        int size_q = questions.size();
        if (size_q != 0) {
            db.deleteQuestions();
        }

        db.addQuestion(new Questions("Q1","Are you a registered Mobile Banking User?",1));
        db.addQuestion(new Questions("Q2","In one word describe your experience.",1));
        db.addQuestion(new Questions("Q3","How old are you?",1));

        db.addQuestion(new Questions("Q1","Are you a registered Internet Banking User?",2));
        db.addQuestion(new Questions("Q2","In one word describe your experience.",2));
        db.addQuestion(new Questions("Q3","How old are you?",2));

        db.addQuestion(new Questions("Q1","Have you registered for any Croesus CSR event?",3));
        db.addQuestion(new Questions("Q2","How did you hear of it?",3));
        db.addQuestion(new Questions("Q3","How old are you?",3));

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(3000);

                    SharedPreferences shared = getSharedPreferences("Croesus", MODE_PRIVATE);
                    String first = (shared.getString("firstTime", ""));

                    if(first.equals(""))
                    {
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("firstTime", "1");
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(),OnBoardActivity.class);
                        startActivity(intent);

                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }

                    finish();

                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                super.run();
            }
        };
        thread.start();
    }
}
