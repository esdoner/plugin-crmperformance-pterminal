package com.fr.plugin.performance.analy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/23
 * Description: 离散时间序列
 */
public abstract class DiscreteSeries implements TimeSeries, MultiDimension{
    protected List<Node> series= new ArrayList<>();

    DiscreteSeries(){ }

    public DiscreteSeries init(List<Map> nodes){
        final long[] i = {0};
        nodes.forEach(m->{
            Node tmpNode= new Node();
            tmpNode.step= i[0];
            tmpNode.dimensions.putAll(m);
            series.add(tmpNode);
            i[0]++;
        });
        return this;
    }

    /**
     * 形成一维散点时间序列
     * @param dimension
     */
    public void makeValue (String dimension){
        series.forEach(n->{
            Object obj= n.getDimension(dimension);
            n.setValue(Float.valueOf(obj.toString()));
        });
    }

    /**
     * 去掉某个点位
     * @param id
     * @return
     */
    protected DiscreteSeries remove(long id){
        series.removeIf(n->{
            if(n.getStep()== id){
               return true;
            } else {
                return false;
            }
        });
        return this;
    }

    /**
     * 尾添某个点位
     * @param node
     */
    protected void append(Node node){
        series.add(node);
    }

    /**
     * 离散序列找图峰
     * @param dimension
     * @return
     */
    public abstract IrregularDiscreteSeries findPeak(String dimension);

    public List<Node> getValidNodes(){
        List<Node> nodes= new ArrayList<>();
        final long[] i = {0};
        series.forEach(n->{
            if(n.isUse()){
                n.setStep(i[0]);
                nodes.add(n);
                i[0]++;
            }
        });
        return nodes;
    }

    protected class Node implements Comparable<Node>, Cloneable{
        private long step;
        private long duration= 1L;
        private long stepLength= 1L;
        private float value;
        private int type;
        private boolean isUse= true;

        private Node(){ };
        
        private Map<String, Object> dimensions= new HashMap<>();

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public long getStepLength() {
            return stepLength;
        }

        public void setStepLength(long stepLength) {
            this.stepLength = stepLength;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getDimension(String var1){
            return dimensions.get(var1);
        };

        public void setDimension(String var1, Object var2){
            this.dimensions.put(var1, var2);
        };

        public long getStep(){
            return step;
        }

        public void setStep(long var){
            this.step = var;
        }

        public float getValue(){
            return value;
        }

        public void setValue(float var){
            this.value = var;
        }

        public boolean isUse() {
            return isUse;
        }

        public void setUse(boolean use) {
            isUse = use;
        }

        public void durationAdd(long var){
            setDuration(getDuration() + var);
        }

        public void durationReduce(long var){
            setDuration(getDuration() - var);
        }

        public void stepLengthAdd(long var){
            setStepLength(getStepLength() + var);
        }

        public void stepLengthReduce(long var){
            setStepLength(getStepLength() - var);
        }

        @Override
        public int compareTo(Node n) {
            float value= n.getValue();
            if(this.value> value){
                return 1;
            } else if(this.value< value){
                return -1;
            }
            return 0;
        }

        @Override
        protected Node clone(){
            Node node = null;
            try{
                node=(Node)super.clone();
            }catch (CloneNotSupportedException e){
                e.printStackTrace();
            }
            return node;
        }
    }
}
