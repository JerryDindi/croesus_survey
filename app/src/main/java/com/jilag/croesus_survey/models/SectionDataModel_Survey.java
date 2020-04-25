package com.jilag.croesus_survey.models;

import com.jilag.croesus_survey.models.Surveys;

import java.util.ArrayList;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class SectionDataModel_Survey {



    private String headerTitle;
    private ArrayList<Surveys> allItemsInSection;


    public SectionDataModel_Survey() {

    }
    public SectionDataModel_Survey(String headerTitle, ArrayList<Surveys> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Surveys> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Surveys> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }
}
