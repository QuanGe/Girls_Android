package com.quange.viewModel;

import java.util.ArrayList;
import java.util.List;

import com.quange.girls.R;
import com.quange.model.GDouBanModel;


import com.quange.views.RoundImageView;

import android.app.Activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class GDouBanAdapter extends BaseAdapter {

	private Activity mAct;
	private float density = 0;
	private int screenWidth = 0;
	private List<GDouBanModel> mlList = new ArrayList<GDouBanModel>();
	public GDouBanAdapter(Activity act, List<GDouBanModel>lList) {
		this.mAct = act;
		this.mlList = lList;
	}
	@Override
	public int getCount() {
		return mlList.size();
	}

	@Override
	public GDouBanModel getItem(int position) {
		return mlList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View cv, ViewGroup parent) {
		GDouBanModel ls = getItem(position);
		HoldView hv = null;
		if (null == cv) {
			hv = new HoldView();
			cv = View.inflate(mAct, R.layout.gridview_item_douban, null);

			hv.contentIv = (ImageView) cv.findViewById(R.id.contentIv);
		
			
			cv.setTag(hv);
		} else {
			hv = (HoldView) cv.getTag();
		}
		
		//hv.contentIv.setType(RoundImageView.TYPE_ROUND);
		//hv.contentIv.setBorderRadius(5);
		hv.contentIv.setScaleType(ScaleType.CENTER_CROP);
		GAPIManager.getInstance(mAct).getImageLoader().displayImage(ls.imageUrlStr, hv.contentIv, GAPIManager.getInstance(mAct).options);
		
		return cv;
	}


	private class HoldView {
	
	
		private ImageView contentIv;
	
		
	}

}
