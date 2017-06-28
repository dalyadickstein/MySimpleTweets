package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by dalyadickstein on 6/26/17.
 */

public class Tweet implements Parcelable {

    public String body;
    public long uid; // database ID for the tweet
    public String createdAt;
    public User user;
    public String relativeDate;

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(body);
        out.writeLong(uid);
        out.writeString(createdAt);
        out.writeString(relativeDate);
        out.writeParcelable(user, flags);
    }

    private Tweet(Parcel in) {
        body = in.readString();
        uid = in.readLong();
        createdAt = in.readString();
        relativeDate = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public Tweet() {}

    @Override
    public int describeContents() {
        return 0;
    }

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.relativeDate = getRelativeTimeAgo(jsonObject.getString("created_at"));
        return tweet;
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
            System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }

    public static final Parcelable.Creator<Tweet> CREATOR
    = new Parcelable.Creator<Tweet>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

}
