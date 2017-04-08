package com.example.lilactests;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class SolvingProblemsActivity extends BaseActivity {
    private Chronometer mChronometer;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_solving_problems);

        backBtn = (Button) findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mChronometer = (Chronometer) findViewById(R.id.chronometer_view);
        mChronometer.start();
    }

    @Override
    protected void onDestroy() {
        mChronometer.stop();
        super.onDestroy();
    }
}
