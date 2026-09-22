package TestCases;
import org.testng.annotations.Test;
import com.microsoft.playwright.Page;

import Base.BasePage;
import Pages.Naukri.NaukriHomePage;
import Pages.Naukri.NaukriLoginPage;
import Pages.Naukri.NaukriProfilePage;

public class UpdateNaukriResume extends BasePage {

    @Test
    public void updateResume(){
    String user = System.getenv("NAUKRI_USERNAME");
    System.out.println(user);
    String pass = System.getenv("NAUKRI_PASSWORD");
    Page page = returnPage();
    // page.setDefaultTimeout(50000);

    //step1: launch naukri.com
    page.navigate("https://www.naukri.com");

    //step2: login to naukri.com
    NaukriLoginPage naukriLogin = new NaukriLoginPage(page);
    naukriLogin.userlogin(user, pass);

    //step3: navigate to profile page
    NaukriHomePage naukriHome = new NaukriHomePage(page);
    naukriHome.goToProfilePage();

    //step4: update the resumeHeadline
    NaukriProfilePage naukriMyProfile = new NaukriProfilePage(page);
    naukriMyProfile.editResumeHeadline();
    }
    
}
