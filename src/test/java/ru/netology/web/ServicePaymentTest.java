package ru.netology.web;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.genData.GenerateData;
import ru.netology.web.page.PaymentPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServicePaymentTest {
    DataHelper data = new DataHelper();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Nested
    public class PositiveTest {

        @Test
        void shouldBeSuccess() {

            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(sqlStatus, status);
        }

        
    }

    @Nested
    public class CardTestField {

        @Test
        void shouldBeSuccessWhenValidDataWithDeclineCard() {

            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getDeclinedCard(), month, year, owner, cvc);
            String status = payment.getError();
            String sqlStatus = data.getStatusPayment();
            assertEquals(sqlStatus, status);
        }

    }

    @Nested
    public class NumberCardTestField {

    }

    @Nested
    public class MonthTestField {

    }

    @Nested
    public class YearTestField {

    }

    @Nested
    public class OwnerTestField {

    }

    @Nested
    public class CvcTestField {

    }
}
