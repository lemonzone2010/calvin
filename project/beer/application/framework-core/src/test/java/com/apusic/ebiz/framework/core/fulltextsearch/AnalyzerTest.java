package com.apusic.ebiz.framework.core.fulltextsearch;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class AnalyzerTest {
	 static String  enText = "The PGP signatures can be verified using PGP or GPG. ";  
	 static String  chText = "女凉鞋世界发达国家12-23cm居乐亿多茶杯民aaa36krbbb风花雪月肖君诺消费1000度的电能的费用占全国月平均IK扩展陆小凤分词测试工资的6.79%";  
	 static String  chText2 = "牛皮男鞋";  
		
	 @Test
	public void  IKtest() throws Exception{
		
	    	Analyzer ch1 = new IKAnalyzer(true); 
	    	List<String> words=new ArrayList(); 
	    	words.add("乐亿多茶杯");
	    	words.add("牛皮男鞋");
	    	Dictionary.loadExtendWords(words);
	    	
	    	analyze(chText2, ch1);  
	}
	
	public void analyze(String text, Analyzer analyzer) throws Exception {

		TokenStream ts = analyzer.tokenStream("", new StringReader(text));
		CharTermAttribute termAtt = (CharTermAttribute) ts.getAttribute(CharTermAttribute.class);
		while (ts.incrementToken()) {
			String token = new String(termAtt.buffer(), 0, termAtt.length());
			System.out.print(token+" | ");
		}

	}
	 
	
}
