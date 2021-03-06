package kkook.team.projectswitch.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kkook.team.projectswitch.common.Definition;
import kkook.team.projectswitch.common.FriendItem;
import kkook.team.projectswitch.R;
import kkook.team.projectswitch.common.SharedApplication;
import kkook.team.projectswitch.view.InteractInfoActivity;
import kkook.team.projectswitch.view.ProfileActivity;


/**
 * UserAdapter - Custom ListView를 구현하기 위해 하나의 아이템 정보와 레이아웃을 가져와서 합친다.
 *
 * @author Cloud Travel
 */
public class ListItemAdapter extends BaseAdapter implements OnClickListener {

	// Activity에서 가져온 객체정보를 저장할 변수
	private FriendItem mUser;
	private Context mContext;
	private Context mContext_tmp;

	private int nSelectedItemNum; //Number of selected data

	private int mListTypeInfo;

	// ListView 내부 View들을 가르킬 변수들
	private ImageView imgUserIcon;
	private TextView tvUserName;
	private TextView tvUserSubText;
	private ImageButton btnImg_01;
	private ImageButton btnImg_02;

	// 리스트 아이템 데이터를 저장할 배열
	private ArrayList<FriendItem> mUserData;

	public ListItemAdapter(Context context) {
		super();
		mListTypeInfo = 0;
		mContext = context;
		mUserData = new ArrayList<FriendItem>();
	}

	public ListItemAdapter(Context context, int listtypeinfo) {
		super();
		mListTypeInfo = listtypeinfo;
		mContext = context;
		mUserData = new ArrayList<FriendItem>();
	}


	public ListItemAdapter() {
		nSelectedItemNum = 0;
	}

	@Override
	/**
	 * @return 아이템의 총 개수를 반환
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		return mUserData.size();
	}

	@Override
	/**
	 * @return 선택된 아이템을 반환
	 */
	public FriendItem getItem(int position) {
		// TODO Auto-generated method stub
		return mUserData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	/**
	 * getView
	 *
	 * @param position - 현재 몇 번째로 아이템이 추가되고 있는지 정보를 갖고 있다.
	 * @param convertView - 현재 사용되고 있는 어떤 레이아웃을 가지고 있는지 정보를 갖고 있다.
	 * @param parent - 현재 뷰의 부모를 지칭하지만 특별히 사용되지는 않는다.
	 * @return 리스트 아이템이 저장된 convertView
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;

		if (mListTypeInfo == Definition.NONE) {
			// 리스트 아이템이 새로 추가될 경우에는 v가 null값이다.
			// view는 어느 정도 생성된 뒤에는 재사용이 일어나기 때문에 효율을 위해서 해준다.
			if (view == null) {
				// inflater를 이용하여 사용할 레이아웃을 가져옵니다.

				view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.list_item, null);

				// 레이아웃이 메모리에 올라왔기 때문에 이를 이용하여 포함된 뷰들을 참조할 수 있습니다.
				imgUserIcon = (ImageView) view.findViewById(R.id.user_icon);
				tvUserName = (TextView) view.findViewById(R.id.user_name);
				tvUserSubText = (TextView) view.findViewById(R.id.user_sub_text);
				btnImg_01 = (ImageButton) view.findViewById(R.id.btn_01);
				btnImg_02 = (ImageButton) view.findViewById(R.id.btn_02);
				btnImg_01.setFocusable(false);
				btnImg_02.setFocusable(false);
			}


			// 받아온 position 값을 이용하여 배열에서 아이템을 가져온다.
			mUser = getItem(position);


			// Tag를 이용하여 데이터와 뷰를 묶습니다.
			btnImg_01.setTag(mUser);
			btnImg_02.setTag(mUser);
			// 데이터의 실존 여부를 판별합니다.
			if (mUser != null) {
				// 데이터가 있다면 갖고 있는 정보를 뷰에 알맞게 배치시킵니다.
				if (mUser.getUserIcon() != null) {
					imgUserIcon.setImageDrawable(mUser.getUserIcon());
				}
				tvUserName.setText(mUser.getUserName());
				tvUserSubText.setText(mUser.getUserSubtext());
				btnImg_01.setOnClickListener(this);
				btnImg_02.setOnClickListener(this);
			}

		} else if (mListTypeInfo == Definition.ADDFRIEND) {
			// 리스트 아이템이 새로 추가될 경우에는 v가 null값이다.
			// view는 어느 정도 생성된 뒤에는 재사용이 일어나기 때문에 효율을 위해서 해준다.
			if (view == null) {
				// inflater를 이용하여 사용할 레이아웃을 가져옵니다.

				view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.list_item_addfriend, null);

				// 레이아웃이 메모리에 올라왔기 때문에 이를 이용하여 포함된 뷰들을 참조할 수 있습니다.
				imgUserIcon = (ImageView) view.findViewById(R.id.user_icon);
				tvUserName = (TextView) view.findViewById(R.id.user_name);
			}


			// 받아온 position 값을 이용하여 배열에서 아이템을 가져온다.
			mUser = getItem(position);


			// 데이터의 실존 여부를 판별합니다.
			if (mUser != null) {
				// 데이터가 있다면 갖고 있는 정보를 뷰에 알맞게 배치시킵니다.
				if (mUser.getUserIcon() != null) {
					imgUserIcon.setImageDrawable(mUser.getUserIcon());
				}
				tvUserName.setText(mUser.getUserName());
			}

		} else if (mListTypeInfo == Definition.SENDMSG) {
			// 리스트 아이템이 새로 추가될 경우에는 v가 null값이다.
			// view는 어느 정도 생성된 뒤에는 재사용이 일어나기 때문에 효율을 위해서 해준다.
			if (view == null) {
				// inflater를 이용하여 사용할 레이아웃을 가져옵니다.

				view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.list_item_sendmsg, null);

				// 레이아웃이 메모리에 올라왔기 때문에 이를 이용하여 포함된 뷰들을 참조할 수 있습니다.
				imgUserIcon = (ImageView) view.findViewById(R.id.user_icon);
				tvUserName = (TextView) view.findViewById(R.id.user_name);
				btnImg_01 = (ImageButton) view.findViewById(R.id.btn_01);
				btnImg_01.setFocusable(false);
			}


			// 받아온 position 값을 이용하여 배열에서 아이템을 가져온다.
			mUser = getItem(position);

			// Tag를 이용하여 데이터와 뷰를 묶습니다.
			btnImg_01.setTag(mUser);
			btnImg_01.setTag(mUser);

			// 데이터의 실존 여부를 판별합니다.
			if (mUser != null) {
				// 데이터가 있다면 갖고 있는 정보를 뷰에 알맞게 배치시킵니다.
				if (mUser.getUserIcon() != null) {
					imgUserIcon.setImageDrawable(mUser.getUserIcon());
				}
				tvUserName.setText(mUser.getUserName());
				btnImg_01.setOnClickListener(this);
			}



		} else if (mListTypeInfo == Definition.ADDFRIENDNAVI) {
			// 리스트 아이템이 새로 추가될 경우에는 v가 null값이다.
			// view는 어느 정도 생성된 뒤에는 재사용이 일어나기 때문에 효율을 위해서 해준다.
			if (view == null) {
				// inflater를 이용하여 사용할 레이아웃을 가져옵니다.

				view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.list_item_addfriend_navi, null);

				// 레이아웃이 메모리에 올라왔기 때문에 이를 이용하여 포함된 뷰들을 참조할 수 있습니다.
				imgUserIcon = (ImageView) view.findViewById(R.id.user_icon);
				tvUserName = (TextView) view.findViewById(R.id.user_name);
				btnImg_01 = (ImageButton) view.findViewById(R.id.btn_01);
				btnImg_02 = (ImageButton) view.findViewById(R.id.btn_02);
				btnImg_01.setFocusable(false);
				btnImg_02.setFocusable(false);
			}


			// 받아온 position 값을 이용하여 배열에서 아이템을 가져온다.
			mUser = getItem(position);

			// Tag를 이용하여 데이터와 뷰를 묶습니다.
			btnImg_01.setTag(mUser);
			// 데이터의 실존 여부를 판별합니다.
			if (mUser != null) {
				// 데이터가 있다면 갖고 있는 정보를 뷰에 알맞게 배치시킵니다.
				if (mUser.getUserIcon() != null) {
					imgUserIcon.setImageDrawable(mUser.getUserIcon());
				}
				tvUserName.setText(mUser.getUserName());
				btnImg_01.setOnClickListener(this);
				btnImg_02.setOnClickListener(this);
			}

		} else if (mListTypeInfo == Definition.MANAGEFRIEND) {
			// 리스트 아이템이 새로 추가될 경우에는 v가 null값이다.
			// view는 어느 정도 생성된 뒤에는 재사용이 일어나기 때문에 효율을 위해서 해준다.
			if (view == null) {
				// inflater를 이용하여 사용할 레이아웃을 가져옵니다.

				view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.list_item_manage, null);

				// 레이아웃이 메모리에 올라왔기 때문에 이를 이용하여 포함된 뷰들을 참조할 수 있습니다.
				imgUserIcon = (ImageView) view.findViewById(R.id.user_icon);
				tvUserName = (TextView) view.findViewById(R.id.user_name);
				btnImg_01 = (ImageButton) view.findViewById(R.id.btn_01);
				btnImg_01.setFocusable(false);
			}


			// 받아온 position 값을 이용하여 배열에서 아이템을 가져온다.
			mUser = getItem(position);


			// Tag를 이용하여 데이터와 뷰를 묶습니다.
			btnImg_01.setTag(mUser);

			// 데이터의 실존 여부를 판별합니다.
			if (mUser != null) {
				// 데이터가 있다면 갖고 있는 정보를 뷰에 알맞게 배치시킵니다.
				if (mUser.getUserIcon() != null) {
					imgUserIcon.setImageDrawable(mUser.getUserIcon());
				}
				tvUserName.setText(mUser.getUserName());
//                tvUserSubText.setText(mUser.getUserSubtext());
				btnImg_01.setOnClickListener(this);
			}

		} else if (mListTypeInfo == Definition.INTERACTIONINFO) {
			// 리스트 아이템이 새로 추가될 경우에는 v가 null값이다.
			// view는 어느 정도 생성된 뒤에는 재사용이 일어나기 때문에 효율을 위해서 해준다.
			if (view == null) {
				// inflater를 이용하여 사용할 레이아웃을 가져옵니다.

				view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.list_item_inter_info, null);

				// 레이아웃이 메모리에 올라왔기 때문에 이를 이용하여 포함된 뷰들을 참조할 수 있습니다.
				imgUserIcon = (ImageView) view.findViewById(R.id.user_icon);
				tvUserName = (TextView) view.findViewById(R.id.user_name);
				tvUserSubText = (TextView) view.findViewById(R.id.user_sub_text);
				btnImg_01 = (ImageButton) view.findViewById(R.id.btn_01);
				btnImg_02 = (ImageButton) view.findViewById(R.id.btn_02);
				btnImg_01.setFocusable(false);
				btnImg_02.setFocusable(false);
			}


			// 받아온 position 값을 이용하여 배열에서 아이템을 가져온다.
			mUser = getItem(position);


			// Tag를 이용하여 데이터와 뷰를 묶습니다.
			btnImg_01.setTag(mUser);
			btnImg_02.setTag(mUser);
			// 데이터의 실존 여부를 판별합니다.
			if (mUser != null) {
				// 데이터가 있다면 갖고 있는 정보를 뷰에 알맞게 배치시킵니다.
				if (mUser.getUserIcon() != null) {
					imgUserIcon.setImageDrawable(mUser.getUserIcon());
				}
				tvUserName.setText(mUser.getUserName());
				tvUserSubText.setText(mUser.getUserSubtext());
				btnImg_01.setOnClickListener(this);
				btnImg_02.setOnClickListener(this);
			}

		}
		// 완성된 아이템 뷰를 반환합니다.
		return view;
	}

	// 데이터를 추가하는 것을 위해서 만들어 준다.
	public void add(FriendItem user) {

		mUserData.add(user);
	}

	// 데이터를 추가하는 것을 위해서 만들어 준다.
	public void addArray(ArrayList<FriendItem> items) {

		mUserData.removeAll(items);
		mUserData.addAll(items);
	}

	public int getnSelectedItemNum() {
		return nSelectedItemNum;
	}

	public void setSeletedItemNum(int delta) {
		nSelectedItemNum += delta;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// Tag를 이용하여 Data를 가져옵니다.
		FriendItem clickItem = (FriendItem) v.getTag();

		switch (v.getId()) {
			case R.id.btn_01:
				//btn event 1
				if (mListTypeInfo == Definition.ADDFRIENDNAVI)
					Toast.makeText(v.getContext(), clickItem.getUserName() + " is added", Toast.LENGTH_SHORT).show();
				else if (mListTypeInfo == Definition.SENDMSG || mListTypeInfo == Definition.INTERACTIONINFO) {
					// FIXME: GCM 전송을 위한 Input Dialog

					AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

					alert.setTitle("메시지 전송");
					alert.setMessage("보낼 메시지 내용");

					// Set an EditText view to get user input
					final EditText input = new EditText(mContext);
					alert.setView(input);

					alert.setPositiveButton("전송", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							String value = input.getText().toString();
							ArrayList<String> targetUsers = new ArrayList<String>();
							// FIXME: Prepare target users to receive this multicast message
							targetUsers.add(SharedApplication.GCM_UserToken);
							// From: 1003688072011,  Message: ?
							new ThreadGCMSender(mContext, value, targetUsers).sendMessage();
							// Do something with value!
						}
					});

					alert.setNegativeButton("취소",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									// Canceled.
								}
							});

					alert.show();

				}
				break;
			case R.id.btn_02:
				//btn event 2
				if (mListTypeInfo == Definition.ADDFRIENDNAVI)
					Toast.makeText(v.getContext(), clickItem.getUserName() + " is Bocked", Toast.LENGTH_SHORT).show();
				else if(mListTypeInfo == Definition.INTERACTIONINFO)
				{
					Intent i = new Intent(mContext, InteractInfoActivity.class);
					mContext.startActivity(i);
				}
				break;
		}
	}
}
