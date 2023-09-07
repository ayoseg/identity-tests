package com.idt.Pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ValueMyCarPage {

    private static final String CAR_VALUATION_SITE = "https://www.cazoo.co.uk/value-my-car/";
    static WebDriver driver;
    By searchField = By.id("vrm");
    By getStartedButton = By.cssSelector(".khfnzn");
    By acceptAllButton = By.cssSelector(".gihvmi");
    By errorAlert = By.cssSelector(".emraRb");

    public ValueMyCarPage(){
        //System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver/chromedriver");
        System.setProperty("webdriver.gecko.driver","src/main/resources/geckodriver/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(CAR_VALUATION_SITE);
        driver.findElement(acceptAllButton).click();
    }


    public ValueEstimatePage getValuation(String regNumber){
        driver.findElement(searchField).clear();
        driver.findElement(searchField).sendKeys(regNumber);
        driver.findElement(getStartedButton).click();
        return new ValueEstimatePage(driver);
    }

    public String regNotFound(String regNumber){
        driver.findElement(searchField).clear();
        driver.findElement(searchField).sendKeys(regNumber);
        driver.findElement(getStartedButton).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorAlert));
        int e = driver.findElements(errorAlert).size();
        if (e > 0) {
            System.err.println(driver.findElement(errorAlert).getText());
        }
        return driver.findElement(errorAlert).getText();
    }

    public void goBack(){
        driver.navigate().back();
    }

    static public void closeBrowser(){
        driver.quit();
    }
}
