package mk.ukim.finki.dnick_project.selenium.scenario;

import mk.ukim.finki.dnick_project.selenium.LecturePage;
import mk.ukim.finki.dnick_project.selenium.LecturesPage;
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
public class LecturePageTestScenarioHelper {
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
    public void testChangeStatus(LecturePage lecturePage) throws InterruptedException {
    }
}
