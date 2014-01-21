package com.yahoo.imagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchOptionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_search_options);
		// Load presettings
		SearchOptions searchOptions=(SearchOptions)getIntent().getSerializableExtra(ImageSearchActivity.SETTING_KEY_NAME);
		EditText filter=(EditText)findViewById(R.id.etSiteFilter);
		filter.setText(searchOptions.getFilter());		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_options, menu);
		return true;
	}
	
	public void showSearchActivity(MenuItem menuitem)
	{
		Intent imageSearchIntent=new Intent();
		setResult(RESULT_CANCELED, imageSearchIntent);
		finish();		
	}
	
	public void saveAdvancedSearchOptions(View view)
	{
		SearchOptions updatedSearchOptions=new SearchOptions();
		
		Spinner imageSizeSpinner=(Spinner)findViewById(R.id.spn_image_size);
		Spinner imageTypeSpinner=(Spinner)findViewById(R.id.spn_image_type);
		Spinner imageColorSpinner=(Spinner)findViewById(R.id.spn_color_filter);
		EditText filter=(EditText)findViewById(R.id.etSiteFilter);
		
		updatedSearchOptions.setImageColor(imageSizeSpinner.getSelectedItem().toString());
		updatedSearchOptions.setImageSize(imageTypeSpinner.getSelectedItem().toString());
		updatedSearchOptions.setImageType(imageColorSpinner.getSelectedItem().toString());
		updatedSearchOptions.setFilter(filter.getText().toString());
		
		Intent imageSearchIntent=new Intent();
		imageSearchIntent.putExtra(ImageSearchActivity.SETTING_KEY_NAME, updatedSearchOptions);
		setResult(RESULT_OK, imageSearchIntent);
		finish();
	}

}
