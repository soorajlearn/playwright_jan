package week5.day2;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class CreateAndDeleteIncident {

	String sys_id;
	Playwright pw;
	Map<String, String> headers;
	APIRequestContext request;

	@BeforeClass
	public void setUp() {
		pw = Playwright.create();
		headers = new HashMap<>();
		headers.put("Authorization", "Basic YWRtaW46bk9ibDdKTUNpPV4z");
		headers.put("Content-Type", "application/json");
		request = pw.request().newContext(new APIRequest
				.NewContextOptions()
				.setBaseURL("https://dev13195.service-now.com/")
				.setExtraHTTPHeaders(headers));

	}

	@Test
	public void createIncident() {
		String body = "{\n"
				+ "    \"short_description\" : \"Create Incident from Playwright\"\n"
				+ "}";

		APIResponse response = request.post("api/now/table/incident", 
				RequestOptions.create().setData(body));
		JsonElement responseJson = new Gson().fromJson(response.text(), JsonElement.class);
		JsonElement resultObj = responseJson.getAsJsonObject().get("result");

		sys_id = getJsonElement(resultObj,"sys_id");
		System.out.println(sys_id);

	}

	public String getJsonElement(JsonElement json, String key) {
		return json.getAsJsonObject().get(key).getAsString();
	}


	@Test(dependsOnMethods = "createIncident")
	public void deleteIncident() {
		APIResponse response = request.delete("api/now/table/incident/"+sys_id);
		int status = response.status();
		System.out.println(status);
		String statusText = response.statusText();
		System.out.println(statusText);
	}

}
