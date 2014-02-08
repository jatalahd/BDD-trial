package org.jb;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.ScenarioType;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.apache.commons.io.FileUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.Date;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.net.URL;
import java.util.Map;
import java.util.Arrays;
import java.util.List;


public class JBWebDriverSteps {

    private static WebDriver drv;
    private int elementTimeout = 30;
    private int waitAfterAction = 0;
    private List<String> locators = Arrays.asList("id", "name", "xpath", "className", "linkText",
                                                  "partialLinkText", "tagName", "cssSelector");

    public JBWebDriverSteps() {
    }

    @BeforeScenario
    public void beforeEachScenario(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))
            System.out.println("Before Web Scenario ...");
    }
 
    @BeforeScenario(uponType=ScenarioType.EXAMPLE)
    public void beforeEachExampleScenario(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))
            System.out.println("Before Web Each Example Scenario ...");
    }
     
    @AfterScenario // equivalent to  @AfterScenario(uponOutcome=AfterScenario.Outcome.ANY)
    public void afterAnyScenario(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))
            System.out.println("After Web Any Scenario ...");
    }
 
    @AfterScenario(uponType=ScenarioType.EXAMPLE)
    public void afterEachExampleScenario(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))
            System.out.println("After Web Each Example Scenario ...");
    }
     
    @AfterScenario(uponOutcome=AfterScenario.Outcome.SUCCESS)
    public void afterSuccessfulScenario(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))
            System.out.println("After Web Successful Scenario ...");
    }
     
    @AfterScenario(uponOutcome=AfterScenario.Outcome.FAILURE)
    public void afterFailedScenario(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))
            System.out.println("After Web Failed Scenario ...");
    }

    @BeforeStory // equivalent to @BeforeStory(uponGivenStory=false)
    public void beforeStory(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))        
            System.out.println("Before Web Story ...");
    }
 
    @BeforeStory(uponGivenStory=true)
    public void beforeGivenStory(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))
            System.out.println("Before Web Given Story ...");
    }
     
    @AfterStory // equivalent to @AfterStory(uponGivenStory=false)
    public void afterStory(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))
            System.out.println("After Web Story ...");
    }
 
    @AfterStory(uponGivenStory=true)
    public void afterGivenStory(@Named("steps") String steps) {
        if (steps.contains("WebSteps"))
            System.out.println("After Web Given Story ...");
    }

    @Given("find element timeout is $timeout")
    public void setFindElementTimeout(String timeout) {
        this.elementTimeout = Integer.parseInt(timeout);
    }

    @Given("wait after action is $wait")
    public void setWaitAfterAction(String wait) {
        this.waitAfterAction = (int) (Float.parseFloat(wait)*1000.0);
    }

    @When("remote $browser browser is opened at server $remoteUrl")
    public void openBrowser(String browser, String remoteUrl) throws Exception {
        DesiredCapabilities cap = null;
        if (browser.equals("ie")) {
            cap = DesiredCapabilities.internetExplorer();
        } else if (browser.equals("chrome")) {
            cap = DesiredCapabilities.chrome();
        } else {
            FirefoxProfile prfl = new FirefoxProfile();
            prfl.setEnableNativeEvents(true);
            cap = DesiredCapabilities.firefox();
            cap.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.BOTTOM);
            cap.setCapability(FirefoxDriver.PROFILE, prfl);
        }
        drv = new RemoteWebDriver(new URL(remoteUrl), cap);
        drv.get("about:blank");
    }

    @When("local $browser browser is opened")
    public void openBrowser(String browser) throws Exception {
        if (browser.equals("ie")) {
            drv = new InternetExplorerDriver();
        } else if (browser.equals("chrome")) {
            drv = new ChromeDriver();
        } else {
            FirefoxProfile prfl = new FirefoxProfile();
            prfl.setEnableNativeEvents(true);
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.BOTTOM);
            cap.setCapability(FirefoxDriver.PROFILE, prfl);
            drv = new FirefoxDriver(cap);
        }
        drv.get("about:blank");
    }

    @When("all browsers are closed")
    public void closeBrowser() {
        drv.quit();
    }

    @When("user navigates to $url")
    public void navigateToURL(String url) {
        drv.get(url);
    }

    @Given("all cookies are deleted")
    public void clearBrowserData() {
        drv.manage().deleteAllCookies();
    }

    @Given("browser window size is $w x $h")
    public void setBrowserWindowSize(String w, String h) {
        drv.manage().window().setSize(new Dimension(Integer.parseInt(w), Integer.parseInt(h)));
    }

    @When("browser window location is queried")
    public String getBrowserWindowLocation() {
        return drv.manage().window().getPosition().toString();
    }

    @Given("browser capabilities are printed to log file")
    public void PrintCapabilitiesToLog() {
        Map<String,?> maps = ((RemoteWebDriver) drv).getCapabilities().asMap();
        for (String capa : maps.keySet()) {
            String value = maps.get(capa).toString();  
            System.out.println(capa + " : " + value);  
        }
    }

    @Given("browser window is moved to location $x , $y")
    public void setBrowserWindowLocation(String x, String y) {
        drv.manage().window().setPosition(new Point(Integer.parseInt(x),Integer.parseInt(y)));
    }

    @Then("browser window has title $title")
    public void getPageTitle(String title) throws Exception {
        Thread.sleep(this.waitAfterAction);
        drv.getTitle();
    }

    @When("page screenshot is saved to a file")
    public void getPageScreenshot() throws Exception {
        File scrFile = ((TakesScreenshot)drv).getScreenshotAs(OutputType.FILE);
        String fileName = new SimpleDateFormat("MMddHHmmss'.png'").format(new Date());
        FileUtils.copyFile(scrFile, new File("./scrshots/"+fileName));
        System.out.println("*HTML* <img src='./scrshots/"+fileName+"'></img>");
    }

    @When("javascript $jScript is executed")
    public String executeJavascript(String jScript) throws Exception {
        Thread.sleep(this.waitAfterAction);
        return (String)((JavascriptExecutor) drv).executeScript(jScript);
    }

    @When("coordinate $x , $y is clicked")
    public void clickOnCoordinate(String x, String y) throws Exception {
            Thread.sleep(this.waitAfterAction);
            Robot robot = new Robot();
            robot.mouseMove(Integer.parseInt(x), Integer.parseInt(y));
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    @When("web element $id is clicked")
    public void clickElement(String id) throws Exception {
        getE(ExpectedConditions.elementToBeClickable(getBy(id))).click();
    }

    @When("text $text is written to field $id")
    public void writeTextToField(String text, String id) throws Exception {
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        e.clear();
        e.sendKeys(text);
    }

    @When("item $text is selected from dropdown $id")
    public void selectDropdownItem(String text, String id) throws Exception {
        new Select(getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)))).selectByVisibleText(text);
    }

    @When("checkbox $id is selected")
    public void selectCheckbox(String id) throws Exception {
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        if ( !e.isSelected() ) { e.click(); }
    }

    @When("checkbox $id is unselected")
    public void unselectCheckbox(String id) throws Exception {
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        if ( e.isSelected() ) { e.click(); }
    }


    public String getTextOfElement(String id) throws Exception {
        return getE(ExpectedConditions.presenceOfElementLocated(getBy(id))).getText().replace("\n"," ");
    }


    public void elementExists(String id) throws Exception {
        getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
    }


    public void elementDoesNotExist(String id) throws Exception {
        getB(ExpectedConditions.invisibilityOfElementLocated(getBy(id)));
    }


    public void elementTextContains(String id, String text) throws Exception {
        getB(textInElement(getBy(id), text, true));
    }


    public void elementTextEquals(String id, String text) throws Exception {
        getB(textInElement(getBy(id), text, false));
    }


    public void elementTextNotContains(String id, String text) throws Exception {
        getB(ExpectedConditions.not(textInElement(getBy(id), text, true)));
    }


    public void elementTextNotEquals(String id, String text) throws Exception {
        getB(ExpectedConditions.not(textInElement(getBy(id), text, false)));
    }


    public void clickPopupOk() throws Exception {
        getA(ExpectedConditions.alertIsPresent()).accept();
    }


    public void clickPopupCancel() throws Exception {
        getA(ExpectedConditions.alertIsPresent()).dismiss();
    }


    public void switchToFrame(String id) throws Exception {
        drv = getD(ExpectedConditions.frameToBeAvailableAndSwitchToIt(id));
    }


    public void switchToDefaultContext() throws Exception {
        drv.switchTo().defaultContent();
    }


    public void switchToWindowWithTitle(String title) throws Exception {
        drv = getD(switchToBrowserWindow("return window.document.title;", title, false));
    }


    public void switchToWindowWithURL(String url) throws Exception {
        drv = getD(switchToBrowserWindow("return window.document.URL;", url, false));
    }


    public void switchToNextWindow() throws Exception {
        drv = getD(switchToBrowserWindow("window handle", "next index", true));
    }


    public void dragAndDrop(String id1, String id2) throws Exception {
        WebElement e1 = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id1)));
        WebElement e2 = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id2)));
        new Actions(drv).dragAndDrop(e1, e2).perform();
    }


    public void dragAndDropBy(String id, String x, String y) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        new Actions(drv).dragAndDropBy(e, Integer.parseInt(x), Integer.parseInt(y)).perform();
    }


    public void mouseDownOnElement(String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        new Actions(drv).clickAndHold(e).perform();
    }


    public void mouseUpOnElement(String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        new Actions(drv).release(e).perform();
    }


    public void hoverOnElement(String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        new Actions(drv).moveToElement(e).perform();
    }


    public void doubleClickOnElement(String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        new Actions(drv).doubleClick(e).perform();
    }


    public void rightClickOnElement(String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        new Actions(drv).contextClick(e).perform();
    }


    public void clickOffsetOnElement(String id, String x, String y) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(id)));
        new Actions(drv).moveToElement(e).moveByOffset(Integer.parseInt(x),Integer.parseInt(y)).click().perform();
    }


    // *** PRIVATE SUPPORT METHODS ***

    // return Alert object after dynamic wait
    private Alert getA(ExpectedCondition<Alert> condition) throws Exception {
        return new WebDriverWait(drv, this.elementTimeout).until(condition);
    }

    // return Boolean value after dynamic wait
    private Boolean getB(ExpectedCondition<Boolean> condition) throws Exception {
        return new WebDriverWait(drv, this.elementTimeout).until(condition);
    }
    
    // return WebDriver object after dynamic wait
    private WebDriver getD(ExpectedCondition<WebDriver> condition) throws Exception {
        return new WebDriverWait(drv, this.elementTimeout).until(condition);
    }

    // return WebElement object after dynamic wait
    private WebElement getE(ExpectedCondition<WebElement> condition) throws Exception {
        return new WebDriverWait(drv, this.elementTimeout).until(condition);
    }

    // return By object using reflection to the static By class
    // id, name, xpath, className, linkText, partialLinkText, tagName, cssSelector
    private By getBy(String id) throws Exception {
        Thread.sleep(this.waitAfterAction);
        String by = "id";
        int x = id.indexOf(':');
        if ( x > 0 && locators.contains(id.substring(0,x)) ) {
            by = id.substring(0,x);
            id = id.substring(x+1);
        }
        return (By) By.class.getMethod(by, String.class).invoke(null,id);
    }

    private static ExpectedCondition<Boolean> textInElement(final By locator, final String text, final Boolean contains) {
        return new ExpectedCondition<Boolean>() {
            String elementText;
            @Override
            public Boolean apply(WebDriver d) {
                try {
                    elementText = d.findElement(locator).getText().replace("\n", " ");
                    if (contains) return elementText.contains(text);
                    else          return elementText.equals(text);
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be present in element found by %s, which had text ('%s')", text, locator, elementText);
            }
        };
    }

    private static ExpectedCondition<WebDriver> switchToBrowserWindow(final String jScript, final String text, final Boolean next) {
        return new ExpectedCondition<WebDriver>() {
            @Override
            public WebDriver apply(WebDriver d) {
                    String wText = "";
                    String origHandle = d.getWindowHandle();
                    for (String handle : d.getWindowHandles()) {
                        if (next && !handle.equals(origHandle)) {
                            return d.switchTo().window(handle);
                        }
                        if (!next) {
                            d.switchTo().window(handle);
                            wText = (String)((JavascriptExecutor) d).executeScript(jScript);
                            if (wText.contains(text)) {
                                return d.switchTo().window(handle);
                            }
                        }
                    }
                    d.switchTo().window(origHandle);
                    return null;
            }

            @Override
            public String toString() {
                return String.format("window containing ('%s') to be present found by %s", text, jScript);
            }
        };
    }

} // End Of JBWebDriverSteps
