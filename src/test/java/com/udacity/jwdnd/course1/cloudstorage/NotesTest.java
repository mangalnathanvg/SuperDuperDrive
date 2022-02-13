package com.udacity.jwdnd.course1.cloudstorage;


import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotesTests extends LoginAndSignupTests {


    private void createDefaultNote() {
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note-btn")));

        WebElement newNoteBtn = driver.findElement(By.id("new-note-btn"));
        Assertions.assertNotNull(newNoteBtn);
        newNoteBtn.click();

        WebElement noteTitle = driver.findElement(By.id("note-title"));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitle));
        noteTitle.sendKeys(getDefaultTitle());

        WebElement noteDescription = driver.findElement(By.id("note-description"));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteDescription));
        noteDescription.sendKeys(getDefaultDescription());

        WebElement noteForm = driver.findElement(By.id("note-form"));
        noteForm.submit();

        Assertions.assertTrue(driver.findElement(By.id("success-add-note")).getText().contains("Note added successfully"));

        WebElement backToHomeLink = driver.findElement(By.id("backtohome"));
        backToHomeLink.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        notesTab.click();
    }

    private void checkIfNoteDisplayed(String title, String description)
    {
        WebElement noteTable = driver.findElement(By.id("userTable"));
        List<WebElement> notes = noteTable.findElements(By.tagName("th"));

        boolean foundTitle = false;
        for (WebElement element : notes) {
            if (element.getAttribute("innerHTML").equals(title)) {
                foundTitle = true;
                break;
            }
        }

        Assertions.assertTrue(foundTitle);

        notes.clear();
        notes = noteTable.findElements(By.tagName("td"));

        boolean foundDescription = false;
        for (WebElement element : notes) {
            if (element.getAttribute("innerHTML").equals(description)) {
                foundDescription = true;
                break;
            }
        }

        Assertions.assertTrue(foundDescription);
    }

    @Test
    public void displayEnteredNote()
    {
        doMockSignUp("Monkey D.", "Luffy", "Mugiwara", "Kaizoku");
        doLogIn("Mugiwara", "Kaizoku");
        createDefaultNote();

        checkIfNoteDisplayed(getDefaultTitle(), getDefaultDescription());
    }

    @Test
    public void editNote()
    {
        doMockSignUp("Marshall", "Teach", "Kurohige", "Kaizoku");
        doLogIn("Kurohige", "Kaizoku");
        createDefaultNote();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note-btn")));

        String newTitle = "My New Dream";
        String newDescription = "Eat all the meat";

        WebElement editButton = driver.findElement(By.id("note-edit"));
        editButton.click();

        WebElement noteTitle = driver.findElement(By.id("note-title"));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitle));
        noteTitle.clear();
        noteTitle.sendKeys(newTitle);

        WebElement noteDescription = driver.findElement(By.id("note-description"));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteDescription));
        noteDescription.clear();
        noteDescription.sendKeys(newDescription);

        WebElement noteForm = driver.findElement(By.id("note-form"));
        noteForm.submit();

        Assertions.assertTrue(driver.findElement(By.id("success-update-note")).getText().contains("Note updated successfully"));

        WebElement backToHomeLink = driver.findElement(By.id("backtohome"));
        backToHomeLink.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        notesTab.click();

        checkIfNoteDisplayed(newTitle, newDescription);
    }

    @Test
    public void deleteNote()
    {
        doMockSignUp("Beast King", "Kaido", "Hyakuju", "Kaizoku");
        doLogIn("Hyakuju", "Kaizoku");
        createDefaultNote();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note-btn")));

        WebElement editButton = driver.findElement(By.id("note-delete"));
        editButton.click();

        Assertions.assertTrue(driver.findElement(By.id("success-delete-note")).getText().contains("Note deleted successfully"));

        WebElement backToHomeLink = driver.findElement(By.id("backtohome"));
        backToHomeLink.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        notesTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note-btn")));

        Assertions.assertTrue(driver.findElement(By.id("no-notes")).getText().contains("No notes found for this user!"));

    }

    public String getDefaultTitle() {
        return "My Dream";
    }

    public String getDefaultDescription() {
        return "Pirate King";
    }
}
