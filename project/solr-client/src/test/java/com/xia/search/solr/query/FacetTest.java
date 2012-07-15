package com.xia.search.solr.query;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocument;

public class FacetTest {
	static SolrServer server = new HttpSolrServer("http://localhost:8080/solr-server");

	// http://localhost:8080/solr-server/select?q=DummyBook_title:lucene&facet=on
	// &facet.field=DummyBook_authors.name&facet.range=DummyBook_price&facet.mincount=1&f.DummyBook_price.facet.range.start=0
	// &f.DummyBook_price.facet.range.end=10&f.DummyBook_price.facet.range.gap=10&facet.query=lucene&f.DummyBook_publicationDate.facet.range.start=NOW/YEAR-10YEARS
	// &f.DummyBook_publicationDate.facet.range.end=NOW&f.DummyBook_publicationDate.facet.range.gap=%2B1MONTH&f.DummyBook_publicationDate.facet.range.other=before
	// &f.DummyBook_publicationDate.facet.range.other=after&facet.range=DummyBook_publicationDate&hl=on&hl.fl=DummyBook_title
	public static void main(String[] args) throws Exception {
		SolrQuery query = new SolrQuery();
		//query.setQueryType("/select");
		query.setQuery("DummyBook_title:lucene");
		query.setFacet(true);
		//query.setRows(0);
		// query.setFacetPrefix("索尼");
		query.addFacetField("DummyBook_authors.name");

		Calendar startCal = Calendar.getInstance();
		startCal.add(Calendar.YEAR, -5);
		Date startDate = startCal.getTime();
		Date endDate = Calendar.getInstance().getTime();
		query.addDateRangeFacet("DummyBook_publicationDate", startDate, endDate, "+1MONTH");
		
		query.addNumericRangeFacet("DummyBook_price", 0, 500, 50);

		 query.setHighlight(true).setHighlightSnippets(1); //set other params as needed
		query.addHighlightField("DummyBook_title");
		query.setHighlightSimplePre("<font color=\"red\">");   
		query.setHighlightSimplePost("</font>"); 
		
		//query.addFacetQuery("DummyBook_title:lucene");
		query.setFacetMinCount(1);

		QueryResponse rsp = server.query(query);
		List<FacetField> facetFields = rsp.getFacetFields();
		List<RangeFacet> facetRanges = rsp.getFacetRanges();
		Map<String, Map<String, List<String>>> highlighting = rsp.getHighlighting();
		Map<String, FieldStatsInfo> fieldStatsInfo = rsp.getFieldStatsInfo();
		
		Iterator<SolrDocument> iter = rsp.getResults().iterator();
		
		/*SpellCheckResponse spellCheckResponse = rsp.getSpellCheckResponse();
		List<Suggestion> suggestions = spellCheckResponse.getSuggestions();
		for (Suggestion suggestion : suggestions) {
			suggestion.getAlternatives();
		}*/
		System.out.println(rsp);
	}

	/*public List<BlogsDO> searchBlogsList(String content, String bTypeId,  
	             String sDate, String eDate, Page page) throws IOException,  
	             ParseException {  
	          List<BlogsDO> blogList=new ArrayList<BlogsDO>();  
	          BlogsDO blogsDO=null;  
	          CommonsHttpSolrServer solrServer= SolrServer.getInstance().getServer();  
	          SolrQuery sQuery = new SolrQuery();  
	          String para="";  
	         //OR 或者  OR 一定要大写  
	          if(StringUtils.isNotEmpty(content)){  
	              para=para+"(title:"+content+" OR content:"+content+")";  
	              //空格 等同于 OR  
	 // para=para+"(title:"+content+"  content:"+content+")";  
	          }  
	         //AND 并且  AND一定要大写  
	          if(!bTypeId.equals("-1")){  
	              if(StringUtils.isNotEmpty(para)){  
	                   para=para+" AND bTypeId:"+bTypeId;  
	              }else{  
	                   para=para+"  bTypeId:"+bTypeId;  
	              }  
	          }  
	          if(StringUtils.isNotEmpty(sDate) && StringUtils.isNotEmpty(eDate)){  
	              if(StringUtils.isNotEmpty(para)){  
	                  para=para+" AND createTime:["+sDate+" TO "+eDate+"]";  
	              }else{  
	                  para=para+" createTime:["+sDate+" TO "+eDate+"]";  
	              }  
	         }  
	         //查询name包含solr apple  
	 //sQuery.setQuery("name:solr,apple");  
	 //manu不包含inc  
	 //sQuery.setQuery("name:solr,apple NOT manu:inc");  
	 //50 <= price <= 200  
	 //sQuery.setQuery("price:[50 TO 200]");  
	 //sQuery.setQuery("popularity:[5 TO 6]");  
	 //params.setQuery("price:[50 TO 200] - popularity:[5 TO 6]");  
	 //params.setQuery("price:[50 TO 200] + popularity:[5 TO 6]");  
	 //50 <= price <= 200 AND 5 <= popularity <= 6  
	 //sQuery.setQuery("price:[50 TO 200] AND popularity:[5 TO 6]");  
	 //sQuery.setQuery("price:[50 TO 200] OR popularity:[5 TO 6]");  
	           
	 // 查询关键词，*:*代表所有属性、所有值，即所有index  
	         if(!StringUtils.isNotEmpty(para)){  
	              para="*:*";   
	          }  
	          logger.info("para:"+para);  
	          sQuery.setQuery(para);  
	          //设置分页  start=0就是从0开始，，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。  
	          sQuery.setStart((page.getCurrentPage()-1)*page.getPerPageSize());  
	          sQuery.setRows(page.getPerPageSize());  
	          //排序 如果按照blogId 排序，，那么将blogId desc(or asc) 改成 id desc(or asc)  
	          sQuery.addSortField("blogId", ORDER.asc);  
	           
	          //设置高亮  
	         sQuery.setHighlight(true); // 开启高亮组件  
	         sQuery.addHighlightField("content");// 高亮字段  
	         sQuery.addHighlightField("title");// 高亮字段  
	         sQuery.setHighlightSimplePre("<font color='red'>");//标记，高亮关键字前缀  
	         sQuery.setHighlightSimplePost("</font>");//后缀  
	         sQuery.setHighlightSnippets(2);//结果分片数，默认为1  
	         sQuery.setHighlightFragsize(1000);//每个分片的最大长度，默认为100  
	           
	 //分片信息  
	         sQuery.setFacet(true)  
	             .setFacetMinCount(1)  
	             .setFacetLimit(5)//段  
	             .addFacetField("content");//分片字段  
	           
	         try {  
	             QueryResponse response = solrServer.query(sQuery);  
	             SolrDocumentList list = response.getResults();  
	             Integer counts=(int) list.getNumFound();  
	             logger.info("counts:"+counts);  
	             page.setCounts(counts);  
	             //获取所有高亮的字段  
	             Map<String,Map<String,List<String>>> highlightMap=response.getHighlighting();  
	             String blogId="";  
	             for (SolrDocument solrDocument : list) {  
	                 blogsDO=new BlogsDO();  
	                 blogId=solrDocument.getFieldValue("blogId").toString();  
	                 blogsDO.setBlogsId(Integer.valueOf(blogId));  
	                 blogsDO.setbTypeId(solrDocument.getFieldValue("bTypeId").toString());  
	                 blogsDO.setbTypeName(solrDocument.getFieldValue("bTypeName").toString());  
	                 blogsDO.setNickName(solrDocument.getFieldValue("nickName").toString());  
	                 List<String> titleList=highlightMap.get(blogId).get("title");  
	                 List<String> contentList=highlightMap.get(blogId).get("content");  
	                 if(titleList!=null && titleList.size()>0){  
	                     blogsDO.setTitle(titleList.get(0));  
	                 }else{  
	                     //获取并设置高亮的字段title  
	                     blogsDO.setTitle(solrDocument.getFieldValue("title").toString());  
	                 }  
	                 if(contentList!=null && contentList.size()>0){  
	                     blogsDO.setContent(contentList.get(0));  
	                 }else{  
	                     //获取并设置高亮的字段content  
	                     blogsDO.setContent(solrDocument.getFieldValue("content").toString());  
	                 }  
	                 blogsDO.setRevDate(solrDocument.getFieldValue("createTime").toString());  
	                 SimpleDateFormat sdf =  new  SimpleDateFormat("yyyyMMddHHmmss");   
	                 try {  
	                     blogsDO.setGmtCreate(sdf.parse(solrDocument.getFieldValue("createTime").toString()));  
	                 } catch (java.text.ParseException e) {  
	                     // TODO Auto-generated catch block  
	                     e.printStackTrace();  
	                 }  
	                 blogList.add(blogsDO);  
	             }  
	         } catch (SolrServerException e) {  
	             // TODO Auto-generated catch block  
	             e.printStackTrace();  
	         }  
	         return blogList;  
	     }  
	public List<BlogsDO> searchBlogsList(String content, String bTypeId,  
	             String sDate, String eDate, Page page) throws IOException,  
	             ParseException {  
	          List<BlogsDO> blogList=new ArrayList<BlogsDO>();  
	          BlogsDO blogsDO=null;  
	          CommonsHttpSolrServer solrServer= SolrServer.getInstance().getServer();  
	          SolrQuery sQuery = new SolrQuery();  
	          String para="";  
	         //OR 或者  OR 一定要大写  
	          if(StringUtils.isNotEmpty(content)){  
	              para=para+"(title:"+content+" OR content:"+content+")";  
	              //空格 等同于 OR  
	 // para=para+"(title:"+content+"  content:"+content+")";  
	          }  
	         //AND 并且  AND一定要大写  
	          if(!bTypeId.equals("-1")){  
	              if(StringUtils.isNotEmpty(para)){  
	                   para=para+" AND bTypeId:"+bTypeId;  
	              }else{  
	                   para=para+"  bTypeId:"+bTypeId;  
	              }  
	          }  
	          if(StringUtils.isNotEmpty(sDate) && StringUtils.isNotEmpty(eDate)){  
	              if(StringUtils.isNotEmpty(para)){  
	                  para=para+" AND createTime:["+sDate+" TO "+eDate+"]";  
	              }else{  
	                  para=para+" createTime:["+sDate+" TO "+eDate+"]";  
	              }  
	         }  
	         //查询name包含solr apple  
	 //sQuery.setQuery("name:solr,apple");  
	 //manu不包含inc  
	 //sQuery.setQuery("name:solr,apple NOT manu:inc");  
	 //50 <= price <= 200  
	 //sQuery.setQuery("price:[50 TO 200]");  
	 //sQuery.setQuery("popularity:[5 TO 6]");  
	 //params.setQuery("price:[50 TO 200] - popularity:[5 TO 6]");  
	 //params.setQuery("price:[50 TO 200] + popularity:[5 TO 6]");  
	 //50 <= price <= 200 AND 5 <= popularity <= 6  
	 //sQuery.setQuery("price:[50 TO 200] AND popularity:[5 TO 6]");  
	 //sQuery.setQuery("price:[50 TO 200] OR popularity:[5 TO 6]");  
	           
	 // 查询关键词，*:*代表所有属性、所有值，即所有index  
	         if(!StringUtils.isNotEmpty(para)){  
	              para="*:*";   
	          }  
	          logger.info("para:"+para);  
	          sQuery.setQuery(para);  
	          //设置分页  start=0就是从0开始，，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。  
	          sQuery.setStart((page.getCurrentPage()-1)*page.getPerPageSize());  
	          sQuery.setRows(page.getPerPageSize());  
	          //排序 如果按照blogId 排序，，那么将blogId desc(or asc) 改成 id desc(or asc)  
	          sQuery.addSortField("blogId", ORDER.asc);  
	           
	          //设置高亮  
	         sQuery.setHighlight(true); // 开启高亮组件  
	         sQuery.addHighlightField("content");// 高亮字段  
	         sQuery.addHighlightField("title");// 高亮字段  
	         sQuery.setHighlightSimplePre("<font color='red'>");//标记，高亮关键字前缀  
	         sQuery.setHighlightSimplePost("</font>");//后缀  
	         sQuery.setHighlightSnippets(2);//结果分片数，默认为1  
	         sQuery.setHighlightFragsize(1000);//每个分片的最大长度，默认为100  
	           
	 //分片信息  
	         sQuery.setFacet(true)  
	             .setFacetMinCount(1)  
	             .setFacetLimit(5)//段  
	             .addFacetField("content");//分片字段  
	           
	         try {  
	             QueryResponse response = solrServer.query(sQuery);  
	             SolrDocumentList list = response.getResults();  
	             Integer counts=(int) list.getNumFound();  
	             logger.info("counts:"+counts);  
	             page.setCounts(counts);  
	             //获取所有高亮的字段  
	             Map<String,Map<String,List<String>>> highlightMap=response.getHighlighting();  
	             String blogId="";  
	             for (SolrDocument solrDocument : list) {  
	                 blogsDO=new BlogsDO();  
	                 blogId=solrDocument.getFieldValue("blogId").toString();  
	                 blogsDO.setBlogsId(Integer.valueOf(blogId));  
	                 blogsDO.setbTypeId(solrDocument.getFieldValue("bTypeId").toString());  
	                 blogsDO.setbTypeName(solrDocument.getFieldValue("bTypeName").toString());  
	                 blogsDO.setNickName(solrDocument.getFieldValue("nickName").toString());  
	                 List<String> titleList=highlightMap.get(blogId).get("title");  
	                 List<String> contentList=highlightMap.get(blogId).get("content");  
	                 if(titleList!=null && titleList.size()>0){  
	                     blogsDO.setTitle(titleList.get(0));  
	                 }else{  
	                     //获取并设置高亮的字段title  
	                     blogsDO.setTitle(solrDocument.getFieldValue("title").toString());  
	                 }  
	                 if(contentList!=null && contentList.size()>0){  
	                     blogsDO.setContent(contentList.get(0));  
	                 }else{  
	                     //获取并设置高亮的字段content  
	                     blogsDO.setContent(solrDocument.getFieldValue("content").toString());  
	                 }  
	                 blogsDO.setRevDate(solrDocument.getFieldValue("createTime").toString());  
	                 SimpleDateFormat sdf =  new  SimpleDateFormat("yyyyMMddHHmmss");   
	                 try {  
	                     blogsDO.setGmtCreate(sdf.parse(solrDocument.getFieldValue("createTime").toString()));  
	                 } catch (java.text.ParseException e) {  
	                     // TODO Auto-generated catch block  
	                     e.printStackTrace();  
	                 }  
	                 blogList.add(blogsDO);  
	             }  
	         } catch (SolrServerException e) {  
	             // TODO Auto-generated catch block  
	             e.printStackTrace();  
	         }  
	         return blogList;  
	     }  */
}
