package application;

public enum Product {

    TWIX(1,100), 

    COKE(2,50),

    WATER(3,30), 

    SANDWICH(4,150),

    CHIPS(5,25), 

    Snickers(6,25), 

    Sprite(7,50),

    RedBull(8,100),

    EMPTY(0,0);



	private int selectionNumber;

    private int price;



    Product(int selectionNumber, int price){

        this.selectionNumber = selectionNumber;

        this.price = price;

    }



    public int getSelectionNumber(){

        return selectionNumber;

    }



    public int getPrice(){

        return this.price;

    }



    public static Product valueOf(int numberSelection){

        for(Product product: Product.values()){

            if(numberSelection == product.getSelectionNumber()){

                return product;

            }

        }

        return EMPTY;

    }

}