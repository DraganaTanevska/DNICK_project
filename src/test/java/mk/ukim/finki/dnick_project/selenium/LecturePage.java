package mk.ukim.finki.dnick_project.selenium;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LecturePage extends AbstractPage{
    private WebElement back;
    private WebElement statusNotCompleted;
    public LecturePage(WebDriver driver){
        super(driver);
    }
    public static boolean checkIfLecturePage(WebDriver driver, LecturePage topicPage){
        return topicPage.title.getText().equals("Lecture");
    }
    public static LecturesPage clickBackButton(WebDriver driver, LecturePage topicPage){
        topicPage.back.click();
        return PageFactory.initElements(driver, LecturesPage.class);
    }
    public static LecturePage changeStatusUnsuccessfully(WebDriver driver, LecturePage topicPage) throws InterruptedException {
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].click()", topicPage.statusNotCompleted);
        Thread.sleep(2000);
        WebElement no=topicPage.driver.findElement(By.className("no"));
        jse2.executeScript("arguments[0].click()", no);
        Thread.sleep(2000);
        return topicPage;
    }
    public static LecturePage changeStatusSuccessfully(WebDriver driver, LecturePage topicPage) throws InterruptedException {
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].click()", topicPage.statusNotCompleted);
        Thread.sleep(2000);
        WebElement mark_as_completed=topicPage.driver.findElement(By.className("mark-as-completed"));
        jse2.executeScript("arguments[0].click()", mark_as_completed);
        Thread.sleep(2000);
        return PageFactory.initElements(driver, LecturePage.class);
    }
}
