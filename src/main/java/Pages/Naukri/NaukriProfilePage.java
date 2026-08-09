package Pages.Naukri;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class NaukriProfilePage {
    private Page page;
    private Locator resumeHeadlineEdit;
    private Locator resumeHeadlineTextArea;
    private Locator resumeHeadlineSave;

    public NaukriProfilePage(Page page){
        this.page = page;
        this.resumeHeadlineEdit = page.locator("div.widgetHead").filter(new Locator.FilterOptions().setHasText("Resume headline")).locator("span.edit.icon");
        this.resumeHeadlineTextArea = page.locator("textarea.fue__text-area");
        this.resumeHeadlineSave = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"));
    }

    public void editResumeHeadline(){
        resumeHeadlineEdit.click();
        String headlinetext = resumeHeadlineTextArea.textContent();
        System.out.println(headlinetext);
        if(headlinetext.contains("|")){
            headlinetext = headlinetext.replace("|", "::");
            resumeHeadlineTextArea.fill(headlinetext);
        }else{
            headlinetext = headlinetext.replace("::", "|");
            resumeHeadlineTextArea.fill(headlinetext);
        }

        resumeHeadlineSave.click();

    }

    
}
