package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by deeptik on 11/03/2017.
 */
public class StepDefinitions {

    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver,20);

    //Set the working Directory
    String workingDir = System.getProperty("user.dir");

    @Before
    public void goToMooUkShop() {
        driver.get(TestConstants.MOO_UK_SHOP);
        closeTheIFrame();
        driver.manage().window().maximize();
    }

    @After
    public void closeTheWindow() {
        driver.close();
    }

    @When("^I search for an Item called \"([^\"]*)\"$")
    public void searchItem(String searchItemName) {
        findByTextBoxIdAndTypeKeys(TestConstants.SEARCH_TEXTBOX_ID, searchItemName);
        findByXpathAndClick(TestConstants.SEARCH_ICON_XPATH);
    }

    @Then("^the error message should consist of \"([^\"]*)\"$")
    public void checkErrorMessage(String expectedMessage) {
        String actualMessage = getTextByCssSelector(TestConstants.ERROR_MESSAGE_ELEMENT_CSS_SELECTOR);
        assert(actualMessage.contains(expectedMessage));
    }

    @Given("^we go to Envelopes$")
    public void goToEnvelopes() throws Throwable {
        findByXpathAndClick(TestConstants.PRODUCTS_LINK);
        findByXpathAndClick(TestConstants.ENVELOPES_LINK);
    }

    @And("^add the first item to the cart$")
    public void addItemToTheCart() throws Throwable {
        findByCssAndClick(TestConstants.ADD_BUTTON_CSS_SELECTOR);
    }

    @Then("^the customer should be directed to the Cart$")
    public void theCustomerShouldBeDirectedToTheCart() throws Throwable {
        assertEquals(driver.getCurrentUrl(), TestConstants.MOO_UK_CART);
    }

    @And("^the cart should have (\\d+) item$")
    public void verifyCartItemCount(int item)  {
        driver.findElement(By.cssSelector(TestConstants.CART_ITEM_COUNT_DROP_DOWN_CSS)).getText().equals(String.valueOf(item));
    }

    @And("^we go to Round Stickers$")
    public void goToRoundStickers()  {
        findByXpathAndClick(TestConstants.PRODUCTS_LINK);
        findByXpathAndClick(TestConstants.STICKERS_LINK);
        findByXpathAndClick(TestConstants.ROUND_STICKERS_LINK);
    }

    @When("^we upload a design$")
    public void uploadDesign() throws Throwable {
        findByXpathAndClick(TestConstants.START_MAKING_BUTTON);
        findByXpathAndClick(TestConstants.UPLOAD_FULL_DESIGN);
        findByXpathAndClick(TestConstants.UPLOAD_COMPLETE_DESIGN);
        findByXpathAndClick(TestConstants.UPLOAD_YOUR_DESIGN);
        String vanLogoFile = workingDir + TestConstants.DESIGN_FILE_LOCATION;
        uploadStickerLogoFile(vanLogoFile);
    }

    @And("^the alert message \"([^\"]*)\" should be displayed on successful file upload$")
    public void theMessageShouldBePresent(String expectedMessage) {
        String actualMessage = getTextByXpath(TestConstants.AFTER_UPLOAD_MESSAGE_HOLDER);
        assert(actualMessage.contains(expectedMessage));
    }

    @Then("^the search result should return more than one occurance of \"([^\"]*)\"$")
    public void checkSearchResultOccurances(String key) throws Throwable {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestConstants.SEARCH_RESULT_VIEW)));
        int size = driver.getPageSource().split(key).length-1;
        assertTrue(size >1);
    }

    private void findByXpathAndClick(String xpathAddress){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAddress)));
        driver.findElement(By.xpath(xpathAddress)).click();
    }

    private void findByCssAndClick(String cssPath){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssPath)));
        driver.findElement(By.cssSelector(cssPath)).click();
    }

    private void findByTextBoxIdAndTypeKeys(String textBoxId, String searchKeyword){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(textBoxId)));
        driver.findElement(By.id(textBoxId)).sendKeys(searchKeyword);
    }

    private String getTextByCssSelector(String cssSelector) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
        return(driver.findElement(By.cssSelector(cssSelector)).getText());
    }

    private String getTextByXpath(String xpathAddress) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAddress)));
        return(driver.findElement(By.xpath(xpathAddress)).getText());
    }

    private void closeTheIFrame() {
        findByXpathAndClick(TestConstants.CLOSE_IFRAME);
    }

    private void uploadStickerLogoFile(String filepath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestConstants.UPLOAD_FILE_MODAL_VIEW)));
        WebElement fileIn = driver.findElement(By.xpath(TestConstants.UPLOAD_FILE_BUTTON));
        fileIn.sendKeys(filepath);
    }
}
