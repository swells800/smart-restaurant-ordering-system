package application;

public class CoinBundle {



    public int number5CentsCoins;

    public int number10CentsCoins;

    public int number20CentsCoins;

    public int number50CentsCoins;

    public int number100CentsCoins;



   

    public CoinBundle() {

        this.number5CentsCoins = 0;

        this.number10CentsCoins = 0;

        this.number20CentsCoins = 0;

        this.number50CentsCoins = 0;

        this.number100CentsCoins = 0;

    }



    public CoinBundle(int... enteredCoins) {

        this.number5CentsCoins = enteredCoins[0];

        this.number10CentsCoins = enteredCoins[1];

        this.number20CentsCoins = enteredCoins[2];

        this.number50CentsCoins = enteredCoins[3];

        this.number100CentsCoins = enteredCoins[4];

    }



    public int getTotal() {

        int total = 0;

        total += this.number5CentsCoins * 5;

        total += this.number10CentsCoins * 10;

        total += this.number20CentsCoins * 20;

        total += this.number50CentsCoins * 50;

        total += this.number100CentsCoins * 100;

        return total;

    }



    public CoinBundle add(CoinBundle other) {

        return new CoinBundle(

            this.number5CentsCoins + other.number5CentsCoins,

            this.number10CentsCoins + other.number10CentsCoins,

            this.number20CentsCoins + other.number20CentsCoins,

            this.number50CentsCoins + other.number50CentsCoins,

            this.number100CentsCoins + other.number100CentsCoins

        );

    }



    public CoinBundle subtract(CoinBundle other) {

        return new CoinBundle(

            this.number5CentsCoins - other.number5CentsCoins,

            this.number10CentsCoins - other.number10CentsCoins,

            this.number20CentsCoins - other.number20CentsCoins,

            this.number50CentsCoins - other.number50CentsCoins,

            this.number100CentsCoins - other.number100CentsCoins

        );

    }



    public boolean isValid() {

        return number5CentsCoins >= 0 &&

               number10CentsCoins >= 0 &&

               number20CentsCoins >= 0 &&

               number50CentsCoins >= 0 &&

               number100CentsCoins >= 0;

    }



    public int[] toArray() {

        return new int[] {

            number5CentsCoins,

            number10CentsCoins,

            number20CentsCoins,

            number50CentsCoins,

            number100CentsCoins

        };

    }



    public void clear() {

        this.number5CentsCoins = 0;

        this.number10CentsCoins = 0;

        this.number20CentsCoins = 0;

        this.number50CentsCoins = 0;

        this.number100CentsCoins = 0;

    }



    @Override

    public String toString() {

        return "CoinBundle {" +

               "5 cents: " + number5CentsCoins +

               ", 10 cents: " + number10CentsCoins +

               ", 20 cents: " + number20CentsCoins +

               ", 50 cents: " + number50CentsCoins +

               ", 100 cents: " + number100CentsCoins +

               ", Total: " + getTotal() + " cents" +

               '}';

    }

}