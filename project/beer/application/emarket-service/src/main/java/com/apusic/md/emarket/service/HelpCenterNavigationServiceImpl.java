package com.apusic.md.emarket.service;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.emarket.dao.HelpCenterNavigationDao;
import com.apusic.md.model.emarket.HelpCenterContent;
import com.apusic.md.model.emarket.HelpCenterNavigation;

@Service("emarket_HelpCenterNavigationService")
public class HelpCenterNavigationServiceImpl implements
		HelpCenterNavigationService { 

	@Autowired
	private CrudService crudService;

	@Autowired
	private QueryService queryService; 

	@Autowired
	private HelpCenterNavigationDao helpCenterNavigationDao;

	@Transactional
	public void create(HelpCenterNavigation hng) {
		crudService.create(hng);
	}

	@Transactional
	public void deleteById(int id) {
		crudService.delete(HelpCenterNavigation.class, id);

	}

	@Transactional(readOnly = true)
	public List<HelpCenterNavigation> findByExample(HelpCenterNavigation hng) {
		return helpCenterNavigationDao.findByExample(hng);
	}

	@Transactional
	public void updateEntity(HelpCenterNavigation hng) {
		crudService.update(hng);
	}

	@Transactional
	public void updateSerialNumber(int[] ids, int[] numbers) {
		if (ArrayUtils.isEmpty(ids) || ArrayUtils.isEmpty(numbers)) {
			return;
		}
		List<HelpCenterNavigation> hcns = helpCenterNavigationDao
				.findByIds(ids);
		for (int i = 0; i < ids.length; i++) {
			for (HelpCenterNavigation hcn : hcns) {
				if (hcn.getId() == ids[i]) {
					hcn.setSerialNumber(numbers[i]);
				}
			}
		}
	}

	@Transactional(readOnly = true)
	public List<HelpCenterNavigation> findAll() {
		return queryService.findAll(HelpCenterNavigation.class);
	}

	@Transactional(readOnly = true)
	public HelpCenterNavigation findById(int id) {
		return crudService.retrieve(HelpCenterNavigation.class, id);
	}

	@Transactional(readOnly = true)
	public List<HelpCenterNavigation> findByLevel(int level) {
		HelpCenterNavigation ex = new HelpCenterNavigation();
		ex.setLevel(level);
		return this.findByExample(ex);
	}

	@Transactional(readOnly = true)
    public HelpCenterContent findContentByTitleId(int id) {
        HelpCenterContent hcc = new HelpCenterContent();
        hcc.setTitleId(id);
        List<HelpCenterContent> hccs = queryService.findByExample(hcc);
        if(hccs != null && hccs.size() > 0) {
            return hccs.get(0);
        }
        return null;
    }

    @Transactional
    public HelpCenterContent createContent(HelpCenterContent hcc) {
        return crudService.create(hcc);
    }
    
    @Transactional
    public void updateContent(HelpCenterContent hcc) {
        crudService.update(hcc);
    }

}
