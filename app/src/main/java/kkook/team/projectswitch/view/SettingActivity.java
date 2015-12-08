package kkook.team.projectswitch.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import kkook.team.projectswitch.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("설정");
/*
		RelativeLayout relative01 = (RelativeLayout)findViewById(R.id.setting01_layout);
		relative01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
*/
		RelativeLayout relative01=(RelativeLayout)findViewById(R.id.setting01_layout);
		RelativeLayout relative02=(RelativeLayout)findViewById(R.id.setting02_layout);
		RelativeLayout relative03=(RelativeLayout)findViewById(R.id.setting03_layout);
		RelativeLayout relative04=(RelativeLayout)findViewById(R.id.setting04_layout);
		RelativeLayout relative05=(RelativeLayout)findViewById(R.id.setting05_layout);
		relative01.setOnClickListener(this);
		relative02.setOnClickListener(this);
		relative03.setOnClickListener(this);
		relative04.setOnClickListener(this);
		relative05.setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
			case android.R.id.home:
				finish();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.setting01_layout:

				Toast.makeText(v.getContext(),"01", Toast.LENGTH_SHORT).show();
				break;
			case R.id.setting02_layout:

				Toast.makeText(v.getContext(),"02", Toast.LENGTH_SHORT).show();
				break;
			case R.id.setting03_layout:

				Toast.makeText(v.getContext(),"03", Toast.LENGTH_SHORT).show();
				break;
			case R.id.setting04_layout:

				Toast.makeText(v.getContext(),"04", Toast.LENGTH_SHORT).show();
				break;
			case R.id.setting05_layout:

				Toast.makeText(v.getContext(),"05", Toast.LENGTH_SHORT).show();
				break;
		}
	}
}
