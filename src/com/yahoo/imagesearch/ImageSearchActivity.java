package com.yahoo.imagesearch;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ImageSearchActivity extends Activity 
{
	private static final int SEARCH_OPTIONS_REQUEST_CODE=123;
	public static final String SETTING_KEY_NAME="settings";
	
	private SearchOptions searchOptions=new SearchOptions();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
	}

	
	public void searchImages(View view)
	{
		EditText etSearch=((EditText)findViewById(R.id.etSearch));
		String searchTerm=etSearch.getText().toString();
		if(searchTerm.length()>0)
		{
			Toast.makeText(this, "Search results coming shortly...", Toast.LENGTH_SHORT).show();
		}
		else		
			Toast.makeText(this, "Enter search term", Toast.LENGTH_SHORT).show();
	}
	
	public void showAdvancedSearchOptions(MenuItem item)
	{
		Intent intentSearchOptions=new Intent(this, SearchOptionsActivity.class);
		// Pass current options
		intentSearchOptions.putExtra(ImageSearchActivity.SETTING_KEY_NAME, this.searchOptions);
		startActivityForResult(intentSearchOptions, ImageSearchActivity.SEARCH_OPTIONS_REQUEST_CODE);
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
