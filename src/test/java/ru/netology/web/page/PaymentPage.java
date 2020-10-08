package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Getter
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
    private SelenideElement successNotice = $("#root > div > div:nth-child(7) > div:nth-child(2)");
    private SelenideElement errorNotice = $("#root > div > div:nth-child(8) > div:nth-child(2)");

    private SelenideElement cardWrongFormat = $("#root > div > form > fieldset > div:nth-child(1) > span > span > span.input__sub");
    private SelenideElement monthWrongFormat = $("#root > div > form > fieldset > div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__sub");
    private SelenideElement yearWrongFormat = $("#root > div > form > fieldset > div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__sub");
    private SelenideElement ownerWrongFormat = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__sub");
    private SelenideElement cvcWrongFormat = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__sub");

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
    }

    public String getSuccess() {
        successNotice.waitUntil(visible, 15000);
        return "APPROVED";
    }

    public String getError() {
        errorNotice.waitUntil(visible, 15000);
        return "DECLINED";
    }

}
