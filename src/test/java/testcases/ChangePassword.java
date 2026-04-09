package testcases;

import common.Constant;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.ChangePasswordPage;

public class ChangePassword {
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("pre-condition");

        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
    }

    @Test
    public void TC09() {
        System.out.println("TC09 - UserCanChangePassword");
        String email = "phamphuong20032005@gmail.com";
        String oldPassword = "01042005";
        String newPassword = "12345678";

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(email, oldPassword);

        ChangePasswordPage changePasswordPage = loginPage.gotoChangePasswordPage();
        changePasswordPage.changePassword(oldPassword, newPassword, newPassword);

        Assert.assertEquals(
                changePasswordPage.getChangePasswordSuccessMessage(),
                "Your password has been updated"
        );
    }


}