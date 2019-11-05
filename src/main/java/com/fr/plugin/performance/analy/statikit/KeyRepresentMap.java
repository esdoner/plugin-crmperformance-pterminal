package com.fr.plugin.performance.analy.statikit;

import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/3/20
 * Description:时间序列
 */
@Deprecated
public class KeyRepresentMap<V> implements Comparable<KeyRepresentMap>{
    private String identity;
    private V behavior;

    public KeyRepresentMap(){ }

    public KeyRepresentMap(String var1, V var2){
        setAll(var1, var2);
    }

    public String setKey(String var){
        if(var != null) { identity = var; }
        return identity;
    }

    public V setValue(V var){
        if(var != null) { behavior=var; }
        return behavior;
    }

    public KeyRepresentMap setAll(String var1, V var2){
        this.setKey(var1);
        this.setValue(var2);
        return this;
    }

    public String getIdentity(){
        return identity;
    }

    public V getBehavior() {
        return behavior;
    }

    /*
    * @params [anotherMap]
    * @return int
    * @description: 使identity可比较，用于排序
    */
    @Override
    public int compareTo(KeyRepresentMap anotherMap) {
        int len1 = identity.length();
        int len2 = anotherMap.identity.length();
        int lim = Math.min(len1, len2);
        char v1[] = identity.toCharArray();
        char v2[] = anotherMap.identity.toCharArray();

        int k = 0;
        while (k < lim) {
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
    }
}
