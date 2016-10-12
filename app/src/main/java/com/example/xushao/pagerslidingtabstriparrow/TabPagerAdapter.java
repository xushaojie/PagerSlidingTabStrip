package com.example.xushao.pagerslidingtabstriparrow;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xushao.pagerslidingtabstriparrow.model.Item;

import java.util.List;

/**
 * @author xushao
 */
public class TabPagerAdapter extends PagerAdapter {

    private final List<Item> mItems;
    private final Activity mActivity;

    public TabPagerAdapter(Activity activity, List<Item> items) {
        mActivity = activity;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.tab_pager_view, null);

        final Item item = mItems.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.pager_cover_image);
        imageView.setImageResource(item.coverResId);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, item.desc, Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
