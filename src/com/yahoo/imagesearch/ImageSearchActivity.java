package com.yahoo.imagesearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ImageSearchActivity extends Activity 
{
	private static final int SEARCH_OPTIONS_REQUEST_CODE=123;
	public static final String SETTING_KEY_NAME="settings";
	
	Button btnSearch;
	EditText etSearch;
	GridView gvResults;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	String queryString = "";
	String query="";
	Map<String, String> sizeMap = new HashMap<String, String>();
	
	
	private SearchOptions searchOptions=new SearchOptions();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setUpViews();
		setUpMaps();
		
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("resultImage", imageResult);
				startActivity(i);
			}

		
		});
		
        gvResults.setOnScrollListener(new EndlessScrollListener() {
	    @Override
	    public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
	        customLoadMoreDataFromApi(page); 
                // or customLoadMoreDataFromApi(totalItemsCount); 
	    }
        });		
	}

	
	public void searchImages(View view)
	{
		query = etSearch.getText().toString();
		if(query.isEmpty()){
			Toast.makeText(this, "Please enter a search item", Toast.LENGTH_SHORT).show();
			return;
		}
		imageResults.clear();
		Toast.makeText(this, "Searching for "+query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(getQueryString(0) + Uri.encode(query),
				new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response){
				JSONArray imageJsonResults = null;
				
				try{
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			
		});

	}
	
	public void showAdvancedSearchOptions(MenuItem item)
	{
		Intent intentSearchOptions=new Intent(this, SearchOptionsActivity.class);
		// Pass current options
		intentSearchOptions.putExtra(ImageSearchActivity.SETTING_KEY_NAME, this.searchOptions);
		startActivityForResult(intentSearchOptions, ImageSearchActivity.SEARCH_OPTIONS_REQUEST_CODE);
	}

    public void customLoadMoreDataFromApi(int offset) {
    	AsyncHttpClient client = new AsyncHttpClient();
		client.get(getQueryString(offset*8) + Uri.encode(query),
				new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response){
				JSONArray imageJsonResults = null;
				try{
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));	
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			
		});
    	
    	
    }

	private String getQueryString(int offset){
		String queryStr = "https://ajax.googleapis.com/ajax/services/search/images?"+"&rsz=8&v=1.0"+
							"&imgsz="+sizeMap.get(searchOptions.getImageSize()) +
							"&imgcolor="+searchOptions.getImageColor() +
							"&imgtype="+searchOptions.getImageType()+
							"&as_sitesearch="+searchOptions.getFilter()+
							"&start="+offset+
							"&q=";
		
		return queryStr;
	}
	
	private void setUpViews(){
		btnSearch = (Button) findViewById(R.id.btnSearch);
		etSearch = (EditText) findViewById(R.id.etSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
	}
	
	private void setUpMaps(){
		sizeMap.put("small", "icon");
		sizeMap.put("medium","medium");
		sizeMap.put("large","xxlarge");
		sizeMap.put("extralarge", "huge");
	}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(requestCode==ImageSearchActivity.SEARCH_OPTIONS_REQUEST_CODE && resultCode==RESULT_OK)
		{
			this.searchOptions=(SearchOptions)data.getSerializableExtra(ImageSearchActivity.SETTING_KEY_NAME);
			Toast.makeText(this, this.searchOptions.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}

}
