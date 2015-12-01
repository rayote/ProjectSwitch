package kkook.team.projectswitch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


/**
 * UserAdapter - Custom ListView를 구현하기 위해 하나의 아이템 정보와 레이아웃을 가져와서 합친다.
 *
 * @author Cloud Travel
 */
public class ListItemAdapter extends BaseAdapter implements OnClickListener{

    // Activity에서 가져온 객체정보를 저장할 변수
    private Item mUser;
    private Context mContext;

    private int nSelectedItemNum; //Number of selected data

    private int mListTypeInfo;

    // ListView 내부 View들을 가르킬 변수들
    private ImageView imgUserIcon;
    private TextView tvUserName;
    private TextView tvUserSubText;
    private ImageButton btnImg_01;
    private ImageButton btnImg_02;

    // 리스트 아이템 데이터를 저장할 배열
    private ArrayList<Item> mUserData;

    public ListItemAdapter(Context context) {
        super();
        mListTypeInfo = 0 ;
        mContext = context;
        mUserData  = new ArrayList<Item>();
    }

    public ListItemAdapter(Context context, int listtypeinfo) {
        super();
        mListTypeInfo = listtypeinfo ;
        mContext = context;
        mUserData  = new ArrayList<Item>();
    }


    public ListItemAdapter()
    {
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
    public Item getItem(int position) {
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

        }

        else if(mListTypeInfo == Definition.ADDFRIEND)
        {
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

        }
        else if (mListTypeInfo == Definition.SENDMSG) {
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
            // 데이터의 실존 여부를 판별합니다.
            if (mUser != null) {
                // 데이터가 있다면 갖고 있는 정보를 뷰에 알맞게 배치시킵니다.
                if (mUser.getUserIcon() != null) {
                    imgUserIcon.setImageDrawable(mUser.getUserIcon());
                }
                tvUserName.setText(mUser.getUserName());
                btnImg_01.setOnClickListener(this);
            }

        }
        else if (mListTypeInfo == Definition.ADDFRIENDNAVI) {
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
            btnImg_02.setTag(mUser);
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

        }
        else if (mListTypeInfo == Definition.MANAGEFRIEND) {
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
                tvUserSubText.setText(mUser.getUserSubtext());
                btnImg_01.setOnClickListener(this);
            }

        }
        else if (mListTypeInfo == Definition.INTERACTIONINFO) {
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
    public void add(Item user){

        mUserData.add(user);
    }
    // 데이터를 추가하는 것을 위해서 만들어 준다.
    public void addArray(ArrayList<Item> items){

        mUserData.removeAll(items);
        mUserData.addAll(items);
    }

    public int getnSelectedItemNum(){return nSelectedItemNum;}
    public void setSeletedItemNum(int delta){nSelectedItemNum+=delta;}
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        // Tag를 이용하여 Data를 가져옵니다.
        Item clickItem = (Item)v.getTag();

        switch (v.getId()){
            case R.id.btn_01:
                //btn event 1
                break;
            case R.id.btn_02:
                //btn event 2
                break;
        }
    }
}
