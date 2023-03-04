package week5.day2;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class GetIncident {

	@Test
	public void getIncident() {

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
		APIResponse response = request.get("api/now/table/incident/46cebb88a9fe198101aee93734f9768b");


		int status = response.status();
		System.out.println(status);

		String statusText = response.statusText();
		System.out.println(statusText);

		//		String text = response.text();
		//		System.out.println(text);


		JsonElement responseJson = new Gson().fromJson(response.text(), JsonElement.class);
		JsonElement resultObj = responseJson.getAsJsonObject().get("result");
		
		System.out.println(getJsonElement(resultObj,"sys_id"));
		System.out.println(getJsonElement(resultObj,"number"));
		System.out.println(getJsonElement(resultObj,"category"));

	}
	
	
	public String getJsonElement(JsonElement json, String key) {
		return json.getAsJsonObject().get(key).toString();
	}

}
