package testcases;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.BookTicketPage;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.TimeTable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookticketTest {

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("== Pre-condition: Mở trình duyệt Chrome ==");
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
        Constant.WEBDRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void TC04() {
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoBookTicketPage();

        Assert.assertTrue(loginPage.isLoginFormDisplayed(),
                "Login page is not displayed when an un-logged user clicks Book ticket.");
    }

    @Test
    public void TC14() {
        String username = "thanhhanh0104@gmail.com";
        String password = "Thanhhanh0104@";

        Constant.WEBDRIVER.get("http://railwayb1.somee.com");

        Constant.WEBDRIVER.findElement(By.linkText("Login")).click();
        Constant.WEBDRIVER.findElement(By.id("username")).sendKeys(username);
        Constant.WEBDRIVER.findElement(By.id("password")).sendKeys(password);
        Constant.WEBDRIVER.findElement(By.cssSelector("input[type='submit']")).click();

        Constant.WEBDRIVER.findElement(By.linkText("Book ticket")).click();

        Random random = new Random();

        By dateLocator = By.name("Date");
        By departLocator = By.name("DepartStation");
        By arriveLocator = By.name("ArriveStation");
        By seatTypeLocator = By.name("SeatType");
        By ticketAmountLocator = By.name("TicketAmount");

        String departDate = getRandomOptionText(dateLocator, random);
        String departFrom = getRandomOptionText(departLocator, random);

        waitForDropdownReady(arriveLocator);

        String arriveAt = getRandomOptionTextExcluding(arriveLocator, departFrom, random);
        String seatType = getRandomOptionText(seatTypeLocator, random);

        new Select(Constant.WEBDRIVER.findElement(ticketAmountLocator)).selectByVisibleText("1");
        String ticketAmount = "1";

        System.out.println("Depart Date: " + departDate);
        System.out.println("Depart From: " + departFrom);
        System.out.println("Arrive At: " + arriveAt);
        System.out.println("Seat Type: " + seatType);
        System.out.println("Ticket Amount: " + ticketAmount);

        WebElement bookTicketButton = Constant.WEBDRIVER.findElement(By.cssSelector("input[type='submit']"));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView({block:'center'});", bookTicketButton);

        try {
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(bookTicketButton))
                    .click();
        } catch (Exception e) {
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", bookTicketButton);
        }

        Assert.assertTrue(
                Constant.WEBDRIVER.findElement(By.xpath("//*[contains(text(),'Ticket booked successfully!')]")).isDisplayed(),
                "Success message is not displayed."
        );

        WebElement row = Constant.WEBDRIVER.findElement(
                By.xpath("//table[@class='MyTable WideTable']/tbody/tr[2]")
        );

        List<WebElement> cells = row.findElements(By.tagName("td"));

        String actualDepart = cells.get(0).getText().trim();
        String actualArrive = cells.get(1).getText().trim();
        String actualSeatType = cells.get(2).getText().trim();
        String actualDepartDate = cells.get(3).getText().trim();
        String actualAmount = cells.get(6).getText().trim();

        Assert.assertEquals(actualDepart, departFrom, "Depart Station is incorrect.");
        Assert.assertEquals(actualArrive, arriveAt, "Arrive Station is incorrect.");
        Assert.assertEquals(actualSeatType, seatType, "Seat Type is incorrect.");
        Assert.assertEquals(actualDepartDate, departDate, "Depart Date is incorrect.");
        Assert.assertEquals(actualAmount, ticketAmount, "Ticket Amount is incorrect.");
    }

    private void waitForDropdownReady(By locator) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(driver -> {
            Select select = new Select(driver.findElement(locator));
            return select.getOptions().size() > 1;
        });
    }

    private String getRandomOptionText(By locator, Random random) {
        waitForDropdownReady(locator);

        Select select = new Select(Constant.WEBDRIVER.findElement(locator));
        List<String> validOptions = new ArrayList<>();

        for (WebElement option : select.getOptions()) {
            String text = option.getText().trim();
            if (!text.isEmpty()) {
                validOptions.add(text);
            }
        }

        String chosen = validOptions.get(random.nextInt(validOptions.size()));
        new Select(Constant.WEBDRIVER.findElement(locator)).selectByVisibleText(chosen);
        return chosen;
    }

    private String getRandomOptionTextExcluding(By locator, String excludedText, Random random) {
        waitForDropdownReady(locator);

        List<String> validOptions = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            try {
                Select select = new Select(Constant.WEBDRIVER.findElement(locator));
                validOptions.clear();

                for (WebElement option : select.getOptions()) {
                    String text = option.getText().trim();
                    if (!text.isEmpty() && !text.equals(excludedText)) {
                        validOptions.add(text);
                    }
                }

                break;
            } catch (StaleElementReferenceException e) {
                if (i == 2) throw e;
            }
        }

        String chosen = validOptions.get(random.nextInt(validOptions.size()));

        for (int i = 0; i < 3; i++) {
            try {
                new Select(Constant.WEBDRIVER.findElement(locator)).selectByVisibleText(chosen);
                return chosen;
            } catch (StaleElementReferenceException e) {
                if (i == 2) throw e;
            }
        }

        return chosen;
    }
    @Test
    public void TC15() {
        String username = "thanhhanh0104@gmail.com";
        String password = "Thanhhanh0104@";

        System.out.println("TC15 - User can open \"Book ticket\" page by clicking on \"Book ticket\" link in \"Train timetable\" page");

        Constant.WEBDRIVER.get("http://railwayb1.somee.com");

        Constant.WEBDRIVER.findElement(By.linkText("Login")).click();
        Constant.WEBDRIVER.findElement(By.id("username")).sendKeys(username);
        Constant.WEBDRIVER.findElement(By.id("password")).sendKeys(password);
        Constant.WEBDRIVER.findElement(By.cssSelector("input[type='submit']")).click();

        Constant.WEBDRIVER.findElement(By.linkText("Timetable")).click();

        WebElement bookTicketLink = Constant.WEBDRIVER.findElement(
                By.xpath("//tr[td[normalize-space()='Huế'] and td[normalize-space()='Sài Gòn']]//a[normalize-space()='book ticket']")
        );

        ((JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", bookTicketLink);

        try {
            new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(bookTicketLink))
                    .click();
        } catch (Exception e) {
            ((JavascriptExecutor) Constant.WEBDRIVER)
                    .executeScript("arguments[0].click();", bookTicketLink);
        }

        Assert.assertTrue(
                Constant.WEBDRIVER.getCurrentUrl().contains("BookTicketPage.cshtml"),
                "Book ticket page is not loaded."
        );

        Select departDropdown = new Select(Constant.WEBDRIVER.findElement(By.name("DepartStation")));
        Select arriveDropdown = new Select(Constant.WEBDRIVER.findElement(By.name("ArriveStation")));

        String actualDepart = departDropdown.getFirstSelectedOption().getText().trim();
        String actualArrive = arriveDropdown.getFirstSelectedOption().getText().trim();

        Assert.assertEquals(actualDepart, "Huế", "Depart from value is incorrect.");
        Assert.assertEquals(actualArrive, "Sài Gòn", "Arrive at value is incorrect.");
    }
    @AfterMethod
    public void afterMethod() {
        System.out.println("== Post-condition: Đóng trình duyệt ==");
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
        }
    }
}
