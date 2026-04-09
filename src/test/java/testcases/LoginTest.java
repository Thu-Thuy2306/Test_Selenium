package testcases;

import common.Constant;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.RegisterPage;

public class LoginTest {

    @BeforeMethod
    public void beforeMethod() {
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().setSize(new org.openqa.selenium.Dimension(1440, 900));
        Constant.WEBDRIVER.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }

    @AfterMethod
    public void afterMethod() {
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
        }
    }

    @Test
    public void TC01() {
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        String actualMsg = homePage.getWelcomeMessage();
        String expectedMsg = "Welcome " + Constant.USERNAME;
        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is incorrect.");
    }

    @Test
    public void TC02() {
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("", Constant.PASSWORD);

        String actualErrorMessage = loginPage.getLoginErrorMessage();
        String expectedErrorMessage = "There was a problem with your login and/or errors exist in your form.";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
                "The login validation message is incorrect for blank username.");
    }

    @Test
    public void TC03() {
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, "123456789");

        String actualErrorMessage = loginPage.getLoginErrorMessage();
        String expectedErrorMessage = "There was a problem with your login and/or errors exist in your form.";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
                "The login validation message is incorrect for invalid password.");
    }

    @Test
    public void TC05() {
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        String wrongPassword = "123456789";

        for (int i = 1; i <= 4; i++) {
            loginPage.loginInvalid(Constant.USERNAME, wrongPassword);
        }

        String actualMsg = loginPage.getLoginErrorMessage();
        String expectedMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
        Assert.assertEquals(actualMsg, expectedMsg,
                "Warning message after 4 failed login attempts is not displayed as expected.");
    }

    @Test
    public void TC06() {
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        Assert.assertTrue(homePage.getTabMyTicket().isDisplayed(), "Tab My Ticket is not displayed.");
        Assert.assertTrue(homePage.getTabChangePassword().isDisplayed(), "Tab Change Password is not displayed.");
        Assert.assertTrue(homePage.getTabLogout().isDisplayed(), "Tab Log out is not displayed.");
        Assert.assertEquals(homePage.getTabLogout().getText().trim(), "Logout", "Logout tab text is incorrect.");
    }

    @Test
    public void TC08() {
        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegisterPage();
        String email = generateEmail();
        String password = "123456789";
        String pid = "123456789";

        registerPage.register(email, password, password, pid);
        Assert.assertEquals(registerPage.getSuccessMessage(),
                "Registration Confirmed! You can now log in to the site.",
                "The account was not created successfully.");

        LoginPage loginPage = registerPage.gotoLoginPage();
        HomePage loggedInHomePage = loginPage.login(email, password);

        String actualMessage = loggedInHomePage.getWelcomeMessage();
        Assert.assertEquals(actualMessage, "Welcome " + email,
                "Newly registered account could not login successfully.");
    }

    private String generateEmail() {
        return "user" + System.currentTimeMillis() + "@mail.com";
    }
}
