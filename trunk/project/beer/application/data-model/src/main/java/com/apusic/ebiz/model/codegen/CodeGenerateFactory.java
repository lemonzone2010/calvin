package com.apusic.ebiz.model.codegen;

public class CodeGenerateFactory {
	private static CodeGenerator mvcGenerator = new CodeGeneratorImpl();
	private static CodeGenerator modainModelGenerator = new DomainModelGeneratorImpl();

	/**
	 * 对已定义@FieldView注解的实体进行代码生成
	 * 
	 * @param clazzs
	 */
	public static void generateMVC(Class<?>... clazzs) {
		String folder="codegen/";
		for (Class<?> clazz : clazzs) {
			mvcGenerator.process(clazz, folder+"{0}Manager.jsp.vm", folder+"{0}Controller.java.vm", folder+"{0}.properties.vm");
			// TODO 增加 生成领域的get，领域的构造方法,以及DTO,Assembler
		}
	}

	public static void generateDomainModel(Class<?>... clazzs) {
		for (Class<?> clazz : clazzs) {
			modainModelGenerator.process(clazz, "domain.model.vm");
		}
	}
}
