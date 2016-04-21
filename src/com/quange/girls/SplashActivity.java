package com.quange.girls;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.umeng.analytics.MobclickAgent;
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
			
			MobclickAgent.updateOnlineConfig(this);
			MobclickAgent.openActivityDurationTrack(false);
			Timer timer = new Timer(); // 实例化Timer定时器对象
			timer.schedule(new TimerTask() { // schedule方法(安排,计划)需要接收一个TimerTask对象和一个代表毫秒的int值作为参数
						@Override
						public void run() {
							startMainActivity();
						}
					}, 2000);
	    }
	 
	 @Override
		protected void onResume() {
			super.onResume();
		
			MobclickAgent.onPageStart("闪屏页");
			MobclickAgent.onResume(this);
		}

		@Override
		protected void onPause() {
			super.onPause();
		
			MobclickAgent.onPageEnd("闪屏页");
			MobclickAgent.onPause(this);
		}
	 
	 private void startMainActivity() {
		 final Intent it = new Intent(this, MainActivity.class);
		 startActivity(it);
		 
	 }
}
