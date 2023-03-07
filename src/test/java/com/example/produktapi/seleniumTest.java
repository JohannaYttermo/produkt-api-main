package com.example.produktapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class seleniumTest {
    @Test
    @Disabled
    void checkTitle() {

        // Hämta in den webDriver som ska användas.
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

        // test som failar - "webbutik"
        assertEquals("Webbutik", driver.getTitle(),"Felmeddelande: Title don't match as expected! Note it's Case Sensitive");

        driver.quit();
    }

    @Test
     @Disabled
    public void numberOfProductsShouldBeTwenty() {

        // Hämta in den webDriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas -  navigate().to kan va get()
        driver.navigate().to("https://java22.netlify.app/");

                //
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        List <WebElement> products = driver.findElements(By.className("productItem"));

        // 21 = fail
        assertEquals(20,products.size(), "Antalet do not match!");

        driver.quit();
    }

    @Test
    @Disabled
    public void checkIfProductOneHaveRightPrice() {

        // Hämta in den webDriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas
        driver.get("https://java22.netlify.app/");

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        String productOne = driver.findElement(By.xpath("//*[@id='productsContainer']/div/div[1]/div/div/p")).getText();

        String findPriceForProductOne = "109.95";

        boolean validatePriceForProductOne = productOne.contains(findPriceForProductOne);

        assertTrue(validatePriceForProductOne, "Priset på produkt 1 stämmer inte!");

        driver.quit();
    }

    @Test
    @Disabled
    public void checkIfProductTwoHaveRightPrice() {

        // Hämta in den webDriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas -  navigate().to kan va get()
        driver.get("https://java22.netlify.app/");

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String productTwo = driver.findElement(By.xpath("//*[@id='productsContainer']/div/div[2]/div/div/p")).getText();

        String findPriceForProductTwo = "22.3";

        boolean validatePriceForProductTwo = productTwo.contains(findPriceForProductTwo);

        assertTrue(validatePriceForProductTwo, "Priset på produkt 2 stämmer inte!");

        driver.quit();
    }
    @Test
    @Disabled
    public void checkIfProductThreeHaveRightPrice() {

        // Hämta in den webDriver som ska användas
        WebDriver driver = new ChromeDriver();

        // Navigera till den webbsida som ska testas -  navigate().to kan va get()
        driver.get("https://java22.netlify.app/");

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String productThree = driver.findElement(By.xpath("//*[@id='productsContainer']/div/div[3]/div/div/p")).getText();

        String findPriceForProductThree = "55.99";

        boolean validatePriceForProductThree = productThree.contains(findPriceForProductThree);

        assertTrue(validatePriceForProductThree, "Priset på produkt 3 stämmer inte!");

        driver.quit();
    }


}