package com.fuseIn.api.utils;

public class TokenGenerator {
	
	private int token;
	private String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	public String tokenGenerator()
	{
		int i=0;
		StringBuilder strBuild = new StringBuilder();
		
		while(i<=15) {
		token = (int) (Math.random()* ALPHABET.length());
		strBuild.append(ALPHABET.charAt(token));
		i++;
		}
		return strBuild.toString();
	}
}
