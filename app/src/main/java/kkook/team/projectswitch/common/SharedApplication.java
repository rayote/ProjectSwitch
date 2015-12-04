package kkook.team.projectswitch.common;

import android.app.Application;

import com.kakao.sdk.common.GlobalApplication;

/**
 * Created by Askai on 2015-12-04.
 */
public class SharedApplication extends GlobalApplication {
	public static final String SERVER_URL = "http://project-switch.appspot.com/";
	public static String GCM_UserToken = null;
	public final String testStr = "GlobalApplication 테스트";

	// TODO: 여러 Activity에서 공유할 친구목록 등을 관리하기..?
}
