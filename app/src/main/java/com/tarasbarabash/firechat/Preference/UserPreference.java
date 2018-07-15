package com.tarasbarabash.firechat.Preference;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tarasbarabash.firechat.R;
import com.tarasbarabash.firechat.databinding.PrefUserBinding;

/**
 * Created by Taras
 * 5/7/2018, 20:26.
 */

public class UserPreference extends Preference {
    private static final String TAG = UserPreference.class.getSimpleName();
    private Drawable mIcon;
    private String mTitle;
    private String mSummary;
    private ImageView mImageView;
    private TextView mTitleTV;
    private TextView mSummaryTV;

    public UserPreference(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.preferenceStyle);
        init(context, attrs);
    }

    public UserPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserPreference);
        int iconId = typedArray.getResourceId(R.styleable.UserPreference_icon, 0);
        if (iconId != 0)
            mIcon = ContextCompat.getDrawable(context, iconId);
        int titleId = typedArray.getResourceId(R.styleable.UserPreference_title, 0);
        if (titleId != 0)
            mTitle = context.getString(titleId);
        else {
            mTitle = typedArray.getString(R.styleable.UserPreference_title);
        }
        int descriptionId = typedArray.getResourceId(R.styleable.UserPreference_summary, 0);

        if (descriptionId != 0)
            mSummary = context.getString(descriptionId);
        else {
            mSummary = typedArray.getString(R.styleable.UserPreference_summary);
        }
        setLayoutResource(R.layout.pref_user);
        typedArray.recycle();
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        mImageView = (ImageView) holder.findViewById(R.id.icon);
        mTitleTV = (TextView) holder.findViewById(R.id.title);
        mSummaryTV = (TextView) holder.findViewById(R.id.summary);

        mImageView.setImageDrawable(mIcon);
        mTitleTV.setText(mTitle);
        mSummaryTV.setText(mSummary);
    }

    @Override
    public Drawable getIcon() {
        return mIcon;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSummary() {
        return mSummary;
    }

    @Override
    public void setIcon(Drawable icon) {
        mIcon = icon;
    }

    public void setTitle(String title) {
        mTitle = title;
        if (mTitleTV != null)
            mTitleTV.setText(mTitle);
    }

    public void setSummary(String summary) {
        mSummary = summary;
        if (mSummaryTV != null)
            mSummaryTV.setText(summary);
    }
}
