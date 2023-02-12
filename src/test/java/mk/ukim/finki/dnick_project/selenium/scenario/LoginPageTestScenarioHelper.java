package mk.ukim.finki.dnick_project.selenium.scenario;

import mk.ukim.finki.dnick_project.selenium.HomePage;
import mk.ukim.finki.dnick_project.selenium.LoginPage;
import mk.ukim.finki.dnick_project.selenium.RegisterPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginPageTestScenarioHelper {
    private HtmlUnitDriver driver;
    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver();
        //initData();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }
    public LoginPage getLoginPage(){
        return LoginPage.openLogin(this.driver);
    }
    @Test
    public void testLoginPageRetrieve(){
        Assertions.assertTrue(LoginPage.checkIfLoginPage(this.driver,getLoginPage()));
    }
    @Test
    public void testLoginWithNoArguments(){
        //get login
        LoginPage loginPage=getLoginPage();

        //attempt login with no arguments (or 1 missing argument) -> the page should not change
        HomePage homePage=LoginPage.doLogin(this.driver,loginPage,"","");
        Assertions.assertFalse(HomePage.checkIfHomePage(this.driver,homePage));
        homePage=LoginPage.doLogin(this.driver,loginPage,"test","");
        Assertions.assertFalse(HomePage.checkIfHomePage(this.driver,homePage));
        homePage=LoginPage.doLogin(this.driver,loginPage,"","test");
        Assertions.assertFalse(HomePage.checkIfHomePage(this.driver,homePage));
    }
    @Test
    public void testSuccessfulLogin(){
        //get login
        LoginPage loginPage=getLoginPage();

        //attempt login with valid arguments
        HomePage homePage=LoginPage.doLogin(this.driver,loginPage,"dtn","password");
        Assertions.assertTrue(HomePage.checkIfHomePage(this.driver,homePage));
    }
    @Test
    public void testInvalidArguments(){
        //get login
        LoginPage loginPage=getLoginPage();

        //attempt login with invalid arguments
        HomePage homePage=LoginPage.doLogin(this.driver,loginPage,"invalid","arguments");
        Assertions.assertFalse(HomePage.checkIfHomePage(this.driver,homePage));
        Assertions.assertTrue(LoginPage.checkIfErrorIs(this.driver,loginPage,"Невалидна комбинација на корисничко име и лозинка"));
    }
    @Test
    public void testNavigateToRegisterPage(){
        //get login
        LoginPage loginPage=getLoginPage();

        //click on button for registration
        RegisterPage registerPage=LoginPage.navigateToRegisterPage(this.driver,loginPage);
        Assertions.assertTrue(RegisterPage.checkIfRegisterPage(this.driver,registerPage));
    }
}
