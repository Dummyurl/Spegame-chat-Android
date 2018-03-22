package com.speganet.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.speganet.model.Users;
import com.speganet.service.UsersService;
import com.speganet.util.Constants;
import com.speganet.util.OneTimePassword;
import com.speganet.util.SendSMS;

public class UserServlet extends HttpServlet{
	
	public String action="";
	UsersService userService;
	public UserServlet() {
		// TODO Auto-generated constructor stub
		userService=new UsersService();
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
	 
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
		try{
			  
			  StringBuffer reqBuffer = new StringBuffer();
			  String reqLine = null;
			  BufferedReader reqReader = req.getReader();
			  while((reqLine = reqReader.readLine()) != null){
				  reqBuffer.append(reqLine);
 			  }
			  System.out.println(reqBuffer.toString());
			  JSONObject reqJson=new JSONObject(reqBuffer.toString());
			  JSONObject user=reqJson.getJSONObject("user");
			  
			  action=user.getString("action").trim();
			  if(action.equals(Constants.INSERT.getString())){
				JSONObject  respJson=insertUser(user);
 				 resp.setContentType("application/json;charset=UTF-8");
				 resp.setHeader("Content-Type", "application/json");
				 resp.getOutputStream().print(respJson.toString());
		        resp.getOutputStream().flush();
 
 			  }
			  else if(action.equals("confirmotp")){
				     JSONObject  respJson = null;
				     try{
				    	 respJson =new JSONObject();
				    	 respJson.put("status",confirmOtp(user));
				     }
				     catch (Exception e) {
						e.printStackTrace();
					}
	 				 resp.setContentType("application/json;charset=UTF-8");
					 resp.setHeader("Content-Type", "application/json");
					 resp.getOutputStream().print(respJson.toString());
			        resp.getOutputStream().flush();
	 
			  }
			  
			  else if(action.equals("resendotp")){
				
	 				 resp.setContentType("application/json;charset=UTF-8");
					 resp.setHeader("Content-Type", "application/json");
					 resp.getOutputStream().print(resendOtp(user).toString());
					
			        resp.getOutputStream().flush();
	 
			  }
			  
			  
			  
			  
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	public JSONObject resendOtp(JSONObject user){
		 int newOtp=OneTimePassword.geOneTimePassword().getOTP();
		 SendSMS sms=new SendSMS();
		 sms.sendOtp(user.getString("phoneId"), String.valueOf(newOtp));
		return userService.resendOTP(user.getString("phoneId"),String.valueOf(newOtp));
		
	}
	
	public JSONObject insertUser(JSONObject user){
		try{
			 
			   Users users=new Users();
			   users.setFullname(user.getString("fullName"));
			   users.setPhonenumber(user.getString("phoneNumber"));
			   users.setCountrycode(user.getString("countryCode"));
			   users.setLogindevice(user.getString("loginDevice"));
			   users.setEmailid(user.getString("emailId"));
			   users.setStatus(Constants.PENDING.getString());
			   users.setOtp(String.valueOf(OneTimePassword.oneTimePassword.geOneTimePassword().getOTP()));
			   users.setCountry(user.getString("country"));
		       JSONObject result=userService.insert(users);
		       return result;
		       
			   
 		}
		catch (Exception e) {
			e.printStackTrace();
			JSONObject userError=new JSONObject("result");
			userError.put("status", "error");
			return userError;
		}
	
	}
	
	public boolean confirmOtp(JSONObject user){
		try{
			String phoneId=user.getString("phoneId");
			String otp=user.getString("otp");
			boolean reply=userService.checkOtp(phoneId, otp);
			return reply;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	

}
