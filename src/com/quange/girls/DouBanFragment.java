package com.quange.girls;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quange.model.GQiuBaiModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DouBanFragment extends Fragment {
	private View fgmView;
//	public static class abc {
//		public int a;
//	
//	}
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
		
		
		
//		String json = "[{\"a\":100,\"b\":100},{\"a\":100,\"c\":100},{\"a\":100,\"b\":null}]";
//		try {
//			List<abc> result = new Gson().fromJson(json,
//					new TypeToken<List<abc>>() {
//					}.getType());
//			
//			System.out.println(result);
//		} catch (Exception e) {
//			System.out.println("caocaocaocaocao");
//		}
		return fgmView;
	}
}


