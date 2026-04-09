package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends GeneralPage {
    private final By txtUsername = By.id("username");
    private final By txtPassword = By.id("password");
    private final By btnLogin = By.xpath("//input[@value='login']");
    private final By lnkForgotPassword = By.xpath("//a[contains(text(),'Forgot Password page')]");
    private final By lblLoginError = By.xpath("//p[@class='message error LoginForm']");

    public WebElement getTxtUsername() {
        return Constant.WEBDRIVER.findElement(txtUsername);
    }

    public WebElement getTxtPassword() {
        return Constant.WEBDRIVER.findElement(txtPassword);
    }

    public WebElement getBtnLogin() {
        return Constant.WEBDRIVER.findElement(btnLogin);
    }

    public HomePage login(String username, String password) {
        getTxtUsername().clear();
        getTxtUsername().sendKeys(username);
        getTxtPassword().clear();
        getTxtPassword().sendKeys(password);
        getBtnLogin().click();
        return new HomePage();
    }

    public WebElement getLnkForgotPassword() {
        return Constant.WEBDRIVER.findElement(lnkForgotPassword);
    }

    public ForgotPasswordPage gotoForgotPasswordPage() {
        getLnkForgotPassword().click();
        return new ForgotPasswordPage();
    }

    public String getLoginErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblLoginError).getText();
    }

    public boolean isLoginFormDisplayed() {
        return getTxtUsername().isDisplayed()
                && getTxtPassword().isDisplayed()
                && getBtnLogin().isDisplayed();
    }

    public void loginInvalid(String username, String password) {
        getTxtUsername().clear();
        getTxtUsername().sendKeys(username);
        getTxtPassword().clear();
        getTxtPassword().sendKeys(password);
        getBtnLogin().click();
    }
}
