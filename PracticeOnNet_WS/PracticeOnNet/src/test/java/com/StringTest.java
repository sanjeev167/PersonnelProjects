package com;

import com.util.PathUtilities;

public class StringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       String urlPattern="/admin/pvt/*";
       String url="/admin/pvt/postLogin/";    
       
       System.out.println("Contains "+ urlPattern.contains("*"));
       System.out.println("Ends with * "+ urlPattern.endsWith("*"));
       System.out.println(PathUtilities.match(url, urlPattern));
	}

}
