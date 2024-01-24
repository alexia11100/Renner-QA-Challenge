import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

// História do Usuário #1:
// Como um cliente cadastrado no https://automationexercise.com/
// Eu quero fazer a compra de ao menos três produtos
// Para que eu possa estar bem vestida
// Os seguintes produtos do e-commerce devem ser adicionados ao carrinho de compras com as seguintes quantidades.
// Stylish Dress  - 3 Itens
// Beautiful Peacock Blue Cotton Linen Saree - 2 itens
// Men Tshirt - 1
public class TestCase1 {
    @Test
    public void testPurchaseMultipleItems() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\alexia.oliveira\\rest assured VemSer\\vs12-qa\\modulo-04-api\\restassured\\QA-Challenge-UI\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Usuário acessa a conta
        driver.get("https://automationexercise.com/login");

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[2]"));
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/button"));

        emailField.sendKeys("teste87@teste.com");
        passwordField.sendKeys("teste123");
        loginButton.click();

        List<WebElement> loggedInElement = driver.findElements(By.xpath("//a[contains(text(), 'Logged in as')]"));
        Assert.assertEquals(1, loggedInElement.size());

        // Usuário acessa a página de produtos
        driver.get("https://automationexercise.com/products");

        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"search_product\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"submit_search\"]"));

        searchField.sendKeys(Keys.CONTROL, "a");
        searchField.sendKeys("Stylish Dress");
        searchButton.click();

        searchField = driver.findElement(By.xpath("//*[@id=\"search_product\"]"));
        searchButton = driver.findElement(By.xpath("//*[@id=\"submit_search\"]"));

        searchField.sendKeys(Keys.CONTROL, "a");
        searchField.sendKeys("Beautiful Peacock Blue Cotton Linen Saree");
        searchButton.click();

        searchField = driver.findElement(By.xpath("//*[@id=\"search_product\"]"));
        searchButton = driver.findElement(By.xpath("//*[@id=\"submit_search\"]"));

        searchField.sendKeys(Keys.CONTROL, "a");
        searchField.sendKeys("Men Tshirt");
        searchButton.click();
    }
}