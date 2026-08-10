package Base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import DriverManager.BrowserManager;
import Listeners.TestListener;

import com.microsoft.playwright.Page;

@Listeners(TestListener.class)
public class BasePage {
    
    @BeforeSuite
    public void setUpPlaywright(){
        System.out.println("Before Suite");
        BrowserManager.setPlaywright();
    }

    @BeforeClass
    public void setUpBrowser(){
        System.out.println("Before Class");
        BrowserManager.setBrowser("chrome");
        BrowserManager.setBrowserContext();
    }

    @BeforeMethod
    public void setUpPage(){
        System.out.println("Before Method");
        BrowserManager.initPage();
    }

    public static Page returnPage(){
        return BrowserManager.getPage();
    }

    @AfterMethod
    public void tearDownBrowser(){

    }
}
