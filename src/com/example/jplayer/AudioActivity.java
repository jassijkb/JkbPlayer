package com.example.jplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class AudioActivity extends ListActivity implements Runnable,OnClickListener,OnSeekBarChangeListener {
	List<String> song=new ArrayList<String>();
	List<String> path=new ArrayList<String>();
	MediaPlayer media=new MediaPlayer();
	ArrayAdapter<String> list;
	Button stop,play;
	ImageButton next,previous;
	 private int counter;
	private SeekBar seekBar;
	static final String MEDIA_PATH=new String("/sdcard/");
	//private static final String NULL=null;
	ArrayList<HashMap<String,String>> songlist=new ArrayList<HashMap<String,String>>();
	String mp3pattern=".mp3";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);
		stop=(Button)findViewById(R.id.button1);
		play=(Button)findViewById(R.id.button2);
		next=(ImageButton)findViewById(R.id.imageButton1);
		previous=(ImageButton)findViewById(R.id.imageButton2);
		 seekBar = (SeekBar) findViewById(R.id.seekBar1);
		  stop.setOnClickListener(this);
		  play.setOnClickListener(this);
		  next.setOnClickListener(this);
		  previous.setOnClickListener(this);
		  seekBar.setOnSeekBarChangeListener(this);
		// seekBar.setMax(media.getDuration());
		  seekBar.setEnabled(false);
       
        addSongs();
	}
	public void run() {
		  int currentPosition = media.getCurrentPosition();
		  int total = media.getDuration();
		
		  while (media != null && currentPosition < total) {
		   try {
		    Thread.sleep(1000);
		    currentPosition = media.getCurrentPosition();
		   } catch (InterruptedException e) {
		    return;
		   } catch (Exception e) {
		    return;
		   }
		   seekBar.setProgress(currentPosition);
		  }
	 }
	public void onClick(View v) {
		  if (v.equals(play)) {
		   
		   if (media.isPlaying()) {
			   media.pause();
			   play.setText("play");
		   } 
		   else {
			   media.start();
			   play.setText("pause");
			  
		   }
		  }
		   if(v.equals(stop) && media != null){
			   if(media.isPlaying() || media.getDuration() > 0){
				    media.stop();
				    media = null;
				    play.setText("play");
				    seekBar.setProgress(0);
			   }
		  }
		   if(v.equals(next)){
			   if(counter<songlist.size()){
					counter=counter+1;
					try{
						 media.reset();
						 System.out.println(1);
						 media.setDataSource(path.get(counter));
						 System.out.println(2);
						
						 media.prepare();
						 media.start();
						
						        
						 }catch(Exception e){
							 e.printStackTrace();
							 System.out.println(e.getCause());
							 System.out.println(e.getMessage());
							 
						 }
				}else{
				
					Toast.makeText(AudioActivity.this,"Last Song",Toast.LENGTH_LONG).show();
				}
		   }
		   if(v.equals(previous)){
			   if(counter>0){
					counter=counter-1;
					try{
						 media.reset();
						 System.out.println(1);
						 media.setDataSource(path.get(counter));
						 System.out.println(2);
						
						 media.prepare();
						 media.start();
						
						        
						 }catch(Exception e){
							 e.printStackTrace();
							 System.out.println(e.getCause());
							 System.out.println(e.getMessage());
							 
						 }
				}else{
				
					Toast.makeText(AudioActivity.this,"First Song",Toast.LENGTH_LONG).show();
			}
		   }
		
		 }
	 public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){
		  try{
			  if(media.isPlaying() || media != null){
				  if(fromUser)
					  media.seekTo(progress);
			  } 
			  else if (media == null){
				  Toast.makeText(getApplicationContext(),"Media is not running",Toast.LENGTH_SHORT).show();
				  seekBar.setProgress(0);
			  }
		  } catch (Exception e){
			  Log.e("seek bar", "" + e);
			  seekBar.setEnabled(false);
		  	}
	 }
	

	 @Override
	 public void onStartTrackingTouch(SeekBar seekBar) {
	  // TODO Auto-generated method stub
	
	 }
	
	 @Override
	 public void onStopTrackingTouch(SeekBar seekBar) {
	  // TODO Auto-generated method stub
	
	 }
	

	protected void onListItemClick(ListView l,View v,int position,long id){
    	try{
    		counter=position;
    		media.reset();
    		media.setDataSource(path.get(position));
    		System.out.println(path.get(position)+song.get(position));
    		media.prepare();
    		media.start();
    		 //media = MediaPlayer.create(getApplicationContext(),pos );
			   seekBar.setEnabled(true);
			   seekBar.setMax(media.getDuration());
			   new Thread(this).start();
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println("1");
    		System.out.println(e.getMessage());
    	}
    }
	private void addSongs(){
    	for(HashMap<String,String>m:getPlayList()){
    		for(Map.Entry<String,String>list1:m.entrySet()){
    			song.add(list1.getValue().toString());
    			path.add(list1.getKey().toString());
    		}
    	}
    	list=new ArrayAdapter<String>(this,R.layout.songlist,song);
    	setListAdapter(list);
    }
    public ArrayList<HashMap<String,String>> getPlayList(){
    	if(MEDIA_PATH!=null){
    	File home=new File(MEDIA_PATH);
    	File[] listFiles=home.listFiles();
    	if(listFiles!=null && listFiles.length>0){
    		for(File file:listFiles){
    			if(file.isDirectory())
    				scanDirectory(file);
    			else
    				addSongToList(file);
    		}
    	}  
    	}
    return songlist;
}
    private void scanDirectory(File directory){
    	if(directory!=null){
    		File[] listFiles=directory.listFiles();
    		if(listFiles!=null && listFiles.length>0){
        		for(File file:listFiles){
        			if(file.isDirectory())
        				scanDirectory(file);
        			else
        				addSongToList(file);
        		}
    		}
    	}
    }
    private void addSongToList(File song){
    	if(song.getName().endsWith(mp3pattern)){
    		HashMap<String,String> songMap=new HashMap<String,String>();
    		songMap.put(song.getAbsolutePath(),song.getName());
    		songlist.add(songMap);
    	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.audio, menu);
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
