package tests;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class ProjectTest extends BaseTest {

    @Test
    public void checkCreateProject() throws InterruptedException {
        open("/login");
        $(shadowCss("#accept", "#usercentrics-cmp-ui")).click();
        $("[name=email]").setValue(validUser);
        $("[name=password]").setValue(validPassword);
        $(byText("Sign in")).click();
        $(byText("Create new project")).click();
        Faker faker = new Faker();
        String projectName = faker.company().name();
        $(byId("project-name")).setValue(projectName);
        String actualProjectName = $(byId("project-name")).getValue();
        String projectCode = faker.code().asin();
        $(byId("project-code")).setValue(projectCode);
        $("[name=description-area]").setValue("The description of the project Project_1 should be here.");
        $("[type=radio]").selectRadio("public");
        $(byText("Create project")).click();
        $x("//*[contains(text(), 'Projects')]").click();
        $x(String.format("//*[contains(text(), '%s')]//ancestor::tr//span[@class='Fkj0XN']",
                actualProjectName)).click();
        $x("//div[@data-testid='remove']").click();
        $x("//span[text()='Delete project']").click();
        var projectInTable = $x(String.format("//table//td[contains(text(), '%s')]", projectName));
        projectInTable.shouldNotBe(com.codeborne.selenide.Condition.visible);
        boolean isProjectVisible = projectInTable.isDisplayed();
        Assert.assertFalse(isProjectVisible,
                "Ошибка!!! Проект '" + projectName + "' не удален!!!");
    }
}