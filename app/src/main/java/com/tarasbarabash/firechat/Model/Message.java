package com.tarasbarabash.firechat.Model;

import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TimeUtils;

import com.google.firebase.database.Exclude;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Taras
 * 01.05.2018, 17:31.
 */

public class Message {
    private static final String TAG = Message.class.getSimpleName();

    @Nullable
    private String convId;
    private String id;
    private String sender;
    private String reciever;
    private String text;
    private int type;
    private long time;

    public Message() {
    }

    public Message(String sender, String reciever, String text, int type, long time) {
        this.sender = sender;
        this.text = text;
        this.type = type;
        this.reciever = reciever;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Sender: " + sender + ", Text: " + text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    @Exclude
    public String getFormattedTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return format.format(time);
    }

    @Exclude
    public String getFormattedDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM", Locale.getDefault());
        return format.format(time);
    }

    @Exclude
    public String getFormattedTimestamp() {
        Calendar now = Calendar.getInstance();
        Calendar mesTime = Calendar.getInstance();
        mesTime.setTimeInMillis(time);
        boolean today = now.get(Calendar.YEAR) == mesTime.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) == mesTime.get(Calendar.DAY_OF_YEAR);
        if (today) return getFormattedTime();
        return getRelationDate();
    }

    @Exclude
    public String getRelationDate() {
        return DateUtils.getRelativeTimeSpanString(time,
                com.tarasbarabash.firechat.Utils.TimeUtils.getTimeInMillisUTC(),
                DateUtils.MINUTE_IN_MILLIS).toString();
    }

    @Nullable
    public String getConvId() {
        return convId;
    }

    public void setConvId(@Nullable String convId) {
        this.convId = convId;
    }
}
