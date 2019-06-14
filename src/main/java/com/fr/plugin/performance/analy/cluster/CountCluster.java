package com.fr.plugin.performance.analy.cluster;

/**
 * Created by yuwh on 2019/3/19
 * Description:none
 */
public final class CountCluster implements BaseCluster<Integer, Object>{
    private String resultFieldName;
    private int result= 0;

    public CountCluster(String var){ resultFieldName= var;}

    @Override
    public Integer mergerTo( Object var2) {
        if (var2 != null) {
           result= result+1;
        }
        return result;
    }

    public String getFieldName(){
        return resultFieldName;
    }

    @Override
    public void resetResult() {
        result= 0;
    }
}
