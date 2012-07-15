package com.apusic.md.emarket.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.apusic.md.model.emarket.HelpCenterContent;
import com.apusic.md.model.emarket.HelpCenterNavigation;

public class HelpPageHtmlGenerator extends AbstractHtmlGenerator {

    public static final String HOME_PAGE_NAME = "help";
    
    @Autowired
    private HelpCenterNavigationService helpCenterNavigationService;
    
    public void generate(Integer id) throws IOException {
        String content = this.getTemplateWrapper().mergeIntoString(createContext(id));
        write(null, HOME_PAGE_NAME + id + ".html", content);
    }

    public String preview(Object obj, String previewName) throws IOException {
        return null;
    }

    public void remove(String id) {

    }
    
    private Map<String, Object> createContext(Integer id) {
        Map<String, Object> context = new HashMap<String, Object>();
        
        //页脚
        List<HelpCenterNavigation> hcns = helpCenterNavigationService.findByLevel(1);
        //页面类容
        HelpCenterContent hcc = helpCenterNavigationService.findContentByTitleId(id);
        if(hcc == null) {
            hcc = new HelpCenterContent();
        }
        HelpCenterNavigation hFoot = null;
        HelpCenterNavigation currentHcn = helpCenterNavigationService.findById(id);
        for(HelpCenterNavigation hcn : hcns){
            if("页脚导航".equals(hcn.getName())){
                context.put("pageFoot", new ArrayList<HelpCenterNavigation>(hcn.getChildrens()));
                hFoot = hcn;
            } 
        }
        hcns.remove(hFoot);
        context.put("helpCenter", hcns);
        context.put("hcontent", hcc);
        context.put("currentHcn", currentHcn);
        fillConfiguraData(context);
        return context;
    }

}
