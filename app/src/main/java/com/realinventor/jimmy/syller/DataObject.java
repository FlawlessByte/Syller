package com.realinventor.jimmy.syller;

/**
 * Created by JIMMY on 26-Feb-17.
 */


public class DataObject {
    private String mNo;
    private String mCode;
    private String mSubject;

    DataObject (String text1, String text2, String text3){
        mNo = text1;
        mCode = text2;
        mSubject = text3;
    }

    public String getmNo() {
        return mNo;
    }

    public void setmNo(String mNo) {
        this.mNo = mNo;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }
}
