package com.example.luzhuo.parcelabledemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * =================================================
 * <p/>
 * 作者:卢卓
 * <p/>
 * 版本:1.0
 * <p/>
 * 创建日期:2015/11/9 21:39
 * <p/>
 * 描述:Parcelable类的案例
 * <p/>
 * 修订历史:
 * <p/>
 * <p/>
 * =================================================
 **/
public class Bean implements Parcelable{
    public int Age;
    public boolean Sexy;

    public static final Creator<Bean> CREATOR = new Creator<Bean>() {
        @Override
        public Bean createFromParcel(Parcel in) {
            // 从 Parcelable 类中取出数据
            // 写的顺序和度的顺序必须一致
            Bean bean = new Bean();
            bean.Age = in.readInt();
            bean.Sexy = in.readByte() != 0;
            return bean;
        }

        @Override
        public Bean[] newArray(int size) {
            return new Bean[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 指定写入 Parcelable 类的数据
        dest.writeInt(Age);
        dest.writeByte((byte) (Sexy ? 1 : 0));
    }
}
