package week4.day2;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.Rule;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Accessibility {
	
	@Test
	public void accessibility() {
		
		Playwright pw = Playwright.create();

		Browser browser = pw.chromium().launch(new BrowserType
				.LaunchOptions()
				.setChannel("chrome")
				.setHeadless(false));
		

		BrowserContext context = browser.newContext();

		Page page = context.newPage();
		
		
		page.navigate("https://www.redbus.in/");
		
		
		AxeBuilder axe  = new AxeBuilder(page);
		
		List<Rule> violations = axe.analyze().getViolations();
		System.out.println(violations);
		
	}

}
