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

import java.sql.SQLException;

import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//в этом классе находятся тесты по оплате картой
public class ServicePaymentTest {
    DataHelper data = new DataHelper();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true).savePageSource(false));
    }

    //////
    //очищаем таблицы в БД, для более удобного разбора дефектов. при необходимости можно закомментировать
    //делается это перед запуском всех тестов, т.к. если делать это в конце тестов,
    //то не из чего будет сравнивать результаты тестов.
    ////////////////////////////////////////////////////////////////
    /////////////!!!НЕ ПРИМЕНЫЯТЬ(ЗАКОММЕНТИРОВАТЬ) ДАННЫЙ МЕТОД НА ПРОДАКШЕНЕ!!!!!!!!!!!!!!!!!!!!!!!!!
    ////////////////////////////////////////////////////////////////
    @BeforeAll
    static void cleanAllTable() throws SQLException {
        DataHelper data = new DataHelper();
        /////раскомментировать, если тестируем серсвис в связке с БД MySql
        data.cleanAllTableMySql();

        /////раскомментировать, если тестируем серсвис в связке с БД Postgre
//        data.cleanAllTablePostgre();
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
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            payment.getErrorNotice().shouldBe(hidden);
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }
    }

    @Nested
    public class NumberCardTestField {

        @Test
        void shouldBeErrorWhenValidDataWithDeclineCard() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getDeclinedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getError();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeDeclinedWhenValidDataWithUnknownCard() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getTestCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            payment.getErrorNotice().waitUntil(visible, 15000);
        }
    }

    @Nested
    public class MonthTestField {

        @Test
        void shouldBeSuccessWhenPlusOneFromCurrentMonth() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getMonthPlus1();
            String year = genData.getNextYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenFirstMonth() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getFirstMonth();
            String year = genData.getNextYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenLastMonth() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getLastMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

    }

    @Nested
    public class YearTestField {

        @Test
        void shouldBeSuccessWhenNextYear() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getNextYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenPlusFiveFromCurrentYear() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus5();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

    }

    @Nested
    public class OwnerTestField {

        @Test
        void shouldBeSuccessWhenOwnerTwoLetter() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwnerWithTwoLetter();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenOwnerWithPoint() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus5();
            String owner = genData.getOwnerWithPoint();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenOwnerWithDoubleName() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus5();
            String owner = genData.getOwnerWithDoubleName();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenOwnerWithDoubleNameViaDash() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus5();
            String owner = genData.getOwnerWithDash();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

    }

    @Nested
    public class CvcTestField {

        @Test
        void shouldBeSuccessWhenCvcTreeChar() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode1();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            payment.getErrorNotice().shouldBe(hidden);
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

        @Test
        void shouldBeSuccessWhenCvvTreeChar() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode2();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String status = payment.getSuccess();
            payment.getErrorNotice().shouldBe(hidden);
            String sqlStatus = data.getStatusPayment();
            assertEquals(status, sqlStatus);
        }

    }

    @Nested
    public class Negative {

        @Test
        void shouldBeHiddenNoticeSuccessWhenError() {   //сообщение УСПЕШНО, не должно появляться при ОТКАЗЕ
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getTestCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            payment.getErrorNotice().waitUntil(visible, 15000);
            payment.getSuccessNotice().shouldBe(hidden);
        }

        //проверка поля НОМЕР КАРТЫ
        @Test
        void shouldBeErrorWithCardFieldIsEmpty() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getEmptyCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = payment.getCardWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenCountCharCardLessSixteenChar() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getLessSixteenCharCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = payment.getCardWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputMoreSixteenCharCardFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getMoreSixteenCharCard(), month, year, owner, cvc);
            String cardNum = DataHelper.getMoreSixteenCharCard().getCard();
            String expected = cardNum.substring(0,4)+" "+cardNum.substring(4,8)+" "+cardNum.substring(8,12)+" "+cardNum.substring(12,16);
            String actual = payment.getCard().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpaceCharCardFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getOneSpaceCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getCard().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecCharCardFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getSpecialCharCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getCard().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputLetterCardFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getCardFromLetter(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getCard().getValue();
            assertEquals(expected, actual);
        }

        //проверка поля МЕСЯЦ
        @Test
        void shouldBeErrorWithEmptyMonthField() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getEmpty();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = payment.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputLetterMonthFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getLetter();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getMonth().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecCharMonthFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getSpecialChar();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getMonth().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenInputZeroMonthFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getZeroMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = payment.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenThirteenthMonthFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getThirteenthMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверно указан срок действия карты";
            String actual = payment.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenMonthCountMore60FromCurrent() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getMonthPlus1();
            String year = genData.getYearPlus5();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверно указан срок действия карты";
            String actual = payment.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenInputOneCharMonthFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getMonthIsOneNumeral();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = payment.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenPreviousMonthToCurrentYear() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getPreviousMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверно указан срок действия карты";
            String actual = payment.getMonthWrongFormat().getText();
            assertEquals(expected, actual);
        }

        //проверка поля ГОД
        @Test
        void shouldBeErrorWhenEmptyYearFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getEmpty();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = payment.getYearWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputLetterYearFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getLetter();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getYear().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecCharYearFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getSpecialChar();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getYear().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenPreviousYearFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getPreviousYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Истёк срок действия карты";
            String actual = payment.getYearWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenDataExpiredMoreFiveYear() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getYearPlus6();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверно указан срок действия карты";
            String actual = payment.getYearWrongFormat().getText();
            assertEquals(expected, actual);
        }

        //проверка поля ВЛАДЕЛЕЦ
        @Test
        void shouldBeErrorWithEmptyOwnerField() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getEmpty();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Поле обязательно для заполнения";
            String actual = payment.getOwnerWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputRusKeyInOwnerFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwnerWithRusKey();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getOwner().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenOnlySpaceToOwnerField() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getSpace();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Поле обязательно для заполнения";
            String actual = payment.getOwner().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecialCharToOwnerField() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwnerWithSpecialChar();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = genData.getOwnerWithSpecialChar().substring(0, 5);
            String actual = payment.getOwner().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputNumeralKeyToOwnerField() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwnerWithNumeral();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getOwner().getValue();
            assertEquals(expected, actual);
        }

        //Проверка поля CVC/CVV
        @Test
        void shouldBeErrorWithEmptyCvcField() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getEmpty();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = payment.getCvcWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputSpecCharCvcFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getSpecialChar();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getCvc().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeNotAllowInputLetterCvcFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getLetter();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            String expected = "";
            String actual = payment.getCvc().getValue();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeErrorWhenLessThreeCharToCvcFiled() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCodeWhenTwoChar();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            String expected = "Неверный формат";
            String actual = payment.getCvcWrongFormat().getText();
            assertEquals(expected, actual);
        }

        @Test
        void shouldBeCleanAllFieldAfterSendForm() {
            PaymentPage payment = new PaymentPage();
            GenerateData genData = new GenerateData();
            String month = genData.getCurrentMonth();
            String year = genData.getCurrentYear();
            String owner = genData.getOwner();
            String cvc = genData.getCvcCode();
            payment.setPayment(DataHelper.getApprovedCard(), month, year, owner, cvc);
            payment.getButtonContinue().click();
            payment.getSuccessNotice().waitUntil(visible, 15000);
            payment.getOwner().shouldBe(empty);
        }

    }

}
