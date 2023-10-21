package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.pageparts.CartProductItem;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.pageparts.CartSummary;

public class CartPage extends Page {

    private static final Log LOG = LogFactory.getLog(HomePage.class);

    @FindBy(className = "cart-product-row")
    private List<WebElement> productsInCartTable;

    @FindBy(className = "cart-summary-breakdown")
    private WebElement summaryTable;

    public CartPage(WebDriver driver) {
        super(driver);

        if (!(driver.getPageSource().contains("checkout"))) {
            throw new IllegalStateException("This is not the cart page");
        }
        LOG.debug("CartPage created successfully");
    }

    public CartProductItem getProductItem(int index) {
        return new CartProductItem(driver, productsInCartTable.get(index));
    }

    public CartSummary getCartSummary() {
        return new CartSummary(summaryTable);
    }

}
