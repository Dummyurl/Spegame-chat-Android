package com.speganet.util;

import java.util.Random;

public class OneTimePassword {
	
	public static OneTimePassword oneTimePassword=new OneTimePassword();
	
	public int getOTP(){
		Random rand = new Random();
	 return rand.nextInt(10000) ;
	}
	
	
	public  static OneTimePassword geOneTimePassword(){
		return oneTimePassword;
	}
	
	
	public static void main(String[] args) {
		System.out.println(oneTimePassword.getOTP());
	}

}
