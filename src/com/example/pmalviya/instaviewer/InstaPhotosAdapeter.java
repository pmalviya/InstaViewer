package com.example.pmalviya.instaviewer;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstaPhotosAdapeter extends ArrayAdapter<InstaPhotoModel> {

	public InstaPhotosAdapeter(Context context, List<InstaPhotoModel> photos) {
		super(context, android.R.layout.simple_list_item_1, photos);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Take the data source at position i
		// get data item
		InstaPhotoModel photo = getItem(position);

		// Check if we are using a recycled view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_photo_layout, parent, false);
		}

		// Lookup the subview within the template
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
		TextView tvCaption = (TextView) convertView
				.findViewById(R.id.tvCaptionText);
		ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivImage);
		ImageView ivProfilePic = (ImageView) convertView
				.findViewById(R.id.ivProfilePic);
		TextView tvLocation = (TextView) convertView
				.findViewById(R.id.tvLocation);
		TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
		tvLikes.setText(photo.getLikesCount() + " likes");
		tvName.setText(photo.getUserName());
		tvCaption.setText(Html.fromHtml("<font color=\"#206199\"><b>" + photo.getUserName() + "  " + "</b></font>" + photo.getCaption()));
		if(photo.getLocation() != ""){
			tvLocation.setText(photo.getLocation());
			tvLocation.setVisibility(View.VISIBLE);
		}else{
			tvLocation.setVisibility(View.GONE);
		}
		ivPhoto.getLayoutParams().height = photo.getHeight();
		ivPhoto.setImageResource(0);
		ivProfilePic.setImageResource(0);
		
		tvTime.setText(DateUtils.getRelativeTimeSpanString(photo.getTime()*1000));
		// Set comments
		if (photo.getPenultimateComment() != null) {
			ImageView ivCommenter1Pic = (ImageView) convertView.findViewById(R.id.ivCommenter1Pic);
			TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
			tvComment1.setText(Html.fromHtml("<font color=\"#206199\"><b>" + photo.getPenultimateComment().getCommenterName() + "  " + "</b></font>"
					+ photo.getPenultimateComment().getCommentStr()));
			Picasso.with(getContext()).load(photo.getPenultimateComment().getCommenterProfilePicURL()).resize(20, 20).centerCrop().into(ivCommenter1Pic);
		}
		if (photo.getLastComment() != null) {
			ImageView ivCommenter2Pic = (ImageView) convertView.findViewById(R.id.ivCommenter2Pic);
			TextView tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);
			tvComment2.setText(Html.fromHtml("<font color=\"#206199\"><b>" + photo.getLastComment().getCommenterName() + "  " + "</b></font>"
					+ photo.getLastComment().getCommentStr()));
			Picasso.with(getContext()).load(photo.getLastComment().getCommenterProfilePicURL()).resize(20, 20).centerCrop().into(ivCommenter2Pic);
		}
		// Ask for photo to be added to the imageview based on image url
		// send network request to the url, download the image bytes, convert
		// iinto bitmap, resize image,
		
		float aspectRatio =  photo.getWidth()/photo.getHeight();
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int calculatedHeight = (int) (screenWidth /aspectRatio);
		ivPhoto.getLayoutParams().height = calculatedHeight;
		
		Picasso.with(getContext()).load(photo.getImageURL()).resize(screenWidth,calculatedHeight ).into(ivPhoto);
		ivProfilePic.getLayoutParams().height = 80;
		Picasso.with(getContext()).load(photo.getProfilePicURL())
				.resize(80, 80).transform(new RoundedTransformation(40, 1)).centerCrop().into(ivProfilePic);
		// return the view
		return convertView;
	}

	// GetView method (int position)
	// Calls toString on InstaPhotoModel
}
