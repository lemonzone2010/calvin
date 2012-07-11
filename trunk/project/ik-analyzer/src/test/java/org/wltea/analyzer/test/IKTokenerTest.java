/**
 * 
 */
package org.wltea.analyzer.test;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.wltea.analyzer.lucene.IKTokenizer;

import junit.framework.TestCase;

/**
 * @author 林良益
 *
 */
public class IKTokenerTest extends TestCase {
	
	public void testLucene3Tokenizer(){
		String t = "IK分词器Lucene Analyzer接口实现类 民生银行";
		t="夏勇是一个好人啊阿黛拉的非凡冒险你是猪,也是中，国，是35W系列\r\n" + 
				"50W系列凌钢\r\n" + 
				"aΦcd新抚钢真的SPCCdefes abc123 ";
		//t="我中华人民共和国人呢";
		String result=printTokener(t);
		System.out.println(result);
		
	}

	private String printTokener(String t) {
		String ret="";
		IKTokenizer tokenizer = new IKTokenizer(new StringReader(t) , false);
		try {
			while(tokenizer.incrementToken()){
				TermAttribute termAtt = tokenizer.getAttribute(TermAttribute.class);
				System.out.println("得到结果："+termAtt.term());
				ret+=(termAtt.term()+" | ");				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	

}
