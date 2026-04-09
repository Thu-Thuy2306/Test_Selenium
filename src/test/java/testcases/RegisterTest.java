package testcases;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.RegisterPage;

public class RegisterTest {
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
    public void TC07() {
        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegisterPage();
        String email = "user" + System.currentTimeMillis() + "@mail.com";
        String password = "123456789";
        String pid = "123456789";

        registerPage.register(email, password, password, pid);

        Assert.assertEquals(
                registerPage.getSuccessMessage(),
                "Registration Confirmed! You can now log in to the site."
        );
    }

    @Test
    public void TC10() {
        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegisterPage();
        String email = "user" + System.currentTimeMillis() + "@mail.com";
        String password = "123456789";
        String confirmPassword = "987654321";
        String pid = "123456789";

        registerPage.registerAccount(email, password, confirmPassword, pid);

        Assert.assertEquals(
                registerPage.getRegisterErrorMessage(),
                "There're errors in the form. Please correct the errors and try again."
        );
    }

    @Test
    public void TC11() throws InterruptedException {
        Constant.WEBDRIVER.get("http://railwayb1.somee.com/Page/HomePage.cshtml");

        WebElement registerLink = Constant.WEBDRIVER.findElement(By.linkText("Register"));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", registerLink);

        Thread.sleep(2000);
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("window.scrollBy(0,500)");
        Thread.sleep(1000);

        Constant.WEBDRIVER.findElement(By.id("email")).sendKeys("thuthuy.01t@gmail.com");

        WebElement btnRegister = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='Register']"));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", btnRegister);

        Thread.sleep(1000);

        String topError = Constant.WEBDRIVER.findElement(
                By.xpath("//*[contains(text(),\"There're errors in the form\")]")
        ).getText();
        Assert.assertTrue(topError.contains("There're errors in the form"));

        String passError = Constant.WEBDRIVER.findElement(
                By.xpath("//*[contains(text(),'Invalid password length')]")
        ).getText();
        Assert.assertTrue(passError.contains("Invalid password length"));

        String pidError = Constant.WEBDRIVER.findElement(
                By.xpath("//*[contains(text(),'Invalid ID length')]")
        ).getText();
        Assert.assertTrue(pidError.contains("Invalid ID length"));
    }
}
