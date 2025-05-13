package application;

public interface Calculator {
	
	int CalculateTotal(CoinBundle enteredCoins);
	CoinBundle calculateChange(int amountMoneyToReturn);

}
