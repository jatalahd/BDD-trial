package org.jb;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

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
import java.util.Map;
import java.net.URL;


public class JBWebDriverSteps {

    private static WebDriver drv;
    private int elementTimeout = 30;
    private int waitAfterAction = 0;

    public JBWebDriverSteps() {
    }

    @Given("find element timeout is $timeout")
    public void setFindElementTimeout(final String timeout) {
        this.elementTimeout = Integer.parseInt(timeout);
    }

    @Given("wait after action is $wait")
    public void setWaitAfterAction(final String wait) {
        this.waitAfterAction = (int) (Float.parseFloat(wait)*1000.0);
    }

    @When("remote $browser browser is opened at server $remoteUrl")
    public void openBrowser(final String browser, final String remoteUrl) throws Exception {
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
    public void openBrowser(final String browser) throws Exception {
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
    public void navigateToURL(final String url) {
        drv.get(url);
    }

    @Given("all cookies are deleted")
    public void clearBrowserData() {
        drv.manage().deleteAllCookies();
    }

    @Given("browser window size is $w x $h")
    public void setBrowserWindowSize(final String w, final String h) {
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
    public void setBrowserWindowLocation(final String x, final String y) {
        drv.manage().window().setPosition(new Point(Integer.parseInt(x),Integer.parseInt(y)));
    }

    @Then("browser window has title $title")
    public void getPageTitle(final String title) throws Exception {
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
    public String executeJavascript(final String jScript) throws Exception {
        Thread.sleep(this.waitAfterAction);
        return (String)((JavascriptExecutor) drv).executeScript(jScript);
    }

    @When("coordinate $x , $y is clicked")
    public void clickOnCoordinate(final String x, final String y) throws Exception {
            Thread.sleep(this.waitAfterAction);
            Robot robot = new Robot();
            robot.mouseMove(Integer.parseInt(x), Integer.parseInt(y));
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    @When("web element $id with locator $by is clicked")
    public void clickElement(final String id, final String by) throws Exception {
        getE(ExpectedConditions.elementToBeClickable(getBy(by,id))).click();
    }

    @When("text $text is writted to web field $id with locator $by")
    public void writeTextToField(final String text, final String id, final String by) throws Exception {
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
        e.clear();
        e.sendKeys(text);
    }

    @When("item $text is selected from dropdown $id with locator $by")
    public void selectDropdownItem(final String text, final String id, final String by) throws Exception {
        new Select(getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)))).selectByVisibleText(text);
    }

    @When("checkbox $id with locator $by is selected")
    public void selectCheckbox(final String id, final String by) throws Exception {
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
        if ( !e.isSelected() ) { e.click(); }
    }

    @When("checkbox $id with locator $by is unselected")
    public void unselectCheckbox(final String id, final String by) throws Exception {
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
        if ( e.isSelected() ) { e.click(); }
    }


    public String getTextOfElement(final String by, final String id) throws Exception {
        return getE(ExpectedConditions.presenceOfElementLocated(getBy(by,id))).getText().replace("\n"," ");
    }


    public void elementExists(final String by, final String id) throws Exception {
        getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
    }


    public void elementDoesNotExist(final String by, final String id) throws Exception {
        getB(ExpectedConditions.invisibilityOfElementLocated(getBy(by,id)));
    }


    public void elementTextContains(final String by, final String id, final String text) throws Exception {
        getB(textInElement(getBy(by,id), text, true));
    }


    public void elementTextEquals(final String by, final String id, final String text) throws Exception {
        getB(textInElement(getBy(by,id), text, false));
    }


    public void elementTextNotContains(final String by, final String id, final String text) throws Exception {
        getB(ExpectedConditions.not(textInElement(getBy(by,id), text, true)));
    }


    public void elementTextNotEquals(final String by, final String id, final String text) throws Exception {
        getB(ExpectedConditions.not(textInElement(getBy(by,id), text, false)));
    }


    public void clickPopupOk() throws Exception {
        getA(ExpectedConditions.alertIsPresent()).accept();
    }


    public void clickPopupCancel() throws Exception {
        getA(ExpectedConditions.alertIsPresent()).dismiss();
    }


    public void switchToFrame(final String id) throws Exception {
        drv = getD(ExpectedConditions.frameToBeAvailableAndSwitchToIt(id));
    }


    public void switchToDefaultContext() throws Exception {
        drv.switchTo().defaultContent();
    }


    public void switchToWindowWithTitle(final String title) throws Exception {
        drv = getD(switchToBrowserWindow("return window.document.title;", title, false));
    }


    public void switchToWindowWithURL(final String url) throws Exception {
        drv = getD(switchToBrowserWindow("return window.document.URL;", url, false));
    }


    public void switchToNextWindow() throws Exception {
        drv = getD(switchToBrowserWindow("window handle", "next index", true));
    }


    public void dragAndDrop(String by1, String id1, String by2, String id2) throws Exception {
        WebElement e1 = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by1,id1)));
        WebElement e2 = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by2,id2)));
        new Actions(drv).dragAndDrop(e1, e2).perform();
    }


    public void dragAndDropBy(String by, String id, String x, String y) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
        new Actions(drv).dragAndDropBy(e, Integer.parseInt(x), Integer.parseInt(y)).perform();
    }


    public void mouseDownOnElement(final String by, final String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
        new Actions(drv).clickAndHold(e).perform();
    }


    public void mouseUpOnElement(final String by, final String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
        new Actions(drv).release(e).perform();
    }


    public void hoverOnElement(final String by, final String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
        new Actions(drv).moveToElement(e).perform();
    }


    public void doubleClickOnElement(final String by, final String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
        new Actions(drv).doubleClick(e).perform();
    }


    public void rightClickOnElement(final String by, final String id) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
        new Actions(drv).contextClick(e).perform();
    }


    public void clickOffsetOnElement(String by, String id, String x, String y) throws Exception { 
        WebElement e = getE(ExpectedConditions.visibilityOfElementLocated(getBy(by,id)));
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
    private By getBy(final String by, final String id) throws Exception {
        Thread.sleep(this.waitAfterAction);
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
