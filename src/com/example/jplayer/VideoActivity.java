package com.example.jplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VideoActivity extends ListActivity {
	List<String> song=new ArrayList<String>();
	List<String> path=new ArrayList<String>();
	ArrayAdapter<String> list;
	
	static final String MEDIA_PATH=new String("/sdcard/");
	//private static final String NULL=null;
	ArrayList<HashMap<String,String>> videolist=new ArrayList<HashMap<String,String>>();
	String mp4pattern=".mp4";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		 addSongs();
	}

	protected void onListItemClick(ListView l,View v,int position,long id){
		Intent i=new Intent(VideoActivity.this,PlayVideo.class);	
		i.putExtra("mediapath", path.get(position));
		System.out.println(path.get(position));
		startActivity(i);
    }
    private void addSongs(){
    	for(HashMap<String,String>m:getPlayList()){
    		for(Map.Entry<String,String>list1:m.entrySet()){
    			song.add(list1.getValue().toString());
    			path.add(list1.getKey().toString());
    		}
    	}
    	list=new ArrayAdapter<String>(this,R.layout.videolist,song);
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
    return videolist;
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
	if(song.getName().endsWith(mp4pattern)){
		HashMap<String,String> songMap=new HashMap<String,String>();
		songMap.put(song.getAbsolutePath(),song.getName());
		videolist.add(songMap);
	}
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video, menu);
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
