package com.nopCommerce;
import java.util.LinkedHashMap;

import com.nopCommerce.helpClasses.DataExtractor;
import com.nopCommerce.helpClasses.ValueInput;

public class LoginPage extends ValueInput{
	
	public void login(String userName, String Password) {
		initDriver(propertiesFile("browser"), propertiesFile("URL"));
		elementClick("xpath", "(//*[text()='Log in'])[1]");
		setTextField("xpath", "//*[@id='Email']", userName);
		setTextField("xpath", "//*[@id='Password']", Password);
		elementClick("xpath", "(//*[text()='Log in'])[2]");
	}
	
	public void registerPage() {
		initDriver(propertiesFile("browser"), propertiesFile("URL"));
		LinkedHashMap<String, String> lh = DataExtractor.KeyValueDataReturnForMultipleRowMatch(1, propertiesFile("nopCommerce_Excellocation"), "Register");

		elementClick("xpath", "(//*[text()='Register'])[1]");
		if("Male".equalsIgnoreCase(lh.get("Gender"))) {
			elementClick("xpath", "//*[@id='gender-male']");
		}
		else if("Female".equalsIgnoreCase(lh.get("Gender"))) {
			elementClick("xpath", "//*[@id='gender-female']");
		}
		setTextField("xpath", "//*[@id='FirstName']", lh.get("First name"));
		setTextField("xpath", "//*[@id='LastName']", lh.get("Last name"));
		setDropdown("VT", "xpath", "//*[@name='DateOfBirthDay']", lh.get("BirthDay"));
		setDropdown("VT", "xpath", "//*[@name='DateOfBirthMonth']", lh.get("BirhtMonth"));
		setDropdown("VT", "xpath", "//*[@name='DateOfBirthYear']", lh.get("BirthYear"));
		setTextField("xpath", "//*[@id='Email']", lh.get("Email"));
		setTextField("xpath", "//*[@id='Company']", lh.get("Comp Name"));
		if("Tick".equalsIgnoreCase(lh.get("Newsletter"))) {
			elementClick("xpath", "//*[@id='Newsletter']");
		}
		setTextField("xpath", "//*[@name='Password']", lh.get("Password"));
		setTextField("xpath", "//*[@name='ConfirmPassword']", lh.get("Conf Password"));
		elementClick("xpath", "(//*[text()='Register'])[3]");
		quiteDriver();
				
	}

}
