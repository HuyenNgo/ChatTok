package com.example.ngothihuyen.chattok;

import android.os.Parcel;
import android.os.Parcelable;

public class MyParcelable implements Parcelable {
    private String mData;

    public int describeContents() {
        return 0;
    }
        public  String getmData()
        {
            return mData;
        }
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mData);
    }

    public static final Parcelable.Creator<MyParcelable> CREATOR
            = new Parcelable.Creator<MyParcelable>() {
        @Override
        public MyParcelable createFromParcel(Parcel source) {
            return new MyParcelable(source.readString());
        }


        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };

    public MyParcelable(String s) {
        mData=s;
    }

}
