package com.nopCommerce.helpClasses;

public class Login extends ValueInput {

	public void loginIntoJBK(String userName, String password) {
		ValueInput.initDriver(propertiesFile("browser"), propertiesFile("URL"));
		setTextField("xpath", "(//*[@type='text'])[1]", userName);
		setTextField("xpath", "(//*[@type='password'])[1]", password);
		elementClick("xpath", "(//*[@type='submit'])[1]");
	}
	
}
