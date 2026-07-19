package br.com.dani.portfolio_web_selenium_amazon;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class FluxoPesquisaAmazon {
    public static void main(String[] args) throws IOException {
        ChromeOptions options = new ChromeOptions();
        if ((args.length > 0) && "automatizado".equalsIgnoreCase(args[0])) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com.br");

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(90));

        WebElement campoPesquisa = espera.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        campoPesquisa.sendKeys("presente");
        WebElement botaoPesquisa = driver.findElement(By.id("nav-search-submit-button"));
        botaoPesquisa.click();
        espera.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(), 'Resultados')]")));

        TakesScreenshot camera = (TakesScreenshot) driver;
        File fotoTemporaria = camera.getScreenshotAs(OutputType.FILE);
        File pastaDestino = new File("screenshots");
        if (!pastaDestino.exists()) {
            pastaDestino.mkdir();
        }
        File arquivoFinal = new File(pastaDestino, "resultado.png");
        Files.copy(fotoTemporaria.toPath(), arquivoFinal.toPath(), StandardCopyOption.REPLACE_EXISTING);

         driver.quit();
    }
}
