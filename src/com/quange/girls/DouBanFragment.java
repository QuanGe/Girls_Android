package com.quange.girls;

import java.util.ArrayList;

import com.quange.views.GDouBanGridView;
import com.quange.views.PagerSlidingTabStrip;

import android.R.color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DouBanFragment extends Fragment  implements TabListener{
	private View fgmView;
	private ViewPager doubanViewPaper;
	private ArrayList<GDouBanGridView> subs= new ArrayList<GDouBanGridView>();
	private PagerSlidingTabStrip doubanTabs;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (fgmView == null) {
			fgmView = inflater.inflate(R.layout.fragment_douban, container, false);
		
		}

		ViewGroup parent = (ViewGroup) fgmView.getParent();
		if (parent != null) {
			parent.removeView(fgmView);
		}
		doubanViewPaper = (ViewPager) fgmView.findViewById(R.id.doubanViewPaper);
		
		subs.add(new GDouBanGridView(getActivity(),3));
		subs.add(new GDouBanGridView(getActivity(),6));
		subs.add(new GDouBanGridView(getActivity(),4));
		subs.add(new GDouBanGridView(getActivity(),2));
		subs.add(new GDouBanGridView(getActivity(),7));
		subs.add(new GDouBanGridView(getActivity(),5));
		
		for(GDouBanGridView sub :subs)
		{
			doubanViewPaper.addView(sub.getView());
		}
		
		doubanViewPaper.setAdapter(new PagerAdapter() {
			String[] title = { "美腿控","小翘臀","有颜值", "大胸妹","黑丝袜", "大杂烩" };
			public boolean isViewFromObject(View view, Object o) {
				return view == o;
			}

			public int getCount() {
				return doubanViewPaper.getChildCount();
			}

			public void destroyItem(View container, int position, Object object) {
			}

			public Object instantiateItem(View container, int position) {
				return doubanViewPaper.getChildAt(position);
			}
	
			public CharSequence getPageTitle(int position) {
				return title[position];
			}
			
		});

		GDouBanGridView sub = subs.get(0);
    	sub.firstLoadData();
    	
		//
		doubanTabs = (PagerSlidingTabStrip)fgmView.findViewById(R.id.tabs);
		doubanTabs.setOnPageChangeListener(mPagerChangerListener);
		doubanTabs.setViewPager(doubanViewPaper);
		 //tab 宽度均分
		doubanTabs.setShouldExpand(true);
		doubanTabs.setDividerColor(color.white);
        //设置选中的滑动指示
		doubanTabs.setIndicatorColor(this.getResources().getColor(R.color.red));
		doubanTabs.setIndicatorHeight(3);
        //设置背景颜色
		doubanTabs.setBackgroundColor(getResources().getColor(R.color.white));
		

		return fgmView;
	}
	
	OnPageChangeListener mPagerChangerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        	doubanTabs.setTranslationX(0);
        }

        @Override
        public void onPageSelected(int position) {
//            switch (position){
//                case 0:
//                    //MyToast.makeText(MainActivity.this, "the page is " + "messager", Toast.LENGTH_SHORT);
//                    break;
//                case 1:
//                    //MyToast.makeText(MainActivity.this, "the page is " + "news", Toast.LENGTH_SHORT);
//                    break;
//                case 2:
//                    //MyToast.makeText(MainActivity.this, "the page is " + "user", Toast.LENGTH_SHORT);
//                    break;
//            }

        	GDouBanGridView sub = subs.get(position);
        	sub.firstLoadData();
        	
        }

        @Override
        public void onPageScrollStateChanged(int state) {


        }
    };


    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {

    	doubanViewPaper.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }
}


