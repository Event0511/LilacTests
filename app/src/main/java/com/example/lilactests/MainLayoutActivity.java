package com.example.lilactests;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.lilactests.adapter.MainPagerAdapter;
import com.example.lilactests.app.LilacTestsApp;
import com.example.lilactests.utils.ActivityCollector;
import com.example.lilactests.utils.PrefUtils;

public class MainLayoutActivity extends BaseActivity {

    private DrawerLayout theDrawerLayout;
    private PagerSlidingTabStrip thePagerSlidingTabStrip;
    private ViewPager theViewPager;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        theDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBar = getSupportActionBar();
        final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.usercofig_button);
        }
        navView.setCheckedItem(R.id.nav_navigate);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_settings:
                        Intent intent = new Intent(MainLayoutActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                }
                theDrawerLayout.closeDrawers();
                return true;
            }
        });

        thePagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.main_tabs);
        theViewPager = (ViewPager) findViewById(R.id.main_pager);
        theViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        thePagerSlidingTabStrip.setViewPager(theViewPager);
        initTabsValue();
        thePagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 捕捉返回事件按钮
     *
     * Activity 继承 TabActivity 时用 onKeyDown 无响应，所以改用 dispatchKeyEvent
     * 一般的 Activity 用 onKeyDown 就可以了
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                // 判断2次点击事件时间
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    ActivityCollector.finishAll();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                theDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.add_item:
                Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
                break;
            case R.id.destroy_activity:
                ActivityCollector.finishAll();
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 设置状态栏颜色
     */
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        }
    }
    /**
     * thePagerSlidingTabStrip默认值配置
     *
     */
    private void initTabsValue() {
        if (PrefUtils.isNightMode()) {
            // tab背景
            thePagerSlidingTabStrip.setBackgroundColor(ContextCompat.getColor(LilacTestsApp.getContext(),
                    R.color.colorPrimary_Night));
            // 底部游标颜色
            thePagerSlidingTabStrip.setIndicatorColor(ContextCompat.getColor(LilacTestsApp.getContext(),
                    R.color.colorAccent_Night));
        } else {
            // tab背景
            thePagerSlidingTabStrip.setBackgroundColor(ContextCompat.getColor(LilacTestsApp.getContext(),
                    R.color.colorPrimary));
            // 底部游标颜色
            thePagerSlidingTabStrip.setIndicatorColor(ContextCompat.getColor(LilacTestsApp.getContext(),
                    R.color.colorAccent));
        }

        // tab的分割线颜色
        thePagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);
        // 正常文字颜色
        thePagerSlidingTabStrip.setTextColor(Color.WHITE);
        // tab底线高度
        thePagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                                        1, getResources().getDisplayMetrics()));
        // 游标高度
        thePagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                                        5, getResources().getDisplayMetrics()));
        // 选中的文字颜色
        //thePagerSlidingTabStrip.setSelectedTextColor(Color.WHITE);Color.parseColor("#4876FF")
    }

}
