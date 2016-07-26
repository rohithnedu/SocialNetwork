package org.p565.team6.controller;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;

import org.json.JSONObject;
import org.p565.team6.beans.LoginValidationAndRegistration;
import org.p565.team6.beans.PostAndComment;
import org.p565.team6.beans.SearchFriendAndSuggestion;
import org.p565.team6.model.RegisterModel;
import org.p565.team6.model.SearchModel;
import org.p565.team6.model.UserModel;
import org.p565.team6.model.SearchModel;
import org.p565.team6.model.AddFriendModel;
import org.p565.team6.model.NewPostModel;
import org.p565.team6.model.RegisterModel;



@Controller
@RequestMapping(value = "/searchPage")
@SessionAttributes({"UserModel","registerDetail","NewPostModel","backgroundPic","profilePic","searchResult"})
public class searchController {

	@Autowired
	private SearchFriendAndSuggestion searchFriendAndSuggestion;
	
	@Autowired
	private LoginValidationAndRegistration registration;
	
	@Autowired
	private PostAndComment postComment;
	
	@RequestMapping(value = "/test")
	public String loginPage(ModelMap model){
		
		UserModel userModelAtt = (UserModel) model.get("UserModel");
		String userEmail = userModelAtt.getEmailId();
	//	NewPostModel returnPost = postComment.getPost(userModelAtt.getUsersFriendList());
//		System.out.println("return Post--"+returnPost.getNewPost()+" "+returnPost.getAccessedBy());
//		model.addAttribute("NewPostModel", returnPost);
		return "tryhard";
	}
	
	@RequestMapping(value = "/doPost",method = RequestMethod.POST)
	public @ResponseBody String sayHello(@RequestBody  NewPostModel newPostModel,ModelMap model ){
		
		JSONObject json = 	new JSONObject();
		/*boolean flag = loginValidation.getAuthentication(registerTemplateModel);
		if(flag==true){
			json.put("moveTo", "newsFeed");
			return json.toString();
		}
		else{
			json.put("moveTo","invalidEntery");
			System.out.println(json.toString());
			return json.toString();
		}*/
		json.put("key", "homePage/test");
		UserModel userModel = (UserModel) model.get("UserModel");
		newPostModel.setAccessedBy(userModel.getUsersFriendList());
		postComment.doPost(newPostModel,"NewPost_"+userModel.getEmailId());
		postComment.doPost(newPostModel, "CommonPost");
		//model.addAttribute("NewPostModel", newPostModel);
		return json.toString();
		
   	}
	
	@RequestMapping(value = "/doSearch",method = RequestMethod.POST)
	public @ResponseBody String searchFriends(@RequestBody  SearchModel searchModel,ModelMap model ){
	
		JSONObject json = 	new JSONObject();
		ArrayList<UserModel> t1 = searchFriendAndSuggestion.getFriendSearchResult(searchModel);
		json.put("searchResult", t1);
		//	registration.doRegistration(registerTemplateModel);
		//-->model.addAttribute("registerDetail", registerTemplateModel);
		System.out.println("printing Jason: "+json.toString());
		return json.toString();
		
   	}
	
	@RequestMapping(value = "/acceptRequest",method = RequestMethod.POST)
	public @ResponseBody String acceptRequest(@RequestBody  SearchModel searchModel,ModelMap model ){
	
		JSONObject json = 	new JSONObject();
		ArrayList<UserModel> t1 = searchFriendAndSuggestion.getFriendSearchResult(searchModel);
		json.put("searchResult", t1);
		//	registration.doRegistration(registerTemplateModel);
		//-->model.addAttribute("registerDetail", registerTemplateModel);
		System.out.println("printing Jason: "+json.toString());
		return json.toString();
		
   	}
	
	@RequestMapping(value = "/requestRequest",method = RequestMethod.POST)
	public @ResponseBody String requestRequest(@ModelAttribute("email") String rejectEmail ,ModelMap model ){
	
		System.out.println("rejecting this email "+rejectEmail);
		JSONObject json = 	new JSONObject();
//		ArrayList<UserModel> t1 = searchFriendAndSuggestion.getFriendSearchResult(searchModel);
//		json.put("searchResult", t1);
//		//	registration.doRegistration(registerTemplateModel);
//		//-->model.addAttribute("registerDetail", registerTemplateModel);
//		System.out.println("printing Jason: "+json.toString());
		return json.toString();
		
   	}
	
	
	@RequestMapping(value = "/addFriend",method = RequestMethod.POST)
	public @ResponseBody String addFriends(@RequestBody AddFriendModel addFriendEmailId,ModelMap model ){
	
		UserModel userModel = (UserModel) model.get("UserModel");
		System.out.println(addFriendEmailId.getAddFriend());
		searchFriendAndSuggestion.addFriend(addFriendEmailId,userModel);
		return null;
//		JSONObject json = 	new JSONObject();
//		ArrayList<UserModel> t1 = searchFriendAndSuggestion.getFriendSearchResult(searchModel);
//		json.put("searchResult", t1);
//		//	registration.doRegistration(registerTemplateModel);
//		//-->model.addAttribute("registerDetail", registerTemplateModel);
//		System.out.println("printing Jason: "+json.toString());
//		return json.toString();
//		
   	}
	
	@RequestMapping(value = "/newsFeed",method = RequestMethod.POST)
	public String returnNewsfeed(@ModelAttribute RegisterModel registerTemplateModel,@ModelAttribute  UserModel userModel, ModelMap model ){

		registration.doRegistration(registerTemplateModel);
		registration.profileCreation(registerTemplateModel);
		MultipartFile profilePicMultiPart = registration.getProfile(registerTemplateModel.getEmailId()+"_profile");
		MultipartFile backgroundPicMultiPart = registration.getProfile(registerTemplateModel.getEmailId()+"_background");
		
		String profilePic = null;
		String backgroundPic = null;
		try {
			profilePic = new String(Base64.encodeBase64(profilePicMultiPart.getBytes()), "UTF-8");
			backgroundPic = new String(Base64.encodeBase64(backgroundPicMultiPart.getBytes()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("profilePic",profilePic);
		model.addAttribute("backgroundPic", backgroundPic);
		return "profilePage";
	}

	
	@RequestMapping(value = "/newsFeed",method = RequestMethod.GET)
	public String reloadNewsfeed(ModelMap model ){
		UserModel userModelAtt = (UserModel) model.get("UserModel");
		ArrayList<NewPostModel> returnPost = postComment.getPost(userModelAtt.getUsersFriendList());
		model.put("NewPostModel", returnPost);
		return "profilePage";
	}
	
}
