package com.apusic.md.emarket.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.md.emarket.util.Attribute;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductImage;
import com.apusic.md.model.emarket.ProductStateType;
import com.apusic.md.model.emarket.ProductStockType;
import com.apusic.md.model.usersphere.UserProfile;
import com.apusic.md.usersphere.service.UserProfileService;

public class ProductHtmlGenerator extends AbstractHtmlGenerator {
	private String tempDir = "temp";
	private String htmlDir = "";
	private String namePrefix="";
	private int folderNum = 100;
	@Autowired
	private CrudService crudService;

	@Autowired
	private UserProfileService userProfileService;

	@Transactional(rollbackFor = { Exception.class })
	public void generate(Integer id) throws IOException {
		Product product = crudService.retrieve(Product.class, id);
		String content = this.getTemplateWrapper().mergeIntoString(
				createContext(product));
		//按id分文件夹放置html文件
		
		write(htmlDir+ getForlder(id), namePrefix+ id.toString() + ".html", content);
	}

	private String getForlder(Integer id) {
		return folderNum != 0 ? "/"+( id % folderNum )+"/":"";
	}

	@Transactional(rollbackFor = { Exception.class })
	public String preview(Object object, String previewName) throws IOException {
		if (object instanceof Product) {
			Product product = (Product) object;
			String content = this.getTemplateWrapper().mergeIntoString(
					createContext(product));
			write(tempDir, namePrefix+previewName + ".html", content);
			return previewName + ".html";
		}
		return null;
	}

	@Transactional(rollbackFor = { Exception.class })
	public void remove(String id) {
		String fileName = htmlDir+getForlder(Integer.parseInt(id))+"/" +namePrefix+id + ".html";
		this.removeFile(fileName);
	}

	private Map<String, Object> createContext(Product product) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("productBean", product);
		List<Attribute> customizations = Attribute.parseAttribute(product
				.getCustomization());
		context.put("customizations", customizations);
		List<Attribute> attributes = Attribute.parseAttribute(product
				.getAttribute());
		context.put("attributes", attributes);

		//下架
		if (ProductStateType.CANCEL_PUBLISH.equals(product.getState())) {
			context.put("isCancel", true);
		}else{
			context.put("isCancel", false);
		}
		//有货缺货
		if (ProductStockType.IN_STOCK.equals(product.getStock())) {
			context.put("inStock", true);
		}else{
			context.put("inStock", false);
		}

		//图片
		Set<ProductImage> imgSet = product.getImages();
		List<ProductImage> images = new ArrayList<ProductImage>();
		images.addAll(imgSet);
		Collections.sort(images);
		context.put("images", images);
		if (images.size() > 0) {
			context.put("mainImage", images.get(0));
		}
		if (product.getUser() != null) {
			UserProfile userProfile = userProfileService.getUserProfileByUserId(product
					.getUser().getId());
			context.put("userProfile", userProfile);
		}

		fillConfiguraData(context);
		return context;
	}

	public String getHtmlDir() {
		return htmlDir;
	}

	public void setHtmlDir(String htmlDir) {
		this.htmlDir = htmlDir;
	}

	public String getNamePrefix() {
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}


}
