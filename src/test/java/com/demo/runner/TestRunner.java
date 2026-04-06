package com.demo.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestRunner — identical to your existing Cucumber-TestNG runner.
 * Nothing Playwright-specific here — Playwright is wired in via Hooks.
 */
@CucumberOptions(
    features  = "src/test/resources/features",
    glue      = {"com.demo.steps", "com.demo.hooks"},
    plugin    = {
        "pretty",                                    // coloured console output
        "html:target/cucumber-reports/report.html",  // HTML report
        "json:target/cucumber-reports/report.json"   // for CI integration
    },
    monochrome = true,
    tags = ""   // run all — change to "@smoke" or "@regression" as needed
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Uncomment @Override + parallel = true to run scenarios in parallel.
     * Each thread gets its own Playwright instance via PlaywrightManager's ThreadLocal.
     */
    // @Override
    // @DataProvider(parallel = true)
    // public Object[][] scenarios() {
    //     return super.scenarios();
    // }
}
