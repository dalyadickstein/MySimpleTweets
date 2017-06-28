package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dalyadickstein on 6/26/17.
 */

public class User implements Parcelable {

    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeLong(uid);
        out.writeString(screenName);
        out.writeString(profileImageUrl);
    }

    private User(Parcel in) {
        name = in.readString();
        uid = in.readLong();
        screenName = in.readString();
        profileImageUrl = in.readString();
    }

    public User() {}

    @Override
    public int describeContents() {
        return 0;
    }

    // deserialize the JSON
    public static User fromJSON(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.uid = jsonObject.getLong("id");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url");
        return user;
    }

    public static final Parcelable.Creator<User> CREATOR
    = new Parcelable.Creator<User>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
