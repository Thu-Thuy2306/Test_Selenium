package pageobjects;

import org.openqa.selenium.By;
import common.Constant;

public class ChangePasswordPage extends GeneralPage {
    private final By _txtCurrentPassword = By.xpath("//input[@id='currentPassword']");
    private final By _txtNewPassword = By.xpath("//input[@id='newPassword']");
    private final By _txtConfirmPassword = By.xpath("//input[@id='confirmPassword']");
    private final By _btnChangePassword = By.xpath("//input[@value='Change Password']");
    private final By _lblChangePasswordSuccessMsg = By.xpath("//p[@class='message success']");

    public void changePassword(String oldPassword, String newPassword, String confirmPassword) {
        Constant.WEBDRIVER.findElement(_txtCurrentPassword).sendKeys(oldPassword);
        Constant.WEBDRIVER.findElement(_txtNewPassword).sendKeys(newPassword);
        Constant.WEBDRIVER.findElement(_txtConfirmPassword).sendKeys(confirmPassword);
        Constant.WEBDRIVER.findElement(_btnChangePassword).click();
    }

    public String getChangePasswordSuccessMessage() {
        return Constant.WEBDRIVER.findElement(_lblChangePasswordSuccessMsg).getText().trim();
    }
}