package kkook.team.projectswitch;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Item {
    private Drawable mUserIcon;
    private String mUserName;
    private String mUserSubtext;
    private int mDataType;
    private View mView;

    Item(Drawable userIcon, String userName, String usersubtext){
        mUserIcon = userIcon;
        mUserName = userName;
        mUserSubtext = usersubtext;
        mDataType = 0 ;
    }

    Item(Drawable userIcon, String userName, String userSubtext, int DataType){
        mUserIcon = userIcon;
        mUserName = userName;
        mUserSubtext = userSubtext;
    }
    public Drawable getUserIcon() {
        return mUserIcon;
    }
    public String getUserName() {
        return mUserName;
    }
    public String getUserSubtext() {
        return mUserSubtext;
    }
    public int getDataType() {
        return mDataType;
    }

    public void setmUserIcon(Drawable userIcon)
    {
        mUserIcon = userIcon;
        return;
    }
    public void setUserName(String name) {
        mUserName = name;
        return;
    }
    public void setmUserSubtext(String subtext)
    {
        mUserSubtext = subtext;
        return;
    }
    public void setDataType(int datatype)
    {
        mDataType=datatype;
        return;
    }
    public View getView()
    {
        return mView;
    }
    public void setView(View view)
    {
        mView =view;
    }
}
