package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import common.Constant;

// Kế thừa GeneralPage
public class LoginPage extends GeneralPage {
    // 1. Locators: Địa chỉ các ô nhập liệu
    private final By txtUsername = By.id("username");
    private final By txtPassword = By.id("password");
    private final By btnLogin = By.xpath("//input[@value='login']");

    // 2. Elements: Lấy ra Web Element
    public WebElement getTxtUsername() {
        return Constant.WEBDRIVER.findElement(txtUsername);
    }

    public WebElement getTxtPassword() {
        return Constant.WEBDRIVER.findElement(txtPassword);
    }

    public WebElement getBtnLogin() {
        return Constant.WEBDRIVER.findElement(btnLogin);
    }

    // 3. Methods: Hành động đăng nhập
    // Nhập user, pass -> Nhấn Login -> Trả về trang HomePage (đã login thành công)
    public HomePage login(String username, String password) {
        this.getTxtUsername().sendKeys(username);
        this.getTxtPassword().sendKeys(password);
        this.getBtnLogin().click();
        return new HomePage();
    }

    // Locator cho link "Forgot Password page"
    private final By lnkForgotPassword = By.xpath("//a[contains(text(),'Forgot Password page')]");

    // Element và Method để click vào link
    public WebElement getLnkForgotPassword() {
        return Constant.WEBDRIVER.findElement(lnkForgotPassword);
    }

    // Click và chuyển sang trang ForgotPasswordPage
    public ForgotPasswordPage gotoForgotPasswordPage() {
        this.getLnkForgotPassword().click();
        return new ForgotPasswordPage();
    }

    private final By lblLoginError = By.xpath("//p[@class='message error LoginForm']");

    // 2. Method để lấy nội dung chữ của lỗi đó
    public String getLoginErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblLoginError).getText();
    }

    public void loginInvalid(String username, String password) {
        getTxtUsername().clear();
        getTxtUsername().sendKeys(username);

        getTxtPassword().clear();
        getTxtPassword().sendKeys(password);

        getBtnLogin().click();
    }

//    tc5


}