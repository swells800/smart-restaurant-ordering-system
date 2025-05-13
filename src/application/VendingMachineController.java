package application;

public interface VendingMachineController {

	CoinBundle calculateChange(VendingMachineRequest request);
}
