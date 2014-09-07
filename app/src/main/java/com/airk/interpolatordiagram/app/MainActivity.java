package com.airk.interpolatordiagram.app;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.airk.interpolatordiagram.app.factory.FragmentFactory;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.content_frame)
    FrameLayout mContent;
    @InjectView(R.id.guide)
    TextView mGuide;
    @InjectView(R.id.list)
    ListView mList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar mActionBar;
    private String[] mArray;
    private int mSelectedInterpolator = -1;
    private int mDrawerWidth;

    private final String SELECTED_INTERPOLATOR_KEY = "selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // 设置ActionBar显示向上，以适应之后NavigationDrawer设置的ic_drawer
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        mArray = getResources().getStringArray(R.array.interpolators);
        mDrawerWidth = getResources().getDimensionPixelSize(R.dimen.drawer_width);
        if (savedInstanceState != null
                && savedInstanceState.containsKey(SELECTED_INTERPOLATOR_KEY)) {
            mSelectedInterpolator = savedInstanceState.getInt(SELECTED_INTERPOLATOR_KEY);
            mActionBar.setTitle(mArray[mSelectedInterpolator]);
        }

        // 对NavigationDrawer的Drawer添加右侧的阴影
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // 使NavigationDrawer在打开时并不产生灰色遮罩
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.ic_navigation_drawer,
                android.R.string.ok,
                android.R.string.cancel) {
            /**
             * {@link android.support.v4.widget.DrawerLayout.DrawerListener} callback method. If you do not use your
             * ActionBarDrawerToggle instance directly as your DrawerLayout's listener, you should call
             * through to this method from your own listener object.
             *
             * @param drawerView Drawer view that is now open
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mActionBar.setTitle(getString(R.string.app_name));
            }

            /**
             * {@link android.support.v4.widget.DrawerLayout.DrawerListener} callback method. If you do not use your
             * ActionBarDrawerToggle instance directly as your DrawerLayout's listener, you should call
             * through to this method from your own listener object.
             *
             * @param drawerView Drawer view that is now closed
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (mSelectedInterpolator != -1) {
                    mActionBar.setTitle(mArray[mSelectedInterpolator]);
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                slidingContent(slideOffset);
            }
        };
        mList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                mArray));
        mList.setOnItemClickListener(this);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (mSelectedInterpolator == -1 && !mDrawerLayout.isDrawerOpen(mList)) {
            mDrawerLayout.openDrawer(mList);
            slidingContent(1f);
        } else if (mDrawerLayout.isDrawerOpen(mList)) {
                mActionBar.setTitle(mArray[mSelectedInterpolator]);
        }
        mGuide.setOnClickListener(this);
    }

    private void slidingContent(float slideOffset) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mContent, "translationX",
                slideOffset * mDrawerWidth);
        animator.setDuration(0).start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSelectedInterpolator != -1) {
            outState.putInt(SELECTED_INTERPOLATOR_KEY, mSelectedInterpolator);
        }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mDrawerLayout.isDrawerOpen(mList)) {
            mDrawerLayout.closeDrawer(mList);
        }
        mSelectedInterpolator = position;
        mActionBar.setTitle(mArray[position]);
        Fragment fragment = FragmentFactory.getInstance().getInterpolator(position);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.guide) {
            mDrawerLayout.openDrawer(mList);
        }
    }
}
