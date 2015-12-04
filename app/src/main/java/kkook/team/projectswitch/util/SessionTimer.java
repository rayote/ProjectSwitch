package kkook.team.projectswitch.util;

import android.content.Context;
import android.os.Handler;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Askai on 2015-12-04.
 */
public class SessionTimer {
	int maxMin, maxSec, remainSec;
	Handler handler;
	ProgressBar progressBar;
	Timer timer;
	TimerTask timerTask;

	public SessionTimer(Handler handler, ProgressBar progressBar, int maxMin) {
		this.handler = handler;
		this.progressBar = progressBar;
		this.maxMin = maxMin;

		init();
	}

	public void init() {
		maxSec = maxMin * 60;
		remainSec = maxSec;

		progressBar.setMax(maxSec);
		progressBar.setProgress(maxSec);

		timerTask = new TimerTask() {
			@Override
			public void run() {
				remainSec--;

				if(remainSec > 0)
					handler.sendEmptyMessage(remainSec);
				else
					timer.cancel();
			}
		};
		timer = new Timer();
		timer.schedule(timerTask, 0, 1000);
	}
}
