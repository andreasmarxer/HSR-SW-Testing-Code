/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.weekenddiscount.tests;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import ch.hsr.testing.systemtest.weekenddiscount.Constants;
import ch.hsr.testing.systemtest.weekenddiscount.extension.ScreenshotOnFailureExtension;
import ch.hsr.testing.systemtest.weekenddiscount.extension.WebDriverKeeper;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.CartPage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.HomePage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.HotSaucesPage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.SauceDetailPage;
import ch.hsr.testing.systemtest.weekenddiscount.util.DBUtil;
import ch.hsr.testing.systemtest.weekenddiscount.util.DateFactory;
import ch.hsr.testing.systemtest.weekenddiscount.util.DriverFactory;

/**
 * The Class HeatClinicAcceptanceTests. In this class the acceptance Tests for
 * the weekend discount features are implemented.
 */
@ExtendWith(ScreenshotOnFailureExtension.class)
public class WeekendDiscountAcceptanceTests implements Constants {

    private static final Log LOG = LogFactory
            .getLog(WeekendDiscountAcceptanceTests.class);

    public ScreenshotOnFailureExtension screenshot = new ScreenshotOnFailureExtension();

    private WebDriver driver;

    @BeforeEach
    public void setup() {

        driver = DriverFactory.createDriver(getChromeDriverPath());
        WebDriverKeeper.getInstance().setDriver(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testWeekendDiscountEnabled() {

        Date within4thWeekend = DateFactory.createDate(2018, 6, 23, 0, 0, 0);
        DBUtil.setTestTime(within4thWeekend);

        // Navigate TO HotSaucePage
        HomePage homePage = HomePage.navigateTo(driver);
        HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

        // SELect First
        // NOT Out of Stock
        SauceDetailPage detailPage = hotSaucesPage.selectFirstNotOutOfStockSauce();
        detailPage.buySauce();

        // Navigate To cart
        CartPage cartPage = hotSaucesPage.goToCart();
        // GET PRODUCT

        // ASSERT: DISCOUNT PRESENT

    }

    @Test
    public void testWeekendDiscountDisabled() {

        Date after4thWeekend = DateFactory.createDate(2018, 6, 25, 0, 0, 0);
        DBUtil.setTestTime(after4thWeekend);

        // TODO: Implement this
        Assertions.fail("Implement Testcase");

    }

}
