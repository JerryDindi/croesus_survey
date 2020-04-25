package com.jilag.croesus_survey.models;

public class Answers {

    int id;
    int user_id;
    int qstn_id;
    String answer;

    public Answers() {
    }

    public Answers(int id, int user_id, int qstn_id, String answer) {
        this.id = id;
        this.user_id = user_id;
        this.qstn_id = qstn_id;
        this.answer = answer;
    }

    public Answers(int user_id, int qstn_id, String answer) {
        this.user_id = user_id;
        this.qstn_id = qstn_id;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQstn_id() {
        return qstn_id;
    }

    public void setQstn_id(int qstn_id) {
        this.qstn_id = qstn_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answers{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", qstn_id=" + qstn_id +
                ", answer='" + answer + '\'' +
                '}';
    }
}
