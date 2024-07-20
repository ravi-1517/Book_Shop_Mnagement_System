package models;

public class Cart extends Book {
	
	private int quantity;
	
	public Cart() {
		this.quantity = 0;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
