package com.jilag.croesus_survey.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jilag.croesus_survey.models.Answers;
import com.jilag.croesus_survey.models.Questions;
import com.jilag.croesus_survey.models.Surveys;
import com.jilag.croesus_survey.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class AsyncFunctions {

    Context context;

    String CallURL = "https://croesus.com/";

    public AsyncFunctions(Context context)
    {
        this.context = context;
    }

    public void getSurveys()
    {
        new SurveysAsync().execute();
    }

    public void getQuestions()
    {
        new QuestionsAsync().execute();
    }

    public void updateUsers()
    {
        new updateUsersAsync().execute();
    }

    public void updateAnswers()
    {
        new updateAnswersAsync().execute();
    }

    private class SurveysAsync extends AsyncTask<String, Void, String> {

        public SurveysAsync() {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String resp = "UNDEFINED";

            try {
                URL url = new URL(CallURL + "Surveys");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                resp = builder.toString();

                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {

            if (!resp.equals("UNDEFINED")) {

                JSONArray respArr = null;
                try {
                    JSONObject response = new JSONObject(resp);
                    resp = response.getString("SurveysResult");
                    respArr = new JSONArray(resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (resp.length() != 0) {
                    final DBHelper db = new DBHelper(context);

                    int[] id = new int[respArr.length()];
                    String[] name = new String[respArr.length()];
                    String[] descr = new String[respArr.length()];

                    List<Surveys> surveys = db.getAlSurveys();
                    int size = surveys.size();
                    if (size != 0) {
                        db.deleteSurveys();
                    }

                    for (int i = 0; i < respArr.length(); i++) {
                        try {
                            id[i] = respArr.getJSONObject(i).getInt("SurveyID");
                            name[i] = respArr.getJSONObject(i).getString("SurveyName");
                            descr[i] = respArr.getJSONObject(i).getString("SurveyDescription");

                            db.addSurvey(new Surveys(id[i],name[i], descr[i]));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private class QuestionsAsync extends AsyncTask<String, Void, String> {

        public QuestionsAsync() {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String resp = "UNDEFINED";

            try {
                URL url = new URL(CallURL + "Questions");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                resp = builder.toString();

                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {

            if (!resp.equals("UNDEFINED")) {

                JSONArray respArr = null;
                try {
                    JSONObject response = new JSONObject(resp);
                    resp = response.getString("QuestionsResult");
                    respArr = new JSONArray(resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (resp.length() != 0) {
                    final DBHelper db = new DBHelper(context);

                    int[] id = new int[respArr.length()];
                    String[] name = new String[respArr.length()];
                    String[] value = new String[respArr.length()];
                    int[] survey_id = new int[respArr.length()];

                    List<Surveys> surveys = db.getAlSurveys();
                    int size = surveys.size();
                    if (size != 0) {
                        db.deleteSurveys();
                    }

                    for (int i = 0; i < respArr.length(); i++) {
                        try {
                            id[i] = respArr.getJSONObject(i).getInt("QuestionID");
                            name[i] = respArr.getJSONObject(i).getString("QuestionName");
                            value[i] = respArr.getJSONObject(i).getString("QuestionValue");
                            survey_id[i] = respArr.getJSONObject(i).getInt("SurveyID");

                            db.addQuestion(new Questions(id[i],name[i], value[i], survey_id[i]));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private class updateUsersAsync extends AsyncTask<String, Void, String> {

        public updateUsersAsync() {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String resp = "UNDEFINED";
            String jsonStr = "";
            JSONArray usersArr;
            try {

                DBHelper db = new DBHelper(context);
                List<Users> user = db.getAllUsers();
                int size = user.size();

                if (size > 0)
                {
                    for (int j=0; j<size; j++) {

                        if (j == (size-1)) // eliminate (,) for the last value
                        {
                            jsonStr += "{ \"userID\": " + user.get(j).getId() + ", "
                                    + "\"userFname\": \"" + user.get(j).getFname() + "\","
                                    + "\"userLname\": \"" + user.get(j).getLname() + "\","
                                    + "\"userIDnum\": " + user.get(j).getId_num() + "}";
                        }
                        else
                        {
                            jsonStr += "{ \"userID\": " + user.get(j).getId() + ", "
                                    + "\"userFname\": \"" + user.get(j).getFname() + "\","
                                    + "\"userLname\": \"" + user.get(j).getLname() + "\","
                                    + "\"userIDnum\": " + user.get(j).getId_num() + "},";
                        }

                    }

                    usersArr = new JSONArray(jsonStr);

                    URL url = new URL(CallURL + "Users");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                    out.write(usersArr.toString());
                    out.flush();
                    out.close();

                    int res = urlConnection.getResponseCode();

                    InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();

                    String inputString;
                    while ((inputString = bufferedReader.readLine()) != null) {
                        builder.append(inputString);
                    }

                    resp = builder.toString();

                    urlConnection.disconnect();

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {

            if (!resp.equals("UNDEFINED")) {

                try {
                    JSONObject response = new JSONObject(resp);
                    resp = response.getString("UsersResult");
                    //
                    if (resp.equals("Success"))
                    {
                        // success
                    }
                    else
                    {
                        // update failed
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class updateAnswersAsync extends AsyncTask<String, Void, String> {

        public updateAnswersAsync() {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String resp = "UNDEFINED";
            String jsonStr = "";
            JSONArray usersArr;
            try {

                DBHelper db = new DBHelper(context);
                List<Answers> answers = db.getAllAnswers();
                int size = answers.size();

                if (size > 0)
                {
                    for (int j=0; j<size; j++) {

                        if (j == (size-1)) // eliminate (,) for the last value
                        {
                            jsonStr += "{ \"ansID\": " + answers.get(j).getId() + ", "
                                    + "\"ansUserID\": " + answers.get(j).getUser_id() + ","
                                    + "\"ansQstnID\": " + answers.get(j).getQstn_id() + ","
                                    + "\"answer\": \"" + answers.get(j).getAnswer() + "\" }";
                        }
                        else
                        {
                            jsonStr += "{ \"ansID\": " + answers.get(j).getId() + ", "
                                    + "\"ansUserID\": " + answers.get(j).getUser_id() + ","
                                    + "\"ansQstnID\": " + answers.get(j).getQstn_id() + ","
                                    + "\"answer\": \"" + answers.get(j).getAnswer() + "\" }, ";
                        }

                    }

                    usersArr = new JSONArray(jsonStr);

                    URL url = new URL(CallURL + "Answers");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                    out.write(usersArr.toString());
                    out.flush();
                    out.close();

                    int res = urlConnection.getResponseCode();

                    InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();

                    String inputString;
                    while ((inputString = bufferedReader.readLine()) != null) {
                        builder.append(inputString);
                    }

                    resp = builder.toString();

                    urlConnection.disconnect();

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {

            if (!resp.equals("UNDEFINED")) {

                try {
                    JSONObject response = new JSONObject(resp);
                    resp = response.getString("AnswersResult");
                    //
                    if (resp.equals("Success"))
                    {
                        // success
                    }
                    else
                    {
                        // update failed
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
