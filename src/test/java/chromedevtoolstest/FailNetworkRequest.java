package chromedevtoolstest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.asynchttpclient.resolver.RequestHostnameResolver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.fetch.Fetch;
import org.openqa.selenium.devtools.v110.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v110.network.model.ErrorReason;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FailNetworkRequest {

	@Test
	public void failNetworkRequest()
	{
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options =new ChromeOptions();
		options.addArguments(List.of("--remote-allow-origins=*","start-maximized"));
		
		ChromeDriver driver=new ChromeDriver(options);
		
		DevTools devTools=driver.getDevTools();
		devTools.createSession();
		
		Optional<List<RequestPattern>> pattern=Optional.of(List.of(new RequestPattern(Optional.of("*GetBook*"),Optional.empty(), Optional.empty())));
		
		devTools.send(Fetch.enable(pattern, Optional.empty()));
		
		devTools.addListener(Fetch.requestPaused(), request ->{
			
			devTools.send(Fetch.failRequest(request.getRequestId(),ErrorReason.FAILED));
		});
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink*=library]")).click();
		
		
		
	}
}
