package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    private TextView tvCharCount;
    private EditText etTweetBody;
    private String inReplyToStatusId;

    private final TextWatcher TweetEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a TextView to the current length
            tvCharCount.setText(String.format("Chars Remaining: %s/140",
                String.valueOf(140 - s.length())
            ));
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient();
        etTweetBody = (EditText) findViewById(R.id.etTweetBody);
        etTweetBody.addTextChangedListener(TweetEditorWatcher);
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);

        String replyingToScreenName = getIntent().getStringExtra("screenName");
        if (!replyingToScreenName.equals("")) {
            etTweetBody.setText(replyingToScreenName);
            inReplyToStatusId = getIntent().getStringExtra("inReplyToStatusId");
        } else {
            inReplyToStatusId = "";
        }
    }

    public void sendMessage(View view) {
        String tweetBody = etTweetBody.getText().toString();

        client.sendTweet(inReplyToStatusId, tweetBody, new JsonHttpResponseHandler() {
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
