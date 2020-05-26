package com.climber.cloud_basecore.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.xml.crypto.Data;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.climber.cloud_basecore.util.ExcelTest.excelName;

public class FileDown {




    public static Map<String,String> identityAndNamsMap= new HashMap<>();

	/**
	 * 说明：根据指定URL将文件下载到指定目标位置
	 *
	 * @param urlPath
	 *            下载路径
	 * @param downloadDir
	 *            文件存放目录
	 * @return 返回下载文件
     *
     *
	 */
	@SuppressWarnings("finally")
	public static File downloadFile(String urlPath, String downloadDir, Integer countNum) {
		File file = null;
        String urlError="";
		try {
			String[] strings=new String[1];
			// 统一资源
			if(urlPath.contains(";")){
				strings = urlPath.split(";");

			}else{
				strings[0]=urlPath;
			}
			int num=0;
			String 	identity="";
			String xyDocumentName="";

			for(String urlTemp:strings){
                urlError=urlTemp;
				num++;
				URL url = new URL(urlTemp);
				// 连接类的父类，抽象类
				URLConnection urlConnection = url.openConnection();
				// http的连接类
				HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
				//设置超时
				httpURLConnection.setConnectTimeout(1000*5);
				//设置请求方式，默认是GET
				httpURLConnection.setRequestMethod("GET");
				// 设置字符编码
				httpURLConnection.setRequestProperty("Charset", "UTF-8");
				// 打开到此 URL引用的资源的通信链接（如果尚未建立这样的连接）。
				httpURLConnection.connect();
				// 文件大小
				int fileLength = httpURLConnection.getContentLength();

				// 控制台打印文件大小
				//System.out.println("您要下载的文件大小为:" + fileLength / (1024 * 1024) + "MB");

				// 建立链接从请求中获取数据
				URLConnection con = url.openConnection();
				BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
				// 指定文件名称(有需求可以自定义)
				String fileFullName = urlPath.substring(urlPath.lastIndexOf("/"),urlPath.length());


				// 指定存放位置(有需求可以自定义)
				String path = downloadDir + File.separatorChar + fileFullName;
				file = new File(path);
				// 校验文件夹目录是否存在，不存在就创建一个目录
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}

				OutputStream out = new FileOutputStream(file);
				int size = 0;
				int len = 0;
				byte[] buf = new byte[2048];
				while ((size = bin.read(buf)) != -1) {
					len += size;
					out.write(buf, 0, size);
				}
				// 关闭资源
				bin.close();
				out.close();


				identity = dealPdf(file);
				if(xyDocumentName==""){
					xyDocumentName= excelName+"_"+String.format("%08d", countNum) +"_"+String.format("%02d", num) +".pdf";
				}else{
					xyDocumentName =xyDocumentName +";"+excelName+"_"+String.format("%08d", countNum) +"_"+String.format("%02d", num) +".pdf";
				}

				String pathNew = downloadDir + File.separatorChar + excelName+"_"+String.format("%08d", countNum) +"_"+String.format("%02d", num)+".pdf";
				file.renameTo(new File(pathNew));

			}

            if(countNum%100==0){
                System.out.println("已经下载了"+countNum+"条数据"+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }


			//写入map
            identityAndNamsMap.put(identity,xyDocumentName);



		} catch (MalformedURLException e) {
            System.out.println("出错的url"+urlError);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("文件下载失败！");
		} finally {
			return file;
		}

	}

	public static String  dealPdf(File file){
		File pdfFile = file;
		PDDocument document = null;
		try
		{
			document=PDDocument.load(pdfFile);
			// 获取页码
			int pages = document.getNumberOfPages();

			// 读文本内容
			PDFTextStripper stripper=new PDFTextStripper();
			// 设置按顺序输出
			stripper.setSortByPosition(true);
			stripper.setStartPage(1);
			stripper.setEndPage(pages);
			String content = stripper.getText(document);

			String identityNo = content.substring(content.indexOf("身份证号码：")+6,content.indexOf("身份证号码：")+24);
			//System.out.println("身份证号码:"+identityNo);
			document.close();
			return identityNo;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return "--";
		}
	}


	public  static void writeQingDanList(String shanghuhao) throws IOException {

        Iterator<Map.Entry<String, String>> iterator = identityAndNamsMap.entrySet().iterator();

        OutputStream outputStream = null;
        SXSSFWorkbook wb = new SXSSFWorkbook();
        try {
            System.out.println("执行中...");
            //获取开始时间
            long startTime=System.currentTimeMillis();

            Sheet sheet = wb.createSheet("sheet1");
            Row row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("身份证号");
            cell = row.createCell(1);
            cell.setCellValue("商户订单号");
            cell = row.createCell(2);
            cell.setCellValue("协议文件编号");


            int  rowNo=0;
            while (iterator.hasNext()){
                rowNo++;
                Map.Entry<String, String> next = iterator.next();
                row = sheet.createRow(rowNo);
                cell = row.createCell(0);
                cell.setCellValue(next.getKey());

                cell = row.createCell(1);
                cell.setCellValue(shanghuhao);

                cell = row.createCell(2);
                cell.setCellValue(next.getValue());
            }


            String path ="/Users/zhoushengqiang/Desktop/dkxy/"+excelName+"_扣款协议清单.xlsx";
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(new File(path));
            wb.write(outputStream);
            outputStream.flush();
            long end=System.currentTimeMillis();
            System.out.println("写Excel清单耗时:"+(end-startTime)/1000+"秒");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(wb!=null){
                    wb.close();
                }
                if(outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }



	/**
	 * 测试
	 *s
	 * @param args
	 */
	public static void main(String[] args) {
		// 指定资源地址，下载文件测试
		//downloadFile("https://dafyossfile.dafy.com/OSS/20190615/150d55e8b9bf489bbcc480f70a30a614.pdf", "/Users/zhoushengqiang/Desktop/dkxy");

	}
}
