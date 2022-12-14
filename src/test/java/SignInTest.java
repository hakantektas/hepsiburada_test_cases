import com.beust.jcommander.internal.Console;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import javafx.scene.layout.Priority;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.aspectj.weaver.ast.And;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class SignInTest {

///CASE -1
    /*
    Senaryo 1
• Uygulama açılır
• Anasayfada Konum alanına tıklanır.
• İl, ilçe ve mahalle seçilir ve kaydet butonuna Kklanır.
• Konumunuz Kaydedildi popup’ı kontrol edilir.
• Tab bar üzerinden kategoriler tabına tıklanır.
• Herhangi bir kategori seçilip listeleme sayfasına yönlenilir.
• Listeleme sayfasında Yarın Kapında alanında gelen il bilgisi ile anasayfada seçilen il bilgisinin
aynı olduğu kontrol edilir.
     */

    private AndroidDriver<MobileElement> driver;
    String folder_name;
    DateFormat df;
    public WebDriverWait wait;

    private final By pop_Up_Close       =     By.id("com.pozitron.hepsiburada:id/imageViewClose");
    private final By popUP              =     By.id("com.pozitron.hepsiburada:id/headerView");
    private final By location_View      =     By.id("com.pozitron.hepsiburada:id/locationView");
    private final By city_Dropdown      =     By.id("com.pozitron.hepsiburada:id/citySelectorView");
    private final By city_Select        =     By.xpath("//*[contains(@text , 'Adana')]");
    private final By town_Dropdown      =     By.id("com.pozitron.hepsiburada:id/townSelectorView");

    private final By town_Select        =     By.xpath("//*[contains(@text , 'Çukurova')]");
    private final By district_Dropdown  =     By.id("com.pozitron.hepsiburada:id/districtSelectorView");
    private final By district_Select    =     By.xpath("//*[contains(@text , 'Dörtler')]");
    private final By save_Btn           =     By.id("com.pozitron.hepsiburada:id/buttonSave");
    private final By save_konum_title   =     By.id("com.pozitron.hepsiburada:id/textViewLocation");
    private final By category           =     By.xpath("//*[contains(@text , 'Kategoriler')]");
    private final By first_category     =     By.xpath("(//*[(@class='androidx.cardview.widget.CardView' and contains(@resource-id,'com.pozitron.hepsiburada:id/cardViewRecoComponent'))])[1]");
    private final By text_Location      =     By.id("com.pozitron.hepsiburada:id/textViewLocation");

 ///CASE -2/////
    /*


Senaryo 2
• Uygulama açılır.
• Anasayfada Süper Fiyat, Süper Teklif’e tıklanır.
• Süper Fiyat, Süper Teklif sayfasından bir ürüne gidilir.(Birden fazla görseli olan ürün
seçilir.)
• Ürün detayda ürünün görseline tıklanır ve yana doğru scroll edilir.
• Görsel ekranı kapatılır.
• Ürün detaydan favori butonuna basılır.
• Açılan login sayfasından login olunur ve login olunduğu kontrol edilir.
• Beğendiklerim sayfasına gidilip ürünün beğendiklerime eklendi görülür.


 */
    private final By header             =     By.id("com.pozitron.hepsiburada:id/image_message_of_day");
    private final By super_Fiyat        =     By.xpath("//*[contains(@text , 'Tümü')]");
    private final By ilk_urun           =     By.id("com.pozitron.hepsiburada:id/vp_product_list_item_image");//scrollable= true

    private final By urun_detay_gorsel  =     By.xpath("//android.widget.ImageView[@content-desc='ürün fotoğrafı']");//burdan sonra swipe edilir
    private final By urun_text          =     By.xpath("//*[(@class='android.widget.TextView' and contains(@resource-id,'com.pozitron.hepsiburada:id/productName'))]"); // text bastırılır

    private final By fav_ekle           =     By.id("com.pozitron.hepsiburada:id/favourite");
    private final By email_txt          =     By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[3]/android.view.View/android.view.View/android.widget.EditText");
    private final By sifre_txt          =     By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[3]/android.view.View/android.widget.EditText");
    private final By login_btn          =     By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[3]/android.widget.Button");
    private final By login_btn_2        =     By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ScrollView/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[3]/android.widget.Button");
    private final By hesabim_title      =     By.id("com.pozitron.hepsiburada:id/tvToolbarTitle");
    private final By login_popup_text   =     By.id("android:id/message");
    private final By login_popup_tamam  =     By.id("android:id/button1");
    private final By geriBtn            =     By.xpath("//android.widget.ImageView[@content-desc='Geri']");
    private final By hesabim_btn        =     By.id("com.pozitron.hepsiburada:id/iv_toolbar_user_account_menu");
    private final By begendiklerim      =     By.xpath("//*[contains(@text , 'Beğendiklerim')]");
    private final By fav_eklenen        =     By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[1]/android.webkit.WebView/android.webkit.WebView/android.view.View/android.widget.ListView/android.view.View[1]/android.view.View[5]/android.widget.TextView");
    String email                        =     "jorinax514@upsdom.com";
    String sifre                        =     "159476Hb.";


    public void swipeUp(){
        TouchAction action =new TouchAction(driver);
        Dimension size	=driver.manage().window().getSize();
        int width=size.width;
        int height=size.height;
        int middleOfX=width * 1/2;
        int startYCoordinate= (int)(height*2/3); //ortalaam 1890
        int endYCoordinate= (int)(height*1/4); //ortalama 600
        System.out.println("Width:" + width);
        System.out.println("Height:" + height);
        System.out.println("MiddleOfX:" + middleOfX);
        System.out.println("Start Y Coordinate:" + startYCoordinate);
        System.out.println("End Y Coordinate:" + endYCoordinate);
        action.press(PointOption.point(middleOfX, startYCoordinate))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
                .moveTo(PointOption.point(middleOfX, endYCoordinate)).release().perform();
        action.press(PointOption.point(middleOfX, startYCoordinate))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
                .moveTo(PointOption.point(middleOfX, endYCoordinate)).release().perform();
    }
    public void swipeAction(int Xcoordinate, int Ycoordinate, String direction) {
        //get device width and height
        Dimension dimension = driver.manage().window().getSize();
        int deviceHeight = dimension.getHeight();
        int deviceWidth = dimension.getWidth();
        System.out.println("Height x Width of device is: " + deviceHeight + " x " + deviceWidth);

        switch (direction) {
            case "Left":
                System.out.println("Swipe Right to Left");
                //define starting and ending X and Y coordinates
                int startX=deviceWidth - Xcoordinate;
                int startY=Ycoordinate; // (int) (height * 0.2);
                int endX=Xcoordinate;
                int endY=Ycoordinate;
                //perform swipe from right to left
                new TouchAction((AppiumDriver) driver).longPress(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, endY)).release().perform();
                break;

            case "Right":
                System.out.println("Swipe Left to Right");
                //define starting X and Y coordinates
                startX=Xcoordinate;
                startY=Ycoordinate;
                endX=deviceWidth - Xcoordinate;
                endY=Ycoordinate;
                //perform swipe from left to right
                new TouchAction((AppiumDriver) driver).longPress(PointOption.point(startX, startY)).moveTo(PointOption.point(endX, endY)).release().perform();
                break;
        }
    }
    public void captureScreenShots() throws IOException {
        folder_name="screenshot";
        File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //Date format fot screenshot file name
        df=new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        //create dir with given folder name
        new File(folder_name).mkdir();
        //Setting file name
        String file_name=df.format(new Date())+".png";
        //coppy screenshot file into screenshot folder.
        FileUtils.copyFile(f, new File(folder_name + "/" + file_name));
    }
    @BeforeMethod
    public void setUp() throws IOException  {



        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("momentumGw", momentum_Gw);
        desiredCapabilities.setCapability("momentumUser", momentum_user);
        desiredCapabilities.setCapability("momentumToken", momentum_Token);//desiredCapabilities.setCapability("deviceName", "Pixel 3 XL API 30");
        //desiredCapabilities.setCapability("automationName", "UIAutomator2"); //DeviceId from "adb devices" command
        desiredCapabilities.setCapability("udid", "");
        desiredCapabilities.setCapability("deviceName", "");
        desiredCapabilities.setCapability("automationName", "UiAutomator2");
        desiredCapabilities.setCapability("app", "https://www.testrelic.com/Bilgikolik.apk");//desiredCapabilities.setCapability("appActivity", "com.pozitron.hepsiburada");
        desiredCapabilities.setCapability("noReset", "false");
        driver = new AndroidDriver<>(new URL(momentum_url), desiredCapabilities);
        wait = new WebDriverWait(driver, 10);

    }

    @Test
    public void sampleTest() throws  IOException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(header)).click();
        captureScreenShots();
        System.out.println("Pop up kapatmak için header alana tıklanır");
        wait.until(ExpectedConditions.visibilityOfElementLocated(location_View)).click();
        captureScreenShots();

        wait.until(ExpectedConditions.visibilityOfElementLocated(city_Dropdown)).click();
        captureScreenShots();
        wait.until(ExpectedConditions.visibilityOfElementLocated(city_Select)).click();
        captureScreenShots();
        System.out.println("İl seçilir");
        wait.until(ExpectedConditions.visibilityOfElementLocated(town_Dropdown)).click();
        captureScreenShots();
        wait.until(ExpectedConditions.visibilityOfElementLocated(town_Select)).click();
        captureScreenShots();
        System.out.println("İlçe seçilir");
        wait.until(ExpectedConditions.visibilityOfElementLocated(district_Dropdown)).click();
        captureScreenShots();
        wait.until(ExpectedConditions.visibilityOfElementLocated(district_Select)).click();
        captureScreenShots();
        System.out.println("Mahalle seçilir");
        wait.until(ExpectedConditions.visibilityOfElementLocated(save_Btn)).click();
        captureScreenShots();
        System.out.println("Lokasyon kayıt edilir");
        String text1= wait.until(ExpectedConditions.visibilityOfElementLocated(save_konum_title)).getText();
        System.out.println("Kayıt edilen konum:" +" "+ text1);
        captureScreenShots();
        wait.until(ExpectedConditions.visibilityOfElementLocated(category)).click();
        captureScreenShots();
        System.out.println("Kategoriler tıklanr");
        wait.until(ExpectedConditions.visibilityOfElementLocated(first_category)).click();
        captureScreenShots();
        System.out.println("İlk kategori seçilir");
        swipeUp();
        String view_Location_Text=wait.until(ExpectedConditions.visibilityOfElementLocated(text_Location)).getText();
        captureScreenShots();
        Assert.assertEquals(view_Location_Text,"Adana");
        captureScreenShots();
        System.out.println("Konum bilgisi doğrulama işlemi yapılır");
        System.out.println("Konum Bilgisi :" +" "+ view_Location_Text);
        captureScreenShots();
        }

    @Test
    public void sampleTest2() throws InterruptedException, IOException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(header)).click();
        System.out.println("Pop up kapatmak için header alana tıklanır");
        swipeUp();
        System.out.println("Aşağı kaydırma işlemi yapılır Süper Fiyat Süper Teklif alanına tıklamak için");
        Thread.sleep(2500);
        captureScreenShots();
        wait.until(ExpectedConditions.visibilityOfElementLocated(super_Fiyat)).click();
        System.out.println("Süper Fiyat Süper Teklif alanına tıklanır");
        captureScreenShots();
        Thread.sleep(2500);
        boolean fotoSayisi =driver.findElement(ilk_urun).getAttribute("scrollable") =="true";
        System.out.println("Görseli birden fazla olan ürün kontrolü yapılır");
        captureScreenShots();

        if (fotoSayisi!=true){
                wait.until(ExpectedConditions.visibilityOfElementLocated(ilk_urun)).click();
            }
            else if (fotoSayisi!=true){
                swipeUp();
                swipeUp();
                wait.until(ExpectedConditions.visibilityOfElementLocated(ilk_urun)).click();
            }
            else if (fotoSayisi!=true){
                swipeUp();
                swipeUp();
                wait.until(ExpectedConditions.visibilityOfElementLocated(ilk_urun)).click();
            }
        System.out.println("Görseli birden fazla olan ürün ise tıkla değil ise swipe et tekrar kontrol et işlemi...");
        Thread.sleep(6000);

            String secilenUrun=wait.until(ExpectedConditions.visibilityOfElementLocated(urun_text)).getText();
            System.out.println("Seçim yapılan ürün :"+" "+secilenUrun);
       /* Thread.sleep(6000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.pozitron.hepsiburada:id/productImage"))).click();
        Thread.sleep(6000);
            driver.findElementByAccessibilityId(geri_btn).click();
        Thread.sleep(6000);*/

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.pozitron.hepsiburada:id/productImage"))).click();
        System.out.println("Ürün detayda görsel tıklanır...");
        /*MobileElement el3 = (MobileElement) driver.findElementById("com.pozitron.hepsiburada:id/productImage");
        el3.click();*/

        swipeAction(150,813,"Left");
        captureScreenShots();
        System.out.println("Görsel sola kaydırma işlemleri yapılır...");
        swipeAction(150,813,"Left");
        captureScreenShots();
        System.out.println("Görsel sola kaydırma işlemleri yapılır...");
        swipeAction(150,813,"Left");
        captureScreenShots();


        wait.until(ExpectedConditions.visibilityOfElementLocated(fav_ekle)).click();
        System.out.println("Favorilere eklenir...");
        Thread.sleep(30000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(email_txt)).sendKeys(email);
        System.out.println("Login işlemi yapılır ilk olarak Email girilir...");
        Thread.sleep(30000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(login_btn)).click();
        Thread.sleep(30000);
        System.out.println("Login buton tıklanır...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(sifre_txt)).sendKeys(sifre);
        Thread.sleep(15000);
        System.out.println("Şifre bilgisi girilir...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(login_btn_2)).click();

        System.out.println("Login buton tıklanır...");
        /*    String popupmesaj= wait.until(ExpectedConditions.visibilityOfElementLocated(login_popup_text)).getText();
            System.out.println("mesaj" +popupmesaj);
            wait.until(ExpectedConditions.visibilityOfElementLocated(login_popup_tamam)).click();
            String hesabimTitle=wait.until(ExpectedConditions.visibilityOfElementLocated(hesabim_title)).getText();
            System.out.println("Login olunduğu bilgisi" +hesabimTitle);
            Thread.sleep(2000);*/
        captureScreenShots();

        wait.until(ExpectedConditions.visibilityOfElementLocated(geriBtn)).click();
        Thread.sleep(1000);
        captureScreenShots();
        System.out.println("Geri buton tıklanır...");
        Thread.sleep(1500);
        driver.navigate().back();
        Thread.sleep(1000);
        captureScreenShots();
        System.out.println("Geri buton tıklanır...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(hesabim_btn)).click();
        System.out.println("Hesabım buton tıklanır...");
        captureScreenShots();
        Thread.sleep(1500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(begendiklerim)).click();
        System.out.println("Beğendiklerim buton tıklanır...");
        Thread.sleep(1500);
        captureScreenShots();
        Thread.sleep(1500);
        String favEklenen=wait.until(ExpectedConditions.visibilityOfElementLocated(fav_eklenen)).getText();
        System.out.println("Fav eklenen bilgisi:"+""+favEklenen);
        Assert.assertEquals(favEklenen,secilenUrun,"Favoriye eklenen ile beklenen ürünün uyuşmuyor...");//Fav eklenen ürün ile beğendiklerim ekranındaki aynı mı kontrolü...

    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }




}
