package com.example.pmalviya.instaviewer;

public class Comment {
	private String commentStr;
	private String commenterProfilePicURL;
	private String commenterName;
	public String getCommentStr() {
		return commentStr;
	}

	public void setCommentStr(String commentStr) {
		this.commentStr = commentStr;
	}

	public String getCommenterProfilePicURL() {
		return commenterProfilePicURL;
	}

	public void setCommenterProfilePicURL(String commenterProfilePicURL) {
		this.commenterProfilePicURL = commenterProfilePicURL;
	}

	public Comment(String commentStr, String commenterProfilePicURL, String name) {
		this.commentStr = commentStr;
		this.commenterProfilePicURL = commenterProfilePicURL;
		this.commenterName = name;
	}

	public String getCommenterName() {
		return commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

}
