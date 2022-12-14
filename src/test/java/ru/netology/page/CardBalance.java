package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardBalance {
    private SelenideElement heading = $("[data-test-id=dashboard]");
   // private static SelenideElement firstCardButton = $("[data-test-id=\"action-deposit\"]");
   // private static SelenideElement secondCardButton = $("data-test-id=\"action-deposit\"");

    // к сожалению, разработчики не дали нам удобного селектора, поэтому так
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public CardBalance() {
        heading.shouldBe(visible);
    }

    public static TransactionPage pushCardButton(int index) {
        $$x("//button[@data-test-id=\"action-deposit\"]").get(index).click();
        return new TransactionPage();
    }

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = cards.last().text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}


