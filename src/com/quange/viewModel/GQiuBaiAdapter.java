package com.quange.viewModel;

import java.util.ArrayList;
import java.util.List;


import com.android.volley.toolbox.*;
import com.quange.girls.R;
import com.quange.model.GQiuBaiModel;
import com.quange.model.GQiuBaiModel.GQiuBaiImageSizeModel;
import com.quange.views.RoundImageView;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class GQiuBaiAdapter  extends BaseAdapter {
	private Activity mAct;
	private float density = 0;
	private int screenWidth = 0;
	private List<GQiuBaiModel> mlList = new ArrayList<GQiuBaiModel>();
	public GQiuBaiAdapter(Activity act, List<GQiuBaiModel>lList) {
		this.mAct = act;
		this.mlList = lList;
	}
	@Override
	public int getCount() {
		return mlList.size();
	}

	@Override
	public GQiuBaiModel getItem(int position) {
		return mlList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View cv, ViewGroup parent) {
		GQiuBaiModel ls = getItem(position);
		HoldView hv = null;
		if (null == cv) {
			hv = new HoldView();
			cv = View.inflate(mAct, R.layout.list_item_qiubai, null);

			hv.contentTv = (TextView) cv.findViewById(R.id.tv_title);
			hv.contentIv = (ImageView) cv.findViewById(R.id.contentImageView);
			hv.userIcon = (RoundImageView) cv.findViewById(R.id.userIcon);
			hv.userNickName = (TextView) cv.findViewById(R.id.userNickName);
			
			cv.setTag(hv);
		} else {
			hv = (HoldView) cv.getTag();
		}
		
		hv.userIcon.setType(RoundImageView.TYPE_CIRCLE);
		hv.contentTv.setText(ls.content);
		
		if(ls.user != null)
		{
			hv.userNickName.setText(ls.user.login);
			String icon = ls.user.icon;
			String userId = ls.user.id+"";
			String prefixUserId = userId.substring(0, userId.length()-4);
			String userImageURL = "http://pic.qiushibaike.com/system/avtnew/"+prefixUserId+"/"+userId+"/medium/"+icon;
			GAPIManager.getInstance(mAct).getImageLoader().displayImage(userImageURL, hv.userIcon, GAPIManager.getInstance(mAct).userIconOptions);
		}
		else
		{
			hv.userIcon.setImageResource(R.drawable.qiubai_normal);
			hv.userNickName.setText("匿名");
		}
		
		{
			int height = 0;
			if(ls.format.equals("image"))
				height = imageHeight(ls.image_size);
			else if(ls.format.equals("video"))
				height = LayoutParams.MATCH_PARENT;
			LinearLayout.LayoutParams llp = new LayoutParams((int) (screenWidth-24*density),height );
			llp.leftMargin = (int) (12*density);
			llp.bottomMargin = (int) (2*density);
			hv.contentIv.setScaleType(ScaleType.FIT_XY);
			hv.contentIv.setLayoutParams(llp);
		}
		if(!ls.format.equals("word"))
		{
			String imageId = ls.id+"";
			String prefiximageId = imageId.substring(0, imageId.length()-4);
			String image = ls.image;
			String url = "http://pic.qiushibaike.com/system/pictures/"+prefiximageId+"/"+imageId+"/medium/"+image;
			GAPIManager.getInstance(mAct).getImageLoader().displayImage(url, hv.contentIv, GAPIManager.getInstance(mAct).options);
		}
		
		
		
		return cv;
	}
	
	private int imageHeight(GQiuBaiImageSizeModel imagesize)
	{
		DisplayMetrics metrics = new DisplayMetrics();
		mAct.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		density = metrics.density;
		screenWidth = metrics.widthPixels;
		float height = (metrics.widthPixels - 24*density) *((float)(imagesize.m[1])/(float)imagesize.m[0] );
		return (int)height;
		
	}

	private class HoldView {
	
		private TextView contentTv; // name
		private ImageView contentIv;
		private RoundImageView userIcon;
		private TextView userNickName;
		
	}
}
