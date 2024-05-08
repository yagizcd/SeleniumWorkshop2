package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SeleniumWorkShop2Tests {
    WebDriver driver;
    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.navigate().to("https://www.saucedemo.com/v1/");

        driver.manage().window().maximize();
    }
    @After
    public void end(){
        driver.quit();
    }
    @Test
    public void urlTest(){
        String currentURL = driver.getCurrentUrl();
        assert(currentURL.contains("https://www.saucedemo.com/v1/"));
    }
    @Test
    public void loginTest(){
        driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement product = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#inventory_filter_container > div")));
        assert(product.getText().toLowerCase().contains("product"));
    }

    @Test
    public void findProducts(){
        loginTest();
        List<WebElement> elements = driver.findElements(By.cssSelector(".inventory_item_name"));
        String productsExp[] = {"Sauce Labs Backpack","Sauce Labs Bike Light","Sauce Labs Bolt T-Shirt",
        "Sauce Labs Fleece Jacket","Sauce Labs Onesie","Test.allTheThings() T-Shirt (Red)"};
        List<String> productsAct = new ArrayList<>();
        for (WebElement element:elements){
            productsAct.add(element.getText());
        }
        assertArrayEquals(productsExp,productsAct.toArray());
    }
    @Test
    public void testTitle(){
        assert(driver.getTitle().toLowerCase().contains("swag"));
    }
    @Test
    public void backTest(){
        loginTest();
        driver.navigate().back();
        urlTest();
    }
    @Test
    public void addtoBasket(){
        loginTest();
        driver.findElement(By.xpath("//div[text()='29.99']/following-sibling::button")).click();
        driver.findElement(By.cssSelector("svg[data-icon='shopping-cart']")).click();
        assert(driver.findElement(By.cssSelector("div.inventory_item_name")).getText().contains("Backpack"));

    }


}
