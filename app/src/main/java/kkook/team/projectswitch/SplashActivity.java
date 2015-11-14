package kkook.team.projectswitch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kakao.sdk.sample.common.KakaoLoginActivity;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		((ImageView) findViewById(R.id.imgProjectSwitchIcon)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.set_hover_short));
		((ImageView) findViewById(R.id.imgProjectSwitchLabel)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.set_hover_short));
//		((TextView) findViewById(R.id.tvTouchToGoNext)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.sanim.set_blink_fast));

		findViewById(R.id.imgProjectSwitchIcon).postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, KakaoLoginActivity.class));
				finish();
			}
		}, 2000);
	}

	/*@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP) {
			startActivity(new Intent(this, KakaoLoginActivity.class));
			finish();
		}
		return super.onTouchEvent(event);
	}*/
}
