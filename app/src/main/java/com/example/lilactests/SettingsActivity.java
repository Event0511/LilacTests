package com.example.lilactests;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.FragmentTransaction;
import android.preference.PreferenceFragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lilactests.app.LilacTestsApp;
import com.example.lilactests.view.layoutfragment.SettingFragment;
import com.example.lilactests.utils.ActivityCollector;
import com.example.lilactests.utils.StatusBarUtils;

public class SettingsActivity extends BaseActivity {
    private Toolbar mToolbar;
    private ViewGroup mViewGroup = null;
    private ImageView mImageView = null;
    private PreferenceFragment mSettingFragment = null;
    private final long ANIMTION_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_preference);
        initToolbar(mToolbar);
        addPreferenceFragment(R.id.content_frame);

        mViewGroup = (ViewGroup) findViewById(R.id.settings_view);
        mImageView = (ImageView) findViewById(R.id.image_view_transition);
    }

    //@InjectView(R.id.toolbar_preference)


    /**
     * 初始化Toolbar
     */
    private void initToolbar(Toolbar mToolbar) {
        mToolbar.setTitle(getResources().getString(R.string.title_activity_setting));
        mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 选项菜单
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    /**
     * 获取布局的DrawableCache给ImageView，令其显示来覆盖Fragment视图
     */
    private void setDrawableCache() {
        //设置false清除缓存
        mViewGroup.setDrawingCacheEnabled(false);
        //设置true之后可以获取Bitmap
        mViewGroup.setDrawingCacheEnabled(true);
        mImageView.setImageBitmap(mViewGroup.getDrawingCache());
        mImageView.setAlpha(1f);
        mImageView.setVisibility(View.VISIBLE);
    }

    /**
     * 添加Fragment,如果已存在Fragment就先移除在添加
     * @param resource 资源id
     */
    private void addPreferenceFragment(final int resource) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (mSettingFragment != null){
            fragmentTransaction.remove(mSettingFragment);
        }
        mSettingFragment =  new SettingFragment();
        fragmentTransaction.add(resource, mSettingFragment);
        fragmentTransaction.commit();
    }

    /**
     * 开始一段View的渐隐补间动画
     * @param view 要进行动画的view
     */
    private void startAnimation(final View view) {
        // 设定一个在ANIMTION_TIME时间内，从0变化到1f的ValueAnimator
        ValueAnimator animator = ValueAnimator.ofFloat(1f).setDuration(ANIMTION_TIME);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // ValueAnimator每次递增变化，都获得其当前值，为视图设置透明度为 1f-当前值
                float n = (float) animation.getAnimatedValue();
                view.setAlpha(1f - n);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 设定动画停止后，隐藏ImageView
                super.onAnimationEnd(animation);
                mImageView.setVisibility(View.INVISIBLE);
            }
        });
        animator.start();
    }

    /**
     * 转换主题，利用ImageView做渐隐过渡
     */
    public void beginTransition() {
        // 设置当前LinearLayout的Draw缓存，把缓存的Bitmap设定给ImageView显示出来
        setDrawableCache();
        switch (LilacTestsApp.getAppTheme()) {
            case R.style.DefaultAppTheme:
                mToolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                StatusBarUtils.setWindowStatusBarColor(this, R.color.colorPrimaryDark);
                break;
            case R.style.NightModeAppTheme:
                mToolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary_Night));
                StatusBarUtils.setWindowStatusBarColor(this, R.color.colorPrimaryDark_Night);
                break;
        }
        this.setTheme(LilacTestsApp.getAppTheme());
        addPreferenceFragment(R.id.content_frame);
        // 开始ImageView的渐隐补间动画
        startAnimation(mImageView);
        // 除了当前活动，重新创建所有活动的视图以更新其主题
        ActivityCollector.removeActivity(this);
        ActivityCollector.recreateAll();
        ActivityCollector.addActivity(this);
    }

}
