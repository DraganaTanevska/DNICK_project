package mk.ukim.finki.dnick_project.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends AbstractPage{
    private WebElement toLogin;
    private WebElement toRegister;
    private WebElement toLogout;
    private WebElement toLectures;
    public HomePage(WebDriver driver){
        super(driver);
    }
    public static HomePage openHome(WebDriver driver) {
        get(driver, "/home");
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, HomePage.class);
    }
    public static LoginPage navigateToLogin(WebDriver driver,HomePage homePage){
        homePage.toLogin.click();
        return PageFactory.initElements(driver, LoginPage.class);
    }
    public static LecturesPage navigateToLectures(WebDriver driver,HomePage homePage){
        homePage.toLectures.click();
        return PageFactory.initElements(driver, LecturesPage.class);
    }
    public static RegisterPage navigateToRegister(WebDriver driver,HomePage homePage){
        homePage.toRegister.click();
        return PageFactory.initElements(driver, RegisterPage.class);
    }
    public static LoginPage logout(WebDriver driver,HomePage homePage){
        homePage.toLogout.click();
        return PageFactory.initElements(driver, LoginPage.class);
    }
    public static boolean checkIfHomePage(WebDriver driver,HomePage homePage){
        return homePage.title.getText().equals("Home");
    }
}