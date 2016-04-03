package com.example.restauranttipcalculator;

import java.util.Locale;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.RatingBar;

public class MainActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		//mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		//mViewPager = (ViewPager) findViewById(R.id.pager);
		//mViewPager.setAdapter(mSectionsPagerAdapter);
		InitializeView();

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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			//InitializeView();
			return rootView;
		}
		
		
	}

	public void InitializeView()
	{
		RatingBar restaurantRating = (RatingBar) findViewById(R.id.ratingBar1);
		restaurantRating.setRating(3.5f);
		restaurantRating.setClickable(false);
		EditText x = (EditText) findViewById(R.id.editText1);
		x.setText("0.0");
		EditText y = (EditText) findViewById(R.id.editText3);
		y.setText("0.0");
		EditText z = (EditText) findViewById(R.id.editText5);
		z.setText("0.0");
		final EditText TipRate = (EditText) findViewById(R.id.editText2);
		TipRate.setText("0.0");
		TipRate.addTextChangedListener(new TextWatcher(){
			
			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
			     int count, int after) {
			   }

			@Override
			public void onTextChanged(CharSequence s, int start,
			     int before, int count) {
				
				if(s.length() != 0 )
				{
				EditText BillAmount = (EditText) findViewById(R.id.editText1);
				float amount = Float.parseFloat(BillAmount.getText().toString());
				EditText TipAmount = (EditText) findViewById(R.id.editText5);
				EditText FinalAmount = (EditText) findViewById(R.id.editText3);  
				float tip = amount * Float.parseFloat(TipRate.getText().toString()) / 100;
				TipAmount.setText(String.valueOf(tip));
			    float finalAmount = tip + amount;
			    FinalAmount.setText(String.valueOf(finalAmount));
				}
			      
			   }

			
		});
		
		final Button rate1Button = (Button) findViewById(R.id.button2);
		rate1Button.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v)
			{
				EditText BillAmount = (EditText) findViewById(R.id.editText1);
				float amount = Float.parseFloat(BillAmount.getText().toString());
				int tipRate = 15;
				EditText TipAmount = (EditText) findViewById(R.id.editText5);
				EditText FinalAmount = (EditText) findViewById(R.id.editText3);  
				float tip = amount * tipRate / 100;
				TipAmount.setText(String.valueOf(tip));
			    float finalAmount = tip + amount;
			    FinalAmount.setText(String.valueOf(finalAmount));
			    TipRate.setText(String.valueOf(tipRate));
			}
		
		});
		final Button rate2Button = (Button) findViewById(R.id.button3);
		rate2Button.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v)
			{
				EditText BillAmount = (EditText) findViewById(R.id.editText1);
				float amount = Float.parseFloat(BillAmount.getText().toString());
				int tipRate = 18;
				EditText TipAmount = (EditText) findViewById(R.id.editText5);
				EditText FinalAmount = (EditText) findViewById(R.id.editText3);  
				float tip = amount * tipRate / 100;
				TipAmount.setText(String.valueOf(tip));
			    float finalAmount = tip + amount;
			    FinalAmount.setText(String.valueOf(finalAmount));
			    TipRate.setText(String.valueOf(tipRate));
			}
		
		});
		final Button rate3Button = (Button) findViewById(R.id.button4);
		rate3Button.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v)
			{
				EditText BillAmount = (EditText) findViewById(R.id.editText1);
				float amount = Float.parseFloat(BillAmount.getText().toString());
				int tipRate = 20;
				EditText TipAmount = (EditText) findViewById(R.id.editText5);
				EditText FinalAmount = (EditText) findViewById(R.id.editText3);  
				float tip = amount * tipRate / 100;
				TipAmount.setText(String.valueOf(tip));
			    float finalAmount = tip + amount;
			    FinalAmount.setText(String.valueOf(finalAmount));
			    TipRate.setText(String.valueOf(tipRate));
			}
		
		});
		
		final Button roundButton = (Button) findViewById(R.id.button1);
		roundButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v)
			{
				EditText TipAmount = (EditText) findViewById(R.id.editText5);
				EditText FinalAmount = (EditText) findViewById(R.id.editText3);  
				
				float tipAmount = Float.parseFloat(TipAmount.getText().toString());
			    float finalAmount = Float.parseFloat(FinalAmount.getText().toString());
			    float roundedFinalAmount = Math.round(finalAmount);
			    float diff = finalAmount - roundedFinalAmount;
			    TipAmount.setText(String.valueOf(tipAmount - diff));
			    FinalAmount.setText(String.valueOf(Math.round(finalAmount)));
			    
			}
		
		});
		
		final EditText BillSplit = (EditText) findViewById(R.id.editText4);
		BillSplit.setText("0");
		BillSplit.addTextChangedListener(new TextWatcher(){
			
			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
			     int count, int after) {
			   }

			@Override
			public void onTextChanged(CharSequence s, int start,
			     int before, int count) {
				
				if(s.length() != 0 )
				{
				int num = Integer.parseInt(BillSplit.getText().toString());
				EditText FinalAmount = (EditText) findViewById(R.id.editText3);
				TextView ShareView = (TextView) findViewById(R.id.textView7);
				float x = Float.parseFloat(FinalAmount.getText().toString());
				float finalShare = x / num;
				String prefix = getResources().getString(R.string.ShareLabel);
			    ShareView.setText(prefix + " " + String.valueOf(finalShare));
				}
			      
			   }

			
		});
		
		final SeekBar TipBar = (SeekBar) findViewById(R.id.seekBar1);
		TipBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {       

		    @Override       
		    public void onStopTrackingTouch(SeekBar seekBar) {      
		        // TODO Auto-generated method stub      
		    }       

		    @Override       
		    public void onStartTrackingTouch(SeekBar seekBar) {     
		        // TODO Auto-generated method stub      
		    }       

		    @Override       
		    public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {     
		        // TODO Auto-generated method stub      

		        TipRate.setText(String.valueOf(progress));

		    }
		});
	}
	
	
	
	
}
