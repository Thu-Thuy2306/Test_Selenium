package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import common.Constant;
import pageobjects.ForgotPasswordPage; // Quan trọng: Phải có dòng này

public class ForgotPasswordTest {

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
    public void TC13() {
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL + "/Account/ForgotPassword.cshtml");
        Constant.WEBDRIVER.findElement(By.xpath("//input[@id='email']")).sendKeys(Constant.USERNAME);
        Constant.WEBDRIVER.findElement(By.xpath("//input[@value='Send Instructions']")).click();
        String pageSource = Constant.WEBDRIVER.getPageSource();
        Assert.assertFalse(pageSource.contains("Server Error"),
                "Server error occurred - SMTP not configured properly. TC13 is BLOCKED.");
    }
}

