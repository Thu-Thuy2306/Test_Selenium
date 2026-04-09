package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.Constant;

public class GeneralPage {
    // 1. Locators: Chỉ giữ lại các nút cơ bản
    private final By tabLogin = By.xpath("//span[text()='Login']");
    private final By tabLogout = By.xpath("//span[text()='Log out']");
    private final By tabMyTicket = By.xpath("//span[text()='My ticket']");
    private final By tabChangePassword = By.xpath("//span[text()='Change password']");
    private final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");

    // 2. Elements: Các hàm lấy ra Web Element cơ bản
    public WebElement getTabLogin() {
        return Constant.WEBDRIVER.findElement(tabLogin);
    }

    public WebElement getTabLogout() {
        return Constant.WEBDRIVER.findElement(tabLogout);
    }

    public WebElement getTabMyTicket() {
        return Constant.WEBDRIVER.findElement(tabMyTicket);
    }

    public WebElement getTabChangePassword() {
        return Constant.WEBDRIVER.findElement(tabChangePassword);
    }

    protected WebElement getLblWelcomeMessage() {
        return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
    }

    private final By tabRegister = By.xpath("//a[@href='/Account/Register.cshtml']");

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

    //tc9

    public ChangePasswordPage gotoChangePasswordPage() {
        this.getTabChangePassword().click();
        return new ChangePasswordPage();
    }

    //tc10
    public RegisterPage gotoRegisterPage() {
        Constant.WEBDRIVER.findElement(tabRegister).click();
        return new RegisterPage();
    }
}