package ru.netology.test;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardBalance;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.page.CardBalance.pushCardButton;


public class TransferFromCardToCardTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var cardBalance = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferMoneyFromFirstToSecond() {
        var cardBalance = new CardBalance();
        int amount = cardBalance.getFirstCardBalance() / 2;
        var firstCardBalanceStart = cardBalance.getFirstCardBalance();
        var secondCardBalanceStart = cardBalance.getSecondCardBalance();
        var transactionPage = CardBalance.pushCardButton(1);
        transactionPage.transferMoney(amount, String.valueOf(getFirstCardNumber()));
        var firstCardBalanceFinish = firstCardBalanceStart - amount;
        var secondCardBalanceFinish = secondCardBalanceStart + amount;

        assertEquals(firstCardBalanceFinish, firstCardBalanceStart - amount);
        assertEquals(secondCardBalanceFinish, secondCardBalanceStart + amount);
    }

    @Test
    public void shouldTransferMoneyFromSecondToFirst() {
        var cardBalance = new CardBalance();
        int amount = cardBalance.getSecondCardBalance() / 2;
        var firstCardBalanceStart = cardBalance.getFirstCardBalance();
        var secondCardBalanceStart = cardBalance.getSecondCardBalance();
        var transactionPage = CardBalance.pushCardButton(0);
        transactionPage.transferMoney(amount, String.valueOf(getSecondCardNumber()));
        var firstCardBalanceFinish = firstCardBalanceStart + amount;
        var secondCardBalanceFinish = secondCardBalanceStart - amount;

        assertEquals(firstCardBalanceFinish, firstCardBalanceStart + amount);
        assertEquals(secondCardBalanceFinish, secondCardBalanceStart - amount);
    }
    @Test
    public void TransferFromANonExistentCard() {
        var cardBalance = new CardBalance();
        int amount = cardBalance.getFirstCardBalance() / 2;
        var transactionPage = pushCardButton(0);
        transactionPage.transferMoney(amount, String.valueOf(getThirdCardNumber()));
        transactionPage.invalidCard();
    }

    @Test
    public void shouldTransferFromSecondToSecondCard() {
        var cardBalance = new CardBalance();
        int amount = cardBalance.getSecondCardBalance() / 2;
        var transactionPage = pushCardButton(1);
        transactionPage.transferMoney(amount, String.valueOf(getSecondCardNumber()));
        transactionPage.sameCard();

    }

    @Test
    public void TransferFromFirstToSecondAboveTheLimit() {
        var cardBalance = new CardBalance();
        int amount = cardBalance.getFirstCardBalance() + 200;
        var transactionPage = CardBalance.pushCardButton(1);
        transactionPage.transferMoney(amount, String.valueOf(getFirstCardNumber()));
        transactionPage.errorLimit();

    }
}