package com.fr.plugin.performance.analy.statikit;

import com.fr.plugin.performance.analy.cluster.BaseCluster;
import com.fr.plugin.performance.analy.cluster.CountCluster;
import com.fr.plugin.performance.analy.cluster.SumCluster;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yuwh on 2019/3/19
 * Description: new GroupFeatureHandler().addField().groupBy().getResult() 获取数据集分组结果
 */
public class GroupFeatureHandler<E extends List<Map>> {
    private List<Map> origin;
    private int orginSize;
    private List<String> orders;
    private int ordersSize;
    private List<KeyRepresentMap<HashMap>> Table;
    private HashMap<String, BaseCluster> calcuFields= new HashMap<>();
    private static String PRIMARYKEYNAME = "_ManualPK";
    private static String PRIMARYKEYSEPARATOR = "%%";

    public GroupFeatureHandler(E e){
        origin = e;
        orginSize = origin.size();
        Table = new ArrayList<>();
    }

    /*注册归并字段和归并方式*/
    public GroupFeatureHandler addField(String var1, BaseCluster var2){
        if(var2 == null){
            calcuFields.remove(var1);
        } else {
            calcuFields.put(var1, var2);
        }
        return this;
    }

    /*注册分组字段和分组方式，并生成主键和排序*/
    public GroupFeatureHandler groupBy(Collection<String> var){
        if( orders== null || ! orders.equals(var) ) {
            orders = new ArrayList<String>(var);
            ordersSize = orders.size();
            generateKey();
        }
        return this;
    }

    /*无序的结果集*/
    public void getResult(HashMap target){
        StringBuffer thisIdentity;
        StringBuffer lastIdentity = new StringBuffer();
        HashMap templine= new HashMap();
        for(int i= 0; i< orginSize; i++) {
            thisIdentity = new StringBuffer(Table.get(i).getIdentity());
            if(i==0) {
                templine = Table.get(0).getBehavior();
            }else{
                if (! thisIdentity.toString().equals(lastIdentity.toString())) {
                    target.put(lastIdentity.toString(), templine.clone());
                    templine.clear();
                    templine = Table.get(i).getBehavior();
                    resetCluster();
                }
            }
            doCluster(templine, Table.get(i).getBehavior());
            lastIdentity = thisIdentity;
        }
        target.put(lastIdentity.toString(), templine.clone());
        templine.clear();
    }

    private void generateKey(){
        StringBuffer tempKeyValue;
        //一次循环生成主键
        for(int i= 0; i< orginSize; i++){
            tempKeyValue= new StringBuffer();
            for(int j= 0; j< ordersSize; j++) {
                tempKeyValue.append(origin.get(i).get(orders.get(j)));
                tempKeyValue.append(PRIMARYKEYSEPARATOR);
            }
            origin.get(i).put(PRIMARYKEYNAME, tempKeyValue.toString());
            Table.add(new KeyRepresentMap(tempKeyValue.toString(), origin.get(i)));
        }
        Collections.sort(Table);
    }

    private void resetCluster(){
        for(BaseCluster var:calcuFields.values()){
            var.resetResult();
        }
    }

    private void doCluster(HashMap var1, HashMap var2){
        Iterator<String> calcutor = calcuFields.keySet().iterator();
        BaseCluster cluster;
        StringBuffer field;
        StringBuffer newfield;
        while(calcutor.hasNext()){
            field = new StringBuffer(calcutor.next());
            cluster= calcuFields.get(field.toString());
            newfield  = new StringBuffer(cluster.getFieldName());
            var1.put(newfield.toString(), cluster.mergerTo(var2.get(field.toString())).toString());
        }
    }

    /*测试用例10000*10,3字段分组*/
    /*public static void main(String[] args) {
        List<HashMap<String,String>> a = new ArrayList<HashMap<String, String>>() ;
        HashMap<String,String> temp = new HashMap<String,String>();
        for(int i=1;i<=10000;i++){
            for(int j=1;j<=10;j++){
                if(i>5000) {
                    temp.put(String.valueOf(j), String.valueOf(j + (i - 5001) * 10));
                } else {
                    temp.put(String.valueOf(j), String.valueOf(j + (i - 1) * 10));
                }
            }
            //System.out.println(temp);
            a.add((HashMap<String, String>) temp.clone());
        }
        GroupFeatureHandler b = new GroupFeatureHandler(a);
        List<String> c= new ArrayList<>();
        c.add("1");
        c.add("2");
        c.add("3");
        b.groupBy(c);
        b.addField("1", new CountCluster("num"));
        b.addField("2", new SumCluster("sum"));
        HashMap d = new HashMap();
        b.getResult(d);
    }*/
}
