package com.example.lilactests;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.appcompat.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.lilactests.app.LilacTestsApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_info0);
        ButterKnife.bind(this);
        init();
    }

    @BindView(R.id.collapsing_toolbar_user_info)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_user_info)
    androidx.appcompat.widget.Toolbar toolbar;
    @BindView(R.id.header_background)
    ImageView background;
    @BindView(R.id.header_floating_button)
    FloatingActionButton header;
    @OnClick(R.id.header_floating_button)
    public void clickHead(){
        /*点击更换头像代码*/
    }
    public void init() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(getString(R.string.user_name));
        /*调用Glide加载圆形图片*/
//        Glide.with(this).asBitmap().centerCrop().load(R.mipmap.logo).into(new BitmapImageViewTarget(header) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(LilacTestsApp.getContext().getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                header.setImageDrawable(circularBitmapDrawable);
//            }
//        });
        Glide.with(this)
                .load(R.mipmap.logo)
                .apply(RequestOptions.bitmapTransform(new CenterCrop()))
                .into(header);
        Glide.with(this)
                .load(R.drawable.header_background_nightcity)
                .into(background);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // 点击返回按钮返回
        if(item.getItemId() == android.R.id.home)
        {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}