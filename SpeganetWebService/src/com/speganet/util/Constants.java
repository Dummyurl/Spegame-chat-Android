package com.speganet.util;
public enum Constants {
    DBURL("jdbc:mysql://localhost:3306/schat"),
    DBUSERNAME("root"),
    DBPASSWORD(""),
    INSERT("insert"),
    ACTIVE("active"),
    INACTIVE("inactive"),
    ONLINE("online"),
    PENDING("pending"),
    UNKNOWN("unknown");
	
	
	

    private String url;

    Constants(String url) {
        this.url = url;
    }

    public String getString() {
        return url;
    }
}