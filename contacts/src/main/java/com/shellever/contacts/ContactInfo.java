package com.shellever.contacts;


import android.support.annotation.NonNull;

/**
 * Author: Shellever
 * Date:   11/13/2016
 * Email:  shellever@163.com
 */

public class ContactInfo implements Comparable<ContactInfo> {

    private String rawName;             // raw
    private String pinyinName;          // filter
    private String sortLetters;         // sort

    public ContactInfo() {
    }

    public ContactInfo(String rawName, String pinyinName, String sortLetters) {
        this.rawName = rawName;
        this.pinyinName = pinyinName;
        this.sortLetters = sortLetters;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    @Override
    public int compareTo(@NonNull ContactInfo another) {
        if (sortLetters.startsWith("#")) {
            return 1;
        } else if (another.getSortLetters().startsWith("#")) {
            return -1;
        } else {
            return sortLetters.compareTo(another.getSortLetters());
        }
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "rawName='" + rawName + '\'' +
                ", pinyinName='" + pinyinName + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                '}';
    }
}
