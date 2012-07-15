package com.apusic.md.emarket.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.md.model.emarket.ProductCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-md-emarket-service.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-emarket-service-test.xml" })
public class ProductFullTextQueryServiceTest {

/*	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private CrudService crudService;
*/
	@Autowired
	private ProductFullTextQueryService fullQueryService;

	//private boolean first = false;

/*	@Before
	public void init() {
		if(first){
			return;
		}
		ProductCategory pc1 = new ProductCategory();
		ProductCategory pc2 = new ProductCategory();
		ProductCategory pc3 = new ProductCategory();
		ProductCategory pc4 = new ProductCategory();
		pc1.setKey("通讯");
		pc1.setLevel(2);
		pc1.setName("通讯");
		pc1.setSerialNumber(1);
		pc1.setState(StateType.ENABLE);

		pc2.setKey("手机");
		pc2.setLevel(3);
		pc2.setName("手机");
		pc2.setParent(pc1);
		pc2.setSerialNumber(1);
		pc2.setState(StateType.ENABLE);

		pc3.setKey("对讲机");
		pc3.setLevel(3);
		pc3.setName("对讲机");
		pc3.setParent(pc2);
		pc3.setSerialNumber(1);
		pc3.setState(StateType.ENABLE);

		pc4.setKey("选号入网");
		pc4.setLevel(3);
		pc4.setName("选号入网");
		pc4.setParent(pc2);
		pc4.setSerialNumber(1);
		pc4.setState(StateType.ENABLE);

		Set<ProductCategory> set1 = new HashSet<ProductCategory>();
		set1.add(pc2);
		set1.add(pc3);
		set1.add(pc4);
		pc1.setChildrens(set1);
		crudService.create(pc1);
		first = true;
	}*/

	@Test
	public void fullTextQueryCategorys(){
		List<ProductCategory> categorys = this.fullQueryService.fullTextQueryCategorys("对讲机");
		if(categorys!=null && categorys.size() > 0){
			for(ProductCategory p : categorys){
				if(p.getLevel()==3){
					ProductCategory parent = p.getParent();
				}
			}
		}
	}
}
