package com.airk.interpolatordiagram.app;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.content_frame)
    FrameLayout mContent;
    @InjectView(R.id.list)
    ListView mList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // 设置ActionBar显示向上，以适应之后NavigationDrawer设置的ic_drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // 对NavigationDrawer的Drawer添加右侧的阴影
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // 使NavigationDrawer在打开时并不产生灰色遮罩
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                android.R.string.ok,
                android.R.string.cancel);
        mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.interpolators)));
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    // 添加这个使NavigationDrawer所设置的ic_drawer图标替换原有的up向上图标
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    // 添加这个以使NavigationDrawer也能够响应ActionBar上的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 使NavigationDrawer能够适应如屏幕旋转等状态改变的情况
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
