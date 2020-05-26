package com.climber.cloud_basecore.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTest {

    public static AtomicInteger rowNo= new AtomicInteger(0);

    public static Map<String,String> userIdAndUrlsMap = new HashMap<>();
    public static String excelName="684454";


    public static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    public static  ExecutorService threadPoolExecutor = new ThreadPoolExecutor(17, 17,
            3000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(3000000), namedThreadFactory);


//    public static        ExecutorService threadPoolExecutor2 = new ThreadPoolExecutor(10, 10,
//                    60L, TimeUnit.SECONDS,
//                    new SynchronousQueue<Runnable>());


    public static void main(String[] args) throws InterruptedException, IOException {

        long startTime=System.currentTimeMillis();   //获取开始时间
        System.out.println("开始处理数据:"+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));


        String filePath = "/Users/zhoushengqiang/Desktop/dkxy/"+excelName+".xlsx";
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
                    if(userIdAndUrlsMap.containsKey(userId)&&!"".equals(url)){
                        userIdAndUrlsMap.put(userId, userIdAndUrlsMap.get(userId)+";"+url);
                    }else {
                        if(!"".equals(url)){
                            userIdAndUrlsMap.put(userId,url);
                        }
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

        long readExcelEndTime=System.currentTimeMillis();
        System.out.println("读取excel,获取url地址共花费:"+(readExcelEndTime-startTime)/1000+"秒");

        mulDownPdf();

        FileDown.writeQingDanList(excelName);

        //获取开始时间
        long end=System.currentTimeMillis();
        System.out.println("全部工作,合计耗时:"+(end-startTime)/1000+"秒");

    }


    /**
     * 多线程下载文件,并改名
     */
    private static void mulDownPdf() {

        long start=System.currentTimeMillis();

        Iterator<Map.Entry<String, String>> iterator = userIdAndUrlsMap.entrySet().iterator();
        Integer mapNum=0;

        while (iterator.hasNext()){
            mapNum++;
            Map.Entry<String, String>  entry= iterator.next();
            String urls =entry.getValue();

            threadPoolExecutor.execute(new Runnable() {
                private int number=0;
                public Runnable setNumber(int name)
                {
                    this.number = name;
                    return this;
                }

                @Override
                public void run() {
                    FileDown.downloadFile(urls,"/Users/zhoushengqiang/Desktop/dkxy",number);
                }
            }.setNumber(mapNum));

        }
        threadPoolExecutor.shutdown();
        while(true){
            if(threadPoolExecutor.isTerminated()){
                break;
            }
        }
        long end=System.currentTimeMillis();
        System.out.println("多线程下载耗时:"+(end-start)/1000+"秒");
        System.out.println("一共处理:"+mapNum+"条");

    }
}
