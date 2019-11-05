package com.fr.plugin.performance.view.health;

import com.fr.general.data.TableDataException;
import com.fr.plugin.performance.util.dao.DaoFineSwift;
import com.fr.plugin.performance.util.timer.FunCalendar;
import com.fr.plugin.performance.util.timer.TimeFormat;
import com.fr.plugin.performance.view.BaseTableData;
import com.fr.stable.ParameterProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/22
 * Description:系统全局性能指标统计业务TableData
 */
public class FullIndexTableData extends BaseTableData {
    private DaoFineSwift dao;
    private StringBuilder sqlString;
    private ResultSet resultSet= null;

    private String[] Paras;
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
    private List<String[]> periods = new ArrayList<String[]>();
    private List<String> periodsKey = new ArrayList<String>();

    private HashMap<String, HashMap<String, String>> resultMap = new HashMap<>();
    private String[][] result;

    public FullIndexTableData() { unFinish();  }

    @Override
    public int getColumnCount() throws TableDataException {
        return 10;
    }

    @Override
    public String getColumnName(int i) throws TableDataException {
        return "col" + String.valueOf(i);
    }

    @Override
    public int getRowCount() throws TableDataException {
        if (!isFinished()) { init(); }
        return result.length;
    }

    @Override
    public Object getValueAt(int i, int j) {
        if (!isFinished()) { init(); }
        return result[i][j];
    }

    @Override
    public void init() {
        if (!isFinished()) {
            dao = new DaoFineSwift("swiftdb");
            dao.createCon();
            this.prepareParas();
            this.initializeData();
            this.doIndexCalculate();
            dao.closeCon();
        }
        finish();
    }

    private void prepareParas() {
        /*收集原始参数*/
        ParameterProvider[] parameters = this.getParameters(null);
        Paras = new String[parameters.length];
        for (int i= 0; i< parameters.length; i++) {
            Paras[i] = parameters[i].getValue().toString();
        }
        /*生成日历*/
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        TimeFormat tf = new TimeFormat().setSDF("yyyy-MM");
        FunCalendar fun = new FunCalendar(tf.string2Date(Paras[0]), tf.string2Date(Paras[1]), "m");
        periods= fun.getArrayList();
    }

    private void initializeData() {
        int l= periods.size();
        for(int i= 0; i< l; i++) {
            periodsKey.add(periods.get(i)[0]);
            /*查询部分*/
            sqlString = new StringBuilder("SELECT tname,count(tname),sum(consume) FROM fine_record_execute WHERE tname is not null AND consume>0 AND time>='");
            sqlString.append(periods.get(i)[1] + "' AND time<='");
            sqlString.append(periods.get(i)[2] + "' GROUP BY tname");
            try {
                resultSet = dao.executeDQL(sqlString.toString());
                this.doStatic(resultSet,"q", periods.get(i)[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            /*填报部分*/
            sqlString = new StringBuilder("SELECT tname,count(tname),sum(sqlTime) FROM fine_record_write WHERE tname is not null AND sqlTime>0 AND time>='");
            sqlString.append(periods.get(i)[1] + "' AND time<='");
            sqlString.append(periods.get(i)[2] + "' GROUP BY tname");
            try {
                resultSet = dao.executeDQL(sqlString.toString());
                this.doStatic(resultSet,"c", periods.get(i)[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(periodsKey);
    }

    private void doStatic(ResultSet rst,String mod,String period) throws SQLException {
        int colnum;
        StringBuffer rowkey;
        StringBuffer rowtname;
        StringBuffer rowtime;
        int rowcount;
        Long rowconsume;

        HashMap<String, String>  temp = new HashMap<>();

        if(rst !=null) {
            colnum= rst.getMetaData().getColumnCount();
            while (rst.next()) {
                if (rst.getString(1) != null && rst.getString(2) != null && rst.getString(colnum) != null) {
                    rowtime = new StringBuffer(period);
                    rowtname = new StringBuffer(rst.getString(1));
                    rowcount = rst.getInt(2);
                    rowconsume = rst.getLong(colnum);

                    rowkey = new StringBuffer(rst.getString(1));
                    rowkey.append("%%").append(mod).append("%%").append(rowtime);

                    temp.put("tname", String.valueOf(rowtname));
                    temp.put("time", String.valueOf(rowtime));
                    temp.put("mod", mod);
                    temp.put("count", String.valueOf(rowcount));
                    temp.put("sum", String.valueOf(rowconsume));
                    resultMap.put(rowkey.toString(), (HashMap<String, String>) temp.clone());
                }
            }
        }
    }

    /**
     * TODO method over 100 lines
     */
    private void doIndexCalculate(){
        List<String> periodsReverse = new ArrayList(periodsKey);

        StringBuilder key;
        HashMap<String, String> value;
        HashMap<String, Float> indexMap;
        HashMap<String, Float[]> indextemp = new HashMap<>();

        Calendar curr = Calendar.getInstance();
        Calendar chag = Calendar.getInstance();
        StringBuilder avg_this;
        StringBuilder avg_last;
        String find;
        /*标记出是否为期初期初得分都是0*/
        boolean firstPeriod;

        for(Map.Entry<String, HashMap<String, String>> var1 : resultMap.entrySet()){
            key = new StringBuilder(var1.getKey());
            value = var1.getValue();
            curr.set(Integer.valueOf(value.get("time").substring(0,4)), Integer.valueOf(value.get("time").substring(5,7)), 1);
            avg_last = new StringBuilder(String.valueOf( Long.valueOf(value.get("sum"))/Integer.valueOf(value.get("count"))) );
            avg_this = new StringBuilder(String.valueOf( Long.valueOf(value.get("sum"))/Integer.valueOf(value.get("count"))) );
            firstPeriod = true;

            for(String var2:periodsReverse){
                chag.set(Integer.valueOf(var2.substring(0,4)), Integer.valueOf(var2.substring(5,7)), 1);
                if(chag.before(curr)){
                    /*按顺序查找前期的key*/
                    find = key.toString().replace(value.get("time"),var2);
                    if(resultMap.containsKey(find)) {
                        avg_last = new StringBuilder(String.valueOf( Long.valueOf(resultMap.get(find).get("sum"))/Integer.valueOf(resultMap.get(find).get("count")) ));
                        firstPeriod = false;
                        break;
                    }else{
                        continue;
                    }
                } else {
                    continue;
                }
            }
            /*计算该组合的指标片段，3种方式*/
            indexMap = indexEstablish(avg_this.toString(), avg_last.toString(), value.get("count"), firstPeriod);
            /*结果集种类1*/
            if(Paras[3].equals("1")) {
                for (int i = 1; i <= 6; i++) {
                    value.put("index" + "_" + String.valueOf(i), String.valueOf(indexMap.get("index" + "_" + String.valueOf(i))));
                }
                value.put("avglast",avg_last.toString());
                value.put("avgthis",avg_this.toString());
                value.put("firstPeriod", String.valueOf(firstPeriod));
            }
            /*结果集种类2*//*逐步汇总*/
            if(Paras[3].equals("2")) {
                if (indextemp.containsKey(value.get("time"))) {
                    indextemp.get(value.get("time"))[0] += 1;
                    indextemp.get(value.get("time"))[1] += indexMap.get("index_1");
                    indextemp.get(value.get("time"))[2] += indexMap.get("index_2");
                    indextemp.get(value.get("time"))[3] += indexMap.get("index_3");
                    indextemp.get(value.get("time"))[4] += indexMap.get("index_4");
                    indextemp.get(value.get("time"))[5] += indexMap.get("index_5");
                    indextemp.get(value.get("time"))[6] += indexMap.get("index_6");
                } else {
                    indextemp.put(value.get("time"), new Float[]{1F,
                            indexMap.get("index_1"), indexMap.get("index_2"),
                            indexMap.get("index_3"), indexMap.get("index_4"),
                            indexMap.get("index_5"), indexMap.get("index_6")
                    });
                }
            }
        }
        /** 结果集种类1 */
        if(Paras[3].equals("1")){
            doSort();
        }
        /** 结果集种类2 */
        if(Paras[3].equals("2")) {
            result = new String[periodsKey.size()][10];
            for (int i = 0; i < periodsKey.size(); i++) {
                result[i][0] = periodsKey.get(i);
                if (indextemp.containsKey(periodsKey.get(i))) {
                    result[i][1] = String.valueOf(indextemp.get(periodsKey.get(i))[1]);
                    result[i][2] = String.valueOf(indextemp.get(periodsKey.get(i))[2] / indextemp.get(periodsKey.get(i))[0]);
                    result[i][3] = String.valueOf(indextemp.get(periodsKey.get(i))[3]);
                    result[i][4] = String.valueOf(indextemp.get(periodsKey.get(i))[4] / indextemp.get(periodsKey.get(i))[0]);
                    result[i][5] = String.valueOf(indextemp.get(periodsKey.get(i))[5]);
                    result[i][6] = String.valueOf(indextemp.get(periodsKey.get(i))[6] / indextemp.get(periodsKey.get(i))[0]);
                } else {
                    for (int j = 1; j < 7; j++) {
                        result[i][j] = "0";
                    }
                }
            }
        }
    }

    private HashMap<String, Float> indexEstablish(String avgthis, String avglast, String count, boolean firstPeriod){
        HashMap<String, Float> result = new HashMap<>();
        Float index1;
        Float index2;
        Float index3;
        Float index4;
        Float index5;
        Float index6;
        /*方式1：不考虑期初是否达标*/
        index1 = - (Float.valueOf(avgthis)-Float.valueOf(avglast))* Integer.valueOf(count);
        index2 = - (Float.valueOf(avgthis)-Float.valueOf(avglast))/(Float.valueOf(avglast)!=0? Float.valueOf(avglast): 1);
        result.put("index_1", index1);
        result.put("index_2", index2);

        /*方式2：考虑期初的惰性指标*/
        if(Float.valueOf(avgthis)<=Float.valueOf(Paras[2])){
            if(! (Float.valueOf(avglast)<=Float.valueOf(Paras[2]))){
                index3 = - (Float.valueOf(avgthis)-Float.valueOf(Paras[2]))* Integer.valueOf(count);
                index4 = - (Float.valueOf(avgthis)-Float.valueOf(Paras[2]))/(Float.valueOf(Paras[2])!=0? Float.valueOf(Paras[2]): 1);
            } else {
                index3 = index1;
                index4 = index2;
            }
            result.put("index_3", index3);
            result.put("index_4", index4);
        } else {
            index3 = index1;
            index4 = index2;
            result.put("index_3", index3> 0? 0: index3);
            result.put("index_4", index4> 0? 0: index4);
        }

        /*方式3：考虑期初的活性指标*/
        if( !(Float.valueOf(avglast)<=Float.valueOf(Paras[2])) && !firstPeriod){
            index5 = - (Float.valueOf(avgthis)-Float.valueOf(Paras[2]))* Integer.valueOf(count);
            index6 = - (Float.valueOf(avgthis)-Float.valueOf(Paras[2]))/(Float.valueOf(Paras[2])!=0? Float.valueOf(Paras[2]): 1);
        } else {
            index5 = index1;
            index6 = index2;
        }
        result.put("index_5", index5);
        result.put("index_6", index6);

        return result;
    }

    private void doSort(){
        List<String> list = new ArrayList<>();
        Iterator<String> item = resultMap.keySet().iterator();
        StringBuffer next;
        while(item.hasNext()){
            next = new StringBuffer(item.next());
            if(resultMap.get(next.toString()).get("firstPeriod").equals("false")){
                list.add(next.toString());
            }else{
                continue;
            }
        }
        Collections.sort(list);
        Iterator<String> item2 = list.iterator();
        result = new String[list.size()][10];
        int i = 0;
        while(item2.hasNext()){
            String key = item2.next();
            result[i][0] = key.replace("%%q%%","-查询-").replace("%%c%%","-填报-");
            for(int j=1;j<=6;j++){
                result[i][j]= resultMap.get(key).get("index"+"_"+String.valueOf(j));
            }
            result[i][7] = resultMap.get(key).get("avglast");
            result[i][8] = resultMap.get(key).get("avgthis");
            result[i][9] = resultMap.get(key).get("count");
            i++;
        }
    }
}