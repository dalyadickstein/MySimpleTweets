package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

/**
 * Created by dalyadickstein on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private Context context;

    // pass in the Tweets array into the constructor
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    // for each row, inflate the layout and cache references into the ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create the view using the item_tweet layout
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        // return a new ViewHolder
        return new ViewHolder(tweetView);
    }

    // bind the values based on the position of the element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the tweet data at the specified position
        Tweet tweet = mTweets.get(position);
        // populate the view with the tweet data
        holder.tvUserName.setText(tweet.user.name);
        holder.tvScreenName.setText(String.format("@%s", tweet.user.screenName));
        holder.tvBody.setText(tweet.body);
        holder.tvRelativeDate.setText(tweet.relativeDate);
        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

    // create ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // track view objects
        ImageView ivProfileImage;
        TextView tvUserName;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvRelativeDate;
        ImageButton btReply;

        // constructor takes in an inflated layout
        public ViewHolder(View itemView) {
            super(itemView);
            // perform findViewById lookups
            this.ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            this.tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            this.tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            this.tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            this.tvRelativeDate = (TextView) itemView.findViewById(R.id.tvRelativeDate);
            this.btReply = (ImageButton) itemView.findViewById(R.id.btReply);
        }

    }

}
