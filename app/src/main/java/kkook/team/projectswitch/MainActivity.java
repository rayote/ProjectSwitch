package kkook.team.projectswitch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	RelativeLayout relativeLayout;
	ImageView imgBtnSwitch;
	ArrayList<ImageView> alImgBtnSideSwitch;
	float deltaX = 0, deltaY = 0;
	private enum DIRECTION {
		TOP(0), RIGHT(1), BOTTOM(2), LEFT(3);

		private int val;
		private DIRECTION(int val) {
			this.val = val;
		}
		public int value() {
			return this.val;
		}
	};
	final int DEFAULT_SELECTED_MINUTES = 15;
	int selectedMin = DEFAULT_SELECTED_MINUTES;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
		imgBtnSwitch = (ImageView) findViewById(R.id.imgBtnSwitch);
		imgBtnSwitch.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int X = (int) event.getRawX();
				int Y = (int) event.getRawY();
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						((ImageView) v).setImageResource(R.drawable.ic_switch_on);
						int initLeftMargin = (relativeLayout.getWidth() - v.getWidth()) / 2;
						int initTopMargin = (relativeLayout.getHeight() - v.getHeight()) / 2;
						layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
						deltaX = X - initLeftMargin;
						deltaY = Y - initTopMargin;
						break;
					case MotionEvent.ACTION_UP:
						((ImageView) v).setImageResource(R.drawable.ic_switch_off);
						layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
						v.setLayoutParams(layoutParams);

						Intent i = new Intent(getApplicationContext(), FriendListActivity.class);
						i.putExtra("selectedMin", selectedMin);
						startActivity(i);
						finish();
						break;
					case MotionEvent.ACTION_MOVE:
						// Drag the center switch only within the gray area
						final int maxMove = 200;
						final int centerPosX = relativeLayout.getWidth() / 2;
						final int centerPosY = relativeLayout.getHeight() / 2;
						final double dist = Math.sqrt(Math.pow(X - centerPosX, 2) + Math.pow(Y - centerPosY, 2));
						final double angleX = Math.acos((double) (X - centerPosX) / dist);
						final double angleY = Math.asin((double) (Y - centerPosY) / dist);

						if(dist > maxMove) {
							X = (int) (centerPosX + Math.cos(angleX) * maxMove);
							Y = (int) (centerPosY + Math.sin(angleY) * maxMove);
						}

						layoutParams.leftMargin = (int) (X - deltaX);
						layoutParams.topMargin = (int) (Y - deltaY);
						v.setLayoutParams(layoutParams);

						// Turn on particular quick slot by dragging the center switch
						// FIXME: get a time value of quick slots from saved preference
//						((TextView) findViewById(R.id.tvBtnSideTop)).setText(String.format("angleX= %f", angleX));
//						((TextView) findViewById(R.id.tvBtnSideBottom)).setText(String.format("angleY= %f", angleY));
						final int minMove = 120;
						if(dist > minMove) {
							if (angleX >= 0.8 && angleX < 2.4 && angleY < -0.8) {
								alImgBtnSideSwitch.get(DIRECTION.TOP.value()).setImageResource(R.drawable.side_switch_top_on);
								selectedMin = 15;
							} else
								alImgBtnSideSwitch.get(DIRECTION.TOP.value()).setImageResource(R.drawable.side_switch_top_off);
							if (angleX < 0.8 && angleY >= -0.8 && angleY < 0.8) {
								alImgBtnSideSwitch.get(DIRECTION.RIGHT.value()).setImageResource(R.drawable.side_switch_right_on);
								selectedMin = 30;
							} else
								alImgBtnSideSwitch.get(DIRECTION.RIGHT.value()).setImageResource(R.drawable.side_switch_right_off);
							if (angleX >= 0.8 && angleX < 2.4 && angleY >= 0.8) {
								alImgBtnSideSwitch.get(DIRECTION.BOTTOM.value()).setImageResource(R.drawable.side_switch_bottom_on);
								selectedMin = 45;
							} else
								alImgBtnSideSwitch.get(DIRECTION.BOTTOM.value()).setImageResource(R.drawable.side_switch_bottom_off);
							if (angleX >= 2.4 && angleY >= -0.8 && angleY < 0.8) {
								alImgBtnSideSwitch.get(DIRECTION.LEFT.value()).setImageResource(R.drawable.side_switch_left_on);
								selectedMin = 60;
							}else
								alImgBtnSideSwitch.get(DIRECTION.LEFT.value()).setImageResource(R.drawable.side_switch_left_off);
						}
						break;
				}

				relativeLayout.invalidate();
				return true;
			}
		});

		alImgBtnSideSwitch = new ArrayList<ImageView>();
		alImgBtnSideSwitch.add((ImageView) findViewById(R.id.imgBtnSideTop));
		alImgBtnSideSwitch.add((ImageView) findViewById(R.id.imgBtnSideRight));
		alImgBtnSideSwitch.add((ImageView) findViewById(R.id.imgBtnSideBottom));
		alImgBtnSideSwitch.add((ImageView) findViewById(R.id.imgBtnSideLeft));
	}


}
