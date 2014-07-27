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
	
	//���ظ�����·��
	String path = "/storage/sdcard1/Music/��������.mp3";
	
	public MainActivity() {
		super(R.string.left_and_right);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//����mo's
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//������ҳ��Ĳ���
		setContentView(R.layout.main);
		
		//��ʼ��
		init();
		
		//��ʼ�����Ű�ť
		Button playButton = (Button)findViewById(R.id.playButton);
		playButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				
				play();
			}
			
		});
		
		//��ʼ����ͣ��ť
		Button pauseButton = (Button)findViewById(R.id.pauseButton);	
		pauseButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pause();
			}
		});
		
		
		//����໬��ҳ����fragement����
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SampleListFragment2())
		.commit();
		
		
		//���õڶ���SlidingMenu
		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		//���Ҳ໬��ҳ����fragement����
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame_two, new SampleListFragment())
		.commit();
	}	
	//��ʼ�����ֲ���
	void init(){
		//����Idle
		mediaPlayer = new MediaPlayer();
		try {
			//��ʼ��
			mediaPlayer.setDataSource(path);
		    
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			//prepare                 
			// ͨ���첽�ķ�ʽװ��ý����Դ
			mediaPlayer.prepareAsync();
			/*mediaPlayer.setOnPreparedListener(new OnPreparedListener() {                    
			@Override
			public void onPrepared(MediaPlayer mp) {
			// װ����ϻص�
				
				}
			});     */           
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//���Բ�������
	void play(){		
		mediaPlayer.start();
	}
	
	//��ͣ����   
    private void pause() {  
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {  
            mediaPlayer.pause();  
        }  
    }  
	
    //activity �˳�ʱ��ֹͣ�������֣��ͷ���Դ
	@Override
	protected void onDestroy() {
	         // ��activity������ʱ�������Դ
	         if (mediaPlayer != null && mediaPlayer.isPlaying()) {
	             mediaPlayer.stop();
	             mediaPlayer.release();
	             mediaPlayer = null;
	         }
	         super.onDestroy();
	     }
	
	
	//actionbar������
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.actionbar, menu);
		return true;
	}
	//actionbar��ѡ��
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//���home��
			//��ʾ���໬��ҳ��
			getSlidingMenu().showMenu();
			return true;
		
		case R.id.action_settings:
			//����Ҳ�menu��
			//��ʾ�Ҳ໬��ҳ��
			getSlidingMenu().showSecondaryMenu();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
