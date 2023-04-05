package chromedevtoolstest;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.Test;

import com.github.dockerjava.api.model.LogConfig.LoggingType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CaptureLogFromConsole {

	@Test
	public void captureLogsFromConsole() {
		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();

		options.addArguments(List.of("--remote-allow-origins=*", "start-maximized"));

		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		
		driver.findElement(By.linkText("Browse Products")).click();
		driver.findElement(By.partialLinkText("Selenium")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		
		driver.findElement(By.linkText("Cart")).click();
		driver.findElement(By.id("exampleInputEmail1")).sendKeys(Keys.DELETE,"2");
		
		
		LogEntries entries=driver.manage().logs().get(LogType.BROWSER);
		
		List<LogEntry> logs=entries.getAll();
		
		for(LogEntry e : logs)
		{
			System.out.println(e.getMessage());
		}
		
	}
}
