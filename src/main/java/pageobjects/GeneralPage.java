package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.Constant;

public class GeneralPage {
    // 1. Locators: Chỉ giữ lại các nút cơ bản
    private final By tabLogin = By.xpath("//span[text()='Login']");
    private final By tabLogout = By.xpath("//span[text()='Log out']");
    private final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");

    // 2. Elements: Các hàm lấy ra Web Element cơ bản
    public WebElement getTabLogin() {
        return Constant.WEBDRIVER.findElement(tabLogin);
    }

    public WebElement getTabLogout() {
        return Constant.WEBDRIVER.findElement(tabLogout);
    }

    protected WebElement getLblWelcomeMessage() {
        return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
    }

    // 3. Methods: Các hành động chung
    public String getWelcomeMessage() {
        try {
            return Constant.WEBDRIVER.findElement(By.xpath("//div[@class='account']/strong")).getText();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            // Nếu bị cũ (Stale), bắt Selenium tìm lại lần nữa ngay lập tức
            return Constant.WEBDRIVER.findElement(By.xpath("//div[@class='account']/strong")).getText();
        }
    }

    // Nhấn nút Login -> Chuyển sang trang Login -> Trả về đối tượng LoginPage
    public LoginPage gotoLoginPage() {
        this.getTabLogin().click();
        return new LoginPage();
    }
}