package com.climber.cloud_basecore.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTest {

    public static Map<String,String> map= new HashMap<>();

    public static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    public static  ExecutorService threadPoolExecutor = new ThreadPoolExecutor(10, 10,
            3000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(3000), namedThreadFactory);


    public static        ExecutorService threadPoolExecutor2 = new ThreadPoolExecutor(10, 10,
                    60L, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>());


    public static void main(String[] args) throws InterruptedException {
        long startTime=System.currentTimeMillis();   //获取开始时间


        String filePath = "/Users/zhoushengqiang/Desktop/dkxy/1000.xlsx";
        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            Workbook workbook = null;
            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls") || filePath.endsWith(".et")) {
                workbook = new HSSFWorkbook(fis);
            }
            fis.close();
            /* 读EXCEL文字内容 */
            // 获取第一个sheet表，也可使用sheet表名获取
            Sheet sheet = workbook.getSheetAt(0);
            // 获取行
            Iterator<Row> rows = sheet.rowIterator();
            Row row;
            while (rows.hasNext()) {
                row = rows.next();
                String userId =POIUtil.getCellValue(row.getCell(1));
                String url =POIUtil.getCellValue(row.getCell(2));

                if(!userId.equals("lUserId")){
                    if(map.containsKey(userId)){
                        map.put(userId,map.get(userId)+";"+url);
                    }else {
                        map.put(userId,url);
                    }
                }


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        final Map<String,Integer > num=new HashMap<>();


        Integer mapNum=0;

        while (iterator.hasNext()){
            mapNum++;
            Map.Entry<String, String>  entry= iterator.next();
            String lUserId = entry.getKey();
            String urls =entry.getValue();


//            ExecutorService threadPoolExecutor1 = new ThreadPoolExecutor(10, 10,
//                    60L, TimeUnit.SECONDS,
//                    new SynchronousQueue<Runnable>());

            threadPoolExecutor.execute(new Runnable() {

                private int number=0;
                public Runnable setNumber(int name)
                {
                    this.number = name;
                    return this;
                }


                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    FileDown.downloadFile(urls,"/Users/zhoushengqiang/Desktop/dkxy",number);
                }
            }.setNumber(mapNum));

        }
        threadPoolExecutor.shutdown();
        while(true){
            if(threadPoolExecutor.isTerminated()){
                //System.out.println("所有的子线程都结束了！");
                break;
            }
        }


     //   mulDownPdf();
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"s");

    }

    private static void mulDownPdf() {

    }
}
