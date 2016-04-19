package com.quange.girls;




import com.quange.views.GQiuBaiListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QiuBaiFragment extends Fragment {
	private View fgmView;
	private ViewPager qiubaiViewPaper;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (fgmView == null) {
			fgmView = inflater.inflate(R.layout.fragment_qiubai, container, false);
		
		}

		ViewGroup parent = (ViewGroup) fgmView.getParent();
		if (parent != null) {
			parent.removeView(fgmView);
		}
		qiubaiViewPaper = (ViewPager) fgmView.findViewById(R.id.qiubaiViewPaper);
		
		qiubaiViewPaper.addView(new GQiuBaiListView(getActivity(),0).getView());
		qiubaiViewPaper.addView(new GQiuBaiListView(getActivity(),1).getView());
		qiubaiViewPaper.addView(new GQiuBaiListView(getActivity(),1).getView());

		qiubaiViewPaper.setAdapter(new PagerAdapter() {
			public boolean isViewFromObject(View view, Object o) {
				return view == o;
			}

			public int getCount() {
				return qiubaiViewPaper.getChildCount();
			}

			public void destroyItem(View container, int position, Object object) {
			}

			public Object instantiateItem(View container, int position) {
				return qiubaiViewPaper.getChildAt(position);
			}
		});

		return fgmView;
	}
	
}
