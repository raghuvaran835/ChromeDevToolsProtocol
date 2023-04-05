package chromedevtoolstest;

import java.util.List;
import java.util.function.Predicate;
import java.net.URI;

import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicAuthentication {

	@Test
	public void basicAuthentication() {
		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		options.addArguments(List.of("--remote-allow-origins=*", "start-maximized"));

		ChromeDriver driver = new ChromeDriver(options);
		
		Predicate<URI> uriPredicate=uri -> uri.getHost().contains("httpbin.org");
		
		((HasAuthentication)driver).register(uriPredicate, UsernameAndPassword.of("foo", "bar"));
		
		driver.get("https://httpbin.org/basic-auth/foo/bar");
		
		
		//Method2 we can send the credential directly in URL
		
//		Syntax: 
//		https://Username:Password@SiteURLwithout https://
			
//		driver.get("https://foo:bar@httpbin.org/basic-auth/foo/bar");
		
	}
}
