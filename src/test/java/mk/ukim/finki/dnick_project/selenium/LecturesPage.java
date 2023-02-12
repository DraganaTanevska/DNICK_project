package mk.ukim.finki.dnick_project.selenium;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class LecturesPage extends AbstractPage {

    private WebElement back;
    public LecturesPage(WebDriver driver){
        super(driver);
    }

    public static boolean checkIfLecturesPage(WebDriver driver, LecturesPage lecturesPage){
        return lecturesPage.title.getText().equals("Lectures");
    }

    public static LecturePage clickDetailsButton(WebDriver driver, LecturesPage lecturesPage){
        WebElement viewMoreButton=lecturesPage.driver.findElements(By.className("btn")).get(0);
        viewMoreButton.click();
        return PageFactory.initElements(driver,LecturePage.class);
    }

    public void checkButtons(int finishedButtons, int unfinishedButtons){
        List<WebElement> buttons=driver.findElements(By.className("status"));
        Assertions.assertEquals(finishedButtons, buttons.stream().filter(x->!x.getText().equals("Mark as not completed")).count());
        //Assertions.assertEquals(unfinishedButtons, buttons.stream().filter(x->x.getText().equals("Mark as completed")).count());
    }

    public static LecturesPage markTopicAsCompletedSuccessfully(WebDriver driver, LecturesPage lecturesPage) throws InterruptedException {
        WebElement button=lecturesPage.driver.findElements(By.className("status")).stream().filter(x->x.getText().equals("X")).collect(Collectors.toList()).get(0);
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].click()", button);
        Thread.sleep(2000);
        WebElement yes=driver.findElement(By.className("yes"));
        jse2.executeScript("arguments[0].click()", yes);
        Thread.sleep(2000);
        return lecturesPage;
    }
    public static LecturesPage markTopicAsCompletedUnsuccessfully(WebDriver driver, LecturesPage lecturesPage) throws InterruptedException {
        WebElement button=lecturesPage.driver.findElements(By.className("status")).stream().filter(x->x.getText().equals("X")).collect(Collectors.toList()).get(0);
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].click()", button);
        Thread.sleep(2000);
        WebElement no=lecturesPage.driver.findElement(By.className("no"));
        jse2.executeScript("arguments[0].click()", no);
        Thread.sleep(2000);
        return lecturesPage;
    }
}
