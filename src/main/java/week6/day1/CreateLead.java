package week6.day1;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class CreateLead {
	
	@Test
	public void createLead() {
		
		//Playwright Connection establishment
		Playwright pw = Playwright.create();
		
		//Framing headers 
		Map<String, String> headers = new HashMap<>();
		
		headers.put("Authorization", "Bearer 00D5g00000I3FV8!ARsAQHbFK6vbPwp4oZTahcWJ4r0CiYiUwyoJiZZz.vs.MIlnogMqB5xNEG0zBE82iqNirYzCvtL_gfzDvn1CdsQWNfZo3EbI");
		headers.put("Content-Type", "application/json");
		
		//Frame Request
		APIRequestContext request = pw.request().newContext(new APIRequest
				.NewContextOptions()
				.setBaseURL("https://gsk-a-dev-ed.develop.my.salesforce.com/services/data/v57.0/sobjects/")
				.setExtraHTTPHeaders(headers));
		
		//Body
		String body = "{\n"
				+ "    \"FirstName\":\"Playwright\",\n"
				+ "    \"LastName\":\"Kumar\",\n"
				+ "    \"Company\":\"testleaf\"\n"
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
		
		
		
	}

}
