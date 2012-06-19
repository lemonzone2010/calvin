package com.apusic.ebiz.model.navigation;

import com.apusic.ebiz.model.BaseModel;

public class ApplicationInfo extends BaseModel implements Comparable<ApplicationInfo>{
    private static final long serialVersionUID = 2040061905266760072L;

    
    private String status;
    
    private String applicationRoot;
    
    private String applicationName;
    
    private Integer sequence;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplicationRoot() {
        return applicationRoot;
    }

    public void setApplicationRoot(String applicationRoot) {
        this.applicationRoot = applicationRoot;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public int compareTo(ApplicationInfo o) {
        return 0;
    }
    
}
