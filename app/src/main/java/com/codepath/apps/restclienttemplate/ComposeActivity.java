package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient();
    }

    public void sendMessage(View view) {
        EditText etTweetBody = (EditText) findViewById(R.id.etTweetBody);
        String tweetBody = etTweetBody.getText().toString();

        client.sendTweet(tweetBody, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet newTweet = Tweet.fromJSON(response);
                    Intent data = new Intent();
                    data.putExtra("tweet", newTweet);
                    setResult(RESULT_OK, data);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
