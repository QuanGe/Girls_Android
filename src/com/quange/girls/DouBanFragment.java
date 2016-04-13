package com.quange.girls;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DouBanFragment extends Fragment {
	private View fgmView;
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
		return fgmView;
	}
}
