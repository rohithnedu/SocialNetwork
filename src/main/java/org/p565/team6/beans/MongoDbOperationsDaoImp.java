package org.p565.team6.beans;

import java.util.ArrayList;


import org.omg.PortableServer.ServantActivatorHelper;
import org.p565.team6.model.AddFriendModel;
import org.p565.team6.model.NewPostModel;
import org.p565.team6.model.RegisterModel;
import org.p565.team6.model.SearchModel;
import org.p565.team6.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class MongoDbOperationsDaoImp implements MongoDbOperationsDao {

	private MongoOperations mongoOps;
		
	
	public MongoDbOperationsDaoImp(MongoOperations mongoOps){
        this.mongoOps=mongoOps;
    }
	
	@Override
	public Object searchOne(String searchField, String searchValue, String searchCollections) {
		// TODO Auto-generated method stub
		
		Query query = new Query(Criteria.where(searchField).is(searchValue));
		
		Object t1 = this.mongoOps.findOne(query, Object.class,searchCollections );
		return t1;
	}
	
	@Override
	public boolean insertOne(Object object,String collectionName) {
		// TODO Auto-generated method stub
		
		this.mongoOps.insert(object,collectionName);
		return false;
	}
	
	@Override
	public boolean insertOneFile(Object object,String collectionName)
	{
		
		return false;
		
	}

	@Override
	public Object searchOneProfile(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object searchPost(String searchField, ArrayList<String> searchList, String searchCollections) {
		// TODO Auto-generated method stub
		
		Query query = new Query(Criteria.where(searchField).in(searchList));
		//System.out.println(query.toString()+" "+searchCollections);
		ArrayList<NewPostModel> t1 = (ArrayList<NewPostModel>)this.mongoOps.find(query, NewPostModel.class,searchCollections );
		return t1;
	}

	@Override
	public Object searchFriend(SearchModel searchModel, String searchCollections) {
		// TODO Auto-generated method stub
		
		


		Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("fullName").is(searchModel.getNameSearch())
        		,Criteria.where("address").is(searchModel.getLocationSearch())
        		,Criteria.where("school").is(searchModel.getSchoolSearch()));
        
		Query query1 = new Query(criteria);
		//query1.addCriteria(criteriaDefinition)
//		query1.addCriteria(Criteria.where("fullName").is(searchModel.getNameSearch()),
//				Criteria.where("address").is(searchModel.getLocationSearch()));
		//	  Criteria.where("school").is(searchModel.getSchoolSearch())));
//			  .orOperator(Criteria.where("professionSearch").is(searchModel.getProfessionSearch()))));
		System.out.println(query1.toString()+" "+searchCollections);
			  
		ArrayList<UserModel> t1 = (ArrayList<UserModel>)this.mongoOps.find(query1, UserModel.class,searchCollections );
		return t1;
	}

	@Override
	public Object updateUserModel(AddFriendModel addFriendModel, AddFriendModel currentUser) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("_id").is(addFriendModel.getAddFriend()));
		Update update = new Update();
		update.addToSet("pendingFriendList", currentUser);
		System.out.println("searching for -- to update"+addFriendModel.getAddFriend());
		System.out.println("going to add this "+currentUser.getAddFriend());
		
		System.out.println("query--"+query.toString());
		System.out.println("update--"+update.toString());
		mongoOps.findAndModify(query, update, UserModel.class, "UserModel");
		
		return null;
	}
	
	
	

}
