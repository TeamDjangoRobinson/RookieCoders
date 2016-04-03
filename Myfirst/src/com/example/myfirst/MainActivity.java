package com.example.myfirst;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initDisplayButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void initDisplayButton()
	{
		Button displayButton  = (Button) findViewById(R.id.button2)	;
		float r1 = R.id.button2;
		displayButton.setOnClickListener(new OnClickListener()
				{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText lastName = (EditText) findViewById(R.id.editText1);
				EditText firstName = (EditText) findViewById(R.id.editText2);
				TextView textDisplay = (TextView) findViewById(R.id.textView2);
				String nameToDisplay = firstName.getText().toString() + " " + lastName.getText().toString();
				textDisplay.setText("Hello "+ nameToDisplay);
			}
				});
		
		Button clearButton  = (Button) findViewById(R.id.button1);
		clearButton.setOnClickListener(new OnClickListener()
				{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText lastName = (EditText) findViewById(R.id.editText1); lastName.setText("");
				EditText firstName = (EditText) findViewById(R.id.editText2); firstName.setText("");
				TextView textDisplay = (TextView) findViewById(R.id.textView2);
				textDisplay.setText("Hello World!");
			}
				});
	}
}
