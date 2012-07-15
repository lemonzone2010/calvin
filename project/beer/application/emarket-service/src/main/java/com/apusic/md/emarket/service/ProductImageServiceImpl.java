package com.apusic.md.emarket.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.emarket.dao.ProductImageDao;
import com.apusic.md.model.emarket.ProductImage;

@Service("emarket_ProductImageService")
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private ProductImageDao productImageDao;
	

    @Autowired
    @Qualifier("ebiz_ConfigService")
    private ConfigService configService;
    
    @Autowired
    private QueryService queryService;
    
    @Autowired
    private CrudService crudService;

	@Transactional(readOnly=true)
	public ProductImage getOneProductImageByProductId(Integer id) {
		return this.productImageDao.getOneProductImageByProductId(id);
	}

	@Transactional(readOnly=true)
	public List<ProductImage> getProductImagesByProductId(Integer id) {
		return this.productImageDao.getProductImagesByProductId(id);
	}
	
	@Transactional
    public void deleteDataBasePictureFile(ProductImage productImage){
	    this.crudService.delete(productImage);
    }
	
	@Transactional
    public void deletePictureFile(String parameterPictureName){
	    parameterPictureName = parameterPictureName.substring(parameterPictureName.indexOf("/",1));
        String localProjectName=  configService.getValueByKey("velocity.html.basepath")+parameterPictureName;
        FileUtils.deleteQuietly(new File(localProjectName));
    }
}
