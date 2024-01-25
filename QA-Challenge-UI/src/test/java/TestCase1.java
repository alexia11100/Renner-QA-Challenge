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
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @Test
    public void testPurchaseMultipleItems() {
        // Set the chromedriver path here
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\alexia.oliveira\\chromedriver-win64\\chromedriver.exe");
        driver.manage().window().maximize();

        login("teste87@teste.com", "teste123");
        addProduct("Stylish Dress", "3");
        addProduct("Beautiful Peacock Blue Cotton Linen Saree", "2");
        addProduct("Men Tshirt", "1");
        validateCartProduct("Stylish Dress", "3");
        validateCartProduct("Beautiful Peacock Blue Cotton Linen Saree", "2");
        validateCartProduct("Men Tshirt", "1");
        buyCartProducts();

        driver.quit();
    }

    public void login(String email, String password){
        // Access the login page and assert its url
        driver.get("https://automationexercise.com/login");
        Assert.assertEquals("https://automationexercise.com/login", driver.getCurrentUrl());

        // Declare email field WebElement, fill it out and assert its value
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[2]")));
        emailField.sendKeys(email);
        Assert.assertEquals(email, emailField.getAttribute("value"));

        // Declare password field WebElement, fill it out and assert its value
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]")));
        passwordField.sendKeys(password);
        Assert.assertEquals(password, passwordField.getAttribute("value"));

        // Click on login button and wait it shows the element with the text 'Logged in as'
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/button")));
        loginButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Logged in as')]")));
    }

    public void addProduct(String name, String quantity){
        // Access the products url and assert our current url is the products url
        driver.get("https://automationexercise.com/products");
        Assert.assertEquals("https://automationexercise.com/products", driver.getCurrentUrl());

        // Declare search field WebElement, clean and fill it out and assert its value
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"search_product\"]")));
        searchField.sendKeys(Keys.CONTROL, "a");
        searchField.sendKeys(name);
        Assert.assertEquals(name, searchField.getAttribute("value"));

        // Declare the search button WebElement and click on it
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"submit_search\"]")));
        searchButton.click();

        // Declare the view product button WebElement, click on it and close ad if it shows on the screen
        WebElement viewProductButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section[2]/div[1]/div/div[2]/div/div[2]/div/div[2]/ul/li/a")));
        viewProductButton.click();
        closeAd();

        // Declare the product title WebElement and assert its value
        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/h2")));
        Assert.assertEquals(name, productTitle.getText());

        // Declare the product quantity, clean out, set its value to quantity and assert its value
        WebElement productQuantity = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"quantity\"]")));
        productQuantity.sendKeys(Keys.CONTROL, "a");
        productQuantity.sendKeys(quantity);
        Assert.assertEquals(quantity, productQuantity.getAttribute("value"));

        // Declare the add to cart WebElement and click on it
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/span/button")));
        addToCartButton.click();

        // Declare the added text WebElement and assert it's showing on the screen the text 'Added!'
        WebElement addedText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space() = 'Added!']")));
        Assert.assertEquals("Added!", addedText.getText());
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

    public void validateCartProduct(String productName, String quantity){
        // Access the cart url and assert our current url is the cart url
        driver.get("https://automationexercise.com/view_cart");
        Assert.assertEquals("https://automationexercise.com/view_cart", driver.getCurrentUrl());

        // Declare product row xpath and its name and quantity columns
        String productRowXpath = String.format("//h4[normalize-space() = '%s']/../..", productName);
        WebElement nameColumn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(productRowXpath + "//td/h4")));
        WebElement quantityColumn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(productRowXpath + "//td/button")));

        // Assert product and quantity values
        Assert.assertEquals(productName, nameColumn.getText());
        Assert.assertEquals(quantity, quantityColumn.getText());
    }

    public void buyCartProducts() {
        // Access the cart url and assert our current url is the cart url
        driver.get("https://automationexercise.com/view_cart");
        Assert.assertEquals("https://automationexercise.com/view_cart", driver.getCurrentUrl());

        // Declare the checkout button WebElement, click on it and assert url is checkout url
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"do_action\"]/div[1]/div/div/a")));
        checkoutButton.click();
        Assert.assertEquals("https://automationexercise.com/checkout", driver.getCurrentUrl());

        // Declare the place order button WebElement, click on it (close ad if it shows) and assert url is payment url
        WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart_items\"]/div/div[7]/a")));
        placeOrderButton.click();
        closeAd();
        Assert.assertEquals("https://automationexercise.com/payment", driver.getCurrentUrl());

        // Declare name on card field, fill it out and assert its value
        WebElement nameOnCardField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"payment-form\"]/div[1]/div/input")));
        nameOnCardField.sendKeys("teste");
        Assert.assertEquals("teste", nameOnCardField.getAttribute("value"));

        // Declare card number field, fill it out and assert its value
        WebElement cardNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"payment-form\"]/div[2]/div/input")));
        cardNumberField.sendKeys("0123456789");
        Assert.assertEquals("0123456789", cardNumberField.getAttribute("value"));

        // Declare cvc field, fill it out and assert its value
        WebElement cvcField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"payment-form\"]/div[3]/div[1]/input")));
        cvcField.sendKeys("123");
        Assert.assertEquals("123", cvcField.getAttribute("value"));

        // Declare month expiration field, fill it out and assert its value
        WebElement monthExpirationField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"payment-form\"]/div[3]/div[2]/input")));
        monthExpirationField.sendKeys("03");
        Assert.assertEquals("03", monthExpirationField.getAttribute("value"));

        // Declare year expiration field, fill it out and assert its value
        WebElement yearExpirationField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"payment-form\"]/div[3]/div[3]/input")));
        yearExpirationField.sendKeys("2049");
        Assert.assertEquals("2049", yearExpirationField.getAttribute("value"));

        // Declare confirm button and click on it
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"submit\"]")));
        confirmButton.click();

        // Declare success message label and assert its text value is correct
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/div/div/p")));
        Assert.assertEquals("Congratulations! Your order has been confirmed!", successMessage.getText());
    }
}
