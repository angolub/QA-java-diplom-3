package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MainPageTest extends BaseTest {
    private long startScrollContainerPosition = 0;

    @Override
    @Before
    public void startUp() throws IOException {
        super.startUp();
        startScrollContainerPosition = startMainPage.getDesignerContainerScrollPosition();
    }

    @Test
    public void sauceTabClickTest() {
        int sauceTabIndex = 1;
        startMainPage.designerTabClick(sauceTabIndex);
        checkDesignerTabClick(sauceTabIndex);

    }

    @Test
    public void fillingTabClickTest() {
        int fillingTabIndex = 2;
        startMainPage.designerTabClick(fillingTabIndex);
        checkDesignerTabClick(fillingTabIndex);
    }

    @Test
    public void bunTabClickTest() {
        startMainPage.designerTabClick(2);
        int bunTabIndex = 0;
        startMainPage.designerTabClick(bunTabIndex);
        checkDesignerTabClick(bunTabIndex);
    }

    @Step("Check designer tab click")
    public void checkDesignerTabClick(int index) {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(startMainPage.isDesignerTabSelected(index))
                    .as("Проверяем выбранный tab").isTrue();

            long newContainerScrollPosition = startMainPage.getDesignerContainerScrollPosition();

            softAssertions.assertThat(newContainerScrollPosition)
                    .as("Проверяем смену скролла").isGreaterThan(startScrollContainerPosition);
        });
    }
}
