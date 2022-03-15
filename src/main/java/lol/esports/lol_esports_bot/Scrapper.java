package lol.esports.lol_esports_bot;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public class Scrapper {

    private WebDriver driver;
    private FirefoxBinary binary;
    private FirefoxOptions options;

    private String baseUrl = "https://lolesports.com/standings/";
    private String finalUrl = "";
    private String leagueName = "";

    public Scrapper(){

        System.setProperty("webdriver.gecko.driver","assets/geckodriver/0.30.0/geckodriver");
        this.binary = new FirefoxBinary();
        this.binary.addCommandLineOptions("--headless");

        this.options = new FirefoxOptions();
        this.options.setBinary(this.binary);

        this.driver = new FirefoxDriver(this.options);
        this.driver.manage().window().maximize();

    }

    private WebDriver getWebDriver(){
        return this.driver;
    }

    public void getLeagueTable(String split, String season, String league){
        switch (league){
            case "cblol":
                Date d=new Date();
                Integer year=d.getYear()+1900;
                this.leagueName = league + year.toString();

                if(season.toLowerCase(Locale.ROOT).replaceAll(" ", "") == "temporadaregular") {
                    if (split.toLowerCase(Locale.ROOT).replaceAll(" ", "") == "split1") {
                        this.finalUrl = this.baseUrl
                                + league.toLowerCase(Locale.ROOT)
                                + "-brazil/"
                                + league.toLowerCase(Locale.ROOT)
                                + "_" + year
                                + "_split1/"
                                + "regular_season";
                        try {
                            this.takeScreenShot(this.finalUrl, "group");
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    } else if (split.toLowerCase(Locale.ROOT).replaceAll(" ", "") == "split2") {
                        this.finalUrl = this.baseUrl
                                + league.toLowerCase(Locale.ROOT)
                                + "-brazil/"
                                + league.toLowerCase(Locale.ROOT)
                                + "_" + year
                                + "_split2/"
                                + "regular_season";
                        try {
                            this.takeScreenShot(this.finalUrl, "group");
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    }
                } else if(season.toLowerCase(Locale.ROOT) == "playoffs"){
                    if (split.toLowerCase(Locale.ROOT).replaceAll(" ", "") == "split1") {
                        this.finalUrl = this.baseUrl
                                + league.toLowerCase(Locale.ROOT)
                                + "-brazil/"
                                + league.toLowerCase(Locale.ROOT)
                                + "_" + year
                                + "_split1/"
                                + "playoffs";
                        try {
                            this.takeScreenShot(this.finalUrl,"stage");
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    } else if (split.toLowerCase(Locale.ROOT).replaceAll(" ", "") == "split2") {
                        this.finalUrl = this.baseUrl
                                + league.toLowerCase(Locale.ROOT)
                                + "-brazil/"
                                + league.toLowerCase(Locale.ROOT)
                                + "_" + year
                                + "_split2/"
                                + "payoffs";
                        try {
                            this.takeScreenShot(this.finalUrl, "stage");
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    }
                }

            case "tcl":

            case "ljl":

            case "lla":

            case "lpl":
            case "lec":
            case "lck":
            case "lcl":
            case "lco":
            case "lcs":
        }
    }

    private void takeScreenShot (String url, String type) throws IOException {

        WebDriver InternalDriver = getWebDriver();
        InternalDriver.get(url);

        WebElement topBar = InternalDriver.findElement(By.cssSelector("div.TopMenu"));

        ((JavascriptExecutor)InternalDriver).executeScript("arguments[0].style.visibility='hidden'", topBar);

        WebElement button = InternalDriver.findElement(By.cssSelector("button.osano-cm-accept"));
        button.click();

        WebDriverWait wait = new WebDriverWait(InternalDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.osano-cm-window__dialog")));


        if(type == "group") {
            WebElement logo = InternalDriver.findElement(By.cssSelector("div.group"));

            File f = logo.getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(f, new File("assets/leagueTables/"+this.leagueName+"_regular.png"));

            InternalDriver.close();
            this.driver.close();

        } else if (type == "stage"){

            WebElement logo = InternalDriver.findElement(By.cssSelector("div.StandingsBracket"));

            File f = logo.getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(f, new File("assets/leagueTables/"+this.leagueName+"_stage.png"));

            InternalDriver.close();
            this.driver.close();
        }

    }

 
}
