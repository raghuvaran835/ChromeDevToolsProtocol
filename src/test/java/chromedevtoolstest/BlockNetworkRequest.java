package chromedevtoolstest;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.network.Network;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BlockNetworkRequest {

	@Test
	public void blockNetworkRequest() {
		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		options.addArguments(List.of("--remote-allow-origins=*", "start-maximized"));

		ChromeDriver driver = new ChromeDriver(options);

		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		
		devTools.send(Network.setBlockedURLs(ImmutableList.of("*.jpg","*.css")));
		
		long startTime=System.currentTimeMillis();
	
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");

		long endTime=System.currentTimeMillis();
		
		System.out.println(endTime - startTime);
	}

}
