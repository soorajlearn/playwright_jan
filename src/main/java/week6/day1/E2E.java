package week6.day1;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;

public class E2E {

	Playwright pw;
	Browser browser;
	BrowserContext context;
	Page page;
	private String token;
	private String leadId;
	String username = "soorajlearn@gmail.com";
	String password = "password@123";
	String firstName = "E2E", lastName="Kumar", companyName="Qeagle", pHNumber="9486500949";


	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	@BeforeTest
	public void setup() {

		pw = Playwright.create();

		//Frame Request
		APIRequestContext request = pw.request().newContext(new APIRequest
				.NewContextOptions()
				.setBaseURL("https://login.salesforce.com/services/oauth2/token"));


		//Get Response
		APIResponse response = request.post("", 
				RequestOptions.create().setForm(
						FormData.create()
						.set("grant_type","password")
						.set("client_id","3MVG9fe4g9fhX0E4sCbkATxzXOqjhyARAP.AizYlsTo20HQTmQq_gVO_SUSuwTSN1_1HjoqjSKAFD3DK3x.eU")
						.set("client_secret","6BAD6ABF3193A3BFFB6AB16525720258105952A76C420AA51ABC4BB571C72457")
						.set("username",username)
						.set("password",password)
						));

		JsonElement responseJson = new Gson().fromJson(response.text(), JsonElement.class);
		JsonElement resultObj = responseJson.getAsJsonObject().get("access_token");

		setToken(resultObj.getAsString());

	}

	@BeforeMethod
	public void createLeadUsingAPI() {

		Map<String, String> headers = new HashMap<>();

		headers.put("Authorization", "Bearer "+ getToken());
		headers.put("Content-Type", "application/json");

		APIRequestContext request = pw.request().newContext(new APIRequest
				.NewContextOptions()
				.setBaseURL("https://gsk-a-dev-ed.develop.my.salesforce.com/services/data/v57.0/sobjects/")
				.setExtraHTTPHeaders(headers));

		//Body
		String body = "{\n"
				+ "    \"FirstName\":\""+firstName+"\",\n"
				+ "    \"LastName\":\""+lastName+"\",\n"
				+ "    \"Company\":\""+companyName+"\"\n"
				+ "}";

		//Get Response
		APIResponse response = request.post("Lead/", 
				RequestOptions.create().setData(body));


		int status = response.status();
		System.out.println(status);

		String statusText = response.statusText();
		System.out.println(statusText);

		String text = response.text();
		System.out.println(text);
		
		JsonElement responseJson = new Gson().fromJson(response.text(), JsonElement.class);
		JsonElement resultObj = responseJson.getAsJsonObject().get("id");
		setLeadId(resultObj.getAsString());
		

	}

	@Test
	public void editLeadUsingUI() {

		browser = pw.chromium().launch(new BrowserType
				.LaunchOptions()
				.setChannel("chrome")
				.setHeadless(false));

		context = browser.newContext();

		page = context.newPage();

		page.navigate("https://login.salesforce.com");

		page.type("#username", username);
		page.type("#password", password);
		page.click("#Login");
		page.click(".slds-icon-waffle");
		page.type("//label[text()='Search apps and items...']/following::input", "Sales");
		page.click("(//b[text()='Sales'])[3]");
		page.click("//span[text()='Leads']");
		page.type("(//label[text()='Search this list...']/following::input)[1]", firstName);
		page.keyboard().press("Enter");
		page.click("(//a[contains(@class,'slds-button slds-button--icon-x-small')])[1]");
		page.click("//a[@title='Edit']");
		page.type("(//label[text()='Phone']/following::input)[1]", pHNumber);
		page.click("//button[text()='Save']");
	}

	@AfterMethod
	public void verifyEditedLeadUsingAPI() {

		Map<String, String> headers = new HashMap<>();

		headers.put("Authorization", "Bearer "+getToken());
		headers.put("Content-Type", "application/json");

		//Frame Request
		APIRequestContext request = pw.request().newContext(new APIRequest
				.NewContextOptions()
				.setBaseURL("https://gsk-a-dev-ed.develop.my.salesforce.com/services/data/v57.0/sobjects/")
				.setExtraHTTPHeaders(headers));


		//Get Response
		APIResponse response = request.get("Lead/"+getLeadId());


		int status = response.status();
		System.out.println(status);

		String statusText = response.statusText();
		System.out.println(statusText);

		String text = response.text();
		System.out.println(text);
		
		JsonElement responseJson = new Gson().fromJson(response.text(), JsonElement.class);
		JsonElement resultObj = responseJson.getAsJsonObject().get("Phone");
		
		System.out.println(resultObj);
	}
	
	@AfterTest
	public void tearDown() {

		page.close();
		context.close();
		browser.close();
		pw.close();

	}

}
