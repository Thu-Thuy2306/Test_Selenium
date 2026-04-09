//package pageobjects;
//
//import common.Constant;
//import org.openqa.selenium.By;
//
//public class RegisterPage extends GeneralPage {
//
//    private final By txtEmail = By.id("email");
//    private final By txtPassword = By.id("password");
//    private final By txtConfirmPassword = By.id("confirmPassword");
//    private final By txtPID = By.id("pid");
//    private final By btnRegister = By.xpath("//input[@value='Register']");
//    private final By lblErrorMessage = By.xpath("//p[@class='message error']");
//
//    public void registerAccount(String email, String password, String confirmPassword, String pid) {
//        Constant.WEBDRIVER.findElement(txtEmail).sendKeys(email);
//        Constant.WEBDRIVER.findElement(txtPassword).sendKeys(password);
//        Constant.WEBDRIVER.findElement(txtConfirmPassword).sendKeys(confirmPassword);
//        Constant.WEBDRIVER.findElement(txtPID).sendKeys(pid);
//        Constant.WEBDRIVER.findElement(btnRegister).click();
//    }
//
//    public String getRegisterErrorMessage() {
//        return Constant.WEBDRIVER.findElement(lblErrorMessage).getText();
//    }
//}
package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class RegisterPage extends GeneralPage {

    private final By txtEmail = By.id("email");
    private final By txtPassword = By.id("password");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By txtPID = By.id("pid");
    private final By btnRegister = By.xpath("//input[@value='Register']");
    private final By lblErrorMessage = By.xpath("//p[@class='message error']");

    public WebElement getTxtEmail() {
        return Constant.WEBDRIVER.findElement(txtEmail);
    }

    public WebElement getTxtPassword() {
        return Constant.WEBDRIVER.findElement(txtPassword);
    }

    public WebElement getTxtConfirmPassword() {
        return Constant.WEBDRIVER.findElement(txtConfirmPassword);
    }

    public WebElement getTxtPID() {
        return Constant.WEBDRIVER.findElement(txtPID);
    }

    public WebElement getBtnRegister() {
        return Constant.WEBDRIVER.findElement(btnRegister);
    }

    public WebElement getLblErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblErrorMessage);
    }

    public void registerAccount(String email, String password, String confirmPassword, String pid) {
        getTxtEmail().clear();
        getTxtEmail().sendKeys(email);

        getTxtPassword().clear();
        getTxtPassword().sendKeys(password);

        getTxtConfirmPassword().clear();
        getTxtConfirmPassword().sendKeys(confirmPassword);

        getTxtPID().clear();
        getTxtPID().sendKeys(pid);

        WebElement registerButton = getBtnRegister();

        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].scrollIntoView(true);", registerButton);

        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].click();", registerButton);
    }

    public String getRegisterErrorMessage() {
        return getLblErrorMessage().getText().trim();
    }
}