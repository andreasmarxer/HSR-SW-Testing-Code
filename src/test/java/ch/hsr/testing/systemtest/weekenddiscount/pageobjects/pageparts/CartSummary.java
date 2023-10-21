package ch.hsr.testing.systemtest.weekenddiscount.pageobjects.pageparts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CartSummary {

    private WebElement cartSummaryTable;
    private By subtotalSelector = By.cssSelector("div:nth-child(1)>span.pull-right");
    private By totalSavingSelector = By.cssSelector("div:nth-child(2)>span.pull-right");
    private By estimatedTotalSelector = By.cssSelector(".cart-estimated-total>span.pull-right");

    public CartSummary(WebElement cartSummaryTable) {
        this.cartSummaryTable = cartSummaryTable;
    }

    public float getSubtotal() {
        WebElement subtotalElement = cartSummaryTable
                .findElement(subtotalSelector);
        String subtotalText = subtotalElement.getText();
        return extractFloatValue(subtotalText);
    }

    public float getTotalSavings() {
        WebElement totalSavingsElement = cartSummaryTable
                .findElement(totalSavingSelector);
        String totalSavingsText = totalSavingsElement.getText();
        return extractFloatValue(totalSavingsText);
    }

    public float getEstimatedTotal() {
        WebElement estimatedTotalElement = cartSummaryTable
                .findElement(estimatedTotalSelector);
        String estimatedTotalText = estimatedTotalElement.getText();
        return extractFloatValue(estimatedTotalText);
    }

    private float extractFloatValue(String text) {
        String cleanText = text.replaceAll("[^0-9.,]", "");
        return Float.parseFloat(cleanText);
    }
}