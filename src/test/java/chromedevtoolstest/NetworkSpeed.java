package chromedevtoolstest;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.network.model.ConnectionType;
import org.testng.annotations.Test;
import org.openqa.selenium.devtools.v110.network.Network;

import com.google.common.collect.ImmutableList;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NetworkSpeed {

	@Test
	public void networkSpeed() {
		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		options.addArguments(List.of("--remote-allow-origins=*", "start-maximized"));

		ChromeDriver driver = new ChromeDriver(options);

		
		DevTools devTools = driver.getDevTools();
		devTools.createSession();

		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

		// first param if we give true network will be disconnected
		devTools.send(Network.emulateNetworkConditions(false, 2000, 20000, 100000,Optional.of(ConnectionType.ETHERNET)));

		// below code will listen the loading of the url and report error if loading failed
		devTools.addListener(Network.loadingFailed(), loadingfailed ->{
			System.out.println(loadingfailed.getErrorText());
			System.out.println(loadingfailed.getTimestamp());
		});
		long startTime = System.currentTimeMillis();

		driver.get("http://google.com");
		driver.findElement(By.name("q")).sendKeys("netflix",Keys.ENTER);
		driver.findElements(By.cssSelector(".LC20lb.MBeuO")).get(0).click();
		String storyTitle=driver.findElement(By.xpath("//h1[@data-uia='nmhp-card-hero-text-title']")).getText();
		System.out.println(storyTitle);

		long endTime = System.currentTimeMillis();

		System.out.println(endTime - startTime);

	}
}
