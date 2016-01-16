package kkook.team.projectswitch.common;

import android.app.Application;

import com.kakao.sdk.common.GlobalApplication;

/**
 * Created by Askai on 2015-12-04.
 */
public class SharedApplication extends GlobalApplication {
//	public static final String SERVER_URL = "http://project-switch.appspot.com/";
	public static final String SERVER_URL = "http://ec2-52-69-141-128.ap-northeast-1.compute.amazonaws.com:3000";
	public static final int HTTP_OK = 201;

	public static String GCM_UserToken = null;
	public static String KakaoUserId = null;
	public final String testStr = "GlobalApplication 테스트";

	// TODO: 여러 Activity에서 공유할 친구목록 등을 관리하기..?
}
