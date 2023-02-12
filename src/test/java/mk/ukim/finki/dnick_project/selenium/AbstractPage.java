package mk.ukim.finki.dnick_project.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AbstractPage {

    protected WebElement navToLogout;
    protected WebElement navToRegister;
    protected WebElement navToLogin;
    protected WebElement navToHome;
    protected WebElement navToTest;
    protected WebElement title;
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }
    public static void get(WebDriver driver, String relativeUrl) {
        String url = System.getProperty("geb.build.baseUrl", "http://localhost:9999") + relativeUrl;
        driver.get(url);
    }
    public static HomePage navBarGoToHomePage(WebDriver driver, AbstractPage page){
        page.navToHome.click();
        return PageFactory.initElements(driver, HomePage.class);
    }
    public static LoginPage navBarGoToLoginPage(WebDriver driver, AbstractPage page){
        page.navToLogin.click();
        return PageFactory.initElements(driver, LoginPage.class);
    }
    public static RegisterPage navBarGoToRegisterPage(WebDriver driver, AbstractPage page){
        page.navToRegister.click();
        return PageFactory.initElements(driver, RegisterPage.class);
    }
    public static LoginPage navBarGoToLogoutPage(WebDriver driver, AbstractPage page){
        page.navToLogout.click();
        return PageFactory.initElements(driver, LoginPage.class);
    }
    public static TestPage navBarGoToTestPage(WebDriver driver, AbstractPage page){
        page.navToTest.click();
        return PageFactory.initElements(driver, TestPage.class);
    }
}
