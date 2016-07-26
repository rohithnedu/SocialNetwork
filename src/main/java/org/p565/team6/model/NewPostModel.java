package org.p565.team6.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NewPostModel {

	private String newPost;
	private ArrayList<String> accessedBy;
	
	public ArrayList<String> getAccessedBy() {
		return accessedBy;
	}

	public void setAccessedBy(ArrayList<String> accessedBy) {
		this.accessedBy = accessedBy;
	}

	public String getNewPost() {
		return newPost;
	}

	public void setNewPost(String newPost) {
		this.newPost = newPost;
	}
	
}
