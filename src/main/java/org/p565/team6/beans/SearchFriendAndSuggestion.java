package org.p565.team6.beans;

import java.io.IOException;
import java.util.ArrayList;

import org.p565.team6.model.AddFriendModel;
import org.p565.team6.model.NewPostModel;
import org.p565.team6.model.RegisterModel;
import org.p565.team6.model.SearchModel;
import org.p565.team6.model.UserModel;
import org.p565.team6.model.RegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;


@Component
public class SearchFriendAndSuggestion {

	

	public ArrayList<UserModel> getFriendSearchResult(SearchModel searchFriend){
		System.out.println("--------------------------------searching-------");
	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
	MongoDbOperationsDao search = ctx.getBean("MongoDbOperationsDao", MongoDbOperationsDao.class);
	
	ArrayList<UserModel> returnResult = (ArrayList<UserModel>)search.searchFriend(searchFriend, "UserModel");
	if(returnResult == null){
		System.out.println("Error getting the returnResult");
	}
	else{
		System.out.println("size returned : "+returnResult.size());
		System.out.println(returnResult.get(0).getEmailId());
	}ctx.close();
	return returnResult;
	
	}
	
	public Object getModel(String email){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
		MongoDbOperationsDao getmodel = ctx.getBean("MongoDbOperationsDao", MongoDbOperationsDao.class);
		UserModel returnObject = (UserModel)getmodel.searchOne("_id", email, "UserModel");
		ctx.close();
		return returnObject;
	}
	
	public void addFriend(AddFriendModel emailId,UserModel currentUser){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
	    MongoDbOperationsDao register = ctx.getBean("MongoDbOperationsDao", MongoDbOperationsDao.class);
//	 	UserModel existingUser = (UserModel)register.searchOne("_id", emailId.getAddFriend(), "UserModel");
//	 	existingUser.getPendingFriendList().add(emailId);
	    AddFriendModel temp = new AddFriendModel();
	    temp.setAddFriend(currentUser.getEmailId());
	    temp.setName(currentUser.getFullName());
	 	register.updateUserModel(emailId,temp);
	 	ctx.close();
		
	}
	public void doPost(Object post,String collectionName){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
	    MongoDbOperationsDao register = ctx.getBean("MongoDbOperationsDao", MongoDbOperationsDao.class);
	 	register.insertOne(post,collectionName);
		ctx.close();
	}
	
	public ArrayList<NewPostModel> getPost(ArrayList<String> friendList){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
	    MongoDbOperationsDao register = ctx.getBean("MongoDbOperationsDao", MongoDbOperationsDao.class);
	    ArrayList<NewPostModel> t1 = (ArrayList<NewPostModel>)register.searchPost("accessedBy", friendList, "CommonPost");
	    if(t1 == null)
	    {
	    	System.out.println("t1 is null");
	    }
	    else
	    {
	    	System.out.println("new post returned in post and Comment ");
	    }
		ctx.close();
		return t1;
	}

	
	public void profileCreation(RegisterModel registerTemplateModel){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("grid.xml");
		GridFSOperationDao gridOperations  =  ctx.getBean("GridFSOperationDao",GridFSOperationDao.class);
	    try {
			gridOperations.insertOneFile(registerTemplateModel.getProfilePic().getInputStream(), registerTemplateModel.getEmailId()+"_profile");
			gridOperations.insertOneFile(registerTemplateModel.getBackgroundPic().getInputStream(), registerTemplateModel.getEmailId()+"_background");

	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ctx.close();
	}
	
	public MultipartFile getProfile(String identifer){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("grid.xml");
		GridFSOperationDao gridOperations  =  ctx.getBean("GridFSOperationDao",GridFSOperationDao.class);
	    GridFSDBFile gridFsdbFile = gridOperations.searchOneFile(identifer);
		    MultipartFile retrivingFile = null;
		    try {
		    	retrivingFile = new MockMultipartFile(gridFsdbFile.getFilename(),gridFsdbFile.getInputStream());
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    ctx.close();
		    return retrivingFile;
		
	}
	
}
