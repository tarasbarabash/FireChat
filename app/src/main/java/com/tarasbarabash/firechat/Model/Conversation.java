package com.tarasbarabash.firechat.Model;

/**
 * Created by Taras
 * 02.05.2018, 13:42.
 */

public class Conversation {
    private Profile mProfile;
    private String phoneNumber;
    private Message mMessage;

    public Conversation(Profile user, Message message, String phoneNumber) {
        this.mProfile = user;
        mMessage = message;
        this.phoneNumber = phoneNumber;
    }

    public Profile getProfile() {
        return mProfile;
    }

    public void setProfile(Profile profile) {
        this.mProfile = profile;
    }

    public Message getMessage() {
        return mMessage;
    }

    public void setMessage(Message message) {
        mMessage = message;
    }

    @Override
    public String toString() {
        return "mProfile: " + mProfile + ", message: " + mMessage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
