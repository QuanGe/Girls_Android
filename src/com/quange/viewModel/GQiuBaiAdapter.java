package com.quange.viewModel;

import java.util.ArrayList;
import java.util.List;

import com.quange.girls.R;
import com.quange.model.GQiuBaiModel;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GQiuBaiAdapter  extends BaseAdapter {
	private Activity mAct;
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
			cv.setTag(hv);
		} else {
			hv = (HoldView) cv.getTag();
		}
		hv.contentTv.setText(ls.content);
	
		return cv;
	}

	private class HoldView {
	
		private TextView contentTv; // name
		private ImageView contentIv;
		
	}
}
