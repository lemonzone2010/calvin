package com.apusic.ebiz.model.codegen;

public interface CodeGenerator {

	void process(Class<?> clazz,String... templateNames);
}
