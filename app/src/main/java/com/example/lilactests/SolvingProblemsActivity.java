package com.example.lilactests;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.lilactests.adapter.ProblemPagerAdapter;
import com.example.lilactests.app.LilacTestsApp;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SolvingProblemsActivity extends BaseActivity {
    public static ProblemPagerAdapter adapter;
    public Chronometer mChronometer;
    private Button backBtn;
    private TextView textTitle;
    private ViewPager viewPager;

    private String DB_NAME = "question.db";
    private String DB_PATH = LilacTestsApp.getContext().getFilesDir().getPath()+"/databases/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_solving_problems);

        initFile();
        backBtn = (Button) findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mChronometer = (Chronometer) findViewById(R.id.chronometer_view);
        mChronometer.start();
        textTitle = (TextView) findViewById(R.id.tilte_text);

        final int amount = getIntent().getIntExtra("problem amount", 1);

        adapter = new ProblemPagerAdapter(
                getSupportFragmentManager(), amount);

        viewPager = (ViewPager) findViewById(R.id.problem_pager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                textTitle.setText("第"+(position+1)+"题/总"+amount+"道题");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setPageItem(int position) {
        viewPager.setCurrentItem(position);
    }

    //对数据库进行操作，将数据库拷贝到相应目录
    private void initFile() {

        //check whether Database has been copied to the right path.
        if (!new File(DB_PATH + DB_NAME).exists()) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            // copy
            try {
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        mChronometer.stop();
        super.onDestroy();
    }
}
