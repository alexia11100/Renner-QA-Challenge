import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

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

    @Test
    public void testPurchaseMultipleItems() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\alexia.oliveira\\rest assured VemSer\\vs12-qa\\modulo-04-api\\restassured\\QA-Challenge-UI\\chromedriver-win64\\chromedriver.exe");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Usuário acessa a conta
        driver.get("https://automationexercise.com/login");
        login("teste87@teste.com", "teste123");
        driver.findElement(By.xpath("//a[contains(text(), 'Logged in as')]"));

        // Usuário acessa a página de produtos
        driver.get("https://automationexercise.com/products");

        searchProduct("Stylish Dress");
        WebElement viewProductButton = driver.findElement(By.xpath("/html/body/section[2]/div[1]/div/div[2]/div/div[2]/div/div[2]/ul/li/a"));
        viewProductButton.click();
        closeAd();

        WebElement productTitle = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/h2"));
        Assert.assertEquals("Stylish Dress", productTitle.getText());

        WebElement productAmount = driver.findElement(By.xpath("//*[@id=\"quantity\"]"));
        productAmount.sendKeys(Keys.CONTROL, "a");
        productAmount.sendKeys("3");
        Assert.assertEquals("3", productAmount.getAttribute("value"));


        //searchProduct("Beautiful Peacock Blue Cotton Linen Saree");
        //searchProduct("Men Tshirt");

    }

    public void searchProduct(String name){
        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"search_product\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"submit_search\"]"));

        searchField.sendKeys(Keys.CONTROL, "a");
        searchField.sendKeys(name);
        searchButton.click();
    }

    public void login(String email, String password){
        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[2]"));
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/input[3]"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/form/button"));

        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public void closeAd(){
        WebElement frameAd = driver.findElement(By.xpath("/html/ins/div/iframe"));
        driver.switchTo().frame(frameAd);
        WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"dismiss-button\"]"));
        closeButton.click();
        driver.switchTo().defaultContent();
    }
}
