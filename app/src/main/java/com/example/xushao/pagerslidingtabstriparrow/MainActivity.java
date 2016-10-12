package com.example.xushao.pagerslidingtabstriparrow;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.xushao.pagerslidingtabstriparrow.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabHost.TabContentFactory, TabHost.OnTabChangeListener {

    private TabHost mTabHost;
    private ViewPager mTabViewPager;
    private TabPagerAdapter mTabPagerAdapter;

    private int mTabSelectedIndex;// 当前时间段对应的tab(切换后所在位置)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabHost = (TabHost) findViewById(R.id.host);
        mTabViewPager = (ViewPager) findViewById(R.id.tab_viewpager);

        init(mock());
    }

    @Override
    public void onTabChanged(String tabId) {
        int newPosition = mTabHost.getCurrentTab();
        if (newPosition >= 0 && newPosition < mTabPagerAdapter.getCount()) {
            mTabViewPager.setCurrentItem(newPosition, true);
        }
    }

    @Override
    public View createTabContent(String tag) {
        View view = new View(this);
        view.setMinimumHeight(0);
        view.setMinimumWidth(0);
        return view;
    }

    private void init(final List<Item> items) {
        mTabPagerAdapter = new TabPagerAdapter(this, items);
        mTabViewPager.setAdapter(mTabPagerAdapter);
        mTabHost.setup();
        mTabHost.setOnTabChangedListener(this);

        final ArrowTabWidget widget = (ArrowTabWidget) mTabHost.getTabWidget();
        widget.setTabCount(items.size());

        mTabViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                widget.updateArrow(position, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mTabSelectedIndex = position;
                mTabHost.setCurrentTab(position);

                updateTabText(items);// 每次切换后设置选中效果
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        createTabs(items);
        mTabViewPager.setCurrentItem(mTabSelectedIndex);
        updateTabText(items);// 设置初始选中效果
    }

    private void createTabs(List<Item> items) {
        if (mTabHost.getTabWidget().getTabCount() > 0) {
            mTabHost.setCurrentTab(0);
            mTabHost.clearAllTabs();
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < items.size(); i++) {
            final Item item = items.get(i);
            TabHost.TabSpec spec = mTabHost.newTabSpec("tab" + i);
            spec.setContent(this);

            View tabLayout = inflater.inflate(R.layout.tabhost_item, null);
            TextView timeText = (TextView) tabLayout.findViewById(R.id.tabhost_item_time);
            TextView statusText = (TextView) tabLayout.findViewById(R.id.tabhost_item_status);
            timeText.setText(item.title);
            statusText.setText(item.desc);

            spec.setIndicator(tabLayout);
            mTabHost.addTab(spec);
        }
    }

    /**
     * 每次切换后更新显示文字
     */
    private void updateTabText(List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            View selectTab = mTabHost.getTabWidget().getChildTabViewAt(i);
            TextView timeText = (TextView) selectTab.findViewById(R.id.tabhost_item_time);
            TextView statusText = (TextView) selectTab.findViewById(R.id.tabhost_item_status);
            if (mTabSelectedIndex == i) {
                timeText.setTextColor(getResources().getColor(R.color.colorPrimary));
                statusText.setTextColor(getResources().getColor(R.color.colorPrimary));
                timeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            } else {
                timeText.setTextColor(getResources().getColor(R.color.base_light));
                statusText.setTextColor(getResources().getColor(R.color.base_light));
                timeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            }
        }
    }

    private static final String[] TITLES = {"AA", "BB", "CC", "DD"};
    private static final int[] COVERS = {R.drawable.cover_a, R.drawable.cover_b, R.drawable.cover_c, R.drawable.cover_d};

    @Deprecated
    private List<Item> mock() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            Item item = new Item();
            item.title = TITLES[i];
            item.desc = TITLES[i].toLowerCase();
            item.coverResId = COVERS[i];
            items.add(item);
        }
        // 随机默认选择一个位置
        mTabSelectedIndex = (int) (Math.random() * items.size());
        return items;
    }

}
