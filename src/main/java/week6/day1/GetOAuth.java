package week6.day1;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;

public class GetOAuth {
	
	@Test
	public void getOAuth() {
		
		//Playwright Connection establishment
		Playwright pw = Playwright.create();
		
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
						.set("username","soorajlearn@gmail.com")
						.set("password","password@123")
						));
		
		int status = response.status();
		System.out.println(status);
		
		String statusText = response.statusText();
		System.out.println(statusText);
		
		String text = response.text();
		System.out.println(text);
		
		JsonElement responseJson = new Gson().fromJson(response.text(), JsonElement.class);
		JsonElement resultObj = responseJson.getAsJsonObject().get("access_token");
		
		System.out.println(resultObj.getAsString());
			
	}

}
