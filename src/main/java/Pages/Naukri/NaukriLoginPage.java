package Pages.Naukri;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;


public class NaukriLoginPage{
    Page page;
    private Locator loginButton;
    private Locator username;
    private Locator password;
    private Locator login;

    public NaukriLoginPage(Page page){
        this.page = page;
        // this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        this.loginButton = page.locator("css=.nI-gNb-lg-rg__login");
        // this.username = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your active Email ID / Username"));
        this.username = page.getByLabel("Email ID / Username");
        // this.password = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your password"));
        this.password = page.getByLabel("Password");
        // this.login = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        this.login = page.locator("css=.btn-primary.loginButton");
        
    }
    

    public void userlogin(String user, String pass){
        loginButton.click();
        username.fill(user);
        password.fill(pass);
        login.click();
    }

}
