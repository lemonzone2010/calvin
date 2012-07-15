package org.apache.nutch.crawl.xia;

import org.apache.nutch.crawl.Crawl;
import org.junit.Test;

public class CrawlTest {

	@Test
	public void crawlLocal() throws Exception{
		String args[]=new String[]{"Crawl","myconfig/urls","-depth","2","-topN","300","-threads","10"};
		Crawl.crawl(args);
	}
}
