package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.util.function.BooleanSupplier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NotesTests{

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password){
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depending on the rest of your code.
		*/
        Assertions.assertTrue(driver.findElement(By.id("success-signup")).getText().contains("User sign up successful. Login with registered credentials"));
    }



    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password)
    {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    private void createNote() {
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        noteTab.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note-btn")));

        WebElement newNoteBtn = driver.findElement(By.id("new-note-btn"));
        Assertions.assertNotNull(newNoteBtn);
        newNoteBtn.click();

        WebElement noteTitle = driver.findElement(By.id("note-title"));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitle));
        noteTitle.sendKeys("My Dream");

        WebElement noteDescription = driver.findElement(By.id("note-description"));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteDescription));
        noteDescription.sendKeys("Pirate King");

        WebElement noteForm = driver.findElement(By.id("note-form"));
        noteForm.submit();

        Assertions.assertTrue(driver.findElement(By.id("success-add-note")).getText().contains("Note added successfully"));

        WebElement backToHomeLink = driver.findElement(By.id("backtohome"));
        backToHomeLink.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        notesTab.click();
    }

    @Test
    public void displayEnteredNote()
    {
        doMockSignUp("Monkey D.", "Luffy", "Mugiwara", "Kaizoku");
        doLogIn("Mugiwara", "Kaizoku");
        createNote();
        Assertions.assertNotNull(driver.findElement(By.xpath("//th[text()='My Dream']")));
        Assertions.assertNotNull(driver.findElement(By.xpath("//td[text()='Pirate King']")));
    }

    @Test
    public void editNote()
    {
        doMockSignUp("Marshall", "Teach", "Kurohige", "Kaizoku");
        doLogIn("Kurohige", "Kaizoku");
        createNote();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note-btn")));


        WebElement editButton = driver.findElement(By.id("note-edit"));
        editButton.click();

        WebElement noteTitle = driver.findElement(By.id("note-title"));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitle));
        noteTitle.clear();
        noteTitle.sendKeys("My New Dream");

        WebElement noteDescription = driver.findElement(By.id("note-description"));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteDescription));
        noteDescription.clear();
        noteDescription.sendKeys("Eat all the meat");

        WebElement noteForm = driver.findElement(By.id("note-form"));
        noteForm.submit();

        Assertions.assertTrue(driver.findElement(By.id("success-update-note")).getText().contains("Note updated successfully"));

        WebElement backToHomeLink = driver.findElement(By.id("backtohome"));
        backToHomeLink.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        notesTab.click();

        Assertions.assertNotNull(driver.findElement(By.xpath("//th[text()='My New Dream']")));
        Assertions.assertNotNull(driver.findElement(By.xpath("//td[text()='Eat all the meat']")));
    }

    @Test
    public void deleteNote()
    {
        doMockSignUp("Beast King", "Kaido", "Hyakuju", "Kaizoku");
        doLogIn("Hyakuju", "Kaizoku");
        createNote();

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

        Assertions.assertNotNull(driver.findElement(By.id("no-notes")).getText().contains("No notes found for this user!"));

    }

}
