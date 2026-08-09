package Pages.Naukri;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class NaukriHomePage {

    private Page page;
    private Locator viewProfile;


    public NaukriHomePage(Page page){
        this.page = page;
        this.viewProfile = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View profile"));
    }
    
    public void goToProfilePage(){
        viewProfile.click();
    }

}
