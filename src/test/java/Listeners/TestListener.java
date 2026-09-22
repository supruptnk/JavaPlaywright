package Listeners;

import java.nio.file.Path;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.microsoft.playwright.Page;

import Base.BasePage;
import Utils.ScreenshotUtils;

public class TestListener implements ITestListener{
    @Override
    public void onTestFailure(ITestResult result){
        String testName = result.getMethod().getMethodName();

        try{
            
            Page page = BasePage.returnPage();
            ScreenshotUtils.takeScreenshot(page, "test-output//screenshots//"+testName+".png");

        }catch(IllegalStateException e){

        }
    }
}
