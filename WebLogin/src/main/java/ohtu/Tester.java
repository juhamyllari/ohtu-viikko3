package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tester {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        WebElement element;
        Random rand = new Random();

        driver.get("http://localhost:4567");
        sleep(1);
        element = driver.findElement(By.linkText("register new user"));
        element.click();
        sleep(1);
        element = driver.findElement(By.name("username"));
        element.sendKeys("hikka" + rand.nextInt(100000));
        element = driver.findElement(By.name("password"));
        element.sendKeys("tosisalainen0");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("tosisalainen0");
        element = driver.findElement(By.name("signup"));

        sleep(1);
        element.submit();
        sleep(2);
        
        element = driver.findElement(By.linkText("continue to application mainpage"));
        sleep(1);
        element.click();
        sleep(3);
        
        element = driver.findElement(By.linkText("logout"));
        sleep(1);
        element.click();
        sleep(3);

        driver.quit();

    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
    }
}
