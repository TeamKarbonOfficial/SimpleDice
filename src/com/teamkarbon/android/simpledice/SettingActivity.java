package com.teamkarbon.android.simpledice;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;

public class SettingActivity extends Activity {

	//Declare
	Button ButtonSave;
	CheckBox CheckBoxSound;
	CheckBox CheckBoxAnimation;
	SeekBar SeekBarDuration;
	Spinner SpinnerVibration;

	ArrayAdapter<CharSequence> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		// Show the Up button in the action bar.
		setupActionBar();

		//Initialize
		ButtonSave = (Button) findViewById(R.id.ButtonSave);
		CheckBoxSound = (CheckBox) findViewById(R.id.CheckBoxSound);
		CheckBoxAnimation = (CheckBox) findViewById(R.id.CheckBoxAnimation);
		SeekBarDuration = (SeekBar) findViewById(R.id.SeekBarDuration);
		SpinnerVibration = (Spinner) findViewById(R.id.SpinnerVibration);

		adapter = ArrayAdapter.createFromResource(this, R.array.spinner_textarray, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		SpinnerVibration.setAdapter(adapter);

		LoadSavedPreferences();
	}

	private void LoadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		//Load CheckBoxes
		boolean checkBoxSound_Value = sharedPreferences.getBoolean("checkBoxSound", false);
		if (checkBoxSound_Value == true) {
			CheckBoxSound.setChecked(true);
		} else {
			CheckBoxSound.setChecked(false);
		}

		boolean checkBoxAnimation_Value = sharedPreferences.getBoolean("checkBoxAnimation", false);
		if (checkBoxAnimation_Value == true) {
			CheckBoxAnimation.setChecked(true);
		} else {
			CheckBoxAnimation.setChecked(false);
		}

		int _SeekBarDuration = sharedPreferences.getInt("SeekBarDuration", 1);//Naming clash..
		SeekBarDuration.setProgress(_SeekBarDuration);
	}

	private void SavePreferences(String key, boolean value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				this.finish();
				return true;
			//TODO: Add functionality to the buttons/stuff (setting of values)
			case R.id.ButtonSave:
				break;

		}
		return super.onOptionsItemSelected(item);
	}

}
