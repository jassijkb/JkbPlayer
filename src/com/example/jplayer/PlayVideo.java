package com.example.jplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayVideo extends Activity {
	int pos;
    String mediapath;
    VideoView video;
    MediaController mediaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_video);
		Intent i1=getIntent();
		 mediapath=i1.getStringExtra("mediapath");
		 video =(VideoView)findViewById(R.id.videoView1); 
		 MediaController mediaController= new MediaController(this);
         mediaController.setAnchorView(video);  
         Uri uri=Uri.parse(mediapath);
         video.setMediaController(mediaController);  
         video.setVideoURI(uri);          
         video.requestFocus();  
         video.start();  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_video, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
