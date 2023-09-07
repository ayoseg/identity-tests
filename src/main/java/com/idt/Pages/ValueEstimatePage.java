package com.idt.Pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ValueEstimatePage {

    WebDriver driver;

    By makeAndModel = By.cssSelector(".ibfnPT");
    By variantAndRegNo = By.cssSelector(".jHDwGZ:nth-of-type(2)");
    By your_car_header = By.cssSelector(".jHDwGZ");
    By carDetails = By.cssSelector(".dHVfnH");
    public ValueEstimatePage(WebDriver driver){
        this.driver = driver;
    }

    private String getMakeAndModel(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(your_car_header));
        return driver.findElement(makeAndModel).getText();
    }

    public void returnToValuationSearchPage(){
        driver.navigate().back();
    }

    public String getHeader(){
        return driver.findElement(your_car_header).getText();
    }

    private String getVariantAndRegNo(){
        return driver.findElement(variantAndRegNo).getText();
    }

    private String getCarDetails(){
        return driver.findElement(carDetails).getText();
    }

    public String getRegistration(){
        return getVariantAndRegNo().split("\\|")[1].replaceAll(" ", "");
    }

    public String getMake(){
        return getMakeAndModel().split(" ")[0];
    }

    public String getModel(){
        String make = getMakeAndModel().split(" ")[1] + " " +
                getVariantAndRegNo().split("\\|")[0].stripTrailing()  + " " +
                getCarDetails().split("\\| ", 2)[1].replaceAll("\\|", "").replaceAll("  ", " ");
        return make;
    }

}
