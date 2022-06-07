package com.example.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;

import com.example.R;
import com.example.adapter.MyFragmentAdapter;
import com.example.fragment.homefragment.ActivityFragment;
import com.example.fragment.homefragment.AllServiceFragment;
import com.example.fragment.homefragment.HomeFragment;
import com.example.fragment.homefragment.NewsFragment;
import com.example.fragment.homefragment.UserFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class HomeActivity extends BaseActivity implements HomeFragment.JumpPageListener {
    private static final String TAG = "HomeActivity";
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MyFragmentAdapter fragmentAdapter;
    private String[] title = {"首页", "服务", "新闻", "活动", "个人"};
    private Drawable[] drawables;

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPage2);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragmentAdapter = new MyFragmentAdapter(this, fragments);
        //设置滑动方向
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        viewPager.setAdapter(fragmentAdapter);

        drawables = new Drawable[]{getResources().getDrawable(R.drawable.selector_icon_home, null),
                getResources().getDrawable(R.drawable.selector_icon_service, null),
                getResources().getDrawable(R.drawable.selector_icon_news, null),
                getResources().getDrawable(R.drawable.selector_icon_activity, null),
                getResources().getDrawable(R.drawable.selector_icon_user, null)};


        fragments.add(HomeFragment.getInstance());
        fragments.add(AllServiceFragment.newInstance());
        fragments.add(NewsFragment.newInstance());
        fragments.add(ActivityFragment.newInstance());
        fragments.add(UserFragment.newInstance());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tabLayout.getSelectedTabPosition();
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(title[position]);
                tab.setIcon(drawables[position]);
            }
        }).attach();

        tabLayout.setSelectedTabIndicator(0);

        //禁用viewpage滑动
        viewPager.setUserInputEnabled(false);

        //设置tab栏预加载
//        viewPager.setOffscreenPageLimit(fragments.size());

    }

    @SuppressLint("ResourceType")
    @Override
    public void jumpNews() {
        viewPager.setCurrentItem(2, false);
    }

    @Override
    public void jumpServices() {
        viewPager.setCurrentItem(1, false);
    }
}