package com.github;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SuccessfulSelenideRepositorySearchTest extends TestBase {
    @Test
    void selenideWikiSearchTest() {
        open(baseUrl);
        $("[placeholder='Search or jump to...']").click();
        $("#query-builder-test").setValue("selenide").pressEnter();

        $("[data-testid='results-list']").shouldHave(text("selenide/selenide"));
        $("[href='/selenide/selenide']").click();

        $("#repository-container-header").shouldHave(text("selenide / selenide"));
        $(".Layout-sidebar").shouldHave(text("Contributors"));
        $(".Layout-sidebar").$(byText("Contributors"))
                .closest("h2").sibling(0).$$("li").first().hover();
        $(".Popover-message").shouldHave(text("Andrei Solntsev"));

        $("#wiki-tab").click();
        $("#wiki-body").shouldHave(text("Welcome to the selenide wiki!"));

        $(".markdown-body").shouldHave(text("Soft assertions"));
        $(".markdown-body").$(byText("Soft assertions")).click();
        $(".markdown-body").shouldHave(text("Using JUnit5"));

        $("[href='#3-using-junit5-extend-test-class']").click();
        $(".markdown-body").$$("div pre")
                .findBy(text("@ExtendWith({SoftAssertsExtension.class})"))
                .shouldHave(text("""
                        class Tests {
                          @Test
                          void test() {
                            Configuration.assertionMode = SOFT;
                            open("page.html");
                                               
                            $("#first").should(visible).click();
                            $("#second").should(visible).click();
                          }
                        }"""));
    }
}
