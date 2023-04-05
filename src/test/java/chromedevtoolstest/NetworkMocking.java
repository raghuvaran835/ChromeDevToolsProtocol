package chromedevtoolstest;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.fetch.Fetch;
import org.openqa.selenium.devtools.v110.network.model.Request;
import org.testng.annotations.Test;

public class NetworkMocking {

	@Test
	public void mockNetworkRequestURL()
	{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("start-maximized");
		
		ChromeDriver driver= new ChromeDriver(options);
		
		DevTools devTools= driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		
		devTools.addListener(Fetch.requestPaused(), request ->{
			Request req=request.getRequest();
			
			if(req.getUrl().contains("shetty"))
			{
				String mockurl=req.getUrl().replace("=shetty","=Badguy");
				
				devTools.send(Fetch.continueRequest(request.getRequestId(),Optional.of(mockurl),
						Optional.of(req.getMethod()), Optional.empty(),  Optional.empty(),  Optional.empty()));
			}
			else {
				devTools.send(Fetch.continueRequest(request.getRequestId(),Optional.of(req.getUrl()),
						Optional.of(req.getMethod()), Optional.empty(),  Optional.empty(),  Optional.empty()));
			}
			
		});
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink*=library]")).click();
		
		System.out.println(driver.findElement(By.cssSelector("p")).getText());
	
	}
	
	@Test
	public void mockNetworkResponse()
	{
		// Doesn't work 
		String mockResponseBody="[\r\n"
				+ "    {\r\n"
				+ "      \"book_name\": \"null\",\r\n"
				+ "      \"isbn\": \"SPY40\",\r\n"
				+ "      \"aisle\": \"2529857\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"book_name\": \"RobotFramework\",\r\n"
				+ "      \"isbn\": \"984353\",\r\n"
				+ "      \"aisle\": \"982053\"\r\n"
				+ "    }\r\n"
				+ "  ]";
				
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("start-maximized");
		
		ChromeDriver driver= new ChromeDriver(options);
		
		DevTools devTools= driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		
		devTools.addListener(Fetch.requestPaused(), request ->{
			Request req=request.getRequest();
			
			if(req.getUrl().contains("shetty"))
			{
				String mockurl=req.getUrl().replace("=shetty","=Badguy");
				
				devTools.send(Fetch.fulfillRequest(request.getRequestId(),200,request.getResponseHeaders(),Optional.empty(),Optional.of(mockResponseBody), Optional.empty()));
			}
			else {
				devTools.send(Fetch.continueRequest(request.getRequestId(),Optional.of(req.getUrl()),
						Optional.of(req.getMethod()), Optional.empty(),  Optional.empty(),  Optional.empty()));
			}
			
		});
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink*=library]")).click();
		
	}
}
