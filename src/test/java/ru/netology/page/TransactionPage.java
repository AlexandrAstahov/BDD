package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class TransactionPage {
    private SelenideElement sumAmount = $("[data-test-id='amount'] .input__control");
    private SelenideElement fromAccount = $("[data-test-id='from'] .input__control");
    private SelenideElement clickReplenish = $("[data-test-id=action-transfer]");

    public void transferMoney(int amount, String from) {
        sumAmount.setValue(valueOf(amount));
        fromAccount.setValue(valueOf(from));
        clickReplenish.click();
    }

    public void errorLimit() {
        $(".notification__content").should(Condition.exactText("Ошибка"));
    }

    public void sameCard() {
        $(".notification__content").should(Condition.exactText("Ошибка"));
    }

    public void invalidCard() {
        $(".notification__content").should(Condition.text("Ошибка! Произошла ошибка"));
    }
}