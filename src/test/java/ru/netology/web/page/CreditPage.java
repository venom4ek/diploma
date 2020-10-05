package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CreditPage {
    private String creditUrl = "http://127.0.0.1:8080";
    private SelenideElement toCredit = $(byText("Купить в кредит"));
    private SelenideElement isCredit = $(byText("Кредит по данным карты"));
    private SelenideElement card = $("#root > div > form > fieldset > div:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement month = $("#root > div > form > fieldset > div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement year = $("#root > div > form > fieldset > div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__box > input");
    private SelenideElement owner = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement cvc = $("#root > div > form > fieldset > div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__box > input");
    private SelenideElement buttonContinue = $(byText("Продолжить"));


    public CreditPage() {
        open(creditUrl);
    }

    public void setCredit(DataHelper.Card info, String mm, String yy, String own, String cvv) {
        toCredit.click();
        isCredit.waitUntil(visible, 5000);
        card.setValue(info.getCard());
        month.setValue(mm);
        year.setValue(yy);
        owner.setValue(own);
        cvc.setValue(cvv);
        buttonContinue.click();
        System.out.println("fdsgf");
    }
}
