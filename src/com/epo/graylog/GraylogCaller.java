package com.epo.graylog;

import com.epo.graylog.GraylogClient;
import com.epo.graylog.bean.*;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class GraylogCaller {

    public static <T> T callWebService(GraylogClient client, AbstractConfig ac,
                                       Function<QueryRefactor, T> refactorFunction,
                                       BiFunction<QueryRefactor, QueryParam, T> queryFunction,
                                       Function<SearchResult, T> resultFunction,
                                       BiConsumer<QueryRefactor, QueryParam> errorConsumber){
        T stepOn = null;
        QueryRefactor pr = new QueryRefactor(ac);
        stepOn = refactorFunction.apply(pr);
        pr.resovleMoreInfo();

        if(!canStepOn(stepOn)) {
            errorConsumber.accept(pr,new QueryParam(pr));
            return stepOn;
        }

        QueryParam qp = new QueryParam(pr);
        stepOn = queryFunction.apply(pr, qp);
        qp.setFilter(String.format("streams:%s", pr.getStreamsId()));

        if(!canStepOn(stepOn)) {
            errorConsumber.accept(pr,qp);
            return stepOn;
        }

        SearchResult result = client.search(ac, qp);
        return resultFunction.apply(result);
    }

    private static <T> Boolean canStepOn(T stepOn){
        if(stepOn!=null && stepOn instanceof Boolean){
            if(!((Boolean)stepOn)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

}
