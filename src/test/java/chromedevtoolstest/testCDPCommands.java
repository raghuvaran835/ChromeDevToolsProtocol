package chromedevtoolstest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.network.Network;
import org.openqa.selenium.devtools.v110.network.model.Request;
import org.openqa.selenium.devtools.v110.network.model.Response;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testCDPCommands {

	@Test
	public void testMobileView() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();

		WebDriverManager.chromedriver().setup();

		options.addArguments("--remote-allow-origins=*");
		options.addArguments("start-maximized");
		// Creating the Object of chromedriver ,cdp commands are present in chromium drivers
		
		//method 1 - Launching the application mobile view
		
		Map<String, String> deviceMobEmu = new HashMap<String, String>();
		deviceMobEmu.put("deviceName", "iPhone 12 Pro");
		options.setExperimentalOption("mobileEmulation",deviceMobEmu);
		
		ChromeDriver driver = new ChromeDriver(options);
//		
		DevTools devTools = driver.getDevTools();

		devTools.createSession();

		//method -2
//		devTools.send(Emulation.setDeviceMetricsOverride(375,
//                812,
//                50,
//                true,
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty()));

		//method 3
//		If the above doesn't work ,we can use the below one
		
		
//		Map<String, Object> deviceMetrics = new HashMap<String, Object>();
//
//		deviceMetrics.put("width", 375);
//		deviceMetrics.put("height", 812);
//		deviceMetrics.put("deviceScaleFactor", 50);
//		deviceMetrics.put("mobile", true);
//
//		driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);

		driver.get("https://rahulshettyacademy.com/angularAppdemo/");

		driver.findElement(By.cssSelector(".navbar-toggler-icon")).click();

		Thread.sleep(2000);
		driver.findElement(By.linkText("Library")).click();

	}

	@Test
	public void testGeoLocationOverride()
	{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("start-maximized");
		
		ChromeDriver driver= new ChromeDriver(options);
		
		DevTools devTools= driver.getDevTools();
		devTools.createSession();
		
		Map<String,Object> coordinates=new HashMap<String,Object>();
		coordinates.put("latitude",40.689487);
		coordinates.put("longitude", 4.691706);
		coordinates.put("accuracy",100);
		
		System.out.println(coordinates);
		driver.executeCdpCommand("Emulation.setGeolocationOverride",coordinates);
	
		driver.get("https://the-internet.herokuapp.com/geolocation");
		
		driver.findElement(By.tagName("button")).click();
//		driver.get("http://google.com");
//		driver.findElement(By.name("q")).sendKeys("Nnetflix",Keys.ENTER);
//		driver.findElements(By.cssSelector(".LC20lb.MBeuO")).get(0).click();
//		String storyTitle=driver.findElement(By.xpath("//h1[@data-uia='nmhp-card-hero-text-title']")).getText();
//		System.out.println(storyTitle);
		
	}

	@Test
	public void testNetworkActivity()
	{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("start-maximized");
		
		ChromeDriver driver= new ChromeDriver(options);
		
		DevTools devTools= driver.getDevTools();
		devTools.createSession();
	
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		
		devTools.addListener(Network.requestWillBeSent(), request -> 
		{
			Request req=request.getRequest();
			System.out.println("RequestURL"+request.getDocumentURL());
		});
	
		devTools.addListener(Network.responseReceived(), response ->
		{
			Response res=response.getResponse();
			if(res.getStatus().toString().startsWith("4"))
			{
				System.out.println("ResponseURL:"+res.getUrl() +"\n"+res.getStatus());
			}
		});
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink*=library]")).click();
		
	}
}
