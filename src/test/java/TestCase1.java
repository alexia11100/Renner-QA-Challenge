import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// História do Usuário #1:
// Como um cliente cadastrado no https://automationexercise.com/
// Eu quero fazer a compra de ao menos três produtos
// Para que eu possa estar bem vestida
// Os seguintes produtos do e-commerce devem ser adicionados ao carrinho de compras com as seguintes quantidades.
// Stylish Dress  - 3 Itens
// Beautiful Peacock Blue Cotton Linen Saree - 2 itens
// Men Tshirt - 1
public class TestCase1 {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Test
    public void testPurchaseMultipleItems() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\alexia.oliveira\\rest assured VemSer\\vs12-qa\\modulo-04-api\\restassured\\QA-Challenge-UI\\chromedriver-win64\\chromedriver.exe");
        driver.manage().window().maximize();

        login("teste87@teste.com", "teste123");
        addProduct("Stylish Dress", "3");
        addProduct("Beautiful Peacock Blue Cotton Linen Saree", "2");
        addProduct("Men Tshirt", "1");
        // validateCartProducts()
        // buyCartProducts()
    }

    public void addProduct(String name, String quantity){
        // Go to products page
        driver.get("https://automationexercise.com/products");

        // Get the product by the name
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"search_product\"]")));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"submit_search\"]")));

        searchField.sendKeys(Keys.CONTROL, "a");
        searchField.sendKeys(name);
        searchButton.click();

        WebElement viewProductButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section[2]/div[1]/div/div[2]/div/div[2]/div/div[2]/ul/li/a")));
        viewProductButton.click();
        closeAd();

        // Assert product title, set quantity and add to cart
        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/h2")));
        Assert.assertEquals(name, productTitle.getText());

        WebElement productQuantity = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"quantity\"]")));
        productQuantity.sendKeys(Keys.CONTROL, "a");
        productQuantity.sendKeys(quantity);
        Assert.assertEquals(quantity, productQuantity.getAttribute("value"));

        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/span/button")));
        addToCartButton.click();
        WebElement addedText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space() = 'Added!']")));
        Assert.assertEquals("Added!", addedText.getText());
    }

    public void login(String email, String password){
        driver.get("https://automationexercise.com/login");

        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[2]")));
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/button")));

        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Logged in as')]")));
    }

    public void closeAd(){
        try {
            WebElement frameAd = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/ins/div/iframe")));
            driver.switchTo().frame(frameAd);
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"dismiss-button\"]")));
            closeButton.click();
        } catch (TimeoutException ignored) {
        }
        finally {
            driver.switchTo().defaultContent();
        }
    }
}
