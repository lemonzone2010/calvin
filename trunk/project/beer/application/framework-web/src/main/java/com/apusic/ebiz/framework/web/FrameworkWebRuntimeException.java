package com.apusic.ebiz.framework.web;

import org.springframework.core.NestedRuntimeException;

public class FrameworkWebRuntimeException extends NestedRuntimeException{
    public FrameworkWebRuntimeException(String msg) {
        super(msg);
    }

    public FrameworkWebRuntimeException(String msg, Exception e) {
        super(msg, e);
    }
}
