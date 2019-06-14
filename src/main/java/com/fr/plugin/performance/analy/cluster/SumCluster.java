package com.fr.plugin.performance.analy.cluster;

/**
 * Created by yuwh on 2019/3/19
 * Description:none
 */
public final class SumCluster implements BaseCluster<Long, Object>{
    private String resultFieldName;
    private Long result= 0L;

    public SumCluster(String var){
        resultFieldName= var;
    }

    @Override
    public Long mergerTo(Object var2) {
        if (var2 != null) {
            result= result+ Long.valueOf(String.valueOf(var2));
        }
        return result;
    }

    @Override
    public String getFieldName() {
        return resultFieldName;
    }

    @Override
    public void resetResult() {
        result= 0L;
    }
}
