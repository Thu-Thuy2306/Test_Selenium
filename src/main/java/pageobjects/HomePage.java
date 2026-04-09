package pageobjects;

import common.Constant;

public class HomePage extends GeneralPage {

    // Mở trang chủ
    public HomePage open() {
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this; // Trả về chính nó để có thể gọi hàm tiếp theo một cách gọn gàng
    }
}