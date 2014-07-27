package com.zhaohui.main;


import java.io.IOException;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.happyplayer.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.zhaohui.base.BaseActivity;
import com.zhaohui.base.SampleListFragment;
import com.zhaohui.base.SampleListFragment2;
import com.zhaohui.happyplayer.LeftAndRightActivity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	
	MediaPlayer mediaPlayer;
	
	//本地歌曲的路径
	String path = "/storage/sdcard1/Music/浪漫满屋.mp3";
	
	public MainActivity() {
		super(R.string.left_and_right);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//设置mo's
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//设置主页面的布局
		setContentView(R.layout.main);
		
		//初始化
		init();
		
		//初始化播放按钮
		Button playButton = (Button)findViewById(R.id.playButton);
		playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				
				play();
			}
			
		});
		
		//初始化暂停按钮
		Button pauseButton = (Button)findViewById(R.id.pauseButton);	
		pauseButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pause();
			}
		});
		
		
		//将左侧滑动页面用fragement代替
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SampleListFragment2())
		.commit();
		
		
		//设置第二个SlidingMenu
		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		//将右侧滑动页面用fragement代替
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame_two, new SampleListFragment())
		.commit();
	}	
	//初始化音乐播放
	void init(){
		//进入Idle
		mediaPlayer = new MediaPlayer();
		try {
			//初始化
			mediaPlayer.setDataSource(path);
		    
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			//prepare                 
			// 通过异步的方式装载媒体资源
			mediaPlayer.prepareAsync();
			/*mediaPlayer.setOnPreparedListener(new OnPreparedListener() {                    
			@Override
			public void onPrepared(MediaPlayer mp) {
			// 装载完毕回调
				
				}
			});     */           
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//测试播放音乐
	void play(){		
		mediaPlayer.start();
	}
	
	//暂停音乐   
    private void pause() {  
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {  
            mediaPlayer.pause();  
        }  
    }  
	
    //activity 退出时，停止播放音乐，释放资源
	@Override
	protected void onDestroy() {
	         // 在activity结束的时候回收资源
	         if (mediaPlayer != null && mediaPlayer.isPlaying()) {
	             mediaPlayer.stop();
	             mediaPlayer.release();
	             mediaPlayer = null;
	         }
	         super.onDestroy();
	     }
	
	
	//actionbar的设置
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.actionbar, menu);
		return true;
	}
	//actionbar的选择
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//点击home键
			//显示左侧侧滑动页面
			getSlidingMenu().showMenu();
			return true;
		
		case R.id.action_settings:
			//点击右侧menu键
			//显示右侧滑动页面
			getSlidingMenu().showSecondaryMenu();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
