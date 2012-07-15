package com.apusic.md.emarket.service;

import java.io.IOException;

public interface HtmlGenerator {

	void generate(Integer id) throws IOException;

	void remove(String id);

    /**
     *
     * @param obj 预览实体
     * @param previewName 预览名
     * @return
     * @throws IOException
     */
    String preview(Object obj,String previewName) throws IOException;
}
