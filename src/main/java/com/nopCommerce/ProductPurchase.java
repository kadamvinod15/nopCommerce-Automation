package com.nopCommerce;

import java.util.LinkedHashMap;

import com.nopCommerce.helpClasses.DataExtractor;
import com.nopCommerce.helpClasses.ValueInput;

public class ProductPurchase extends ValueInput {

	public void purchaseDesktop() {
		LinkedHashMap<String, String> lh = DataExtractor.KeyValueDataReturnForMultipleRowMatch(1,
				propertiesFile("nopCommerce_Excellocation"), "DesktopPurchase");
		actionMethods("MouseHover", "xpath", "(//*[text()='Computers '])[1]");
		elementClick("xpath", "(//*[text()='Desktops '])[1]");
		elementClick("xpath", "//*[@class='item-grid']//following::a[text()='%s']".formatted(lh.get("DesktopType")));

		elementClick("xpath", "//*[@id='add-to-cart-button-3']");
		try {
			elementClick("xpath", "//*[@class='close']");
		} catch (Exception e) {
			
		}
		elementClick("xpath", "(//*[text()='Shopping cart'])[1]");
		elementClick("xpath", "//*[@id='termsofservice']");
		elementClick("xpath", "//*[@id='checkout']");
		
		if(isDisplay("xpath", "//*[@id='edit-billing-address-button']")) {
			elementClick("xpath", "(//*[text()='Continue'])[1]");
		}
		else {
			setDropdown("VT", "xpath", "//*[@id='BillingNewAddress_CountryId']", lh.get("Country"));
			setTextField("xpath", "//*[@id='BillingNewAddress_City']", lh.get("City"));
			setTextField("xpath", "//*[@id='BillingNewAddress_Address1']", lh.get("Address 1"));
			setTextField("xpath", "//*[@id='BillingNewAddress_Address2']", lh.get("Address 2"));
			setTextField("xpath", "//*[@id='BillingNewAddress_ZipPostalCode']", lh.get("Zip"));
			setTextField("xpath", "//*[@id='BillingNewAddress_PhoneNumber']", lh.get("Phone number"));
			setTextField("xpath", "//*[@id='BillingNewAddress_FaxNumber']", lh.get("Fax number"));
			elementClick("xpath", "(//*[text()='Continue' and @name='save'])[1]");
			alertReject();
		}
		switch(lh.get("Shipping Method").toLowerCase()) {
		case "land":
			elementClick("xpath", "//*[@id='shippingoption_0']");
			break;
		case "one day air":
			elementClick("xpath", "//*[@id='shippingoption_1']");
			break;
		case "two day air":
			elementClick("xpath", "//*[@id='shippingoption_2']");
			break;
		}
		elementClick("xpath", "(//*[text()='Continue'])[3]");
		
		switch(lh.get("Payment Method").toLowerCase()) {
		case "cheque":
			elementClick("xpath", "//*[@id='paymentmethod_0']");
			elementClick("xpath", "(//*[text()='Continue'])[4]");
			elementClick("xpath", "(//*[text()='Continue'])[5]");
			break;
		case "credit":
			elementClick("xpath", "//*[@id='paymentmethod_1']");
			elementClick("xpath", "(//*[text()='Continue'])[4]");
			setDropdown("VT", "xpath", "//*[@id='CreditCardType']", lh.get("Card Type"));
			setTextField("xpath", "//*[@id='CardholderName']", lh.get("Cardholder"));
			setTextField("xpath", "//*[@id='CardNumber']", lh.get("Card No."));
			setDropdown("VT", "xpath", "//*[@id='ExpireMonth']", lh.get("Expiration month"));
			setDropdown("VT", "xpath", "//*[@id='ExpireYear']", lh.get("Expiration year"));
			setTextField("xpath", "//*[@id='CardCode']", lh.get("Card code"));
			elementClick("xpath", "(//*[text()='Continue'])[5]");
			break;
		}
		elementClick("xpath", "//*[text()='Confirm']");
		quiteDriver();
	}
}
