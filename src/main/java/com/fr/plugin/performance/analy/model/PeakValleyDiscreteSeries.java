package com.fr.plugin.performance.analy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/27
 * Description:none
 */
public class PeakValleyDiscreteSeries extends DiscreteSeries{
    /**
     * 离散序列找图峰
     * @param dimension
     * @return
     */
    @Override
    public IrregularDiscreteSeries findPeak(String dimension) {
        return null;
    }

    public String[][] getPeakReport(float var, String[] var1){
        List<SeriesPeak> report = new ArrayList<>();
        final SeriesPeak[] tmp = {null};
        final Node[] lastNode = new Node[1];
        final Node[] thizNode = new Node[1];
        final Node[] fourNode = new Node[4];
        series.forEach(n->{
            thizNode[0]= n;
            if(lastNode[0]== null){
                lastNode[0]= n;
            }
            if(thizNode[0].getValue()>= var){
                if(lastNode[0].getValue()< var || thizNode[0]== lastNode[0]){
                    fourNode[0]= lastNode[0];
                    fourNode[1]= thizNode[0];
                    tmp[0] = new SeriesPeak();
                }
                tmp[0].nodes.add(thizNode[0]);
            } else if(lastNode[0].getValue()>= var){
                fourNode[2]= lastNode[0];
                fourNode[3]= thizNode[0];
                tmp[0].draw(fourNode[0], fourNode[3], fourNode[1], fourNode[2], var);
                report.add(tmp[0].clone());
            }
            lastNode[0]= n;
        });
        String[][] reportMatrix = new String[report.size()][2+var1.length];
        final int[] i = {1};
        report.forEach(sp->{
            reportMatrix[i[0] -1][0]= i[0] +"";
            reportMatrix[i[0] -1][1]= String.valueOf(sp.getDuration());
            for(int j= 0;j<var1.length; j++) {
                reportMatrix[i[0] - 1][2+j] = String.valueOf(sp.nodes.get(0).getDimension(var1[j]));
            }
            i[0]++;
        });
        return reportMatrix;
    }

    private float getPredictY(float startX, float endX, float startY, float endY, float targetX) {
        if(endX== startX || endY== startY){
            return Math.min(startY, endY);
        }
        float result;
        result= ((endY - startY)/(endX - startX))*(targetX - startX) + startY;
        return result;
    }

    private float getPredictX(float startX, float endX, float startY, float endY, float targetY) {
        if(endX== startX || endY== startY){
            return Math.min(startX, endX);
        }
        float result;
        result= ((targetY - startY)/((endY - startY)/(endX - startX))) + startX;
        return result;
    }

    private class SeriesPeak implements Cloneable{
        private List<Node> nodes= new ArrayList<>();
        private float leftTail;
        private float rightTail;

        private SeriesPeak(){}

        private void draw(Node left, Node right, Node begin, Node end, float line){
            try {
                leftTail= begin.getStepLength() - getPredictX(0, begin.getStepLength(), left.getValue(), begin.getValue(), line);
                rightTail= getPredictX(0, right.getStepLength(), end.getValue(), right.getValue(), line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private float getDuration(){
            final float[] result = {0F};
            nodes.forEach(n->{
                result[0] += n.getDuration();
            });
            result[0] = result[0] + leftTail + rightTail;
            return result[0];
        }

        private Node getBeginNode(){
            return nodes.get(0);
        }

        @Override
        public SeriesPeak clone() {
            SeriesPeak seriesPeak = null;
            try {
                seriesPeak = (SeriesPeak) super.clone();
                seriesPeak.nodes = new ArrayList<>();
                SeriesPeak finalSeriesPeak = seriesPeak;
                this.nodes.forEach(n->{
                    finalSeriesPeak.nodes.add(n.clone());
                });
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return seriesPeak;
        }
    }

    private class SeriesValley{ }
}
