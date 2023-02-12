package mk.ukim.finki.dnick_project.selenium.scenario;

import mk.ukim.finki.dnick_project.selenium.HomePage;
import mk.ukim.finki.dnick_project.selenium.LecturePage;
import mk.ukim.finki.dnick_project.selenium.LecturesPage;
import mk.ukim.finki.dnick_project.selenium.LoginPage;
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
public class LecturesPageTestScenarioHelper {

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
    public void testClickViewMoreButton(LecturesPage lecturesPage){
        //click View More button
        LecturePage lecturePage=LecturesPage.clickDetailsButton(this.driver,lecturesPage);
        Assertions.assertTrue(LecturePage.checkIfLecturePage(this.driver,lecturePage));
    }
    @Test
    public void testMarkAsCompletedSuccessfully(LecturesPage lecturesPage) throws InterruptedException {
        //login and go to page
        lecturesPage.checkButtons(0,8);

        //mark topic as completed but cancel the operation
        lecturesPage=LecturesPage.markTopicAsCompletedUnsuccessfully(this.driver,lecturesPage);
        lecturesPage.checkButtons(0,8);

        //mark topic as completed successfully
        lecturesPage=LecturesPage.markTopicAsCompletedSuccessfully(this.driver,lecturesPage);
        lecturesPage.checkButtons(1,7);
    }
}
