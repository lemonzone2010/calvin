package com.apusic.ebiz.navigation;

import com.apusic.ebiz.framework.core.exception.BaseBussinessException;

public class NavigationException extends BaseBussinessException{

    public NavigationException(String code, String description) {
        super(code, description);
    }

}
