package org.p565.team6.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.p565.team6.beans.LoginValidationAndRegistration;
import org.p565.team6.beans.PostAndComment;
import org.p565.team6.beans.SearchFriendAndSuggestion;
import org.p565.team6.model.NewPostModel;
import org.p565.team6.model.RegisterModel;
import org.p565.team6.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;



@Controller
@SessionAttributes({"UserModel","registerDetail","profilePic","backgroundPic"})
public class LoginController {

	@Autowired
	private LoginValidationAndRegistration loginValidation;
	
	@Autowired
	private LoginValidationAndRegistration registration;
	
	@Autowired
	private SearchFriendAndSuggestion searchFriendAndSuggetion;
	
	@Autowired
	private PostAndComment postComment;
	
	@RequestMapping(value = "/")
	public String loginPage(){
		
		return "login";
		
	}
	
	
	@RequestMapping(value = "/logout" ,method=RequestMethod.POST	)
	public String logoutPage(HttpSession session, Model model){
		System.out.println("logout");
		 session.invalidate();
		 if(model.containsAttribute("counter")) model.asMap().remove("counter");
		 model.asMap().clear();
		return "login";
		
	}
	
	@RequestMapping(value = "/doLogin",method = RequestMethod.POST)
	public @ResponseBody String sayHello(@RequestBody  RegisterModel registerTemplateModel ){
		
		JSONObject json = 	new JSONObject();
		boolean flag = loginValidation.getAuthentication(registerTemplateModel);
		if(flag==true){
			json.put("moveTo", "newsFeed");
			return json.toString();
		}
		else{
			json.put("moveTo","invalidEntery");
			System.out.println(json.toString());
			return json.toString();
		}
   	}
	
	@RequestMapping(value = "/doRegister",method = RequestMethod.POST)
	public @ResponseBody String doRegister(@RequestBody  RegisterModel registerTemplateModel,ModelMap model ){
		JSONObject json = 	new JSONObject();
		//registration.doRegistration(registerTemplateModel);
		json.put("moveTo", "continue");
		model.addAttribute("registerDetail", registerTemplateModel);
		return json.toString();
  	}
	
	@RequestMapping(value = "/newsFeed",method = RequestMethod.POST)
	public String returnNewsfeed(@ModelAttribute RegisterModel registerTemplateModel,@ModelAttribute  UserModel userModel, ModelMap model ){

		userModel.getUsersFriendList().add(userModel.getEmailId());
		registration.doRegistration(userModel);
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
		System.out.println("44-->"+userModel.getUsersFriendList().get(0));
		model.addAttribute("UserModel" ,userModel );
		model.addAttribute("profilePic",profilePic);
		model.addAttribute("backgroundPic", backgroundPic);
		
		UserModel userModel1 = (UserModel) model.get("UserModel");
		System.out.println(" post model --"+userModel1.getEmailId());
		System.out.println(" post model -->"+userModel1.getUsersFriendList());
		
		return "profilePage";
	}
	
	
	@RequestMapping(value = "/searchPage",method = RequestMethod.GET)
	public String searchPage(ModelMap model ){
		UserModel userModelAtt = (UserModel) model.get("UserModel");
		ArrayList<NewPostModel> returnPost = postComment.getPost(userModelAtt.getUsersFriendList());
		
		model.put("NewPostModel", returnPost);
		return "searchPage";
	}
	
	@RequestMapping(value = "/newsFeed",method = RequestMethod.GET)
	public String reloadNewsfeed(ModelMap model ){
		UserModel userModelAtt = (UserModel) model.get("UserModel");
		ArrayList<NewPostModel> returnPost = postComment.getPost(userModelAtt.getUsersFriendList());
		model.put("NewPostModel", returnPost);
		return "profilePage";
	}
	@RequestMapping(value = "/continue")
	public String newsFeed(ModelMap model){
		
		return "continue";
	}
	@RequestMapping(value = "/invalidEntery")
	public String invalidEntery(){
		System.out.println("....invalid");
		return "invalidEntery";
	}
	
	@RequestMapping(value = "/profilePage")
	public String profilePage(ModelMap model){
		UserModel userModelAtt = (UserModel) model.get("UserModel");
		UserModel retUserModel = (UserModel)searchFriendAndSuggetion.getModel(userModelAtt.getEmailId());
		model.put("UserModel", retUserModel);
		return "profilePage";
	}
	@RequestMapping(value = "/backgroundPicture")
	public String backgroundPicture(ModelMap model){
		
		return "backgroundPicture";
	}
	
}
