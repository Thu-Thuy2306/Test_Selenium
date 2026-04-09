package pageobjects;

import common.Constant;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import static common.Constant.WEBDRIVER;

public class HomePage extends GeneralPage {

    // Mở trang chủ
    public HomePage open() {
        WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this; // Trả về chính nó để có thể gọi hàm tiếp theo một cách gọn gàng
    }


    private final By _tabTimetable = By.linkText("Timetable");

    // Method to navigate to the Timetable page
    public TimeTable gotoTimeTable() {
        WEBDRIVER.findElement(_tabTimetable).click();
        return new TimeTable();
    }
}