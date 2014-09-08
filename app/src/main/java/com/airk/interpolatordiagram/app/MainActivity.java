package com.airk.interpolatordiagram.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.airk.interpolatordiagram.app.factory.FragmentFactory;
import com.airk.interpolatordiagram.app.fragment.AboutFragmentDialog;
import com.nineoldandroids.animation.ObjectAnimator;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private final String SELECTED_INTERPOLATOR_KEY = "selected";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // 设置ActionBar显示向上，以适应之后NavigationDrawer设置的ic_drawer
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setIcon(R.drawable.ic_drawer_indicator);
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
                android.R.color.transparent,
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
                invalidateOptionsMenu();
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
                invalidateOptionsMenu();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mList)) {
            return true;
        }
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Overflow中显示Menu item的icon
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    // 添加这个以使NavigationDrawer也能够响应ActionBar上的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.action_rate) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.airk.interpolatordiagram.app"));
            PackageManager pm = getPackageManager();
            List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() <= 0) {
                intent.setData(Uri.parse("https://market.android.com/details?id=com.airk.interpolatordiagram.app"));
            }
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_about) {
            new AboutFragmentDialog().show(getSupportFragmentManager(), "about");
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
