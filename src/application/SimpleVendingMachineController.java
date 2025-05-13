package application;

public class SimpleVendingMachineController implements VendingMachineController {



    private Calculator calculator = new SimpleCalculator();



    @Override

    public CoinBundle calculateChange(VendingMachineRequest request) {

        int total = calculator.CalculateTotal(request.enteredCoins);

        int totalChange = total - request.product.getPrice();

        return calculator.calculateChange(totalChange);

    }

}