package week3.day2;

import java.util.ArrayList;

import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;

public class GeoLocation {
	
	@Test
	public void notifications() throws InterruptedException {
		
		Playwright pw = Playwright.create();

		Browser browser = pw.chromium().launch(new BrowserType
				.LaunchOptions()
				.setChannel("chrome")
				.setHeadless(false));
		
		
		ArrayList<String> permissions = new ArrayList<>();
		permissions.add("geolocation");
		permissions.add("camera");
		permissions.add("microphone");
		

//		BrowserContext context = browser.newContext(new Browser.NewContextOptions()
//				.setGeolocation(13.0827, 80.2707)
//				.setPermissions(permissions)
//				);
		
		BrowserContext context = browser.newContext();

		Page page = context.newPage();
		
		context.grantPermissions(permissions,
				new BrowserContext
				.GrantPermissionsOptions()
				.setOrigin("https://www.gps-coordinates.net/my-location"));
		
		
		page.navigate("https://www.gps-coordinates.net/my-location");
		
		page.waitForLoadState(LoadState.NETWORKIDLE);
		
		String innerText = page.locator("#addr").innerText();
		
		System.out.println(innerText);
		
		
		Thread.sleep(5000);
		
	}

}
