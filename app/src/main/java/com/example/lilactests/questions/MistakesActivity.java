package com.example.lilactests.questions;

import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.ActionMode;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.lilactests.BaseActivity;
import com.example.lilactests.R;
import com.example.lilactests.config.DividerItemDecoration;
import com.example.lilactests.config.MultiSelector;
import com.example.lilactests.model.domain.Question;
import com.example.lilactests.questions.adapter.ChoiceCountEvent;
import com.example.lilactests.questions.adapter.ChoiceModeEvent;
import com.example.lilactests.questions.adapter.EnterActivityEvent;
import com.example.lilactests.questions.adapter.QuestionsAdapter;
import com.example.lilactests.utils.RxBus;

import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 * The Main Interface to show the questions list.
 */
public class MistakesActivity extends BaseActivity implements QuestionsContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MistakesActivity";
    public static final int ADD_NOTE_EVENT = 0;
    public static final int EDIT_NOTE_EVENT = 1;
    private Toolbar mToolbar;
    private ActionBar actionBar;
    private RecyclerView mQuestionsViews;
    private SwipeRefreshLayout mRefreshLayout;

    private QuestionsAdapter mQuestionsAdapter;
    private long[] mHits = new long[2];
    private ActionMode.Callback mMultiMode;
    private MultiSelector mSelector;
    private QuestionsPresenter mQuestionsPresenter;
    private Subscription rxSubscription;
    private ActionMode mActionMode;
    private Subscription mEnterSubscribe;
    private Subscription mTitleSubscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistakes);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mQuestionsViews = (RecyclerView) findViewById(R.id.content_recylerview);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.all_questions);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager
                (MistakesActivity.this, LinearLayoutManager.VERTICAL, false);


        mQuestionsPresenter = new QuestionsPresenter(this, this);
        mQuestionsPresenter.showQuestionsList();
        mQuestionsViews.setLayoutManager(mLinearLayoutManager);
        mQuestionsViews.addItemDecoration(
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showQuestions(List<Question> questionList) {
        mQuestionsAdapter = new QuestionsAdapter(MistakesActivity.this, questionList, new MultiSelector());
//        Log.w("Pointer Case:", ""+mQuestionsAdapter);
//        mSelector = mQuestionsAdapter.getSelector();
        mQuestionsViews.setAdapter(mQuestionsAdapter);
    }

    @Override
    public void refreshQuestions(List<Question> questionList) {
        mQuestionsAdapter.setQuestionsList(questionList);
        mQuestionsAdapter.notifyDataSetChanged();
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
        mQuestionsPresenter.refreshQuestions();
    }


}
