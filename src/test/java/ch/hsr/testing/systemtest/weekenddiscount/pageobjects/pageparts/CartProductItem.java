package ch.hsr.testing.systemtest.weekenddiscount.pageobjects.pageparts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartProductItem {

    WebDriver driver;
    WebElement cartProductRow;
    By priceSelector = By.className("cart-product-pricing");
    By discountSelector = By.className("discount");

    public CartProductItem(WebDriver driver, WebElement cartProductRow) {
        this.driver = driver;
        this.cartProductRow = cartProductRow;
    }

    public boolean hasDiscount() {
        WebElement priceElement = cartProductRow.findElement(priceSelector);
        List<WebElement> discountElements = priceElement.findElements(discountSelector);
        return !discountElements.isEmpty();
    }
}
