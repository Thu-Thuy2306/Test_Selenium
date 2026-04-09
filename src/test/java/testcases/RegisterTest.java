package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import common.Constant;
import pageobjects.HomePage;
import pageobjects.LoginPage;

import org.openqa.selenium.JavascriptExecutor;

public class RegisterTest {
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("== Pre-condition: Mở trình duyệt Chrome ==");
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
        // Thêm dòng chờ để tránh lỗi không tìm thấy element
        Constant.WEBDRIVER.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("== Post-condition: Đóng trình duyệt ==");
        Constant.WEBDRIVER.quit();
    }

    @Test
    public void TC11() throws InterruptedException {
        System.out.println("TC11 - User can't create account while password and PID fields are empty");


        // 1. Mở trang
        Constant.WEBDRIVER.get("http://railwayb1.somee.com/Page/HomePage.cshtml");


        // 2. Click Register bằng JS
        WebElement registerLink = Constant.WEBDRIVER.findElement(By.linkText("Register"));


        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].click();", registerLink);


        Thread.sleep(2000);


        // 3. Scroll xuống form (QUAN TRỌNG)
        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("window.scrollBy(0,500)");


        Thread.sleep(1000);


        // 4. Nhập email
        Constant.WEBDRIVER.findElement(By.id("email")).sendKeys("thuthuy.01t@gmail.com");


        // 5. Click Register button bằng JS (FIX LỖI CHÍNH)
        WebElement btnRegister = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='Register']"));


        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].click();", btnRegister);


        Thread.sleep(1000);


        // ===== ASSERT =====


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


        System.out.println("TC11 PASS");
    }

}
