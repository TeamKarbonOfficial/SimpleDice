package com.teamkarbon.android.simpledice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.Random;
 
public class MainActivity extends Activity {
    //NOTE: Keys used: booleans: 'checkBoxSound', 'checkBoxAnimation'
    //in shared pref.  int (0 to 10) 'SeekBarDuration'
    //                 int (0, 1, 2) 'SpinnerVibrationLength'
    //IMPORTANT: MUST USE com.teamkarbon.android.simpledice PREFIX BEFORE KEY NAME TO PREVENT CONFLICT
    // (check Save Preference function

	//Settings
	boolean sound = false, animation = true, vibration = false;
	int duration = 1;

    public String keyPrefix = "com.teamkarbon.android.simpledice.";
	
	int number, max = 6, min = 1, countdown, beep;
	float volume = 1;
	
	//Declare
	RadioGroup radioGroup1;
	RadioButton radio0;
	RadioButton radio1;
	RadioButton radio2;
	Button RDButton;
	TextView DiceDisplay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new EULA(this).show();
		
		//Initialize
		RDButton = (Button) findViewById(R.id.RDButton);
		DiceDisplay = (TextView) findViewById(R.id.DiceDisplay);
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		radio0 = (RadioButton) findViewById(R.id.radio0);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio2 = (RadioButton) findViewById(R.id.radio2);

		SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		beep = sp.load(this, R.raw.beep_sound_01, 1);
		
		Vibrator v = (Vibrator) getSystemService (Context.VIBRATOR_SERVICE);
		
		sp.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool sp, int sampleId, int status) {
            	sp.play(beep, 20, 20, 1, 0, 1f);
            } 
      });

		addListenerOnradioGroup1();
		addListenerOnRDButton(sp, v);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sound = sharedPreferences.getBoolean(keyPrefix + "checkBoxSound", false);
        animation = sharedPreferences.getBoolean(keyPrefix + "checkBoxAnimation", false);
        duration = sharedPreferences.getInt(keyPrefix + "SeekBarDuration", 1) * 100;

	}
	
	//RadioGroup Listener
	private void addListenerOnradioGroup1() {	
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				
				//Checking RadioButton Selected
				if(radio0.isChecked()) {
					max = 5;
					min = 1;
				} else if(radio1.isChecked()) {
					max = 11;
					min = 2;
				} else if(radio2.isChecked()) {
					max = 16;
					min= 3;
				} else {

				}

			}
		});
	}

	// Button Listener
	private void addListenerOnRDButton(final SoundPool sp, final Vibrator v) {
		RDButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//[Setting] Animation
				if (animation == true) {
					countdown = 100;
					new CountDownTimer(duration, countdown) {
						@Override
						public void onFinish() {
							
							//Change TextView
							RDButton.setText("Roll Dice!");
							
							//Button ClickAble
							RDButton.setEnabled(true);
						}

						@Override
						public void onTick(long millisUntilFinished) {

							// [Setting] Sound
							if (sound == true) {
								sp.play(beep, volume, volume, 1, 0, 1f);
							}

							// Change TextView
							RDButton.setText("Rolling . . .");

							// Button UnClickAble
							RDButton.setEnabled(false);

							random(v);

							countdown = countdown + 200;
						}
					}.start();
				} else {
					random(v);
				}
			}
		});
	}

	//Generates Random Number
	private void random(Vibrator v) {
		// Generates a random number
		Random randomdice = new Random();
		number = randomdice.nextInt(max) + min;
		
		//Vibration
		if (vibration == true) {
			v.vibrate(duration);
		}
		
		//Update DiceDisplay
		DiceDisplay.setText(String.valueOf(number));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_settings:
		//	Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
			Intent settingintent = new Intent(this, SettingActivity.class);
			startActivity(settingintent);
			return true;
			
		case R.id.action_about:
			Intent aboutintent = new Intent(this, AboutActivity.class);
			startActivity(aboutintent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}