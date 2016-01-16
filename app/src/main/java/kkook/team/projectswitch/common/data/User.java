package kkook.team.projectswitch.common.data;

/**
 * Created by Askai on 2016-01-10.
 */
public class User {
	String auth_id, nickname, phone_number, thumbnail_image, profile_image;
	String gcm_user_token;

	public User(String auth_id, String nickname, String phone_number, String thumbnail_image, String profile_image, String gcm_user_token) {
		this.auth_id = auth_id;
		this.nickname = nickname;
		this. phone_number = phone_number;
		this.thumbnail_image = thumbnail_image;
		this.profile_image = profile_image;
		this.gcm_user_token = gcm_user_token;
	}
}
