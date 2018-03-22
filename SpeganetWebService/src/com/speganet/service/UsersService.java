package com.speganet.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONObject;

import com.speganet.model.Users;
import com.speganet.util.Constants;
import com.speganet.util.MySqlConnector;
import com.speganet.util.SendSMS;

public class UsersService {
	
	public   Connection connection=null;
	public PreparedStatement ps;
	
	public UsersService() {
		try {
			connection=MySqlConnector.getSqlConnector().getDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	
	
	public JSONObject insert(Users users){
		try{
			ps=connection.prepareStatement("insert into users(fullname,phonenumber,country,countrycode,logindevice,emailid,status,otp) values(?,?,?,?,?,?,?,?)");
			ps.setString(1, users.getFullname());
			ps.setString(2, users.getPhonenumber());
			ps.setString(3, users.getCountry());
			ps.setString(4, users.getCountrycode());
			ps.setString(5, users.getLogindevice());
			ps.setString(6, users.getEmailid());
			ps.setString(7, users.getStatus());
			ps.setString(8, users.getOtp());
			int queryResult=ps.executeUpdate();
			if(queryResult==1){
				JSONObject json=new JSONObject();
 				json.put("status", "successs");
				JSONObject userInsert=new JSONObject();
				userInsert.put("result", json);
				SendSMS sms=SendSMS.getSms();
				int smsResponse=sms.sendOtp( users.getPhonenumber(), users.getOtp());
 				return userInsert;
			}
			else{
              JSONObject json=new JSONObject();
				
				json.put("status", "fail");
				JSONObject userInsert=new JSONObject();
				userInsert.put("result", json);
				return userInsert;
			}
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSONObject json=new JSONObject();
			String[] error=e.toString().split(":");
			json.put("status", "Phone number already exist");
			JSONObject userInsert=new JSONObject();
			userInsert.put("result", json);
			return userInsert;
		}
	}
	
	
	public boolean findUniquePhoneNumber(String phoneNumber){
		try{
			ps=connection.prepareStatement("select * from users where phonenumber=?");
            ps.setString(1, phoneNumber);
            ResultSet dataRow=ps.executeQuery();
            if(dataRow.next()){
            	return true;
            }
            else
            	return false;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}
	
	
	public boolean checkOtp(String phoneId,String otp){
		
		try{
			ps=connection.prepareStatement("select * from users where phonenumber=? and otp = ?");
            ps.setString(1, phoneId);
            ps.setString(2, otp);

            ResultSet dataRow=ps.executeQuery();
            if(dataRow.next()){
            	updateStatus(phoneId);
            	return true;
            }
            else
            	return false;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void updateStatus(String phoneId)throws Exception
	{
		ps=connection.prepareStatement("update users set status=? where phonenumber=?");
        ps.setString(1, Constants.ACTIVE.getString());
        ps.setString(2, phoneId);
        ps.executeUpdate();
	}
	
	public JSONObject resendOTP(String phoneID,String newOtp){
		JSONObject reply = null;
		try{
			int userId=0;
			reply=new JSONObject();
			ps=connection.prepareStatement("select * from users where phonenumber=?");
			ps.setString(1, phoneID);
			ResultSet dataSet=ps.executeQuery();
			if(dataSet.next())
				userId=dataSet.getInt("id");
            if(userId!=0){
            	ps=connection.prepareStatement("update users set otp =? where phonenumber=?");
            	ps.setString(1, newOtp);
            	ps.setString(2, phoneID);
            	int s=ps.executeUpdate();
            	if(s==1){
            		reply.put("status", "Once again resend new OTP to your Phone");
            		
            	}
            	else{
            		reply.put("status", "Failed to resend new OTP to your Phone");

            	}
            }
			
		}catch (Exception e) {
    		reply.put("status", "Failed to resend new OTP to your Phone");

			e.printStackTrace();
		}
		return reply;
	}
 
}
