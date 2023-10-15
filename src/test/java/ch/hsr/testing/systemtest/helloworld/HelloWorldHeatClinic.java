/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.helloworld;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ch.hsr.testing.systemtest.weekenddiscount.Constants;
import ch.hsr.testing.systemtest.weekenddiscount.util.DriverFactory;

/**
 * The Class HelloWorldHeatClinic. This class can be used for first experiments
 * with selenium against the system under test.
 * <p>
 * Make sure the system under test is running! (start with ./run-SUT-locally.sh)
 */
public class HelloWorldHeatClinic implements Constants {

	private WebDriver driver;

	@BeforeEach
	public void setup() {
		driver = DriverFactory.createDriver(getChromeDriverPath());
	}

	@AfterEach
	public void tearDown() {
		driver.close();
	}

	@Test
	public void testPriceOfSauceGreenGhost() throws InterruptedException {

		driver.get("http://localhost:8080");

		// check if the home page is loaded
		Assertions.assertThat(driver.getPageSource()).contains("Heat Clinic");

		// now go to "Hot Sauces"
		WebElement navigation = driver.findElement(By.xpath("//div[@id='left-nav']"));
		navigation.findElement(By.partialLinkText("HOT")).click();
		Assertions.assertThat(driver.getTitle()).contains("Hot Sauces");

		// jump to the green ghost sauce detail page
		WebElement greenGhostLink = driver.findElement(By.xpath("//a[@href='/hot-sauces/green_ghost']"));
		greenGhostLink.click();

		// and check the price of the green ghost sauce: should be $9.99
		String price = driver.findElement(By.className("price-new")).getText();
		Assertions.assertThat(price).isEqualTo("$9.99");

	}

}
