package com.teamkarbon.android.simpledice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;

public class SettingActivity extends Activity {

	//NOTE: Keys used: booleans: 'checkBoxSound', 'checkBoxAnimation'
	//in shared pref.  int (0 to 10) 'SeekBarDuration'
	//                 int (0, 1, 2) 'SpinnerVibrationLength'
	//IMPORTANT: MUST USE com.teamkarbon.android.simpledice PREFIX BEFORE KEY NAME TO PREVENT CONFLICT
	// (check Save Preference function

	//Declare
	Button ButtonSave;
	CheckBox CheckBoxSound;
	CheckBox CheckBoxAnimation;
	SeekBar SeekBarDuration;
	Spinner SpinnerVibration;

	ArrayAdapter<CharSequence> adapter;

	public String keyPrefix = "com.teamkarbon.android.simpledice.";

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

		//Load CheckBoxes and other controls with saved prefs
		//Much simplifications here
		CheckBoxSound.setChecked(sharedPreferences.getBoolean(keyPrefix + "checkBoxSound", false));

		CheckBoxAnimation.setChecked(sharedPreferences.getBoolean(keyPrefix + "checkBoxAnimation", false));

		int _SeekBarDuration = sharedPreferences.getInt(keyPrefix + "SeekBarDuration", 1);
		SeekBarDuration.setProgress(_SeekBarDuration);

		int VibrationLengthState;//0 - none, 1 - short, 2 - long
		SpinnerVibration.setSelection(sharedPreferences.getInt(keyPrefix + "SpinnerVibrationLength", 0));

	}

	private void SavePreferences(String key, boolean value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(keyPrefix + "" + key, value);
		editor.commit();
	}

	private void SavePreferences(String key, int value) { //Another isotope
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putInt(keyPrefix + "" + key, value);
		editor.apply();

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

		}
		return super.onOptionsItemSelected(item);
	}

	public void save_button_clicked(View v)
	{
		SavePreferences("checkBoxSound", CheckBoxSound.isChecked());
		SavePreferences("checkBoxAnimation", CheckBoxAnimation.isChecked());
		SavePreferences("SeekBarDuration", SeekBarDuration.getProgress());
		SavePreferences("SpinnerVibrationLength", SpinnerVibration.getSelectedItemPosition());
	}

}
