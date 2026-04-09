package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GeneralPage {
    private final By tabLogin = By.xpath("//span[text()='Login']");
    private final By tabBookTicket = By.xpath("//span[text()='Book ticket']");
    private final By tabLogout = By.xpath("//span[text()='Log out']");
    private final By tabMyTicket = By.xpath("//span[text()='My ticket']");
    private final By tabChangePassword = By.xpath("//span[text()='Change password']");
    private final By tabRegister = By.xpath("//a[@href='/Account/Register.cshtml']");
    private final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");

    public WebElement getTabLogin() {
        return Constant.WEBDRIVER.findElement(tabLogin);
    }

    public WebElement getTabBookTicket() {
        return Constant.WEBDRIVER.findElement(tabBookTicket);
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

    public String getWelcomeMessage() {
        try {
            return getLblWelcomeMessage().getText();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            return getLblWelcomeMessage().getText();
        }
    }

    public LoginPage gotoLoginPage() {
        getTabLogin().click();
        return new LoginPage();
    }

    public LoginPage gotoBookTicketPage() {
        getTabBookTicket().click();
        return new LoginPage();
    }

    public ChangePasswordPage gotoChangePasswordPage() {
        getTabChangePassword().click();
        return new ChangePasswordPage();
    }

    public RegisterPage gotoRegisterPage() {
        Constant.WEBDRIVER.findElement(tabRegister).click();
        return new RegisterPage();
    }
}
