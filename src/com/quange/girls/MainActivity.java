package com.quange.girls;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;


public class MainActivity extends FragmentActivity {

	public static FragmentTabHost mTabHost;
	private Class<?> fragmentArray[] = {DouBanFragment.class,QiuBaiFragment.class};
	private int titleArray[] = {R.string.douban,R.string.qiubai};
	private Resources re;
	private TextView tv[] = { null, null };
	private int iconArray[] = {R.drawable.btn_douban_drawable,R.drawable.btn_qiubai_drawable};
	private View mseduView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setupTabView();
    }
    public void onResume() {
		super.onResume();
		
	}

	public void onPause() {
		super.onPause();
		
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	protected void onDestroy() {
		super.onDestroy();
		
	}

	private void init() {
		re = this.getResources();
		
	}
	// 底部导航的文本颜色
		protected void setTvTextColor(String tabId) {
			for (int i = 0; i < 2; i++) {
				if (tv[i].getText().toString().equals(tabId)) {
					tv[i].setTextColor(re.getColor(R.color.color_two));
				} else {
					tv[i].setTextColor(re.getColor(R.color.black_deep));
				}
			}
		}
    private void setupTabView() {
    	mTabHost = (FragmentTabHost) findViewById(R.id.tabhost); 
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.getTabWidget().setDividerDrawable(null);
		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) {
			TabHost.TabSpec tabSpec;
			mseduView = getTabItemView(i);
			tabSpec = mTabHost.newTabSpec(re.getString(titleArray[i])).setIndicator(mseduView);
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
		}

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				setTvTextColor(tabId);
			}
		});
    }

    private View getTabItemView(int index) {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		int lyTab = R.layout.tab_item_view;
		View view = layoutInflater.inflate(lyTab, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
		imageView.setImageResource(iconArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.tv_icon);
		tv[index] = textView;
		textView.setText(re.getString(titleArray[index]));
		if (index == 0) {
			textView.setTextColor(re.getColor(R.color.bottom_tab_text_true));
		} else {
			textView.setTextColor(re.getColor(R.color.black_deep));
		}
		return view;
	}

}
