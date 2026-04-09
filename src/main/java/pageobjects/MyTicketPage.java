package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyTicketPage extends GeneralPage {

    private final By btnCancel = By.xpath("//input[@value='Cancel']");
    private final By tblTicket = By.xpath("//table[@class='MyTable']//tbody//tr");

    public int getTicketCount() {
        return Constant.WEBDRIVER.findElements(tblTicket).size();
    }

    public void cancelFirstTicket() {

        WebElement btn = Constant.WEBDRIVER.findElement(btnCancel);

        // Scroll
        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].scrollIntoView(true);", btn);

        // Click bằng JS (tránh bị che)
        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].click();", btn);

        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        // Accept alert
        wait.until(ExpectedConditions.alertIsPresent());
        Constant.WEBDRIVER.switchTo().alert().accept();

        // Chờ reload bảng
        wait.until(ExpectedConditions.stalenessOf(btn));
    }
}