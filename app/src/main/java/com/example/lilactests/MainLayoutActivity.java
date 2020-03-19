package com.example.lilactests;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import com.google.android.material.navigation.NavigationView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lilactests.adapter.MainPagerAdapter;
import com.example.lilactests.app.LilacTestsApp;
import com.example.lilactests.utils.ActivityCollector;
import com.example.lilactests.utils.PrefUtils;
import com.google.android.material.tabs.TabLayout;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainLayoutActivity extends BaseActivity {

    private DrawerLayout theDrawerLayout;
    private TabLayout theTabLayout;
    private ViewPager theViewPager;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private CircleImageView header;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        theDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBar = getSupportActionBar();
        final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        header = (CircleImageView) headerView.findViewById(R.id.header_image);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.usercofig_button);
        }
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainLayoutActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });

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

        theTabLayout = findViewById(R.id.main_tabs);
        theViewPager = findViewById(R.id.main_pager);
        theViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        theTabLayout.setupWithViewPager(theViewPager);


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
                    Toast.makeText(this, getString(R.string.double_click_exit), Toast.LENGTH_SHORT).show();
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


}
