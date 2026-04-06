# Playwright + Java + Cucumber — Learning Project

## Setup (one-time, ~5 minutes)

### Prerequisites
- Java 17+
- Maven 3.8+
- That's it — no ChromeDriver download needed (Playwright manages browsers itself)

### Install Playwright browsers
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

### Run all tests
```bash
mvn test
```

### Run with browser visible (already set in Hooks.java)
Browser opens automatically — headless=false is the default in this project.
Change to `true` in Hooks.java for silent/CI runs.

---

## Project structure

```
src/test/
├── java/com/demo/
│   ├── hooks/
│   │   ├── PlaywrightManager.java   ← Browser lifecycle (replaces WebDriverManager)
│   │   └── Hooks.java               ← @Before/@After (identical pattern to Selenium)
│   ├── pages/
│   │   ├── LoginPage.java           ← Page Object (same POM pattern)
│   │   └── InventoryPage.java       ← Page Object with list handling
│   ├── steps/
│   │   ├── LoginSteps.java          ← Step definitions (identical to your current work)
│   │   └── InventorySteps.java
│   └── runner/
│       └── TestRunner.java          ← Cucumber-TestNG runner (identical)
└── resources/features/
    ├── login.feature                ← Gherkin (unchanged from Selenium world)
    └── inventory.feature
```

---

## Selenium → Playwright cheatsheet

| What you do in Selenium | Playwright equivalent |
|---|---|
| `new ChromeDriver()` | `playwright.chromium().launch()` |
| `driver.get(url)` | `page.navigate(url)` |
| `driver.findElement(By.id("x"))` | `page.locator("#x")` |
| `driver.findElement(By.xpath("..."))` | `page.locator("xpath=...")` |
| `element.click()` | `locator.click()` |
| `element.sendKeys("text")` | `locator.fill("text")` |
| `element.getText()` | `locator.textContent()` |
| `element.isDisplayed()` | `locator.isVisible()` |
| `driver.findElements(...)` | `page.locator(...)` (already a list) |
| `elements.size()` | `locator.count()` |
| `elements.get(0)` | `locator.first()` |
| `WebDriverWait` + `ExpectedConditions` | Nothing — auto-wait built in |
| `driver.getCurrentUrl()` | `page.url()` |
| `((TakesScreenshot)driver).getScreenshotAs(...)` | `page.screenshot()` |
| `driver.quit()` | `browser.close()` + `playwright.close()` |
| `new ChromeOptions()` | `new BrowserType.LaunchOptions()` |

---

## Key concepts to understand before interview

### 1. Auto-wait (most important concept)
Playwright waits automatically before every action for the element to be:
- Attached to DOM
- Visible
- Stable (not animating)
- Enabled (not disabled)
- Not obscured by another element

You never write `Thread.sleep()` or `WebDriverWait` in Playwright.

### 2. BrowserContext = isolated session
```java
// Each test gets its own context — no cookie/localStorage bleed between tests
BrowserContext context1 = browser.newContext(); // Test 1 session
BrowserContext context2 = browser.newContext(); // Test 2 session — completely isolated
```
In Selenium, you got isolation by creating a new driver instance (expensive).
In Playwright, multiple contexts share one browser process (much faster).

### 3. Locator strategies (preference order)
```java
page.getByRole(AriaRole.BUTTON, ...)    // Best — semantic, resilient
page.getByLabel("Email address")         // Great for form fields
page.getByPlaceholder("Enter email")     // Good for inputs
page.getByText("Submit")                 // Good for buttons/links
page.locator("#id")                      // CSS — fine
page.locator("xpath=//div[...]")         // XPath — last resort
```

### 4. Assertions with auto-retry
```java
// PlaywrightAssertions retries for up to 5 seconds before failing
// No need for explicit waits before assertions
assertThat(locator).isVisible();
assertThat(locator).hasText("expected");
assertThat(locator).hasCount(6);
assertThat(page).hasURL("https://...");
assertThat(page).hasTitle("My App");
```

### 5. API testing with Playwright (bonus — JD mentions API testing)
```java
// Playwright can make HTTP requests too — useful for test setup/teardown
APIRequestContext api = playwright.request().newContext(
    new APIRequest.NewContextOptions().setBaseURL("https://api.example.com")
);
APIResponse response = api.get("/users/1");
assertEquals(200, response.status());
// Parse JSON response
JsonObject json = new Gson().fromJson(response.text(), JsonObject.class);
```

---

## Exercises (do these after reading the code)

### Exercise 1 — Modify LoginPage
Add a method `assertPageTitle(String expected)` that asserts the browser tab title.
Hint: `assertThat(page).hasTitle(...)`

### Exercise 2 — Add a new scenario
In `login.feature`, add a scenario for performance glitch user:
- Username: `performance_glitch_user`
- Expected: lands on inventory page (but slowly)

### Exercise 3 — Add a CartPage
Create `src/test/java/com/demo/pages/CartPage.java` with:
- `navigateToCart()` — click the cart icon
- `assertCartIsEmpty()` — verify no items in cart
- `getCartItemCount()` — return number of items

### Exercise 4 — Enable parallel execution
In `TestRunner.java`, uncomment the parallel DataProvider.
In `PlaywrightManager.java`, verify ThreadLocal is correctly scoped.
Run `mvn test` and observe parallel browser windows.

---

## Interview talking points from this project

- "I built a Playwright + Cucumber + Java framework from scratch using the same POM
  and Cucumber-TestNG patterns from my Selenium work. The main shift was removing
  all explicit waits — Playwright's auto-wait handles that, which eliminated most
  flakiness in my test suite."

- "I used BrowserContext for test isolation instead of creating a new driver per test —
  this reduced our parallel test execution time because multiple contexts share
  one browser process."

- "Playwright's built-in assertThat() retries assertions automatically, so I don't
  need the Awaitility or FluentWait patterns I used with Selenium."
