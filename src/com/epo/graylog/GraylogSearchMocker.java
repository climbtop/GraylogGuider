package com.epo.graylog;

import com.epo.graylog.bean.AbstractConfig;
import com.epo.graylog.bean.Message;
import com.epo.graylog.bean.QueryRefactor;
import com.epo.graylog.bean.QueryParam;
import com.epo.graylog.bean.SearchResult;
import com.epo.graylog.bean.impl.UatConfig;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年1月20日
 * @time 下午4:21:26
 */
public class GraylogSearchMocker {

	public static void main(String[] args) {
		AbstractConfig ac = new UatConfig();
		
		GraylogClient client = new GraylogClient();
		client.login(ac);
		
		QueryParam qp = getQueryParam(getProjectRefactor(ac));
		
		SearchResult result = client.search(ac, qp);
		if(result.hasResults()) {
			for(Message msg : result.getMessages()) {
				System.out.println(msg.getFullMessage());
			}
		}	
	}
	
	public static QueryRefactor getProjectRefactor(AbstractConfig ac) {
		String file = "D:\\EPO_HybrisOMS\\hybris\\bin\\custom\\epo-business\\epotm\\src\\com\\epo\\tm\\job\\pcm\\EpoEcTbProductConvertJob.java";
		String line = "457";
		
		QueryRefactor pr = new QueryRefactor(ac, file);
		pr.setLineNumber(line);
		return pr;
	}

	public static QueryParam getQueryParam(QueryRefactor pr) {
		QueryParam qp = new QueryParam(pr);
		String query = String.format("sourceFileName:%s AND sourceLineNumber:%s", pr.getFileName(), pr.getLineNumber());
		String filter = String.format("streams:%s", pr.getStreamsId());
		qp.setQuery(query);
		qp.setFilter(filter);
		return qp;
	}
}
