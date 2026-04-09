package pageobjects;

import common.Constant;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static common.Constant.WEBDRIVER;

public class HomePage extends GeneralPage {
    private final By lnkLogin = By.linkText("Login");
    private final By lnkBookTicket = By.linkText("Book ticket");
    private final By lnkMyTicket = By.linkText("My ticket");

    // Mở trang chủ
    public HomePage open() {
        WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this; // Trả về chính nó để có thể gọi hàm tiếp theo một cách gọn gàng
    }

    // Đi tới Login page
    public LoginPage gotoLoginPage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(lnkLogin)
        );

        element.click();

        return new LoginPage();
    }

    // Đi tới Book Ticket page (FIX stale)
    public BookTicketPage gotoBookticket() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(lnkBookTicket)
        );

        element.click();

        return new BookTicketPage();
    }

    // Đi tới My Ticket page (FIX stale)
    public MyTicketPage gotoMyticket() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(lnkMyTicket)
        );

        element.click();

        return new MyTicketPage();
    }
    private final By _tabTimetable = By.linkText("Timetable");

    // Method to navigate to the Timetable page
    public TimeTable gotoTimeTable() {
        WEBDRIVER.findElement(_tabTimetable).click();
        return new TimeTable();
    }
}
