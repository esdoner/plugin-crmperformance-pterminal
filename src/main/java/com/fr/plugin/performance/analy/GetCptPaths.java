package com.fr.plugin.performance.analy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fr.data.AbstractTableData;

/**
 * Created by wangtao on 2019/3/26.
 */
public class GetCptPaths extends AbstractTableData {
    public static String path;
    public static String cptEnvPath;
    private String[] columnNames;
    private String[][] rowData;

    public static int id = 1;
    public static int parent = 0;
    public static List<Integer> lastParent = new LinkedList();

    public GetCptPaths() {
        path= this.getClass().getResource("/").getPath();
        cptEnvPath= path.substring(0, path.indexOf("WEB-INF")+7)+"/reportlets";
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        lastParent.add(0);
        getAllFiles(list, cptEnvPath);

        int len = list.size();

        String[][] datas = new String[len][3];

        for (int i = 0; i < list.size(); i++) {
            Map<String, String> pathMap = list.get(i);
            datas[i][0] = pathMap.get("id");
            datas[i][1] = pathMap.get("name");
            datas[i][2] = pathMap.get("parent");
        }
        String[] columnNames = { "id", "name", "parent" };

        this.columnNames = columnNames;
        this.rowData = datas;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public int getRowCount() {
        return rowData.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }

    // 获取文件夹下的所有文件（递归）
    public static void getAllFiles(List<Map<String, String>> list,
                                   String directoryPath) {
        File baseFile = new File(directoryPath);
        if (!baseFile.isFile() && baseFile.exists()) {
            File[] files = baseFile.listFiles();
            for (File file : files) {
                Map<String, String> pathMap = new HashMap();
                pathMap.put("id", id + "");
                pathMap.put("name", file.getName());
                if (parent == 0)
                    pathMap.put("parent", "");
                else
                    pathMap.put("parent", parent + "");
                int tempid = id;
                if (file.getName().indexOf(".") < 0 || parent != 0) {
                    // System.out.println(id + "    " + file.getName() + "   "+
                    // parent);
                    list.add(pathMap);
                    id++;
                }
                if (file.isDirectory()) {
                    parent = tempid;
                    lastParent.add(parent);
                    getAllFiles(list, file.getAbsolutePath());
                }
            }
        }
        parent = getPar();
    }

    // 存储parent的值，链表先进后出模式
    public static int getPar() {
        int par = 0;
        int len = lastParent.size();
        if (len > 2) {
            lastParent.remove(len - 1);
            par = lastParent.get(len - 2);
        } else if (len > 1)
            lastParent.remove(len - 1);
        return par;
    }
}
