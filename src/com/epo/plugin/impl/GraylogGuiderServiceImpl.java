package com.epo.plugin.impl;

import com.alibaba.fastjson.JSON;
import com.epo.form.GraylogSearchForm;
import com.epo.form.SearchConfig;
import com.epo.form.SearchParam;
import com.epo.graylog.GraylogCaller;
import com.epo.graylog.GraylogClient;
import com.epo.graylog.bean.AbstractConfig;
import com.epo.graylog.bean.Message;
import com.epo.graylog.bean.SearchResult;
import com.epo.graylog.bean.impl.ProdConfig;
import com.epo.graylog.bean.impl.SitConfig;
import com.epo.graylog.bean.impl.UatConfig;
import com.epo.plugin.GraylogGuiderService;
import com.epo.plugin.GraylogToolWindow;
import com.intellij.openapi.util.text.StringUtil;
import org.apache.commons.lang.StringUtils;

public class GraylogGuiderServiceImpl implements GraylogGuiderService {

    private GraylogClient client;
    private GraylogSearchForm searchForm;

    public GraylogGuiderServiceImpl(){
        this.client = new GraylogClient();
    }

    @Override
    public GraylogClient getClient() {
        return client;
    }

    @Override
    public GraylogSearchForm getSearchForm() {
        return searchForm;
    }

    @Override
    public void setSearchForm(GraylogSearchForm searchForm) {
        this.searchForm = searchForm;
    }

    private AbstractConfig getAbstractConfig(SearchConfig searchConfig){
        AbstractConfig[] configs = new AbstractConfig[]{new ProdConfig(),new UatConfig(),new SitConfig(),};
        for(AbstractConfig ac : configs){
            if(ac.getEnvironment().equalsIgnoreCase(searchConfig.getEnvironment())){
                return ac;
            }
        }
        return null;
    }

    private void pushToConsoleView(SearchResult result){
        if(getSearchForm()!=null){
            getSearchForm().getConsoleView().setText("");
            getSearchForm().getTotalRecords().setText(String.valueOf(result.getTotalResults()));
        }
    }

    private void printToConsoleView(String message){
        if(getSearchForm()!=null){
            getSearchForm().getConsoleView().append(message);
        }
    }

    @Override
    public void searchGraylogMessage(final SearchParam searchParam){
        if(getSearchForm()!=null){
            searchGraylogMessage(getSearchForm().readSearchParam(), searchParam);
        }
    }

    @Override
    public void searchGraylogMessage(final SearchConfig searchConfig, final SearchParam searchParam){
        if(searchConfig==null) return;
        AbstractConfig ac = getAbstractConfig(searchConfig);
        GraylogCaller.callWebService(client, ac,
                pr->{
                    if(StringUtils.isNotEmpty(searchParam.getSourceFile()) && searchParam.getLineNumber()!=null){
                        pr.setSourceFile(searchParam.getSourceFile());
                        pr.setLineNumber(searchParam.getLineNumber());
                        pr.setLineCount(searchParam.getLineCount());
                        pr.resovleMoreInfo();

                        if (StringUtils.isNotEmpty(searchParam.getSearchText())) {
                            searchParam.setSearchText(String.format("sourceFileName:%s AND message:\"%s\"",
                                    pr.getFileName(), searchParam.getSearchText()));
                        } else {
                            searchParam.setSearchText(String.format("sourceFileName:%s AND sourceLineNumber:%s",
                                    pr.getFileName(), pr.getLineNumber()));
                        }
                        searchParam.setProjectName(pr.getProjectName());
                    }

                    if(StringUtil.isNotEmpty(searchParam.getSearchText())) {
                        pr.setSearchText(searchParam.getSearchText());
                    }else{
                        pr.setSearchText(searchConfig.getSearchText());
                    }
                    if(StringUtil.isNotEmpty(searchParam.getProjectName())) {
                        pr.setProjectName(searchParam.getProjectName());
                    }else{
                        pr.setProjectName(searchConfig.getProjectName());
                    }
                },
                (pr,qp)->{
                    qp.setLimit(searchConfig.getPageSize()); //pageSize
                    qp.setRange(String.valueOf(searchConfig.getSearchRange())); //30 minutes
                    qp.setQuery(searchConfig.getSearchText());
                },
                result->{
                    pushToConsoleView(result);
                    if(result.hasResults()) {
                        for(Message msg : result.getMessages()) {
                            String message = ("Y".equals(searchConfig.getIsDetails())?
                                    msg.getFullMessage():msg.getShortMessage());
                            printToConsoleView(message);
                        }
                    }else{
                        printToConsoleView(JSON.toJSONString(result.getQp().getQuery()));
                    }
                    searchConfig.setTotalRecords(String.valueOf(result.getTotalResults()));
                    GraylogGuiderService.getInstance().getSearchForm().writeSearchParam(searchConfig);
                    return 0;
                }
        );
    }

}
