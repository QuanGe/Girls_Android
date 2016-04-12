package com.quange.girls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	private ImageView ivSp;// 闪屏的图片
	private Button btnSp;// 跳过闪屏
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_splash);
	        
	        ivSp = (ImageView) findViewById(R.id.iv_splash);
			ivSp.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startMainActivity();
				}
			});
			btnSp = (Button) findViewById(R.id.btn_skip_splashimage);
			btnSp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				
					startMainActivity();
				}
			});
	    }
	 
	 private void startMainActivity() {
		 final Intent it = new Intent(this, MainActivity.class);
		 startActivity(it);
		 
	 }
}
