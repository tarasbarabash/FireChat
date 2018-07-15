package com.tarasbarabash.firechat.Model;

import android.os.Parcelable;
import android.telephony.PhoneNumberUtils;

import java.io.Serializable;

/**
 * Created by Taras
 * 5/6/2018, 12:36.
 */

public class Profile {
    private String phoneNumber;
    private String name;
    private String photoUrl;
    private boolean isOnline;
    private String bio;
    private String userName;
    private long lastSeen;

    public Profile() {
    }

    public Profile(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return name + ", " + phoneNumber + ", " + photoUrl;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUserName() {
        return userName;
    }

    public String getAtUserName() {
        return "@" + userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
