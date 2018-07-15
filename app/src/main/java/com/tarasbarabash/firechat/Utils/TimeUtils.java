package com.tarasbarabash.firechat.Utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.tarasbarabash.firechat.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Taras
 * 5/7/2018, 18:15.
 */

public final class TimeUtils {
    private static final String TAG = TimeUtils.class.getSimpleName();

    public static long getTimeInMillisUTC() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
    }

    public static long getLocalTimeInMillis(long UTCTime) {
        Date date = new Date(UTCTime);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static String getLastSeenTime(long profileLastSeen, Context context) {
        if ((getTimeInMillisUTC() - profileLastSeen) <= 6 * 60 * 60 * 1000)
            return DateUtils.getRelativeTimeSpanString(profileLastSeen,
                    getTimeInMillisUTC(),
                    DateUtils.SECOND_IN_MILLIS).toString();
        else {
            SimpleDateFormat dateFormat;
            if ((getTimeInMillisUTC() - profileLastSeen) < 365 * 24 * 60 * 60 * 1000)
                dateFormat = new SimpleDateFormat("dd MMMM '" + context.getString(R.string.at_date) + "' HH:mm");
            else
                dateFormat = new SimpleDateFormat("dd MMMM yyyy '" + context.getString(R.string.at_date) + "' HH:mm");
            return dateFormat.format(getLocalTimeInMillis(profileLastSeen));
        }
    }
}
