package week4.day2;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class LaunchPersistent {

	@Test
	public void launchPersistent() throws InterruptedException {


		Playwright pw = Playwright.create();

		BrowserContext context = pw.chromium().launchPersistentContext(Paths.get("~/Library/Application Support/Google/Chrome"),
				
				new BrowserType.LaunchPersistentContextOptions()
				.setChannel("chrome")
				.setHeadless(false));


		Page page = context.newPage();
		page.setViewportSize(1920, 1200);

		page.navigate("http://leaftaps.com/opentaps");
		
		Thread.sleep(20000);

	}

}
