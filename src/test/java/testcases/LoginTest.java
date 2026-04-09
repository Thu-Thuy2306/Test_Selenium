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

public class LoginTest {

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
    public void TC01() {
        System.out.println("TC01 - Đăng nhập thành công");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        String actualMsg = homePage.getWelcomeMessage();
        String expectedMsg = "Welcome " + Constant.USERNAME;
        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message không đúng!");
    }
    @Test
    public void TC02() {
        System.out.println("TC02 - User can't login with blank 'Username' textbox");

        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();


        loginPage.login("", Constant.PASSWORD);

        String actualErrorMessage = loginPage.getLoginErrorMessage();

        String expectedErrorMessage = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "LỖI: Thông báo hiển thị không đúng yêu cầu của TC02!");

        System.out.println("TC02 ĐÃ PASS (Hệ thống chặn lỗi đúng kịch bản) ===");
    }

    @Test
    public void TC03() {
        System.out.println("TC03 - User cannot log into Railway with invalid password");


        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();




        loginPage.login(Constant.USERNAME, "123456789");


        String actualErrorMessage = loginPage.getLoginErrorMessage();
        String expectedErrorMessage = "There was a problem with your login and/or errors exist in your form.";


        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "LỖI: Thông báo hiển thị không đúng yêu cầu của TC03!");


        System.out.println("TC03 ĐÃ PASS (Hệ thống chặn sai mật khẩu chuẩn");
    }



    @Test
    public void TC05() {
        System.out.println("TC05 - System shows message when user enters wrong password several times");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        String wrongPassword = "123456789";

        for (int i = 1; i <= 4; i++) {
            loginPage.loginInvalid(Constant.USERNAME, wrongPassword);
        }

        String actualMsg = loginPage.getLoginErrorMessage();
        String expectedMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";

        System.out.println("Actual message: " + actualMsg);
        System.out.println("Expected message: " + expectedMsg);

        Assert.assertEquals(actualMsg, expectedMsg,
                "Warning message after 4 failed login attempts is not displayed as expected.");
    }

    @Test
    public void TC06() {
        System.out.println("TC06 - Kiểm tra hiển thị các tab sau đăng nhập");
        HomePage homePage = new HomePage();
        homePage.open();


        LoginPage loginPage = homePage.gotoLoginPage();
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);


        Assert.assertTrue(homePage.getTabMyTicket().isDisplayed(), "Tab My Ticket không hiển thị!");
        Assert.assertTrue(homePage.getTabChangePassword().isDisplayed(), "Tab Change Password không hiển thị!");
        // Khai báo element tab Logout
        var tabLogout = homePage.getTabLogout();


        // 1. Kiểm tra xem element có tồn tại và hiển thị không
        Assert.assertTrue(tabLogout.isDisplayed(), "Tab Log out không hiển thị trên giao diện!");


        // 2. Lấy text thực tế trên web và so sánh với text mong đợi từ tài liệu (để bắt lỗi)
        String actualLogoutText = tabLogout.getText().trim();
        Assert.assertEquals(actualLogoutText, "Logout", "Lỗi: Text hiển thị sai so với yêu cầu!");
        System.out.println("TC06 ĐÃ PASS (Kiểm tra hiển thị các tab sau đăng nhập");
    }



}