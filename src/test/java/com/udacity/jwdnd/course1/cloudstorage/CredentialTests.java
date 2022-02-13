package com.udacity.jwdnd.course1.cloudstorage;


import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests extends LoginAndSignupTests {

    private void createDefaultCredential() {
        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialTab.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential-btn")));

        WebElement newCredentialBtn = driver.findElement(By.id("new-credential-btn"));
        Assertions.assertNotNull(newCredentialBtn);
        newCredentialBtn.click();

        WebElement credentialURL = driver.findElement(By.id("credential-url"));
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialURL));
        credentialURL.sendKeys(getDefaultURL());

        WebElement credUserName = driver.findElement(By.id("credential-username"));
        webDriverWait.until(ExpectedConditions.visibilityOf(credUserName));
        credUserName.sendKeys(getDefaultUsername());

        WebElement credPassword = driver.findElement(By.id("credential-password"));
        webDriverWait.until(ExpectedConditions.visibilityOf(credPassword));
        credPassword.sendKeys(getDefaultPassword());

        WebElement credentialForm = driver.findElement(By.id("credential-form"));
        credentialForm.submit();

        Assertions.assertTrue(driver.findElement(By.id("success-add-credential")).getText().contains("Credential added successfully"));

        WebElement backToHomeLink = driver.findElement(By.id("backtohome"));
        backToHomeLink.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialsTab.click();
    }

    private void checkIfCredentialDisplayed(String url, String username, String password)
    {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        WebElement credentialTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credentials = credentialTable.findElements(By.tagName("th"));

        boolean foundURL = false;
        for (WebElement element : credentials) {
            if (element.getAttribute("innerHTML").equals(url)) {
                foundURL = true;
                break;
            }
        }

        Assertions.assertTrue(foundURL);

        credentials.clear();
        credentials = credentialTable.findElements(By.tagName("td"));

        boolean foundUsername = false, foundPassword = false;
        for (WebElement element : credentials) {
            if (!foundUsername && element.getAttribute("innerHTML").equals(username)) {
                foundUsername = true;
            }

            if (!foundPassword && element.getAttribute("innerHTML").equals(password)) {
                foundPassword = true;
            }

            if (foundUsername && foundPassword) {
                break;
            }
        }

        Assertions.assertTrue(foundUsername);

        // Password must be encrypted.
        Assertions.assertFalse(foundPassword);

        // Find unencrypted password
        WebElement viewCredential = driver.findElement(By.id("credential-edit"));
        viewCredential.click();

        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialPassword));

        Assertions.assertEquals(credentialPassword.getAttribute("value"), password);
    }

    @Test
    public void displayEnteredNote()
    {
        doMockSignUp("Monkey D.", "Luffy", "Mugiwara", "Kaizoku");
        doLogIn("Mugiwara", "Kaizoku");
        createDefaultCredential();
        checkIfCredentialDisplayed(getDefaultURL(), getDefaultUsername(), getDefaultPassword());
    }

    @Test
    public void editCredential()
    {
        doMockSignUp("Marshall", "Teach", "Kurohige", "Kaizoku");
        doLogIn("Kurohige", "Kaizoku");
        createDefaultCredential();

        String newURL = "https://worldgovernment.com";
        String newUsername = "FleetAdmiral";
        String newPassword = "AbsoluteJustice@2025";

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential-btn")));


        WebElement editButton = driver.findElement(By.id("credential-edit"));
        editButton.click();

        WebElement credentialURL = driver.findElement(By.id("credential-url"));
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialURL));
        credentialURL.clear();
        credentialURL.sendKeys(newURL);

        WebElement credUserName = driver.findElement(By.id("credential-username"));
        webDriverWait.until(ExpectedConditions.visibilityOf(credUserName));
        credUserName.clear();
        credUserName.sendKeys(newUsername);

        WebElement credPassword = driver.findElement(By.id("credential-password"));
        webDriverWait.until(ExpectedConditions.visibilityOf(credPassword));
        credPassword.clear();
        credPassword.sendKeys(newPassword);

        WebElement credentialForm = driver.findElement(By.id("credential-form"));
        credentialForm.submit();

        Assertions.assertTrue(driver.findElement(By.id("success-update-credential")).getText().contains("Credential updated successfully"));

        WebElement backToHomeLink = driver.findElement(By.id("backtohome"));
        backToHomeLink.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialsTab.click();

        checkIfCredentialDisplayed(newURL, newUsername, newPassword);
    }

    @Test
    public void deleteCredential()
    {
        doMockSignUp("Beast King", "Kaido", "Hyakuju", "Kaizoku");
        doLogIn("Hyakuju", "Kaizoku");
        createDefaultCredential();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential-btn")));

        WebElement deleteButton = driver.findElement(By.id("credential-delete"));
        deleteButton.click();

        Assertions.assertTrue(driver.findElement(By.id("success-delete-credential")).getText().contains("Credential deleted successfully"));

        WebElement backToHomeLink = driver.findElement(By.id("backtohome"));
        backToHomeLink.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialsTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential-btn")));

        Assertions.assertTrue(driver.findElement(By.id("no-credentials")).getText().contains("No credentials found for this user!"));

    }

    public String getDefaultURL() {
        return "https://onepiece.com";
    }

    public String getDefaultUsername() {
        return "PirateKing";
    }

    public String getDefaultPassword() {
        return "TheOnePiece@2025";
    }
}