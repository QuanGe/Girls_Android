package com.quange.girls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import com.quange.viewModel.Constants;
import com.quange.viewModel.GAPIManager;
import com.umeng.analytics.MobclickAgent;

import uk.co.senab.photoview.PhotoView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class DouBanPhotosActivity extends Activity {
	private ViewPager photoViewPager;
	private TextView indexTV;
	private Button saveBtn;
	private String curUrl;
	private String[] allUrls ;
	private ViewGroup bannerContainer;
	private BannerView bv;
	public class CurriAdapter extends PagerAdapter {

		private View mCurrentView;
	    
	    @Override
	    public void setPrimaryItem(ViewGroup container, int position, Object object) {
	        mCurrentView = (View)object;
	    }
	                                             
	    public View getPrimaryItem() {
	        return mCurrentView;
	    }
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


	}
	 private void initBanner() {
		    this.bv = new BannerView(this, ADSize.BANNER, Constants.APPID, Constants.BannerPosID);
		    bv.setRefresh(30);
		    bv.setADListener(new AbstractBannerADListener() {

		      @Override
		      public void onNoAD(int arg0) {
		        Log.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
		      }

		      @Override
		      public void onADReceiv() {
		        Log.i("AD_DEMO", "ONBannerReceive");
		      }
		    });
		    bannerContainer.addView(bv);
		  }
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
		MobclickAgent.onEvent(this, "girls_detail");
		
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				CurriAdapter a = (CurriAdapter)photoViewPager.getAdapter();
				PhotoView p = (PhotoView) a.getPrimaryItem();
				BitmapDrawable b =  (BitmapDrawable)p.getDrawable();
				String curI =  allUrls[photoViewPager.getCurrentItem()];
				String[] names = curI.split("\\/");
				String  savename = names[names.length-1];
				//savename = "-01.jpg";
				try {
					saveBitmapToFile(b.getBitmap(),getSDPath()+"/ysw/yanshouwan/"+savename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	
		photoViewPager.setAdapter(new CurriAdapter() );
		
		for(int i = 0;i<allUrls.length;i++)
		{
			if(allUrls[i].equals(curUrl))
			{
				photoViewPager.setCurrentItem(i);
				indexTV.setText(i+"/"+allUrls.length);
				break;
			}
		}
		bannerContainer = (ViewGroup) this.findViewById(R.id.bannerContainer);
	    this.initBanner();
	    this.bv.loadAD();
	}
	
	/** 
     * Save Bitmap to a file.保存图片到SD卡。 
     *  
     * @param bitmap 
     * @param file 
     * @return error message if the saving is failed. null if the saving is 
     *         successful. 
     * @throws IOException 
     */  
    public void saveBitmapToFile(Bitmap bitmap, String _file)  
            throws IOException {//_file = <span style="font-family: Arial, Helvetica, sans-serif;">getSDPath()+"</span><span style="font-family: Arial, Helvetica, sans-serif;">/xx自定义文件夹</span><span style="font-family: Arial, Helvetica, sans-serif;">/hot.png</span><span style="font-family: Arial, Helvetica, sans-serif;">"</span>  
        BufferedOutputStream os = null;  
        MobclickAgent.onEvent(this, "girls_detail_save");
        try {  
            File file = new File(_file);  
            // String _filePath_file.replace(File.separatorChar +  
            // file.getName(), "");  
            int end = _file.lastIndexOf(File.separator);  
            String _filePath = _file.substring(0, end);  
            File filePath = new File(_filePath);  
            if (!filePath.exists()) {  
                filePath.mkdirs();  
            }  
            file.createNewFile();  
            os = new BufferedOutputStream(new FileOutputStream(file));  
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);  
        } finally {  
            if (os != null) {  
                try {  
                	os.flush();
                    os.close();  
                    Toast.makeText(this, "已经成功保存在"+_file, Toast.LENGTH_SHORT).show();
                    this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri
                            .parse("file://" + _file)));
                    
                } catch (IOException e) {  
                	System.out.println(e.getMessage());
                	Toast.makeText(this, "保存失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }  
            }  
        }  
    }  
    /** 
     * 获取SDK路径 
     * @return 
     */  
    public String getSDPath(){   
           File sdDir = null;   
           boolean sdCardExist = Environment.getExternalStorageState()     
                               .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在   
           if   (sdCardExist)     
           {                                 
             sdDir = Environment.getExternalStorageDirectory();//获取跟目录   
          }     
           return sdDir.toString();   
             
    }  
	
}
