package com.apusic.md.model.emarket;

import com.apusic.ebiz.model.BaseModel;

public class HelpCenterContent extends BaseModel{
    
    private int titleId;
    
    private String content;


    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
