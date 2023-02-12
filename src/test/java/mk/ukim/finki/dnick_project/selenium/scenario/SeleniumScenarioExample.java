package mk.ukim.finki.dnick_project.selenium.scenario;

import mk.ukim.finki.dnick_project.selenium.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioExample {
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
    @Test
    public void testScenario() throws InterruptedException {
        //get register page and register a new user
        RegisterPage registerPage=RegisterPage.openRegister(this.driver);
        LoginPage loginPage=RegisterPage.doRegister(this.driver,registerPage,"NewUser","test","test","Name","Surname");

        //login with the new user
        HomePage homePage=LoginPage.doLogin(this.driver,loginPage,"NewUser","test");

        //go to learn page
        LecturesPage lecturesPage=HomePage.navigateToLectures(this.driver,homePage);
        lecturesPage.checkButtons(0,3);

        //check status and mark it as completed
        LecturePage lecturePage=LecturesPage.clickDetailsButton(this.driver,lecturesPage);

        //start a test and end it successfully
        TestPage testPage=LecturePage.navBarGoToTestPage(this.driver,lecturePage);
    }

}
