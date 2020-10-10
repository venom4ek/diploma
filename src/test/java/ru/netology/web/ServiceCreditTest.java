package ru.netology.web;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.genData.GenerateData;
import ru.netology.web.page.CreditPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Здесь находятся тесты по оплате в кредит
public class ServiceCreditTest {
    DataHelper data = new DataHelper();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true).savePageSource(false));
    }

    //////
    //очищаем таблицы в БД, для более удобного разбора дефектов.
    //при необходимости можно закомментировать этот метод.
    //очистка БД делается перед запуском всех тестов, для более удобного анализа дефектов.
    //если очистку делать после тестов, не будет записей в БД после тестов.
    ////////////////////////////////////////////////////////////////
    /////////////!!!НЕ ПРИМЕНЫЯТЬ(ЗАКОММЕНТИРОВАТЬ) ДАННЫЙ МЕТОД НА ПРОДАКШЕНЕ!!!!!!!!!!!!!!!!!!!!!!!!!
    ////////////////////////////////////////////////////////////////
    @BeforeAll
    static void cleanAllTable() throws SQLException {
        DataHelper data = new DataHelper();
        data.cleanAllTableMySql();
    }


    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Nested
    public class PositiveTest {

        @Test
        void shouldBeSuccess() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            credit.getErrorNotice().shouldBe(hidden);
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }
    }

    @Nested
    public class NumberCardTestField {

        @Test
        void shouldBeErrorWhenValidDataWithDeclineCard() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getDeclinedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getError();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeDeclinedWhenValidDataWithUnknownCard() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getTestCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            credit.getErrorNotice().waitUntil(visible, 15000);
        }
    }

    @Nested
    public class MonthTestField {

        @Test
        void shouldBeSuccessWhenPlusOneFromCurrentMonth() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getMonthPlus1();
            String year = genData.getNextYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenFirstMonth() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getFirstMonth();
            String year = genData.getNextYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenLastMonth() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getLastMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

    }

    @Nested
    public class YearTestField {

        @Test
        void shouldBeSuccessWhenNextYear() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getNextYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenPlusFiveFromCurrentYear() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus5();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

    }

    @Nested
    public class OwnerTestField {

        @Test
        void shouldBeSuccessWhenOwnerTwoLetter() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwnerWithTwoLetter();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenOwnerWithPoint() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus5();
            String owner = genData.getOwnerWithPoint();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenOwnerWithDoubleName() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus5();
            String owner = genData.getOwnerWithDoubleName();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenOwnerWithDoubleNameViaDash() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus5();
            String owner = genData.getOwnerWithDash();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

    }

    @Nested
    public class CvcTestField {

        @Test
        void shouldBeSuccessWhenCvcTreeChar() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode1();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            credit.getErrorNotice().shouldBe(hidden);
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenCvvTreeChar() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode2();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String status = credit.getSuccess();
            credit.getErrorNotice().shouldBe(hidden);
            String sqlStatus = data.getStatusCredit();
            assertEquals(status, sqlStatus);
        }

    }

    @Nested
    public class Negative {

        @Test
        void shouldBeHiddenNoticeSuccessWhenError() {   //сообщение УСПЕШНО, не должно появляться при ОТКАЗЕ
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getTestCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            credit.getErrorNotice().waitUntil(visible, 15000);
            credit.getSuccessNotice().shouldBe(hidden);
        }

        //проверка поля НОМЕР КАРТЫ
        @Test
        void shouldBeErrorWithCardFieldIsEmpty() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getEmptyCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = credit.getCardWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenCountCharCardLessSixteenChar() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getLessSixteenCharCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = credit.getCardWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputMoreSixteenCharCardFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getMoreSixteenCharCard(), month, year, owner, cvc);
            String cardNum = DataHelper.getMoreSixteenCharCard().getCard();
            String expected = cardNum.substring(0, 4) + " " + cardNum.substring(4, 8) + " " + cardNum.substring(8, 12) + " " + cardNum.substring(12, 16);
            String actual = credit.getCard().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpaceCharCardFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getOneSpaceCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getCard().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecCharCardFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getSpecialCharCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getCard().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputLetterCardFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getCardFromLetter(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getCard().getValue();
            assertEquals(expected, actual);
        }

        //проверка поля МЕСЯЦ
        @Test
        void shouldBeErrorWithEmptyMonthField() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getEmpty();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = credit.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputLetterMonthFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getLetter();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getMonth().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecCharMonthFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getSpecialChar();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getMonth().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenInputZeroMonthFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getZeroMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = credit.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenThirteenthMonthFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getThirteenthMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверно указан срок действия карты";
            String actual = credit.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenMonthCountMore60FromCurrent() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getMonthPlus1();
            String year = genData.getYearPlus5();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверно указан срок действия карты";
            String actual = credit.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenInputOneCharMonthFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getMonthIsOneNumeral();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = credit.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenPreviousMonthToCurrentYear() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getPreviousMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверно указан срок действия карты";
            String actual = credit.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        //проверка поля ГОД
        @Test
        void shouldBeErrorWhenEmptyYearFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getEmpty();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = credit.getYearWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputLetterYearFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getLetter();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getYear().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecCharYearFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getSpecialChar();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getYear().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenPreviousYearFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getPreviousYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Истёк срок действия карты";
            String actual = credit.getYearWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenDataExpiredMoreFiveYear() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus6();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверно указан срок действия карты";
            String actual = credit.getYearWrongFormat().getText();
            assertEquals(expected, actual);
        }

        //проверка поля ВЛАДЕЛЕЦ
        @Test
        void shouldBeErrorWithEmptyOwnerField() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getEmpty();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Поле обязательно для заполнения";
            String actual = credit.getOwnerWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputRusKeyInOwnerFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwnerWithRusKey();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getOwner().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenOnlySpaceToOwnerField() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getSpace();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Поле обязательно для заполнения";
            String actual = credit.getOwnerWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecialCharToOwnerField() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwnerWithSpecialChar();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = genData.getOwnerWithSpecialChar().substring(0, 5);
            String actual = credit.getOwner().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputNumeralKeyToOwnerField() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwnerWithNumeral();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getOwner().getValue();
            assertEquals(expected, actual);
        }

        //Проверка поля CVC/CVV
        @Test
        void shouldBeErrorWithEmptyCvcField() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getEmpty();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = credit.getCvcWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecCharCvcFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getSpecialChar();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getCvc().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputLetterCvcFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getLetter();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = credit.getCvc().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenLessThreeCharToCvcFiled() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCodeWhenTwoChar();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = credit.getCvcWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeCleanAllFieldAfterSendForm() {
            CreditPage credit = new CreditPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            credit.setCredit(DataHelper.getApprovedCard(), month, year, owner, cvc);
            credit.getButtonContinue().click();
            credit.getSuccessNotice().waitUntil(visible, 15000);
            credit.getOwner().shouldBe(empty);
        }

    }

}
