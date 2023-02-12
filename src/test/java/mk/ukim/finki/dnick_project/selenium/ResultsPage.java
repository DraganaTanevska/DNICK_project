package mk.ukim.finki.dnick_project.selenium;

import org.openqa.selenium.WebDriver;

public class ResultsPage {
    protected WebDriver driver;
    public static void get(WebDriver driver, String relativeUrl) {
        String url = System.getProperty("geb.build.baseUrl", "http://localhost:9999") + relativeUrl;
        driver.get(url);
    }
}
