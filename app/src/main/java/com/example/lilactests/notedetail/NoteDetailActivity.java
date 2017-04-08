package com.example.lilactests.notedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lilactests.BaseActivity;
import com.example.lilactests.R;
import com.example.lilactests.addeditnote.EditNoteActivity;
import com.example.lilactests.model.domain.Note;
import com.example.lilactests.utils.TimeUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The Activity to show the note detail.
 */
public class NoteDetailActivity extends BaseActivity implements NoteDetailContract.View {

    private static final String TAG = "NoteDetailActivity";
    private static final int EDIT_NOTE_ITEM = 0;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_title)
    TextView mEtTitle;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.cb_alarm)
    CheckBox mCbAlarm;
    @BindView(R.id.et_content)
    TextView mEtContent;
    @BindView(R.id.edit)
    FloatingActionButton mEdit;
    @BindView(R.id.load)
    ProgressBar mLoadProgessBar;
    private Note mNoteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        updateViews(mNoteItem);
    }

    public void setViews(Note noteItem) {
        mEtTitle.setText(noteItem.title);
        mEtContent.setText(noteItem.content);
        if (noteItem.hasAlarm) {
            mCbAlarm.setChecked(true);
            Date date = noteItem.date;

            String dateStr = TimeUtils.formatDate(date);
            mTvDate.setText(dateStr);
            mTvDate.setVisibility(View.VISIBLE);

            String timeStr = TimeUtils.formatTime(date);
            mTvTime.setText(timeStr);
            mTvTime.setVisibility(View.VISIBLE);
        } else {
            mCbAlarm.setChecked(false);
            mTvDate.setVisibility(View.INVISIBLE);
            mTvTime.setVisibility(View.INVISIBLE);
        }
        dismissProcess();
    }

    private void initDate() {
        mNoteItem = (Note) getIntent().getSerializableExtra("note");
        new NoteDetailPresenter(this, this);
    }

    @OnClick(R.id.edit)
    public void onClick() {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("note", mNoteItem);
        startActivityForResult(intent, EDIT_NOTE_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case EditNoteActivity.UPDATE_SUCCESS:
                setResult(EditNoteActivity.UPDATE_SUCCESS);
                showProcess();
                Note edit = (Note) data.getSerializableExtra("edit");
                setViews(edit);
                break;
            case EditNoteActivity.UPDATE_FAILED:
                break;
        }
    }

    @Override
    public void showProcess() {
        mLoadProgessBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProcess() {
        mLoadProgessBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateViews(Note noteItem) {
        setViews(noteItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
