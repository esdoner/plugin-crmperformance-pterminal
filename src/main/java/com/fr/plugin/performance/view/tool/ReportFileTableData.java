package com.fr.plugin.performance.view.tool;

import com.fr.general.data.TableDataException;
import com.fr.plugin.performance.util.file.FileHandler;
import com.fr.plugin.performance.view.BaseTableData;

import java.io.File;
import java.util.*;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/05/12
 * Description:生成report lets树结构的TableData，自动过滤隐藏文件，非.cpt.frm文件
 */
public class ReportFileTableData extends BaseTableData {
    private static String path= ReportFileTableData.class.getResource("/").getPath().substring(1);
    private static String cptEnvPath= path.substring(0, path.indexOf("WEB-INF")+ 7)+ "/reportlets";
    private static final String[] COLUMN_NAMES =  { "id", "name", "parent", "path" };
    private static final List<String> VALID_POST_FIX = Arrays.asList(new String[]{"frm", "cpt"});
    private String[][] rowData;

    public ReportFileTableData() { unFinish(); }

    @Override
    public void init(){
        if(!isFinished()){
            List<ReportletNode> list = new ArrayList<>();
            int len;

            getAllFiles(list, cptEnvPath,0);
            Collections.sort(list);
            len = list.size();
            String[][] data = new String[len][COLUMN_NAMES.length];

            for (int i = 0; i < list.size(); i++) {
                data[i][0]= list.get(i).getAttribute("id");
                data[i][1]= list.get(i).getAttribute("name");
                data[i][2]= list.get(i).getAttribute("parent");
                data[i][3]= list.get(i).getAttribute("path");
            }
            this.rowData = data;
        }
        finish();
    }

    private int getAllFiles(List<ReportletNode> list, String directoryPath, int id) {
        File baseFile= new File(directoryPath);
        int parent= id;
        if (baseFile.isDirectory() && baseFile.exists()) {
            File[] files= baseFile.listFiles();
            for (File file: files) {
                if( !FileHandler.isHiden(file)) {
                    id++;
                    Map<String, String> pathMap = new HashMap();
                    pathMap.put("id", id + "");
                    pathMap.put("name", file.getName());
                    pathMap.put("path", file.getPath().replace(cptEnvPath.replace("/", "\\"), "").replace("\\", "/"));
                    if (parent == 0) {
                        pathMap.put("parent", "");
                    } else {
                        pathMap.put("parent", parent + "");
                    }
                    if (file.isDirectory()) {
                        pathMap.put("type", "0");
                        pathMap.put("postFix", "");
                        id = getAllFiles(list, file.getAbsolutePath(), id);
                        list.add(new ReportletNode(pathMap));
                    } else {
                        pathMap.put("type", "1");
                        if (VALID_POST_FIX.contains(FileHandler.getPostFix(file))) {
                            pathMap.put("postFix", FileHandler.getPostFix(file));
                            list.add(new ReportletNode(pathMap));
                        }
                    }
                }
            }
        }
        return id;
    }

    @Override
    public int getColumnCount() throws TableDataException{
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) throws TableDataException {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public int getRowCount() throws TableDataException{
        if( !isFinished() ){ init(); }
        return rowData.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if( !isFinished() ){ init(); }
        return rowData[rowIndex][columnIndex];
    }

    private class ReportletNode implements Comparable<ReportletNode>{
        private Map<String, String> attributes= new HashMap<>();

        public ReportletNode(Map<String, String> var) {
            attributes.putAll(var);
        }

        public String getAttribute(String var){
            return this.attributes.get(var);
        }

        @Override
        public int compareTo(ReportletNode var) {
            short myType= Short.valueOf(this.getAttribute("type"));
            short yourType= Short.valueOf(var.getAttribute("type"));
            String myName= new String(this.getAttribute("name"));
            String yourName= new String(var.getAttribute("name"));
            String myPostFix= new String(this.getAttribute("postFix"));
            String yourPostFix= new String(var.getAttribute("postFix"));
            if(yourType== 1 && myType== 1){
                if(myPostFix.equals(yourName)) {
                    return myName.compareToIgnoreCase(yourName);
                } else {
                    return myPostFix.compareToIgnoreCase(yourPostFix);
                }
            } else if(yourType== 0 && myType== 1) {
                return 1;
            } else if(yourType== 1 && myType== 0) {
                return -1;
            } else if (yourType== 0 && myType== 0){
                return myName.compareToIgnoreCase(yourName);
            }else{
                return 0;
            }
        }
    }
}
