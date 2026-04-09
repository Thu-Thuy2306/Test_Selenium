package pageobjects;

import org.openqa.selenium.By;
import common.Constant;

public class ForgotPasswordPage extends GeneralPage {
    // 1. Locators cho trang yêu cầu (Trang có nút Send Instructions)
    private final By txtEmail = By.id("email");
    private final By btnSendInstructions = By.xpath("//input[@value='Send Instructions']");

    // 2. Locators cho trang Reset Password (Các ô nhập liệu trong TC12)
    private final By txtNewPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By txtResetToken = By.id("resetToken");
    private final By btnResetPassword = By.xpath("//input[@value='Reset Password']");

    // Locators cho các thông báo lỗi (Dùng để Assert)
    private final By lblTopErrorMessage = By.xpath("//p[@class='message error']");
    private final By lblTokenErrorMessage = By.xpath("//label[@for='resetToken' and @class='validation-error']");

    // 3. Methods (Các hành động)

    // Hàm đi tắt thẳng vào trang Reset mật khẩu
    public void openResetPageWithBlankToken() {
        Constant.WEBDRIVER.get("http://railwayb1.somee.com/Account/PasswordReset.cshtml");
    }

    // Hàm thực hiện điền form Reset và bấm nút
    public void submitResetPassword(String newPass, String confirmPass, String token) {
        Constant.WEBDRIVER.findElement(txtNewPassword).sendKeys(newPass);
        Constant.WEBDRIVER.findElement(txtConfirmPassword).sendKeys(confirmPass);

        // Xóa trắng ô Token trước khi nhập (để đảm bảo test đúng ca Token rỗng)
        Constant.WEBDRIVER.findElement(txtResetToken).clear();
        Constant.WEBDRIVER.findElement(txtResetToken).sendKeys(token);

        Constant.WEBDRIVER.findElement(btnResetPassword).click();
    }

    // Hàm lấy thông báo lỗi trên cùng
    public String getTopErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblTopErrorMessage).getText();
    }

    // Hàm lấy thông báo lỗi ngay tại ô Token
    public String getTokenErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblTokenErrorMessage).getText();
    }
}

//package pageobjects;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import common.Constant;
//
//// Kế thừa GeneralPage
//public class ForgotPasswordPage extends GeneralPage {
//    // 1. Locators: Địa chỉ các ô nhập email và nút gửi
//    private final By txtEmail = By.id("email"); // Giả sử ID là email
//    private final By btnSendInstructions = By.xpath("//input[@value='Send Instructions']");
//
//    // Locators cho trang Reset Password Form (xuất hiện sau khi click link trong email)
//    // Để đơn giản cho automation, ta giả sử link này dẫn đến trang ResetPasswordPage với token bị thiếu
//    private final By lblTopErrorMessage = By.xpath("//p[@class='message error top']");
//    private final By lblTokenErrorMessage = By.xpath("//label[@id='token-error']");
//
//    // 2. Elements: Lấy ra Web Element
//    public WebElement getTxtEmail() {
//        return Constant.WEBDRIVER.findElement(txtEmail);
//    }
//
//    public WebElement getBtnSendInstructions() {
//        return Constant.WEBDRIVER.findElement(btnSendInstructions);
//    }
//
//    // 3. Methods: Hành động yêu cầu reset mật khẩu
//    public void requestPasswordReset(String email) {
//        this.getTxtEmail().sendKeys(email);
//        this.getBtnSendInstructions().click();
//    }
//
//    // Giả lập việc mở link từ email với token trống
//    public void openResetPageWithBlankToken() {
//        // Tạo link trực tiếp: link gốc + đoạn reset password với token trống
//        String url = Constant.RAILWAY_URL.replace("Login.cshtml", "") + "Account/PasswordReset?resetToken=";
//        Constant.WEBDRIVER.get(url);
//    }
//
//    // Lấy thông báo lỗi
//    public String getTopErrorMessage() {
//        return Constant.WEBDRIVER.findElement(lblTopErrorMessage).getText();
//    }
//
//    public String getTokenErrorMessage() {
//        return Constant.WEBDRIVER.findElement(lblTokenErrorMessage).getText();
//    }
//}

