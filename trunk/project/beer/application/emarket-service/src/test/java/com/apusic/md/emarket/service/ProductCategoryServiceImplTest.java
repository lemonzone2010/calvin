package com.apusic.md.emarket.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.user.User;
import com.apusic.md.model.emarket.CategoryAttr;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.ProductImage;
import com.apusic.md.model.emarket.ProductStateType;
import com.apusic.md.model.emarket.ProductStockType;
import com.apusic.md.model.emarket.StateType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-datamanagement-service.xml",
		"classpath*:apusic-md-emarket-service.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-md-emarket-service-test.xml"})
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CrudService crudService;

    @Autowired
    private QueryService queryService;

    @Before
    public void init() {
        ProductCategory pc1 = new ProductCategory();
        ProductCategory pc2 = new ProductCategory();
        ProductCategory pc3 = new ProductCategory();
        ProductCategory pc4 = new ProductCategory();
        pc1.setKey("家电");
        pc1.setLevel(1);
        pc1.setName("家电");
        pc1.setSerialNumber(1);
        pc1.setState(StateType.ENABLED);

        pc2.setKey("电视");
        pc2.setLevel(2);
        pc2.setName("电视");
        pc2.setParent(pc1);
        pc2.setSerialNumber(1);
        pc2.setState(StateType.ENABLED);

        pc3.setKey("长虹");
        pc3.setLevel(3);
        pc3.setName("长虹");
        pc3.setParent(pc2);
        pc3.setSerialNumber(1);
        pc3.setState(StateType.ENABLED);

        pc4.setKey("创维");
        pc4.setLevel(3);
        pc4.setName("创维");
        pc4.setParent(pc2);
        pc4.setSerialNumber(1);
        pc4.setState(StateType.ENABLED);
        
        CategoryAttr attr = new CategoryAttr();
        attr.setKey("asd");
        attr.setName("aadf");
        attr.setSerialNumber(11);
        attr.setValue("asdfas");
        attr.setCategory(pc4);

        Set<CategoryAttr> attrs = new HashSet<CategoryAttr>();
        attrs.add(attr);
        pc4.setCategoryAttrs(attrs);
        
        Set<ProductCategory> set1 = new HashSet<ProductCategory>();
        Set<ProductCategory> set2 = new HashSet<ProductCategory>();
        set1.add(pc2);
        set2.add(pc3);
        set2.add(pc4);
        pc1.setChildrens(set1);
        pc2.setChildrens(set2);

        productCategoryService.addCategory(pc1);
    }

    @Test
    public void findAll() {
        List<ProductCategory> pcs = productCategoryService.findAll();
        assertTrue(pcs.size() > 0);
    }

    @Test
    public void findByExample(){
        ProductCategory example = new ProductCategory();
        example.setLevel(3);
        example.setState(StateType.ENABLED);
        List<ProductCategory> categories = productCategoryService.findByExample(example);
        assertTrue(categories.size() > 0);
    }

    @Test
    @Rollback(true)
    public void updateState(){
        ProductCategory category =  this.findEntity("创维");
        productCategoryService.updateState(category.getId(),StateType.DISABLED);
        assertTrue(crudService.retrieve(ProductCategory.class, category.getId()).getState().equals(StateType.DISABLED));
    }

    @Test
    public void findById(){
        ProductCategory category = this.findEntity("创维");
        assertTrue(productCategoryService.findById(category.getId()) != null);
    }

    @Test
    public void findByLevel(){
      //  ProductCategory category = this.findEntity();
        assertTrue(productCategoryService.findByLevel(2) != null);
    }

    @Test
    @Rollback(false)
    public void updateCategory(){
        ProductCategory category = this.findEntity("创维");
        category.setState(StateType.DISABLED);
        productCategoryService.updateCategory(category);
        assertTrue(this.findEntity("创维").getState().equals(StateType.DISABLED));
    }

    @Test
    public void deleteById(){
        ProductCategory category = this.findEntity("家电");
        productCategoryService.deleteById(category.getId());
        assertTrue(crudService.retrieve(ProductCategory.class, category.getId()) == null);
    }

    @Test
    public void addCategory(){
        ProductCategory pc1 = new ProductCategory();
        pc1.setKey("服装");
        pc1.setLevel(1);
        pc1.setName("服装");
        pc1.setSerialNumber(1);
        pc1.setState(StateType.ENABLED);
        productCategoryService.addCategory(pc1);
        ProductCategory example = new ProductCategory();
        example.setName("服装");
        assertTrue(queryService.findByExample(example).size() > 0);
    }

    @Test
    public void saveCategoryAttr(){
        ProductCategory category = findEntity("创维");
        Set<CategoryAttr> attrs = new HashSet<CategoryAttr>();
        CategoryAttr attr = new CategoryAttr();
        attr.setCategory(category);
        attr.setName("网络");
        attr.setValue("GSM|CDMA");
        attrs.add(attr);
        productCategoryService.saveCategoryAttr(category.getId(),attrs);
        ProductCategory categoryNew = crudService.retrieve(ProductCategory.class, category.getId());
        assertTrue(categoryNew.getCategoryAttrs().size() > 0);

    }

    @Test
    public void updateSerialNumber(){
        List<ProductCategory> categories = productCategoryService.findByLevel(3);
        int[] changeIds = new int[categories.size()];
        int[] changeNumbers = new int[categories.size()];
        for(int i=0;i<categories.size();i++){
            changeIds[i] = categories.get(i).getId();
            changeNumbers[i] = i;
        }
        productCategoryService.updateSerialNumber(changeIds,changeNumbers);
        List<ProductCategory> categoriesNew = productCategoryService.findByLevel(3);
        assertTrue(categoriesNew.get(0).getSerialNumber() == 0 && categoriesNew.get(1).getSerialNumber() == 1);

    }

    @Test
    public void isUsedForProduct() throws IOException{
        boolean isUsed = productCategoryService.isUsedProduct(addProduct());
        assertTrue(isUsed);
    }

    @Test
    public void findCategoryFullName(){
        ProductCategory category = productCategoryService.findByLevel(3).get(0);
        String fullName = productCategoryService.findCategoryFullName(category.getId());
        assertTrue(fullName.indexOf(">") > 0);
    }

    private ProductCategory findEntity(String name){
        ProductCategory example = new ProductCategory();
        example.setName(name);
        List<ProductCategory> categories = queryService.findByExample(example);
        if(categories != null && categories.size() > 0){
            return categories.get(0);
        }
        return null;
    }


    private int addProduct() throws IOException{
        Product p = new Product();
        p.setAttribute("网络:移动3G");
        p.setCode("code");
        p.setCreateTime(new Date());
        p.setCustomization("颜色:red|black");
        p.setDesc("好手机");
        p.setIntroduction("introduction");
        p.setKeywords("三星,手机");
        p.setPublishedTime(new Date());
        p.setRetailPrice(1000);
        p.setState(ProductStateType.WAIT_PUBLISH);
        p.setStock(ProductStockType.IN_STOCK);
        p.setWholesalePrice(800);
        p.setWholeSaleThrehold(100);

        ProductImage pi = new ProductImage();
        pi.setImagePath("c:/root/iamge.gif");
        p.addImage(pi);

        User user = new User();
        user.setDisbled(true);
        user.setName("test");
        user.setPassword("admin");
        this.crudService.create(user);

        p.setUser(user);
        ProductCategory category =  findEntity("创维");
        p.setCategory(category);
        this.crudService.create(p);
        ProductCategory categoryParent =  findEntity("家电");
        return categoryParent.getId();
    }
}
