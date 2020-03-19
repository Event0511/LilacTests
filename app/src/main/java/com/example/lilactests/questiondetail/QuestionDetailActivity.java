package com.example.lilactests.questiondetail;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lilactests.BaseActivity;
import com.example.lilactests.R;
import com.example.lilactests.model.Question.QuestionModel;
import com.example.lilactests.model.domain.Question;
import com.example.lilactests.utils.TimeUtils;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The Activity to show the question detail.
 */
public class QuestionDetailActivity extends BaseActivity implements QuestionDetailContract.View {

    private static final String TAG = "QuestionDetailActivity";
    private static final int EDIT_QUESTION_ITEM = 0;
    private Question mQuestionItem;
    private List<Question> mQuestionList;
    private QuestionModel mQuestionModel;
    private static int current;
    private static int amount;
    public static boolean analyseMode = false;

    private TextView [] mAnswer = new TextView[4];
    private MaterialRadioButton [] button = new MaterialRadioButton[4];

    @BindView(R.id.option_group)
    MaterialRadioGroup mMaterialRadioGroup;
    @BindView(R.id.btn_prev)
    Button btn_prev;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.btn_result)
    Button btn_result;
    @BindView(R.id.question_title_text)
    TextView mTitleText;
    @BindView(R.id.problem)
    TextView mSubject;
    @BindView(R.id.tv_result)
    TextView mExplanation;
    @BindView(R.id.load)
    ProgressBar mLoadProgressBar;
    @BindView(R.id.floating_buttons_menu)
    FloatingActionsMenu mFloatingActionsMenu;
    @BindView(R.id.mistake_operate_button)
    com.getbase.floatingactionbutton.FloatingActionButton mistakeOperateButton;
    @BindView(R.id.favorite_operate_button)
    com.getbase.floatingactionbutton.FloatingActionButton favoriteOperateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_questions_detail);
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    @OnClick(R.id.back_button)
    public void onBack() {
        finish();
    }

    @OnClick(R.id.btn_prev)
    public void onPrev() {
        if (current > 0) {
            current -= 1;
            mQuestionList.get(current).reviewDate = TimeUtils.getNowDateTime();
            mQuestionItem = mQuestionList.get(current);
            mMaterialRadioGroup.clearCheck();
            mQuestionItem.selectedAnswer = -1;
            updateViews(mQuestionItem);
        }
    }

    @OnClick(R.id.btn_next)
    public void onNext() {
        if (current < amount-1) {
            current += 1;
            mQuestionList.get(current).reviewDate = TimeUtils.getNowDateTime();
            mQuestionItem = mQuestionList.get(current);
            mMaterialRadioGroup.clearCheck();
            mQuestionItem.selectedAnswer = -1;
            updateViews(mQuestionItem);
        }
    }

    @OnClick(R.id.btn_result)
    public void onResult() {
        if (analyseMode) {
            analyseMode = false;
            btn_result.setText("查看解析");
            updateViews(mQuestionItem);
        } else {
            analyseMode = true;
            btn_result.setText("关闭解析");
            updateViews(mQuestionItem);
        }
        mQuestionList.get(current).reviewDate = TimeUtils.getNowDateTime();
    }

    private void initView() {
        mExplanation.setVisibility(View.INVISIBLE);
        button[0] = (MaterialRadioButton) findViewById(R.id.material_radio_button_a);
        button[1] = (MaterialRadioButton) findViewById(R.id.material_radio_button_b);
        button[2] = (MaterialRadioButton) findViewById(R.id.material_radio_button_c);
        button[3] = (MaterialRadioButton) findViewById(R.id.material_radio_button_d);
        mAnswer[0] = (TextView) findViewById(R.id.textView_optionA);
        mAnswer[1] = (TextView) findViewById(R.id.textView_optionB);
        mAnswer[2] = (TextView) findViewById(R.id.textView_optionC);
        mAnswer[3] = (TextView) findViewById(R.id.textView_optionD);
        updateViews(mQuestionItem);
        if (mQuestionItem.isMistake) {
            mistakeOperateButton.setIcon(R.drawable.delate_button);
            mistakeOperateButton.setTitle(getResources().getString(R.string.label_delete_mistake));
        } else {
            mistakeOperateButton.setIcon(R.drawable.add_button);
            mistakeOperateButton.setTitle(getResources().getString(R.string.label_add_mistake));
        }


        if (mQuestionItem.isFavorite) {
            favoriteOperateButton.setIcon(R.drawable.favorite_button_on);
            favoriteOperateButton.setTitle(getResources().getString(R.string.label_delete_favorite));
        } else {
            favoriteOperateButton.setIcon(R.drawable.favorite_button_off);
            favoriteOperateButton.setTitle(getResources().getString(R.string.label_add_favorite));
        }
    }

    public void setViews(Question questionItem) {
        mTitleText.setText("错题详情");
        mSubject.setText(questionItem.subject);
        mAnswer[0].setText(questionItem.answerA);
        mAnswer[1].setText(questionItem.answerB);
        mAnswer[2].setText(questionItem.answerC);
        mAnswer[3].setText(questionItem.answerD);
        mExplanation.setText(questionItem.explanation);
        dismissProcess();
        if (current == 0) {
            btn_prev.setVisibility(View.INVISIBLE);
            btn_next.setVisibility(View.VISIBLE);
        } else if (current == amount-1) {
            btn_prev.setVisibility(View.VISIBLE);
            btn_next.setVisibility(View.INVISIBLE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
            btn_next.setVisibility(View.VISIBLE);
        }
        if (analyseMode) {
            mExplanation.setVisibility(View.VISIBLE);
            mMaterialRadioGroup.setClickable(false);
            for (MaterialRadioButton btn : button) {
                btn.setButtonRes(R.drawable.letter_radio_button);
            }
            if (mQuestionItem.selectedAnswer != -1) {
                button[mQuestionItem.selectedAnswer].setButtonRes(R.drawable.selecter_radio_button_wrong);
                button[mQuestionItem.selectedAnswer].setText("✘");
            }
            button[mQuestionItem.answer].setButtonRes(R.drawable.selecter_radio_button_correct);
            button[mQuestionItem.answer].setText("✔");
        } else {
            mExplanation.setVisibility(View.INVISIBLE);
            for (MaterialRadioButton btn : button) {
                btn.setButtonRes(R.drawable.letter_radio_button);
            }
        }
    }

    private void initDate() {
        mQuestionItem = (Question) getIntent().getSerializableExtra("question");
        mQuestionList = (List<Question>) getIntent().getSerializableExtra("question_list");
        mQuestionModel = new QuestionModel(this);
        current = getIntent().getIntExtra("current", 0);
        amount = mQuestionList.size();
        new QuestionDetailPresenter(this, this);

        //答案选中
        mMaterialRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                for (int i = 0; i < 4; i++) {
                    if (button[i].isChecked()) {
                        mQuestionList.get(current).selectedAnswer = i;
                        mQuestionItem = mQuestionList.get(current);
                        mQuestionList.get(current).reviewDate = TimeUtils.getNowDateTime();
                        break;
                    }
                }
            }
        });

        mistakeOperateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question wrongQuest = mQuestionList.get(current);
                Question targetQuest = null;
                //遍历数据库中的错题表，对比标识码是否一致
                for (int i = 0; i < mQuestionModel.selectAll().size(); i++) {
                    targetQuest = mQuestionModel.selectAll().get(i);
                    if (wrongQuest.idCode == targetQuest.idCode
                            && targetQuest.isMistake) {
                        //是错题，设置为非错题
                        targetQuest.isMistake = false;
                        mQuestionModel.updateQuestion(targetQuest);
                        mistakeOperateButton.setIcon(R.drawable.add_button);
                        mistakeOperateButton.setTitle(getResources().getString(R.string.label_add_mistake));
                        break;
                    } else if (wrongQuest.idCode == targetQuest.idCode) {
                        //不是错题，设置为错题
                        wrongQuest.isMistake = true;
                        mQuestionModel.updateQuestion(wrongQuest);
                        mistakeOperateButton.setIcon(R.drawable.delate_button);
                        mistakeOperateButton.setTitle(getResources().getString(R.string.label_delete_mistake));
                        break;
                    }
                }
            }
        });

        favoriteOperateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question favoriteQuest = mQuestionList.get(current);
                Question targetQuest = null;
                //遍历数据库中的错题表，对比标识码是否一致
                for (int i = 0; i < mQuestionModel.selectAll().size(); i++) {
                    targetQuest = mQuestionModel.selectAll().get(i);
                    if (favoriteQuest.idCode == targetQuest.idCode
                            && targetQuest.isFavorite) {
                        //是收藏，移出收藏
                        targetQuest.isFavorite = false;
                        mQuestionModel.updateQuestion(targetQuest);
                        favoriteOperateButton.setIcon(R.drawable.favorite_button_off);
                        favoriteOperateButton.setTitle(getResources().getString(R.string.label_add_favorite));
                        break;
                    } else if (favoriteQuest.idCode == targetQuest.idCode) {
                        //不是收藏，加入收藏
                        favoriteQuest.isFavorite = true;
                        mQuestionModel.updateQuestion(favoriteQuest);
                        favoriteOperateButton.setIcon(R.drawable.favorite_button_on);
                        favoriteOperateButton.setTitle(getResources().getString(R.string.label_delete_favorite));
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void showProcess() {
        mLoadProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProcess() {
        mLoadProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateViews(Question questionItem) {
        setViews(questionItem);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new QuestionDetailPresenter(this, this).updateQuestion(mQuestionList);
    }

    private void addMistake() {
        boolean same = false;  //标志变量
        Question wrongQuest;
        //遍历数据库中的错题表，对比标识码是否一致
        for (int i = 0; i < mQuestionList.size(); i++) {
            wrongQuest = mQuestionList.get(current);
            if (wrongQuest.idCode == mQuestionModel.selectAll().get(i).idCode) {
                same = true;
            }
            if (same) {
                //标识码一致，更新错题表
                mQuestionModel.updateQuestion(wrongQuest);
            } else {
                //标识码不一致，增加新的错题
                mQuestionModel.addQuestion(wrongQuest);
            }
            same = false;
        }
    }
}
