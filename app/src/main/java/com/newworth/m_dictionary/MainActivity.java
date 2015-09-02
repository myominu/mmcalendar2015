package com.newworth.m_dictionary;

import java.io.IOException;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	public static DataBaseHelper mDbHelper;
	public static SQLiteDatabase db;
	public static Typeface typefaceOriginal;


	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	SrchFragment srchFragmentMtoJ;
	SrchFragment srchFragmentJtoM;
	SrchFragment srchFragmentKtoM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setDatabase();
		typefaceOriginal = Typeface.createFromAsset(getAssets(), "fonts/zawgyi.ttf");

		if(isTablet()){
			srchFragmentKtoM = new SrchFragmentTablet(this);
		}else{
			srchFragmentKtoM = new SrchFragment(this);
		}
		srchFragmentKtoM.setMode(SrchFragment.K_TO_M_MODE);

		if(isTablet()){
			srchFragmentMtoJ = new SrchFragmentTablet(this);
		}else{
			srchFragmentMtoJ = new SrchFragment(this);
		}
		srchFragmentMtoJ.setMode(SrchFragment.M_TO_J_MODE);

		if(isTablet()){
			srchFragmentJtoM = new SrchFragmentTablet(this);
		}else{
			srchFragmentJtoM = new SrchFragment(this);
		}
		srchFragmentJtoM.setMode(SrchFragment.J_TO_M_MODE);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					public void onPageSelected(int position) {
						if (position == 0) {
						}
					}
				});

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

	public void onDestroy() {
	    db.close();
	    super.onDestroy();
	}

	/**
	 * Homeボタンが押されたときに、あとで強制終了しないように、アプリを終了させる。
	 */
	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		finish();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}

	private void setDatabase() {
	    mDbHelper = new DataBaseHelper(this);
	    try {
	        mDbHelper.createEmptyDataBase();
	        db = mDbHelper.openDataBase();
	    } catch (IOException ioe) {
	        throw new Error("Unable to create database");
	    } catch(SQLException sqle){
	        throw sqle;
	    }
	}

	public SQLiteDatabase getDB(){
		return db;
	}

	public Typeface getFont(){
		return typefaceOriginal;
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
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			switch (position) {
			case 0:
				return srchFragmentKtoM;
			case 1:
				return srchFragmentJtoM;
			case 2:
				return srchFragmentMtoJ;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "カナ検索";
			case 1:
				return "日本語検索";
			case 2:
				return "ミャンマー語検索";
			}
			return null;
		}
	}

	boolean isTablet(){
		Context context = getApplicationContext();
		Resources r = context.getResources();
		Configuration configuration = r.getConfiguration();
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
			if ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
					< Configuration.SCREENLAYOUT_SIZE_LARGE) {
				//Log.i(TAG, "Phone");
				return false;
			} else {
				//Log.i(TAG, "Tablet");
				return true;
			}
		} else {
			if (configuration.smallestScreenWidthDp < 600) {
				//Log.i(TAG, "Phone");
				return false;
			} else {
				//Log.i(TAG, "Tablet");
				return true;
			}
		}
	}
}
