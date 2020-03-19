package com.example.lilactests.view.layoutfragment;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lilactests.R;
import com.example.lilactests.addeditnote.AddNoteActivity;
import com.example.lilactests.addeditnote.EditNoteActivity;
import com.example.lilactests.config.DividerItemDecoration;
import com.example.lilactests.config.MultiSelector;
import com.example.lilactests.model.domain.Note;
import com.example.lilactests.notes.NotesContract;
import com.example.lilactests.notes.NotesPresenter;
import com.example.lilactests.notes.adapter.ChoiceCountEvent;
import com.example.lilactests.notes.adapter.ChoiceModeEvent;
import com.example.lilactests.notes.adapter.EnterActivityEvent;
import com.example.lilactests.notes.adapter.NotesAdapter;
import com.example.lilactests.utils.RxBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 *  Created by Eventory on 2017/2/12 0012.
 */

public class NotesPageFragment extends Fragment implements NotesContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_POSITION = "position";
    private static int nowPosition;
    private static final String TAG = "NotesPageFragment";
    public static final int ADD_NOTE_EVENT = 0;
    public static final int EDIT_NOTE_EVENT = 1;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
    private View mView;



    public NotesPageFragment() {
        super();
    }

    public static NotesPageFragment newInstance(int position) {
        NotesPageFragment mainPageFragment = new NotesPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        mainPageFragment.setArguments(bundle);
        return mainPageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nowPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.page_notes, container, false);
        mAddNoteButton = (FloatingActionButton) mView.findViewById(R.id.fab);
        mAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enterNewActivity = new Intent(getActivity(), AddNoteActivity.class);
                startActivityForResult(enterNewActivity, ADD_NOTE_EVENT);
            }
        });
        ButterKnife.bind(mView);
        init();

        return mView;
    }

    private void init() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager
                (getActivity(), LinearLayoutManager.VERTICAL, false);
        mNotesViews = (RecyclerView) mView.findViewById(R.id.content_recylerview);
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refreshlayout);

        mNotesPresenter = new NotesPresenter(getActivity(), this);
        mNotesPresenter.showNotesList();
        mNotesViews.setLayoutManager(mLinearLayoutManager);
        mNotesViews.addItemDecoration(new DividerItemDecoration(getActivity(), null));
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
                                    mActionMode = getActivity().startActionMode(choiceModeEvent.getCallback());
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
    public void onDestroy() {
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


    @Override
    public void showNotes(List<Note> noteList) {
        mNotesAdapter = new NotesAdapter(getActivity(), noteList, new MultiSelector());
        Log.w(TAG,":mNotesAdapter = "+mNotesAdapter);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                //Google Multi Hit logic
//                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
//                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
//
//                if (mHits[0] >= (SystemClock.uptimeMillis() - 2000)) {
//                    //between (SystemClock.uptimeMillis()- 2000) And SystemClock.uptimeMillis()
//                    finish();
//                } else {
//                    ToastUtils.showToast(this, getString(R.string.double_click_exit));
//                }
//                return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
