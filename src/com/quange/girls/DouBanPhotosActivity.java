package com.quange.girls;

import java.util.ArrayList;

import com.quange.viewModel.GAPIManager;

import uk.co.senab.photoview.PhotoView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DouBanPhotosActivity extends Activity {
	private ViewPager photoViewPager;
	private TextView indexTV;
	private Button saveBtn;
	private String curUrl;
	private String[] allUrls ;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photoViewPager = (ViewPager)findViewById(R.id.photosViewPager);
        indexTV = (TextView)findViewById(R.id.indexTextView);
        saveBtn = (Button)findViewById(R.id.saveBtn);
       
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			curUrl = bundle.getString("curUrl");
			String all = bundle.getString("allUrl");
			allUrls = all.split("\\*");
			
		}
       
	
		photoViewPager.setAdapter(new PagerAdapter() {
				
				public boolean isViewFromObject(View view, Object o) {
					return view == o;
				}

				public int getCount() {
					return allUrls.length;
				}

				public void destroyItem(View container, int position, Object object) {
				}

				public Object instantiateItem(View container, int position) {
					indexTV.setText(position+"/"+allUrls.length);
					PhotoView photoView = new PhotoView(container.getContext());
		            
					GAPIManager.getInstance(null).getImageLoader().displayImage(allUrls[position], photoView, GAPIManager.getInstance(null).options);
		            // Now just add PhotoView to ViewPager and return it
		            ((ViewPager) container).addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		            return photoView;
				}
		
				
				
			});
		
		for(int i = 0;i<allUrls.length;i++)
		{
			if(allUrls[i].equals(curUrl))
			{
				photoViewPager.setCurrentItem(i);
				indexTV.setText(i+"/"+allUrls.length);
				break;
			}
		}
		
		
	}
	
}
