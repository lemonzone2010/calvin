package com.apusic.md.emarket.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.HelpCenterNavigation;
import com.apusic.md.model.emarket.News;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.ProductImage;
import com.apusic.md.model.emarket.ProductRecommend;

public class HomepageHtmlGenerator extends AbstractHtmlGenerator {

	public static final String HOME_PAGE_NAME = "index";

	public static final int HOT_COUNT = 10;
	public static final int RECOMMEND_COUNT = 10;
	public static final int NEW_COUNT = 12;
	
	public static final int BRAND_NUM = 21;
	public static final int BRAND_CATE_TOP = 14;
	 

	@Autowired
	private ProductImageService productImageService;


	@Autowired
	private NewsService newsService;

	@Autowired
	private ProductCategoryService categoryService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductRecommendService productRecommendService;

	@Autowired
	private AdvertisementManagementService advertisementManagementService;
	
	@Autowired
	private HelpCenterNavigationService helpCenterNavigationService;

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;
	public void generate(Integer id) throws IOException {
		String content = this.getTemplateWrapper().mergeIntoString(createContext());
		write(null, HOME_PAGE_NAME + ".html", content);
	}

	public String preview(Object obj, String previewName) throws IOException {
		return null;
	}

	public void remove(String id) {
	}

	private Map<String, Object> createContext() {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("StringUtil", new HomepageHtmlGenerator());

		//公告
		List<News> topNews = newsService.getTopNews();
		context.put("topNews", topNews);
		//类别
		List<ProductCategory> categorys = categoryService.findByLevel(2);
		context.put("categorys", categorys);
		//brand
		List<Brand> brands = brandService.getHomepageBrand(0, BRAND_NUM);
		context.put("brands", brands);
		
		//类别下的品牌
		Map<Integer, List<Brand>> brandMap = new HashMap<Integer, List<Brand>>();
		for (ProductCategory c : categorys) {
			brandMap.put(c.getId(), brandService.findBrandsByLevel2CategoryId(c.getId(),BRAND_CATE_TOP));
		}
		context.put("brandMap", brandMap);
		
		
		//特价 新品 畅销
		List<ProductRecommend> hotProducts=productRecommendService.getTopHotProducts(HOT_COUNT);
		List<ProductRecommend> newProducts=productRecommendService.getTopNewProducts(NEW_COUNT);
		List<ProductRecommend> recommendProducts=productRecommendService.getTopRecommendProducts(RECOMMEND_COUNT);
		context.put("hotProducts", hotProducts);
		context.put("recommendProducts", recommendProducts);
		context.put("newProducts", newProducts);


		//商品图片
		Map<Integer, String> imageMap=new HashMap<Integer, String>();
		putImagePaths(recommendProducts, imageMap);
		putImagePaths(newProducts, imageMap);
		putImagePaths(hotProducts, imageMap);
		context.put("imageMap", imageMap);

		//广告
		context.put("homepageMainAd",advertisementManagementService.findAdsByPosition("homepageMainAd") );
		context.put("homepageRightAd",advertisementManagementService.findFirstAdByPosition("homepageRightAd") );
		context.put("homepageRecommendAd",advertisementManagementService.findFirstAdByPosition("homepageRecommendAd") );
		context.put("homepageNewProductAd",advertisementManagementService.findFirstAdByPosition("homepageNewProductAd") );
		context.put("homepageHotAd",advertisementManagementService.findFirstAdByPosition("homepageHotAd") );
		
		//页脚
		List<HelpCenterNavigation> hcns = helpCenterNavigationService.findByLevel(1);
		HelpCenterNavigation hFoot = null;
		for(HelpCenterNavigation hcn : hcns){
			if("页脚导航".equals(hcn.getName())){
				context.put("pageFoot", new ArrayList<HelpCenterNavigation>(hcn.getChildrens()));
				hFoot = hcn;
			} 
		}
		hcns.remove(hFoot);
		context.put("helpCenter", hcns);
		fillConfiguraData(context);
		return context;
	}



	private void putImagePaths(List<ProductRecommend> recommendProducts,
			Map<Integer, String> imageMap) {
		for (ProductRecommend r : recommendProducts) {
			ProductImage image=productImageService.getOneProductImageByProductId(r.getProduct().getId());
			if (image==null) {
				continue;
			}
			String  suffix = configService.getValueByKey("small.img.suffix");
			imageMap.put(r.getProduct().getId(), image.getImagePath()+suffix);
		}
	}

    /**
     * <li>方法描述：截断字符串，超过指定长度用...代替</li>
     *
     */
    public static String splitString(final String s, final int len) throws UnsupportedEncodingException {
        return splitString(s, len, "...");
    }

    public static String splitString(final String s, final int len, final String endStr)
            throws UnsupportedEncodingException {
        if (StringUtils.isBlank(s))
            return "-";
        int charLen = 0;
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < s.length(); i++) {
            if (s.substring(i, i + 1).getBytes("UTF-8").length == 3) {
                // 中文
                charLen += 2;
            } else if (s.substring(i, i + 1).getBytes("UTF-8").length == 1) {
                // 英文
                charLen += 1;
            }
            sb.append(s.substring(i, i + 1));
            if (charLen >= len && i < s.length() - 1) {
                sb.append(endStr);
                break;
            }
        }
        return sb.toString();
    }

	public static String encode(String param){
    	try {
			return URLEncoder.encode(URLEncoder.encode(param,"UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
    }
}
