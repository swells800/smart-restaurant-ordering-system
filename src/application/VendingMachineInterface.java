package application;





public interface VendingMachineInterface {



    void displayProducts();



    void selectProduct(int product);



    void displayEnterCoinsMessage();



    void displayChangeMessage();



	void enterCoins(int[] enteredCoins);

}