package com.fr.plugin.performance.conf;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/2
 * Description:none
 */
public class BaseConfProvider {
    public static final float TOTAL_MEMORY = Runtime.getRuntime().totalMemory();
    public static final float MAX_MEMORY_PERCENTAGE = 0.9F;
    public static final float MIN_MEMORY_PERCENTAGE = 0F;
    public static final float MAX_CPU_PERCENTAGE = 0.8F;
    public static final float MIN_CPU_PERCENTAGE = 0.25F;
    public static final float MIN_DURATION = 10;
}
