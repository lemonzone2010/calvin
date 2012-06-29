package com.apusic.md.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.apusic.ebiz.model.HibernateUtil;
import com.apusic.ebiz.model.user.User;
import com.apusic.md.model.emarket.Advertisement;
import com.apusic.md.model.emarket.AdvertisementPosition;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.CategoryAttr;
import com.apusic.md.model.emarket.Favorite;
import com.apusic.md.model.emarket.HelpCenterContent;
import com.apusic.md.model.emarket.HelpCenterNavigation;
import com.apusic.md.model.emarket.News;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.ProductRecommend;
import com.apusic.md.model.emarket.ProductStateType;
import com.apusic.md.model.emarket.ProductStockType;
import com.apusic.md.model.emarket.ProductTemplate;
import com.apusic.md.model.emarket.RecommendType;
import com.apusic.md.model.emarket.SaleType;
import com.apusic.md.model.emarket.StateType;
import com.apusic.md.model.orderbook.DeliveryInfo;
import com.apusic.md.model.orderbook.DeliveryMode;
import com.apusic.md.model.orderbook.DeliveryTimeSlot;
import com.apusic.md.model.orderbook.Invoice;
import com.apusic.md.model.orderbook.InvoiceContentType;
import com.apusic.md.model.orderbook.InvoiceType;
import com.apusic.md.model.orderbook.Order;
import com.apusic.md.model.orderbook.OrderInvoice;
import com.apusic.md.model.orderbook.OrderItem;
import com.apusic.md.model.orderbook.OrderPriceChange;
import com.apusic.md.model.orderbook.OrderState;
import com.apusic.md.model.orderbook.PayAndDelivery;
import com.apusic.md.model.orderbook.PayMode;
import com.apusic.md.model.orderbook.PayType;
import com.apusic.md.model.orderbook.PaymentInfo;
import com.apusic.md.model.orderbook.TotalPriceStrategy;
import com.apusic.md.model.usersphere.AgreementType;
import com.apusic.md.model.usersphere.Contact;
import com.apusic.md.model.usersphere.DeliveryAddr;
import com.apusic.md.model.usersphere.MemberType;
import com.apusic.md.model.usersphere.RegisterAgreement;
import com.apusic.md.model.usersphere.RetrievePW;
import com.apusic.md.model.usersphere.UserProfile;

/**
 * This test case is intended to do smoke test on the mapping. Besides, this
 * test case will isolate the unit test from Oracle by leverage Derby as the
 * database
 *
 * @author apusican
 *
 */
public class HibernateMappingTest {

	private Session session; 

	@Before    
	public void setup() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	@Test
	public void registerAgreement() {
		RegisterAgreement g = new RegisterAgreement();
		g.setAgreementContent("测试");
		g.setType(AgreementType.PERSONAL);
		session.beginTransaction();
		session.save(g);
		session.getTransaction().commit();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		RegisterAgreement g1 = (RegisterAgreement) session.get(
				RegisterAgreement.class, 1);
		Assert.assertNotNull(g1);
	}

	@Test
	public void userMutuality() {
		User user = new User();
		user.setDisbled(true);
		user.setName("admin");
		user.setPassword("admin");

		UserProfile u = new UserProfile();
		u.setUser(user);
		u.setEmail("test@apusic.com");
		u.setMemberType(MemberType.PERSONAL);
		u.setUser(user);

		Contact c = new Contact();
		c.setAddress("test");
		c.setUser(user);

		DeliveryAddr d = new DeliveryAddr();
		d.setIsDefault(1);
		d.setArea("南市区");
		d.setCity("深圳市");
		d.setConsignee("张三");
		d.setMobile("13714332174");
		d.setPhone("0755-89868958");
		d.setPostcode("518000");
		d.setProvince("广东省");
		d.setStreetAddr("南山科技园");
		d.setUser(user);

		session.beginTransaction();
		session.save(user);
		session.save(u);
		session.save(c);
		session.save(d);

		session.getTransaction().commit();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		DeliveryAddr d1 = (DeliveryAddr) session.get(DeliveryAddr.class, 1);
		Assert.assertEquals("张三", d1.getConsignee());
	}

	@Test
	public void retrievePw() {
		RetrievePW p = new RetrievePW();
		p.setEmail("email@apusic.com");
		p.setName("admin");
		p.setResetCode("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
		p.setSecurityCode("123456");
		p.setTimeout(new Date());

		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
	}

	@Test
	public void testNews() {
		News news = new News();
		news.setContent("conent");
		news.setCreateTime(new Date());
		news.setName("name");

		session.beginTransaction();
		session.save(news);
		session.getTransaction().commit();

		Assert.assertNotSame(0, news.getId());
	}

	@Test
	public void testAdAndPosition() {
		AdvertisementPosition p = new AdvertisementPosition();
		p.setDesc("desc");
		p.setLimit(5);
		p.setPosition("main_id");

		Advertisement a = new Advertisement();
		a.setImagePath("c:/root");
		a.setName("test");
		a.setPosition(p);
		a.setSerialNumber(1);
		a.setUrl("http://www.sina.com.cn");

		Advertisement b = new Advertisement();
		b.setImagePath("c:/root");
		b.setName("test");
		b.setPosition(p);
		b.setSerialNumber(1);
		b.setUrl("http://www.sina.com.cn");

		session.beginTransaction();
		session.save(p);
		session.save(a);
		session.save(b);
		p.addAdvertisement(a);
		p.addAdvertisement(b);
		session.getTransaction().commit();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		AdvertisementPosition position = (AdvertisementPosition) session.get(
				AdvertisementPosition.class, 1);
		Assert.assertEquals(2, position.getAdvertisements().size());
	}

	@Test
	public void testBrandAndCategory() {
		Brand b1 = new Brand();
		b1.setLogoPath("c:/root");
		b1.setName("brand1");
		b1.setState(StateType.DISABLED);
		b1.setUrl("www.sina.com.cn");

		Brand b2 = new Brand();
		b2.setLogoPath("c:/root");
		b2.setName("brand1");
		b2.setState(StateType.DISABLED);
		b2.setUrl("www.sina.com.cn");

		ProductCategory c1 = new ProductCategory();
		c1.setKey("key");
		c1.setName("name");
		// c1.setParent(c1);
		c1.setSerialNumber(1);
		c1.setState(StateType.DISABLED);
		c1.setLevel(1);

		ProductCategory c2 = new ProductCategory();
		c2.setKey("key");
		c2.setName("name");
		c2.setParent(c1);
		c2.setSerialNumber(1);
		c2.setState(StateType.DISABLED);
		c2.setLevel(2);
		Set<ProductCategory> childrens = new HashSet<ProductCategory>();
		childrens.add(c2);
		c1.setChildrens(childrens);
		session.beginTransaction();
		session.save(b1);
		session.save(b2);
		session.save(c1);
		session.save(c2);
		b1.addCategory(c2);
		b1.addCategory(c1);
		session.getTransaction().commit();

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Brand b = (Brand) session.get(Brand.class, 1);
		Assert.assertEquals(2, b.getCategorys().size());
	}

	@Test
	public void testCategoryAttr() {
		ProductCategory c1 = new ProductCategory();
		c1.setKey("key");
		c1.setName("name");
		c1.setParent(c1);
		c1.setSerialNumber(1);
		c1.setState(StateType.DISABLED);

		CategoryAttr attr = new CategoryAttr();
		attr.setCategory(c1);
		attr.setKey("网络");
		attr.setName("网络");
		attr.setSerialNumber(1);
		attr.setValue("GSM|CDMA联通|3G电信|3G移动|3G双卡双模");

		session.beginTransaction();
		session.save(c1);
		session.save(attr);
		session.getTransaction().commit();

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Assert.assertNotNull(attr.getCategory());
	}

	@Test
	public void testProduct() {

		ProductCategory c1 = new ProductCategory();
		c1.setKey("key");
		c1.setName("name");
		c1.setParent(c1);
		c1.setSerialNumber(1);
		c1.setState(StateType.DISABLED);

		Brand b1 = new Brand();
		b1.setLogoPath("c:/root");
		b1.setName("brand1");
		b1.setState(StateType.DISABLED);
		b1.setUrl("www.sina.com.cn");

		Product p = new Product();
		p.setBrand(b1);
		p.setCategory(c1);
		p.setAttribute("attr");
		p.setCode("code");
		p.setCreateTime(new Date());
		p.setCustomization("customization");
		p.setDesc("desc");
		p.setIntroduction("introduction");
		p.setKeywords("keywords");
		p.setPublishedTime(new Date());
		p.setRetailPrice(100.02);
		p.setState(ProductStateType.WAIT_PUBLISH);
		p.setStock(ProductStockType.IN_STOCK);
		p.setWholesalePrice(100.02);
		p.setWholeSaleThrehold(100);

		User user = new User();
		user.setDisbled(true);
		String timeStr = String.valueOf((new Date()).getTime());
		user.setName("admin"+StringUtils.right(timeStr, 3));
		user.setPassword("admin");
		p.setUser(user);

		ProductRecommend pr = new ProductRecommend();
        Product product = new Product();
        product.setId(1);
        pr.setEndDate(new Date());
        pr.setProduct(product);
        pr.setSerialNumber(1);
        pr.setStartDate(new Date());
        pr.setType(RecommendType.HOT);
		
		session.beginTransaction();
		session.save(user);
		session.save(b1);
		session.save(c1);
		session.save(p);
		session.save(pr);
		session.getTransaction().commit();
	}

	/**
	 *
	 */
	@Test
	public void favorite() {

		Favorite favorite = new Favorite();
		favorite.setCreateTime(new Date());
		favorite.setProductId(5);
		favorite.setProductImg("aaa");
		favorite.setProductName("衣服");
		favorite.setRetailPrice(100.00);
		favorite.setWholesalePrice(50.00);
		favorite.setMemberId(4);
		favorite.setSaleType(SaleType.RETAIL);
		session.beginTransaction();
		session.save(favorite);
		session.getTransaction().commit();
	}

	@Test
	public void productTempate() {

		ProductTemplate p = new ProductTemplate();
		p.setBrandId(1);
		p.setCategoryId(1);
		p.setAttribute("attr");
		p.setCode("code");
		p.setCreateTime(new Date());
		p.setCustomization("customization");
		p.setDesc("desc");
		p.setIntroduction("introduction");
		p.setKeywords("keywords");
		p.setRetailPrice(100.02);
		p.setStock(ProductStockType.IN_STOCK);
		p.setWholesalePrice(100.02);
		p.setWholeSaleThrehold(100);
		p.setSaleType(SaleType.WHOLESALE);
		p.setCreditRaito(0.1f);

		User user = new User();
		user.setDisbled(true);
		user.setName("admintempate");
		user.setPassword("admin");
		p.setUser(user);
	}

	@Test
	public void createOrder() {
		testProduct();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Product product = (Product) session.get(Product.class, 1);
		Order order = new Order();
		OrderItem item = new OrderItem();
		order.setCreateTime(new Date());
		order.setCreator("admin");
		order.setNumber("00001");
		order.setSeller("a");
		order.setState(OrderState.AWAITING_PAYMENT);
		order.setSaleType(SaleType.RETAIL);


		item.setOrder(order);
		item.setPrice(100D);
		item.setProduct(product);
		item.setQuantity(4);
		item.setRemark("aaaaa");
		item.setOrder(order);

		order.addItem(item);

		TotalPriceStrategy stategy = new TotalPriceStrategy() {

			public double getTotalPrice(Order order) {
				Set<OrderItem> items =  order.getOrderItems();
				double totalPrice = 0;
				if (items != null && items.size() >0){
					for (OrderItem item : items){
						totalPrice += item.getPrice()* item.getQuantity();
					}
				}
				return totalPrice * 0.9;
			}
		};

		order.setTotalPriceStrategy(stategy);

		PaymentInfo pi = new PaymentInfo();
		pi.setAmount(500.00D);
		pi.setOrder(order);
		pi.setPayMode(PayMode.ONLINE);
		pi.setPayType(PayType.ALIPAY);
		order.setPaymentInfo(pi);

		OrderInvoice orderInvoice = new OrderInvoice();
		orderInvoice.setContent(InvoiceContentType.DETAIL);
		orderInvoice.setHead("深圳市金蝶中间件有限公司");
		orderInvoice.setOrder(order);
		orderInvoice.setType(InvoiceType.COMPANY);
		order.setOrderInvoice(orderInvoice);

		DeliveryInfo deliveryInfo = new DeliveryInfo();
		deliveryInfo.setAddress("深圳市南山区科技园南路十二号");
		deliveryInfo.setCost(5.00D);
		deliveryInfo.setMobile("13510751040");
		deliveryInfo.setMode(DeliveryMode.EMS);
		deliveryInfo.setOrder(order);
		deliveryInfo.setPhone("88888888");
		deliveryInfo.setPostcode("518000");
		deliveryInfo.setReceiver("顾国庆");
		deliveryInfo.setTimeSlot(DeliveryTimeSlot.HOLIDAY);
		order.setDeliveryInfo(deliveryInfo);

		session.save(order);
		session.getTransaction().commit();

		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Order orderNew = (Order)session.get(Order.class, 1);

		Assert.assertTrue(orderNew.getOrderItems().size() > 0);
		Assert.assertEquals(360d , (double) orderNew.getTotalPrice(), 0.0d);

		Set<OrderItem> items = orderNew.getOrderItems();
		for(OrderItem itemsItem : items){
			Assert.assertTrue(itemsItem.getProduct() != null);
		}
		Assert.assertTrue(orderNew.getSaleType().equals(SaleType.RETAIL));
		Assert.assertTrue(PayMode.ONLINE.equals(orderNew.getPaymentInfo().getPayMode()));
	}

	@Test
	public void creteInvoice(){
	    Invoice invoice = new Invoice();
	    invoice.setContent(InvoiceContentType.DETAIL);
	    invoice.setHead("金蝶中间件有限公司");
	    invoice.setType(InvoiceType.COMPANY);
	    invoice.setUserName("admin");
	    session.beginTransaction();
	    session.save(invoice);
	    session.getTransaction().commit();

	}

	@Test
	public void createPayAndDelivery(){
	    PayAndDelivery pd = new PayAndDelivery();
	    pd.setDeliveryTime(DeliveryTimeSlot.HOLIDAY);
	    pd.setDeliveryMode(DeliveryMode.MD_EXPRESS);
	    pd.setPayMode(PayMode.OFFLINE);
	    pd.setUserName("admin");
	    session.beginTransaction();
	    session.save(pd);
	    session.getTransaction().commit();
	}

	@Test
	public void createOrderPriceChange(){
	    OrderPriceChange opc = new OrderPriceChange();
	    opc.setAction("修改价格");
	    opc.setCreateTime(new Date());
	    opc.setCurrentPrice(500.00);
	    opc.setOperator("guguoqing");
	    opc.setOrderItemId(1);
	    opc.setOriginalPrice(560.00);
	    session.beginTransaction();
        session.save(opc);
        session.getTransaction().commit();
	}
	
	@Test
	public void helpCenterNavigation(){
		HelpCenterNavigation hcnP = new HelpCenterNavigation();
		hcnP.setLevel(1);
		hcnP.setName("帮助中心");
		hcnP.setSerialNumber(1);

		HelpCenterNavigation hcnc = new HelpCenterNavigation();
		hcnc.setLevel(2);
		hcnc.setName("配送方式");
		hcnc.setParent(hcnP);
		
		Set<HelpCenterNavigation> hset = new HashSet<HelpCenterNavigation>();
		hset.add(hcnc);
		hcnP.setChildrens(hset);
		session.beginTransaction();
        session.save(hcnP);
        session.getTransaction().commit();
	}
	
	@Test
	public void helpCenterContent(){
	    HelpCenterContent hcc = new HelpCenterContent();
	    hcc.setTitleId(1);
	    hcc.setContent("testestsets");
	    session.beginTransaction();
        session.save(hcc);
        session.getTransaction().commit();
	}
	
	@After
	public void teardown() {
		if (session != null) {
			try {
				session.close();
			} catch (Exception e) {
			}
		}
	}
}
