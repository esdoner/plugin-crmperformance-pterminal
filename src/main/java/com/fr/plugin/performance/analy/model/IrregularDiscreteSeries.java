package com.fr.plugin.performance.analy.model;

import java.util.List;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/23
 * Description: 无规律离散序列
 */
public class IrregularDiscreteSeries extends DiscreteSeries {

    /**
     * 1.value值设定
     * 2.去除邻位同value的点位，记录步长；将每个点位归类为峰谷坡之一
     * 3.去除坡点
     * 4.新建峰谷离散时间序列PeakValleyDiscreteSeries
     * @param dimension
     * @return
     */
    @Override
    public IrregularDiscreteSeries findPeak(String dimension) {
        /**  把需要查峰的维度放到value里，这里要是数字类型的*/
        this.makeValue(dimension);
        /** 将重复点位转换成权重处理*/
        this.wareDeal();
        return this;
    }

    public PeakValleyDiscreteSeries findPeak(){
        PeakValleyDiscreteSeries series = new PeakValleyDiscreteSeries();
        List<Node> nodes = this.getValidNodes();
        nodes.forEach(n->{
           series.append(n);
        });
        return series;
    }

    /**
     * 波动处理
     * 1. 去连续点位
     * 2. 定位分类成波峰(1) 波(-1) 谷(0)
     * 3. 去掉坡点
     */
    private void wareDeal(){
        final Node[] thizNode = new Node[1];
        final Node[] lastNode = new Node[1];
        series.forEach(n->{
            thizNode[0] = n;
            if(thizNode[0].isUse()) {
                if (lastNode[0] == null) {
                    thizNode[0].setType(1);
                    lastNode[0]= n;
                    /** doing nothing*/
                } else {
                    if (thizNode[0].getValue() == lastNode[0].getValue()) {
                        lastNode[0].durationAdd(1L);
                        thizNode[0].setUse(false);
                    } else {
                        if(lastNode[0].getType() == 1 && lastNode[0].compareTo(thizNode[0])== -1){
                            lastNode[0].setType(0);
                            lastNode[0].setUse(false);
                            thizNode[0].setStepLength(lastNode[0].getStepLength() + 1);
                        } else if(lastNode[0].getType() == -1 && lastNode[0].compareTo(thizNode[0])== 1){
                            lastNode[0].setType(0);
                            lastNode[0].setUse(false);
                            thizNode[0].setStepLength(lastNode[0].getStepLength() + 1);
                        }
                        thizNode[0].setType(thizNode[0].compareTo(lastNode[0]));
                        lastNode[0]= n;
                    }
                }
            }
        });
    }
}