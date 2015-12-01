package kkook.team.projectswitch;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class Item implements Parcelable{

    private Drawable mUserIcon;
    private String mUserName;
    private String mUserSubtext;
    private int mDataType;

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

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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
    public Item(Parcel src)
    {
        // Deserialize Parcelable and cast to Bitmap first:
        Bitmap bitmap = (Bitmap)src.readParcelable(getClass().getClassLoader());
        mUserName=src.readString();
        mUserSubtext=src.readString();
        mDataType=src.readInt();

        // Convert Bitmap to Drawable:
        mUserIcon = new BitmapDrawable(bitmap);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        // Convert Drawable to Bitmap first:
        Bitmap bitmap = (Bitmap)((BitmapDrawable) mUserIcon).getBitmap();

        // Serialize bitmap as Parcelable:
        dest.writeParcelable(bitmap, flags);
        dest.writeString(mUserName);
        dest.writeString(mUserSubtext);
        dest.writeInt(mDataType);
    }
}
