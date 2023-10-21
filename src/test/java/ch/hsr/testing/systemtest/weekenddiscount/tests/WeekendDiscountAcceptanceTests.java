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
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.pageparts.CartProductItem;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.pageparts.CartSummary;
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

        // Arrange
        Date within4thWeekend = DateFactory.createDate(2018, 6, 23, 0, 0, 0);
        DBUtil.setTestTime(within4thWeekend);

        HomePage homePage = HomePage.navigateTo(driver);
        HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

        // Act
        SauceDetailPage detailPage = hotSaucesPage.selectFirstNotOutOfStockSauce();
        detailPage.buySauce();

        CartPage cartPage = hotSaucesPage.goToCart();
        CartSummary cartSummary = cartPage.getCartSummary();
        CartProductItem productItem = cartPage.getProductItem(0);

        // Assert
        Assertions.assertTrue(productItem.hasDiscount());
        Assertions.assertNotEquals(cartSummary.getTotalSavings(), 0);
        Assertions.assertNotEquals(cartSummary.getSubtotal(), cartSummary.getEstimatedTotal());
    }

    @Test
    public void testWeekendDiscountDisabled() {

        // Arrange
        Date after4thWeekend = DateFactory.createDate(2018, 6, 25, 0, 0, 0);
        DBUtil.setTestTime(after4thWeekend);

        HomePage homePage = HomePage.navigateTo(driver);
        HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

        // Act
        SauceDetailPage detailPage = hotSaucesPage.selectFirstNotOutOfStockSauce();
        detailPage.buySauce();

        CartPage cartPage = hotSaucesPage.goToCart();
        CartSummary cartSummary = cartPage.getCartSummary();
        CartProductItem productItem = cartPage.getProductItem(0);

        // Assert
        Assertions.assertFalse(productItem.hasDiscount());
        Assertions.assertEquals(cartSummary.getTotalSavings(), 0);
        Assertions.assertEquals(cartSummary.getSubtotal(), cartSummary.getEstimatedTotal());

    }

}
