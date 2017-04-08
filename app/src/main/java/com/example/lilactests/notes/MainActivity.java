package com.example.lilactests.notes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lilactests.BaseActivity;
import com.example.lilactests.R;
import com.example.lilactests.addeditnote.AddNoteActivity;
import com.example.lilactests.addeditnote.EditNoteActivity;
import com.example.lilactests.config.DividerItemDecoration;
import com.example.lilactests.config.MultiSelector;
import com.example.lilactests.model.domain.Note;
import com.example.lilactests.notes.adapter.ChoiceCountEvent;
import com.example.lilactests.notes.adapter.ChoiceModeEvent;
import com.example.lilactests.notes.adapter.EnterActivityEvent;
import com.example.lilactests.notes.adapter.NotesAdapter;
import com.example.lilactests.utils.RxBus;
import com.example.lilactests.utils.ToastUtils;
import com.example.lilactests.view.dialogfragment.AboutFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * The Main Interface to show the notes list.
 */
public class MainActivity extends BaseActivity implements NotesContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";
    public static final int ADD_NOTE_EVENT = 0;
    public static final int EDIT_NOTE_EVENT = 1;
    private static final String ABOUT_FRAGMENT = "ABOUT_FRAGMENT";
    private Toolbar mToolbar;
    private RecyclerView mNotesViews;
    private SwipeRefreshLayout mRefreshLayout;
    private FloatingActionButton mAddNoteButton;

    private NotesAdapter mNotesAdapter;
    private long[] mHits = new long[2];
    private ActionMode.Callback mMultiMode;
    private MultiSelector mSelector;
    private NotesPresenter mNotesPresenter;
    private Subscription rxSubscription;
    private ActionMode mActionMode;
    private Subscription mEnterSubscribe;
    private Subscription mTitleSubscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNotesViews = (RecyclerView) findViewById(R.id.content_recylerview);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        mAddNoteButton = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.all_notes);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager
                (MainActivity.this, LinearLayoutManager.VERTICAL, false);

        mNotesPresenter = new NotesPresenter(this, this);
        mNotesPresenter.showNotesList();
        mNotesViews.setLayoutManager(mLinearLayoutManager);
        mNotesViews.addItemDecoration(
                new DividerItemDecoration(this, null));
        mRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.green_cycle,
                R.color.orange_cycle,
                R.color.red_cycle);
        setListener();
    }


    private void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        rxSubscription = RxBus.getDefault()
                .toObserverable(ChoiceModeEvent.class)
                .subscribe(
                        new Action1<ChoiceModeEvent>() {
                            @Override
                            public void call(ChoiceModeEvent choiceModeEvent) {
                                if (choiceModeEvent.isChoiceMode()) {
                                    mActionMode = startActionMode(choiceModeEvent.getCallback());
                                    setStatusBarColor();
                                } else {
                                    mActionMode.finish();
                                }
                            }
                        }
                );
        mEnterSubscribe = RxBus.getDefault()
                .toObserverable(EnterActivityEvent.class)
                .subscribe(new Action1<EnterActivityEvent>() {
                    @Override
                    public void call(EnterActivityEvent enterActivityEvent) {
                        startActivity(enterActivityEvent.getIntent());
                    }
                });
        mTitleSubscribe = RxBus.getDefault().
                toObserverable(ChoiceCountEvent.class)
                .subscribe(new Action1<ChoiceCountEvent>() {
                    @Override
                    public void call(ChoiceCountEvent event) {
                        mActionMode.setTitle(event.getTitle());
                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
        if (!mEnterSubscribe.isUnsubscribed()) {
            mEnterSubscribe.unsubscribe();
        }
        if (!mTitleSubscribe.isUnsubscribed()) {
            mTitleSubscribe.unsubscribe();
        }
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                AboutFragment aboutFragment = new AboutFragment();
                aboutFragment.show(getFragmentManager(), ABOUT_FRAGMENT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showNotes(List<Note> noteList) {
        mNotesAdapter = new NotesAdapter(MainActivity.this, noteList, new MultiSelector());
        Log.w("Pointer Case:", ""+mNotesAdapter);
//        mSelector = mNotesAdapter.getSelector();
        mNotesViews.setAdapter(mNotesAdapter);
    }

    @Override
    public void refreshNotes(List<Note> noteList) {
        mNotesAdapter.setNotesList(noteList);
        mNotesAdapter.notifyDataSetChanged();
    }


    @Override
    public void showLoading() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void cancelLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mNotesPresenter.refreshNotes();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent enterNewActivity = new Intent(this, AddNoteActivity.class);
        startActivityForResult(enterNewActivity, ADD_NOTE_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_NOTE_EVENT:
                if (resultCode == AddNoteActivity.ADD_SUCCESS) {
                    mNotesPresenter.showNotesList();
                }
                break;

            case EDIT_NOTE_EVENT:
                if (resultCode == EditNoteActivity.UPDATE_SUCCESS) {
                    mNotesPresenter.showNotesList();
                }
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                //Google Multi Hit logic
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();

                if (mHits[0] >= (SystemClock.uptimeMillis() - 2000)) {
                    //between (SystemClock.uptimeMillis()- 2000) And SystemClock.uptimeMillis()
                    finish();
                } else {
                    ToastUtils.showToast(this, getString(R.string.double_click_exit));
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
