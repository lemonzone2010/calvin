package pattern;

public class MediatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Mediator mediator = new Mediator();
		Buy buy = new Buy(mediator);
		Sell sell = new Sell(mediator);
		Storage storage = new Storage(mediator);

		storage.printItem();
		buy.buyItem();
		buy.buyItem();
		buy.buyItem();
		buy.buyItem();
		storage.printItem();
		sell.sellItem();
		storage.printItem();
	}

}

class Mediator {
	Buy buy;
	Sell sell;
	Storage storage;

	public Mediator() {
		buy = new Buy(this);
		sell = new Sell(this);
		storage = new Storage(this);
	}

	public void buyItem() {
		// buy.buyItem();
		storage.addItem();
	}

	public void sellItem() {
		// sell.sellItem();
		storage.reduceItem();
	}
}

abstract class Colleague {
	Mediator mediator;

	public Colleague(Mediator mediator) {
		this.mediator = mediator;
	}

}

class Buy extends Colleague {

	public Buy(Mediator mediator) {
		super(mediator);
	}

	public void buyItem() {
		mediator.buyItem();
		System.out.println("buyed item");
	}

}

class Sell extends Colleague {

	public Sell(Mediator mediator) {
		super(mediator);
	}

	public void sellItem() {
		mediator.sellItem();
		System.out.println("selled item");
	}
}

class Storage extends Colleague {
	static int ITEM = 100;

	public Storage(Mediator mediator) {
		super(mediator);
	}

	public void addItem() {
		ITEM++;
		System.out.println("addeded item");
	}

	public void reduceItem() {
		ITEM--;
		System.out.println("reduceed item");
	}

	public void printItem() {
		System.out.println("Storage items:" + ITEM);
	}

}