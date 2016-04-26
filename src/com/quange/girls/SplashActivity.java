package com.quange.girls;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;

import com.quange.viewModel.Constants;

import com.umeng.analytics.MobclickAgent;
public class SplashActivity extends Activity implements SplashADListener {
	private ImageView ivSp;// 闪屏的图片
	private Button btnSp;// 跳过闪屏
	private SplashAD splashAD;
	private ViewGroup container;
	public boolean canJump = false;
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
			
			container = (ViewGroup) this.findViewById(R.id.splash_container);
		    splashAD = new SplashAD(this, container, Constants.APPID, Constants.SplashPosID, this);
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
	 
	 @Override
	  public void onADPresent() {
	    Log.i("AD_DEMO", "SplashADPresent");
	  }

	  @Override
	  public void onADClicked() {
	    Log.i("AD_DEMO", "SplashADClicked");
	  }

	  @Override
	  public void onADDismissed() {
	    Log.i("AD_DEMO", "SplashADDismissed");
	    
	  }

	  @Override
	  public void onNoAD(int errorCode) {
	    Log.i("AD_DEMO", "LoadSplashADFail, eCode=" + errorCode);
	    /** 如果加载广告失败，则直接跳转 */
	   
	  }
	  /** 开屏页最好禁止用户对返回按钮的控制 */
	  @Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
	      return true;
	    }
	    return super.onKeyDown(keyCode, event);
	  }
}
