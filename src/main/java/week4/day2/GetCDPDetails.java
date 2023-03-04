package week4.day2;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class GetCDPDetails {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Playwright pw = Playwright.create();

		Browser browser = pw.chromium().launch(new BrowserType.LaunchOptions()
				.setArgs(Arrays.asList("--remote-debugging-port=9222"))
				.setChannel("chrome")
				.setHeadless(false));

		Page page = browser.newPage();
		page.setViewportSize(1920, 1200);

		page.navigate("http://leaftaps.com/opentaps");

		Locator locator = page.locator("id=username");
		locator.type("demosalesmanager");
		page.locator("#password").type("crmsfa");
		page.locator(".decorativeSubmit").click();

	}

}
