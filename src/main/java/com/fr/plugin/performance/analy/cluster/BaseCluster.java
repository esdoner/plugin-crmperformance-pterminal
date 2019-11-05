package com.fr.plugin.performance.analy.cluster;

/**
 * Created by yuwh on 2019/3/19
 * Description:none
 * @author think
 */
@Deprecated
public interface BaseCluster<T, K> {
    /**
     * 合并
     * @param var2
     * @return
     */
    T mergerTo( K var2 );

    /**
     * none
     * @return
     */
    String getFieldName();

    /**
     * null
     */
    void resetResult();
}
