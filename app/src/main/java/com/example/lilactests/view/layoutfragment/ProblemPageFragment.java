package com.example.lilactests.view.layoutfragment;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.lilactests.R;
import com.example.lilactests.SolvingProblemsActivity;
import com.example.lilactests.model.Question.QuestionModel;
import com.example.lilactests.model.domain.Question;
import com.example.lilactests.utils.TimeUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  Created by Eventory on 2017/2/12 0012.
 */

public class ProblemPageFragment extends Fragment{
    private static final String TAG = "ProblemPageFragment";
    private static final String ARG_POSITION = "position";
    private static final String ARG_AMOUNT = "amount";
    private static final String ARG_QUESTION_LIST = "question_list";
    private static int nowPosition;
    private static int amount;
    private static boolean wrongMode;
    private static List<Question> questionList;
    private static List<Integer> wrongList;
    private View view;
    private int current;
    private QuestionModel mQuestionModel;

    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton mistakeOperateButton;
    private FloatingActionButton favoriteOperateButton;
    private TextView problem;
    private TextView tv_result;
    private Button btn_prev;
    private Button btn_next;
    private MaterialRadioGroup mMaterialRadioGroup;
    MaterialRadioButton[] button = new MaterialRadioButton[4];
    TextView[] answer = new TextView[4];

    public ProblemPageFragment() {
        super();
    }


    public static ProblemPageFragment newInstance(int position, int amount, List questionList) {
        ProblemPageFragment problemPageFragment = new ProblemPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        bundle.putInt(ARG_AMOUNT, amount);
        bundle.putSerializable(ARG_QUESTION_LIST, (Serializable) questionList);
        problemPageFragment.setArguments(bundle);
        return problemPageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nowPosition = getArguments().getInt(ARG_POSITION);
        amount = getArguments().getInt(ARG_AMOUNT);
        current = nowPosition;
        wrongMode = false;
        questionList = (List<Question>) getArguments().getSerializable(ARG_QUESTION_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_solving_problems, container, false);
        initView();
        initDB();
        return view;
    }

    private void initView() {
        problem = (TextView) view.findViewById(R.id.problem);
        button[0] = (MaterialRadioButton) view.findViewById(R.id.material_radio_button_a);
        button[1] = (MaterialRadioButton) view.findViewById(R.id.material_radio_button_b);
        button[2] = (MaterialRadioButton) view.findViewById(R.id.material_radio_button_c);
        button[3] = (MaterialRadioButton) view.findViewById(R.id.material_radio_button_d);
        answer[0] = (TextView) view.findViewById(R.id.textView_optionA);
        answer[1] = (TextView) view.findViewById(R.id.textView_optionB);
        answer[2] = (TextView) view.findViewById(R.id.textView_optionC);
        answer[3] = (TextView) view.findViewById(R.id.textView_optionD);
        mMaterialRadioGroup = (MaterialRadioGroup) view.findViewById(R.id.option_group);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_prev = (Button) view.findViewById(R.id.btn_prev);
        tv_result = (TextView) view.findViewById(R.id.tv_result);
        mFloatingActionsMenu = (FloatingActionsMenu) view.findViewById(R.id.floating_buttons_menu);
        mistakeOperateButton = (FloatingActionButton) view.findViewById(R.id.mistake_operate_button);
        favoriteOperateButton = (FloatingActionButton) view.findViewById(R.id.favorite_operate_button);
    }

    private void initDB() {
        Question q = questionList.get(current);
        problem.setText(q.subject);
        answer[0].setText(q.answerA);
        answer[1].setText(q.answerB);
        answer[2].setText(q.answerC);
        answer[3].setText(q.answerD);
        tv_result.setText(q.explanation);

        if (wrongMode) {
            //显示结果
            tv_result.setVisibility(View.VISIBLE);
            mMaterialRadioGroup.setClickable(false);
            if (q.selectedAnswer != -1) {
                button[q.selectedAnswer].setButtonRes(R.drawable.selecter_radio_button_wrong);
                button[q.selectedAnswer].setText("✘");
            }
            button[q.answer].setButtonRes(R.drawable.selecter_radio_button_correct);
            button[q.answer].setText("✔");
        }
        //设置按钮内容
        if (current == 0) {
            btn_prev.setClickable(false);
        } else if (current == amount-1) {
            if (wrongMode) {
                btn_next.setClickable(false);
            } else {
                btn_next.setText("完成");
            }
        }

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current > 0) {
                    ((SolvingProblemsActivity)getActivity()).setPageItem(current-1);
                }
//                if (current > 0) {
//                    current--;
//
//                    Question q = list.get(current);
//
//                    problem.setText(q.question);
//
//                    answer[0].setText(q.answerA);
//                    answer[1].setText(q.answerB);
//                    answer[2].setText(q.answerC);
//                    answer[3].setText(q.answerD);
//
//
//                }

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为最后一题
                if (current < amount - 1) {
                    ((SolvingProblemsActivity)getActivity()).setPageItem(current+1);
//                if (current < count - 1) {
//                    current++;
//                    Question q = list.get(current);
//
//                    problem.setText(q.question);
//
//                    answer[0].setText(q.answerA);
//                    answer[1].setText(q.answerB);
//                    answer[2].setText(q.answerC);
//                    answer[3].setText(q.answerD);
//
//                    tv_result.setText(q.explaination);
//
//                    mMaterialRadioGroup.clearCheck();
//
//                    //设置选中
//                    if (q.selectedAnswer != -1) {
//                        button[q.selectedAnswer].setChecked(true);
//                    }
                } else {

                    //没有题目了，开始检测正确性
                    wrongList = checkAnswer(questionList);
                    Chronometer chronometer = ((SolvingProblemsActivity)getActivity()).mChronometer;
                    chronometer.stop();

                    if (wrongList.size() == 0) {
                        new AlertDialog.Builder(getContext()).setTitle("提示")
                                .setMessage("你好厉害，答对了所有题！" + "\n" + "用时" + getChronometerTime(chronometer)
                                        + "\n" + "是否留下查看解析？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((SolvingProblemsActivity)getActivity()).setPageItem(0);
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        }).show();
                    }

                    mQuestionModel = new QuestionModel(getContext());
                    //窗口提示
                    new AlertDialog.Builder(getContext()).setTitle("恭喜，答题完成！")
                            .setMessage("答对了" + (amount - wrongList.size()) + "道题" + "\n"
                                    + "答错了" + wrongList.size() + "道题" + "\n" + "用时"
                                    + getChronometerTime(chronometer) + "\n" + "是否留下查看解析？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            wrongMode = true;

                            boolean same = false;  //标志变量
                            Date fD = TimeUtils.getNowDateTime();  //该函数返回系统当前时间
                            Date rD = TimeUtils.getNowDateTime();
                            Question wrongQuest;
                            //遍历当前错题表
                            for (int i = 0; i < wrongList.size(); i++) {
                                wrongQuest = questionList.get(wrongList.get(i));
                                wrongQuest.finishDate = fD;
                                wrongQuest.reviewDate = rD;
                                wrongQuest.isMistake = true;

                                //遍历数据库中的题表，对比标识码是否一致
                                for (int j = 0; j < mQuestionModel.selectAll().size(); j++) {
                                    if (wrongQuest.ID == mQuestionModel.selectAll().get(j).idCode) {
                                        same = true;
                                    }
                                }
                                if (same) {
                                    //标识码一致，更新题表
                                    mQuestionModel.updateQuestion(wrongQuest);
                                } else {
                                    //标识码不一致，增加新的题
                                    wrongQuest.idCode = wrongQuest.ID;
                                    mQuestionModel.addQuestion(wrongQuest);
                                }
                                same = false;
                            }
                            ((SolvingProblemsActivity)getActivity()).setPageItem(0);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    }).show();

                }
            }
        });

        //答案选中
        mMaterialRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                for (int i = 0; i < 4; i++) {
                    if (button[i].isChecked()) {
                        questionList.get(current).selectedAnswer = i;
                        ((SolvingProblemsActivity)getActivity()).adapter.setSelectedAnswer(current, i);
                        break;
                    }
                }
            }
        });

        mistakeOperateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean same = false;  //标志变量
                Question wrongQuest = questionList.get(current);
                Question targetQuest = null;
                //遍历数据库中的错题表，对比标识码是否一致
                for (int i = 0; i < mQuestionModel.selectAll().size(); i++) {
                    targetQuest = mQuestionModel.selectAll().get(i);
                    if (wrongQuest.idCode == targetQuest.idCode
                            && targetQuest.isMistake) {
                        same = true;
                        break;
                    }
                    same = false;
                }
                if (same) {
                    //标识码一致，移出错题表
                    if (targetQuest != null) {
                        mQuestionModel.deleteQuestion(targetQuest.ID);
                    }
                    mistakeOperateButton.setIcon(R.drawable.add_button);
                } else {
                    //标识码不一致，加入错题表
                    mQuestionModel.addQuestion(wrongQuest);
                    mistakeOperateButton.setIcon(R.drawable.delate_button);
                }
            }
        });

        favoriteOperateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean same = false;  //标志变量
                Question wrongQuest = questionList.get(current);
                Question targetQuest = null;
                //遍历数据库中的错题表，对比标识码是否一致
                for (int i = 0; i < mQuestionModel.selectAll().size(); i++) {
                    targetQuest = mQuestionModel.selectAll().get(i);
                    if (wrongQuest.idCode == targetQuest.idCode
                            && targetQuest.isFavorite) {
                        same = true;
                        break;
                    }
                    same = false;
                }
                if (same) {
                    //标识码一致，移出收藏
                    if (targetQuest != null) {
                        mQuestionModel.deleteQuestion(targetQuest.ID);
                    }
                    favoriteOperateButton.setIcon(R.drawable.favorite_button_off);
                } else {
                    //标识码不一致，加入收藏
                    mQuestionModel.addQuestion(wrongQuest);
                    favoriteOperateButton.setIcon(R.drawable.favorite_button_on);
                }
            }
        });
    }

    //以 x分x秒 或 x时x分x秒 的形式返回计数器时间
    public  static String getChronometerTime(Chronometer cmt) {

        String string = cmt.getText().toString();
        String formatTime;
        if (string.length()==7) {
            String[] split = string.split(":");
            String string2 = split[0];
            int hour = Integer.parseInt(string2);
            String string3 = split[1];
            int min = Integer.parseInt(string3);
            int sec =Integer.parseInt(split[2]);
            formatTime = hour+"时"+min+"分"+sec+"秒";
            return formatTime;
        } else if (string.length()==5) {

            String[] split = string.split(":");
            String string3 = split[0];
            int min = Integer.parseInt(string3);
            int sec =Integer.parseInt(split[1]);
            formatTime = min+"分"+sec+"秒";
            return formatTime;
        }
        return String.valueOf(0);


    }

    // 判断答题正确与否
    static private List<Integer> checkAnswer(List<Question> list) {
        List<Integer> wrongList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            //judge
            if (list.get(i).answer != list.get(i).selectedAnswer) {
                wrongList.add(i);
            }
        }
        return wrongList;
    }

}
