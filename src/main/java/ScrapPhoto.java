import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class ScrapPhoto{
    private String chromeDriverLocationShaiGivati="";
    private String chromeDriverLocationShilo="C:\\Users\\Shilo\\Desktop\\תכנות\\chromedriver.exe";
    private String chromeDriverLocationNachshon="C:\\Users\\kedar\\IdeaProjects\\chromedriver_win32\\chromedriver.exe";
    //שינוי נתיב בהתאם
    private final String CURRENT_CHROME_DRIVER =chromeDriverLocationShilo;

    private String partOfLocation;
    private String fullLocation;
    private String imageName;

    public void setPartOfLocation(String path){
        partOfLocation =path+"\\";
    }
    public void usePhoto(String username) {

        imageName = username;
        System.setProperty("webdriver.chrome.driver", CURRENT_CHROME_DRIVER);
        ChromeDriver driver = new ChromeDriver();

        final String FACEBOOK_URL = "https://www.facebook.com/";
        try {
        driver.get(FACEBOOK_URL + username);
        WebElement enterFullPhoto;

            enterFullPhoto = driver.findElement(By.xpath
                    ("/html/body/div[1]/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div/div[1]/div[2]/div/div/div/div[1]/div/a"));

        String link = enterFullPhoto.getAttribute("href");
        driver.get(link);

        WebElement photoSource=null;
            do {
                try {
                    photoSource = driver.findElement(By.xpath(
                            "/html/body/div[1]/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div[1]/div/div[1]/div/div[2]/div/div/div/img"
                    ));
                } catch (Exception e) {
                    System.out.println("error scrap photo line 51");
                }

            } while (photoSource == null);
            String photoSourceUrl = photoSource.getAttribute("src");

            URL url = new URL(photoSourceUrl);
            BufferedImage bufferedImage = ImageIO.read(url);
            fullLocation=partOfLocation +imageName+".jpg";
            File photo = new File(fullLocation);

            ImageIO.write(bufferedImage, "jpg", photo);
            fullLocation=photo.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            driver.close();
        }
        driver.close();
    }

    public String getFullLocation() {
        fullLocation=fix();
        return fullLocation;
    }
    private String fix(){
        String newFullLocation="\""+fullLocation+"\"";
        return newFullLocation;}
    }
