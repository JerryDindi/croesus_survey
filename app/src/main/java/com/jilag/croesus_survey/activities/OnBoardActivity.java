package com.jilag.croesus_survey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jilag.croesus_survey.R;
import com.jilag.croesus_survey.adapters.SliderAdapter;

public class OnBoardActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private Button btnBack, btnNext;

    private int currPage;

    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        mSlideViewPager = (ViewPager) findViewById(R.id.viewPagerBG);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (currPage == 2)
                {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                    finish();
                }

                else if (currPage < 2)
                {
                    mSlideViewPager.setCurrentItem(currPage + 1);
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(currPage - 1);
            }
        });

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            currPage = position;
            if(position == 0)
            {
                btnBack.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);
                btnNext.setEnabled(true);

                btnNext.setText("Next");
                btnBack.setText("");
            }
            else if (position == 2) // last slide
            {
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setEnabled(true);

                btnNext.setText("Start");
                btnBack.setText("Back");
            }
            else
            {
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setEnabled(true);

                btnNext.setText("Next");
                btnBack.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
