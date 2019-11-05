package com.fr.plugin.performance.analy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/2
 * Description:none
 */
public class Demo {
    public static void main(String[] args) {
        List<Map> tmpList= new ArrayList<>();
        Map tmpMap= new HashMap();
        tmpMap.put("a", 1);
        tmpMap.put("b", 1.234);
        tmpMap.put("c", "点1");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 2);
        tmpMap.put("b", 2.234);
        tmpMap.put("c", "点2");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 2);
        tmpMap.put("b", 2.234);
        tmpMap.put("c", "点3");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 1);
        tmpMap.put("b", 2.134);
        tmpMap.put("c", "点4");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 3);
        tmpMap.put("b", 3.234);
        tmpMap.put("c", "点5");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 4);
        tmpMap.put("b", 4.234);
        tmpMap.put("c", "点6");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 4);
        tmpMap.put("b", 4.134);
        tmpMap.put("c", "点7");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 4);
        tmpMap.put("b", 3.134);
        tmpMap.put("c", "点8");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 4);
        tmpMap.put("b", 2.198);
        tmpMap.put("c", "点9");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 1);
        tmpMap.put("b", 2.198);
        tmpMap.put("c", "点10");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 1);
        tmpMap.put("b", 2.198);
        tmpMap.put("c", "点11");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 2);
        tmpMap.put("b", 2.198);
        tmpMap.put("c", "点12");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 3);
        tmpMap.put("b", 2.198);
        tmpMap.put("c", "点13");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 5);
        tmpMap.put("b", 2.198);
        tmpMap.put("c", "点14");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 3);
        tmpMap.put("b", 2.198);
        tmpMap.put("c", "点15");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        tmpMap.put("a", 1);
        tmpMap.put("b", 2.198);
        tmpMap.put("c", "点16");
        tmpList.add((Map) ((HashMap) tmpMap).clone());
        DiscreteSeries demo= new IrregularDiscreteSeries();
        demo.init(tmpList);
        demo.findPeak("a");
        PeakValleyDiscreteSeries peakDemo = ((IrregularDiscreteSeries) demo).findPeak();
        peakDemo.getPeakReport(3,new String[]{"b"});
    }
}
