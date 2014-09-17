package com.example.pmalviya.instaviewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PopularPhotosActivity extends Activity {
	String popularPhotosURL = "https://api.instagram.com/v1/media/popular?client_id=0ea4b1ebac04474ab07fa13d59d8e956";
	private ArrayList<InstaPhotoModel> photos;
	private InstaPhotosAdapeter aPhotos;
	private SwipeRefreshLayout swipeContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popular_photos);
		fetchInstaPopularPhotos();
		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
		// Setup refresh listener which triggers new data loading
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				fetchInstaPopularPhotos();
			}
		});
	}

	private void fetchInstaPopularPhotos() {
		photos = new ArrayList<InstaPhotoModel>();
		aPhotos = new InstaPhotosAdapeter(this, photos);

		// populate data into List view
		ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
		lvPhotos.setAdapter(aPhotos);
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(popularPhotosURL, new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {

				// Image url
				// Image height
				// user name
				// Image Caption
				JSONArray photosJSON = null;
				try {
					photos.clear();
					photosJSON = response.getJSONArray("data");
					for (int i = 0; i < photosJSON.length(); i++) {
						
						//Get attributes from the JSON data received from End point
						
						// And create a InstaPhoto model with required attributes
						JSONObject photoJSON = photosJSON.getJSONObject(i);
						long time = photoJSON.getLong("created_time");
						String userName = photoJSON.getJSONObject("user").getString("username");
						int likesCount = photoJSON.getJSONObject("likes").getInt("count");
						String imageURL = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
						String proficPicURL = photoJSON.getJSONObject("user").getString("profile_picture");

						String location = "";
						if (!photoJSON.isNull("location")) {
							if (photoJSON.getJSONObject("location").has("name")) {
								location = photoJSON.getJSONObject("location").getString("name");
							} else if (photoJSON.getJSONObject("location").has("latitude") && photoJSON.getJSONObject("location").has("longitude")) {
								Geocoder gcd = new Geocoder(getBaseContext(),Locale.getDefault());
								double lat = photoJSON.getJSONObject("location").getDouble("latitude");
								double lng = photoJSON.getJSONObject("location").getDouble("longitude");
								try {
									List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
									if (addresses.size() > 0) {
										location = addresses.get(0).getLocality();
									}
								} catch (IOException e) {

									e.printStackTrace();
								}
							}
						}
						String caption = "";
						if (!photoJSON.isNull("caption") && photoJSON.getJSONObject("caption").has("text")) {
							caption = photoJSON.getJSONObject("caption").getString("text");
						}
						int height = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
						int width = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("width");
						Comment comment1 = null;
						Comment comment2 = null;
						if (!photoJSON.isNull("comments") && photoJSON.getJSONObject("comments").has("count")) {
							int count = photoJSON.getJSONObject("comments").getInt("count");
							JSONArray listComments = photoJSON.getJSONObject("comments").getJSONArray("data");
							if (count >= 2) {
								// Get the last comment set
								JSONObject comment2JSON = listComments.getJSONObject(listComments.length()-1);
								String commenterName = comment2JSON.getJSONObject("from").getString("username");
								String commenterPic = comment2JSON.getJSONObject("from").getString("profile_picture");
								String text = comment2JSON.getString("text");
								comment2 = new Comment(text, commenterPic, commenterName);
								
								// Get the penultimate comment set
								JSONObject comment1JSON = listComments.getJSONObject(listComments.length()-2);
								commenterName = comment1JSON.getJSONObject("from").getString("username");
								commenterPic = comment1JSON.getJSONObject("from").getString("profile_picture");
								text = comment1JSON.getString("text");
								comment1 = new Comment(text, commenterPic, commenterName);
							}
						}
						InstaPhotoModel photoModel = new InstaPhotoModel(userName, caption, height, likesCount, imageURL, proficPicURL, location, comment1, comment2, width, time);
						photos.add(photoModel);
					}
					aPhotos.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.popular_photos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
