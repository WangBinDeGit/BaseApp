package com.wangbin.mydome.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.wangbin.mydome.R;
import com.wangbin.mydome.adapter.SearchAdapter;
import com.wangbin.mydome.adapter.ViewPagerAdapter;
import com.wangbin.mydome.bean.SearchBean;
import com.wangbin.mydome.fragment.NewsFragment;
import com.wangbin.mydome.fragment.TestFragment;
import com.wangbin.mydome.helper.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavigationView nav_view;
    private DrawerLayout drawer_layout;
    private RecyclerView rec_search;
    private EditText et_search;
    private ImageView img_search;
    private ViewPagerAdapter viewPagerAdapter;
    private SearchAdapter searchAdapter;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private int num = 1;
    private List<SearchBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initLinster();
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPager = findViewById(R.id.vp);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        List<Fragment> list = new ArrayList<>();
        list.add(NewsFragment.newInstance("首页"));
        list.add(NewsFragment.newInstance("公告"));
        list.add(TestFragment.newInstance("趣闻"));
        list.add(TestFragment.newInstance("个人"));
        viewPagerAdapter.setList(list);

        drawer_layout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        View headerView = nav_view.getHeaderView(0);
        rec_search = headerView.findViewById(R.id.rec_search);
        et_search = headerView.findViewById(R.id.et_search);
        img_search = headerView.findViewById(R.id.img_search);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rec_search.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(MainActivity.this, datas);
        rec_search.setAdapter(searchAdapter);
    }

    private void initLinster() {
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datas.clear();
                for (int i = 0; i < 5; i++) {
                    SearchBean searchBean = new SearchBean();
                    searchBean.setName("测试数据" + num);
                    searchBean.setFlag(false);
                    datas.add(searchBean);
                    num++;
                }
                searchAdapter.notifyDataSetChanged();
            }
        });

        searchAdapter.setOnItemClickLitener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (SearchBean searchBean : datas) {
                    searchBean.setFlag(false);
                }
                datas.get(position).setFlag(true);
                searchAdapter.notifyDataSetChanged();
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            menuItem = item;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_person:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

}
