package org.p565.team6.beans;

import java.io.IOException;
import java.util.ArrayList;

import org.p565.team6.model.AddFriendModel;
import org.p565.team6.model.RegisterModel;
import org.p565.team6.model.SearchModel;
import org.p565.team6.model.UserModel;

public interface MongoDbOperationsDao {

	public Object searchOne(String searchField, String searchValue, String searchCollectionis);
	public Object searchPost(String searchField, ArrayList<String> searchList, String searchCollectionis);
	public Object searchFriend(SearchModel searchModel, String searchCollectionis);	
	public boolean insertOne(Object object,String collectionName);
	public boolean insertOneFile(Object object,String collectionName);
	public Object searchOneProfile(String name);
	public Object updateUserModel(AddFriendModel userModel,AddFriendModel currentUser);
	
}
