package com.fr.plugin.performance.util.timer;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yuwh on 2019/3/21
 * Description:none
 * @author think
 */
public class FunCalendar {
    private Calendar min = Calendar.getInstance();
    private Calendar max = Calendar.getInstance();
    private String mod;
    private List<HashMap<String,String>> calendar= new ArrayList<>();

    public FunCalendar(Date var1, Date var2, String var3){
        min.setTime(var1);
        max.setTime(var2);
        mod= var3;
        generate();
    }

    private void generate(){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Field field = null;
        try {
            field= Calendar.class.getField("DATE");
            switch(mod.toLowerCase()){
                case "y":
                    sdf= new SimpleDateFormat("yyyy");
                    field= Calendar.class.getField("YEAR");
                    break;
                case "m":
                    sdf= new SimpleDateFormat("yyyy-MM");
                    field= Calendar.class.getField("MONTH");
                    break;
                case "d":
                    sdf= new SimpleDateFormat("yyyy-MM-dd");
                    field= Calendar.class.getField("DATE");
                    break;
            }
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Calendar curr= min;
        HashMap<String, String> temp= new HashMap<>();
        String[] tempRangeArr= new String[2];
        while (curr.before(max) || curr.equals(max)) {
            temp.put("key", sdf.format(curr.getTime()));
            tempRangeArr= getRange(curr);
            temp.put("down", tempRangeArr[0]);
            temp.put("up", tempRangeArr[1]);
            calendar.add((HashMap) temp.clone());
            try {
                curr.add((Integer) field.get(curr), 1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public String[] getRange(Calendar var){
        String[] result= new String[2];
        Calendar temp= Calendar.getInstance();
        switch(mod.toLowerCase()){
            case "y":
                temp.set(var.get(Calendar.YEAR),0,1,0,0,0);
                temp.set(Calendar.MILLISECOND, 0);
                result[0]= String.valueOf(temp.getTimeInMillis());
                temp.set(var.get(Calendar.YEAR),11,31,23,59,59);
                temp.set(Calendar.MILLISECOND, 999);
                result[1]= String.valueOf(temp.getTimeInMillis());
                break;
            case "m":
                temp.set(var.get(Calendar.YEAR),var.get(Calendar.MONTH),1,0,0,0);
                temp.set(Calendar.MILLISECOND, 0);
                result[0]= String.valueOf(temp.getTimeInMillis());
                temp.set(var.get(Calendar.YEAR),var.get(Calendar.MONTH),var.getActualMaximum(Calendar.DAY_OF_MONTH),23,59,59);
                temp.set(Calendar.MILLISECOND, 999);
                result[1]= String.valueOf(temp.getTimeInMillis());
                break;
            case "d":
                temp.set(var.get(Calendar.YEAR),var.get(Calendar.MONTH),var.get(Calendar.DATE),0,0,0);
                temp.set(Calendar.MILLISECOND, 0);
                result[0]= String.valueOf(temp.getTimeInMillis());
                temp.set(var.get(Calendar.YEAR),var.get(Calendar.MONTH),var.get(Calendar.DATE),23,59,59);
                temp.set(Calendar.MILLISECOND, 999);
                result[1]= String.valueOf(temp.getTimeInMillis());
                break;
        }
        return result;
    }

    public List<String[]> getArrayList(){
        int l= calendar.size();
        List<String[]> result= Arrays.asList(new String[l][3]);;
        for(int i= 0; i<l; i++){
            result.get(i)[0]= calendar.get(i).get("key");
            result.get(i)[1]= calendar.get(i).get("down");
            result.get(i)[2]= calendar.get(i).get("up");
        }
        return result;
    }

    public  List<? extends Map> getMapList(){ return calendar; }
    /*测试用例*/
    /*public static void main(String[] args) {
        TimeFormat a= new TimeFormat();
        a.setSDF("yyyy-MM");
        FunCalendar b= new FunCalendar(a.string2Date("2019-01"),a.string2Date("2019-05"),"m");
        System.out.println(b.getArrayList());
    }*/
}
