package com.study.longl.myselfviewdemo.AutoScrollView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by longl on 2018/11/5.
 * AutoScrollViewAdapter 自定义轮播图的Adapter
 */

public class AutoScrollViewAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private ArrayList<ImageView> arrayList = new ArrayList<>();

    public AutoScrollViewAdapter(Context context, int imgs[]) {
        for (int img : imgs) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(img);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            arrayList.add(imageView);
        }
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(arrayList.get(position));
        arrayList.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //添加点击事件
            }
        });
        return arrayList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(arrayList.remove(position));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}