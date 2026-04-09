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
import pageobjects.ForgotPasswordPage; // Quan trọng: Phải có dòng này

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
    public void TC12() {
        System.out.println("TC12 - Kiểm tra lỗi khi Reset Token để trống");

        // 1. Khởi tạo trang và đi tắt đến URL Reset
        ForgotPasswordPage forgotPage = new ForgotPasswordPage();
        forgotPage.openResetPageWithBlankToken();

        // 2. Thực hiện nhập mật khẩu mới nhưng để Token rỗng ""
        // Các ID như newPassword, confirmPassword đã được xử lý bên trong hàm này
        forgotPage.submitResetPassword("12345678", "12345678", "");

        // 3. Kiểm tra kết quả mong đợi (Assertion)
        String actualError = forgotPage.getTopErrorMessage();
        String expectedError = "Could not reset password. Please correct the errors and try again.";

        // So sánh nội dung chữ để bắt lỗi chính tả nếu có
        Assert.assertEquals(actualError, expectedError, "LỖI: Thông báo lỗi trên cùng không đúng kịch bản Excel!");

        // 4. In thông báo kết quả ra Console
        System.out.println("TC12 ĐÃ PASS (Đã vượt qua bước gửi mail và kiểm tra thông báo lỗi thành công)");
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




}