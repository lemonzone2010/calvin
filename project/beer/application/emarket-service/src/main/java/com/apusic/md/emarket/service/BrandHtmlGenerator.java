package com.apusic.md.emarket.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.ProductCategory;

public class BrandHtmlGenerator extends AbstractHtmlGenerator {
    private String htmlName = "brandlibrary.html";
    private String htmlDir = "brand";

    public static final int CATEGORY_LEVEL=2;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductCategoryService categoryService;
    @Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

    @Transactional(rollbackFor = { Exception.class })
    public void generate(Integer id) throws IOException {

        String content = this.getTemplateWrapper().mergeIntoString(
                createContext());
        write(htmlDir, htmlName, content);
    }

    @Transactional(rollbackFor = { Exception.class })
    public void remove(String id) {
        String fileName = htmlDir+"/"+htmlName;
        this.removeFile(fileName);
    }

    private Map<String, Object> createContext() {
        Map<String, Object> context = new HashMap<String, Object>();
        List<Brand> brands = brandService.findEnabled();
        String name = configService.getValueByKey("otherBrandName");
		for (Brand brand : brands) {
			if (StringUtils.equals(brand.getName(), name)) {
				brands.remove(brand);
				break;
			}
		}
        context.put("brands", brands);
        List<ProductCategory> categories =categoryService.findByLevel(CATEGORY_LEVEL);
        context.put("categories", categories);
        fillConfiguraData(context);
        return context;
    }

    public String preview(Object obj, String previewName) throws IOException {
        return null;
    }


}
