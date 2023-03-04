package week5.day2;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class DeleteIncident {
	
	@Test
	public void deleteIncident() {
		
		//Playwright Connection establishment
		Playwright pw = Playwright.create();
		
		//Framing headers 
		Map<String, String> headers = new HashMap<>();
		
		headers.put("Authorization", "Basic YWRtaW46bk9ibDdKTUNpPV4z");
		headers.put("Content-Type", "application/json");
		
		//Frame Request
		APIRequestContext request = pw.request().newContext(new APIRequest
				.NewContextOptions()
				.setBaseURL("https://dev13195.service-now.com/")
				.setExtraHTTPHeaders(headers));
		
		//Get Response
		APIResponse response = request.delete("api/now/table/incident/46c03489a9fe19810148cd5b8cbf501e");
		
		
		int status = response.status();
		System.out.println(status);
		
		String statusText = response.statusText();
		System.out.println(statusText);
		
//		String text = response.text();
//		System.out.println(text);
		
		
		
	}

}
