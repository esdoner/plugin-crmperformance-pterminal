package com.fr.plugin.performance.analy.cluster;

/**
 * Created by yuwh on 2019/3/19
 * Description:none
 */
public interface BaseCluster<T, K> {
    T mergerTo( K var2 );

    String getFieldName();

    void resetResult();
}
