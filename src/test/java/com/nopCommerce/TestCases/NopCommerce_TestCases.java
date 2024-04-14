package com.nopCommerce.TestCases;

import org.testng.annotations.Test;

import com.nopCommerce.LoginPage;
import com.nopCommerce.ProductPurchase;

public class NopCommerce_TestCases{

	@Test(priority = 1)
	public void registrationPage() {
		LoginPage lp = new LoginPage();
		lp.registerPage();
	}

	@Test(priority = 2)
	public void desktopProduct() {
		LoginPage lp = new LoginPage();
		lp.login("PrishaK@gmail.com", "prisha123");
		ProductPurchase desk = new ProductPurchase();
		desk.purchaseDesktop();
	}
}
