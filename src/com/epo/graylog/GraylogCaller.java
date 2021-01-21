package com.epo.graylog;

import com.epo.graylog.GraylogClient;
import com.epo.graylog.bean.AbstractConfig;
import com.epo.graylog.bean.QueryParam;
import com.epo.graylog.bean.QueryRefactor;
import com.epo.graylog.bean.SearchResult;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class GraylogCaller {

    public static <T> T callWebService(GraylogClient client, AbstractConfig ac,
                                      Consumer<QueryRefactor> refactorConsumer,
                                      BiConsumer<QueryRefactor, QueryParam> queryConsumer,
                                      Function<SearchResult, T> resultFunction){
        QueryRefactor pr = new QueryRefactor(ac);
        refactorConsumer.accept(pr);
        pr.resovleMoreInfo();

        QueryParam qp = new QueryParam(pr);
        queryConsumer.accept(pr, qp);
        qp.setFilter(String.format("streams:%s", pr.getStreamsId()));

        SearchResult result = client.search(ac, qp);

        return resultFunction.apply(result);
    }

}
