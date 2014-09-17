package com.example.pmalviya.instaviewer;

public class InstaPhotoModel {

	private String userName;
	private String caption;
	private int height;
	private int width;
	private long time;
	private int likesCount;
	private String imageURL;
	private String profilePicURL;
	private String location;
	private Comment penultimateComment;
	private Comment lastComment;

	// Getter Setters for the Model attributes
	public Comment getPenultimateComment() {
		return penultimateComment;
	}

	public void setPenultimateComment(Comment comment) {
		this.penultimateComment = comment;
	}
	public Comment getLastComment() {
		return lastComment;
	}

	public void setLastComment(Comment comment) {
		this.lastComment = comment;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getProfilePicURL() {
		return profilePicURL;
	}

	public void setProfilePicURL(String profilePicURL) {
		this.profilePicURL = profilePicURL;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	// Model constructor
	public InstaPhotoModel(String userName, String caption, int height,
			int likesCount, String imageURL, String profilePicURL,
			String location, Comment comment1, Comment comment2, int width, long time) {
		super();
		this.userName = userName;
		this.caption = caption;
		this.height = height;
		this.likesCount = likesCount;
		this.imageURL = imageURL;
		this.profilePicURL = profilePicURL;
		this.location = location;
		this.penultimateComment = comment1;
		this.lastComment = comment2;
		this.width = width;
		this.time =time;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}



}
