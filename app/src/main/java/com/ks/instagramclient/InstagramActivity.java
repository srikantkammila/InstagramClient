package com.ks.instagramclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class InstagramActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private SwipeRefreshLayout swipeContainer;
    private InstagramAdapter adapter;
    private JsonHttpResponseHandler responseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_instagram);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        adapter = new InstagramAdapter(this, new ArrayList());

        ListView lvInstPost = (ListView)findViewById(R.id.lvInstPost);
        lvInstPost.setAdapter(adapter);

//        final InstagramActivity cnxt = this;



         responseHandler = new JsonHttpResponseHandler(){
//            public JSONObject resp, errResp;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                resp.response = response;

                try {
                    adapter.clear();
                    List<ItemInstagramPost> posts  =  InstagramResponseParser.getInstPosts(response, adapter);
//                    for (ItemInstagramPost post: posts
//                         ) {
//                        adapter.add(post);
//                    }
//                    adapter.addAll(posts);
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
//                    InstagramAdapter adapter = new InstagramAdapter(this, posts);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //show error message
                }
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //show error
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        };

        InstagramRestClient.getPopularMedia(responseHandler);

    }

    public void fetchTimelineAsync() {
        InstagramRestClient.getPopularMedia(responseHandler);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instagram, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class ResponseHolder {
        public JSONObject response;
        public JSONObject error;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemInstagramPost post = (ItemInstagramPost)adapter.getItem(position);
        if (post.getType().equals("video")) {
            Intent i = new Intent(this, PlayVideoActivity.class);

            i.putExtra("video_url", post.getVideoLink());
            this.startActivity(i);
        }
    }

    public void showVideo(View v) {
        System.out.println("show video");
    }
}
