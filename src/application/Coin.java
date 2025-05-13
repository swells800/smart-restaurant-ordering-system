package application;

import java.util.Arrays;



public enum Coin {

    FIVE_CENTS(5), TEN_CENTS(10), TWENTY_CENTS(20), FIFTY_CENTS(50), HUNDRED_CENTS(100);



    private int value;



    private Coin(int value) {

        this.value = value;

    }



    public int getValue() {

        return this.value;

    }



    public static Coin fromValue(int value) {

        for (Coin coin : Coin.values()) {

            if (coin.value == value) {

                return coin;

            }

        }

        throw new IllegalArgumentException("No coin with value: " + value);

    }



    public int getCoinCount(int amount) {

        return amount / this.value;

    }



    public static CoinBundle calculateChange(int totalChange) {

        int[] counts = new int[Coin.values().length];

        for (Coin coin : Coin.values()) {

            int coinCount = totalChange / coin.getValue();

            counts[coin.ordinal()] = coinCount;

            totalChange %= coin.getValue();

        }

        return new CoinBundle(counts);

    }



    public static int[] parseCoins(String coins) {

        String[] numberCoinsInText = coins.split(",");

        int[] result = new int[Coin.values().length];



        for (int index = 0; index < numberCoinsInText.length; index++) {

            result[index] = Integer.parseInt(numberCoinsInText[index].trim());

            if (result[index] < 0) {

                throw new IllegalArgumentException("Coin counts cannot be negative");

            }

        }



        return result;

    }



    public static CoinBundle toCoinBundle(String coins) {

        return new CoinBundle(parseCoins(coins));

    }



    @Override

    public String toString() {

        return this.name() + " (" + this.value + " cents)";

    }



    public static Coin[] sortedCoinsDescending() {

        Coin[] coins = Coin.values();

        Arrays.sort(coins, (c1, c2) -> Integer.compare(c2.getValue(), c1.getValue()));

        return coins;

    }



    public static int calculateTotalValue(int[] coinCounts) {

        if (coinCounts.length != Coin.values().length) {

            throw new IllegalArgumentException("Invalid number of coin counts");

        }



        int total = 0;

        for (int i = 0; i < coinCounts.length; i++) {

            total += coinCounts[i] * Coin.values()[i].getValue();

        }

        return total;

    }

}