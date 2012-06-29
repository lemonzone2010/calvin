package com.apusic.md.model.emarket;

import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.apusic.ebiz.model.user.User;

public class ShoppingCartTest {

	 private ShoppingCart shoppingCart;

	 @Before
	 public void init(){
		 shoppingCart = new ShoppingCart(SaleType.RETAIL);
	 }

	 @Test
	 public void addSameProductToShoppingCart(){
		 Product product = new Product();
		 product.setId(1);
		 product.setDesc("Nike sport T-shit");

		 shoppingCart.addToShoppingCart(product, 5,"",false,"");

		 List<CartItem> items = shoppingCart.getItems();

		 Assert.assertEquals(1, items.size());

		 CartItem item = items.get(0);
		 Assert.assertEquals(5,item.getQuantity());


		 Product product2 = new Product();
		 product2.setId(1);
		 product2.setDesc("Nike sport T-shit");

		 shoppingCart.addToShoppingCart(product2, 5,"",false,"");
		 Assert.assertEquals(1, items.size());

		 CartItem item1 = items.get(0);
		 Assert.assertEquals(10,item.getQuantity());
	 }

	 @Test
	 public void addDifferentToShoppingCart2(){
		 Product product = new Product();
		 product.setId(1);
		 product.setDesc("Nike sport T-shit");

		 shoppingCart.addToShoppingCart(product, 5,"",false,"");

		 List<CartItem> items = shoppingCart.getItems();

		 Assert.assertEquals(1, items.size());

		 CartItem item = items.get(0);
		 Assert.assertEquals(5,item.getQuantity());


		 Product product2 = new Product();
		 product2.setId(2);
		 product2.setDesc("Nike sport T-shit");

		 shoppingCart.addToShoppingCart(product2, 5,"",false,"");
		 Assert.assertEquals(2, items.size());

		 CartItem item1 = items.get(0);
		 Assert.assertEquals(5,item.getQuantity());
	 }

	 public void removeProductFromShoppingCart(){
		 Product product = new Product();
		 product.setId(1);
		 product.setDesc("Nike sport T-shit");
		 shoppingCart.addToShoppingCart(product, 5,"",false,"");

		 shoppingCart.remove("",product);
		 Assert.assertEquals(0, shoppingCart.getItems().size());


		 Product product2 = new Product();
		 product2.setId(2);
		 product2.setDesc("Nike sport T-shit");
		 shoppingCart.addToShoppingCart(product2, 5,"",false,"");

		 Product product3 = new Product();
		 product3.setId(3);
		 product3.setDesc("Nike sport T-shit");
		 shoppingCart.addToShoppingCart(product3, 5,"",false,"");

		 shoppingCart.remove("",product2);
		 Assert.assertEquals(1, shoppingCart.getItems().size());

		 shoppingCart.addToShoppingCart(product2, 5,"",false,"");
		 shoppingCart.remove("",new Product[]{product2, product3});
		 Assert.assertEquals(0, shoppingCart.getItems().size());

	 }

	 @Test
	 public void getCartItemsMapBySeller(){
		User user = new User();
		user.setName("hufeng");

		Brand brand = new Brand();
		brand.setName("耐克");

		 Product product = new Product();
		 product.setId(1);
		 product.setDesc("Nike sport T-shit");
		 product.setUser(user);
		 product.setBrand(brand);

		 shoppingCart.addToShoppingCart(product, 5,"",false,"");

		 Product product2 = new Product();
		 product2.setId(2);
		 product2.setDesc("Nike sport T-shit");
		 product2.setUser(user);
		 product2.setBrand(brand);

		 shoppingCart.addToShoppingCart(product2, 5,"",false,"");

		 Map<String, Set<CartItem>> itemMap = shoppingCart.getCartItemsMapBySeller();
		 Assert.assertTrue(itemMap.size()==1);
		 itemMap.get("hufeng");
		 Assert.assertTrue(itemMap.get("hufeng").size()==2);
	 }
}
