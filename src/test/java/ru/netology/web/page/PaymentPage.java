package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PaymentPage {

    private String paymentUrl = "http://127.0.0.1:8080";
    private SelenideElement payment = $(byText("Купить"));
    private SelenideElement isPayment = $(byText("Оплата по карте"));
    private SelenideElement card = $("#root > div > form > fieldset > div:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement month = $("#root > div > form > fieldset > div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement year = $("#root > div > form > fieldset > div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__box > input");
    private SelenideElement owner = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement cvc = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__box > input");
    private SelenideElement buttonContinue = $(byText("Продолжить"));
    private SelenideElement success = $("#root > div > div.notification.notification_status_ok.notification_has-closer.notification_stick-to_right.notification_theme_alfa-on-white > div.notification__title");
    private SelenideElement error = $(byText("#root > div > div.notification.notification_status_error.notification_has-closer." +
            "notification_stick-to_right.notification_theme_alfa-on-white > div.notification__title"));

    public PaymentPage() {
        open(paymentUrl);
    }

    public void setPayment(DataHelper.Card info, String mm, String yy, String own, String cvv) {
        payment.click();
        isPayment.waitUntil(visible, 5000);
        card.setValue(info.getCard());
        month.setValue(mm);
        year.setValue(yy);
        owner.setValue(own);
        cvc.setValue(cvv);
        buttonContinue.click();
    }

    public String getSuccess() {
        success.waitUntil(visible, 15000);
        return "APPROVED";
    }

    public String getError() {
        error.waitUntil(visible, 15000);
        return "DECLINED";
    }

}



