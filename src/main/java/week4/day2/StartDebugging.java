package week4.day2;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class StartDebugging {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Playwright pw = Playwright.create();
		
		Browser browser = pw.chromium().connectOverCDP("http://localhost:9222");
		
		BrowserContext context = browser.contexts().get(0);
		
		Page page = context.pages().get(0);
		
		page.locator("text=CRM/SFA").click();
		page.locator("role=link[name='Leads']").click();
		page.locator("//a[text()='Create Lead']").click();
		
		page.screenshot(new Page.ScreenshotOptions()
				.setPath(Paths.get("snaps/snap1.jpg")));
		page.type("#createLeadForm_companyName", "TL Industries");
		page.locator("#createLeadForm_firstName").type("Sooraj");
		page.locator("#createLeadForm_lastName").type("Krishna");
		page.click(".smallSubmit");
		

	}

}
