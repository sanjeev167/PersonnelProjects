/**
 * 
 */
package com.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * @author Sanjeev
 *
 */
public class LoadPropertiesFileStatically {
	private static Properties prop;    
	static {
		InputStream is = null;
		try {
			prop = new Properties();
			is = LoadPropertiesFileStatically.class.getClassLoader().getResourceAsStream("businessError.properties");
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
     
    public static String getPropertyValue(String key){
        return prop.getProperty(key);
    }
     
    public static void main(String a[]){         
        System.out.println("Sanju be_1.error: "+getPropertyValue("be_1.error"));
        
    }
}
