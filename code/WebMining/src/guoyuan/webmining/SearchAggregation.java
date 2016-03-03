package guoyuan.webmining;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTML;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class SearchAggregation extends Aggrgation {
	
	 public static void main(String[] args) throws IOException
	 {
		 System.out.println("Please Enter keyword here:");
		 Scanner scanner = new Scanner(System.in);
		 searchword = scanner.next();
		 scanner.close();
		 Pattern pattern = Pattern.compile("\\s+?");
		 Matcher matcher = pattern.matcher(searchword);
		 searchword = matcher.replaceAll("");
//	     searchword = "xiangni";
//		 getGoogleResult(searchword);
         getBaiduResult(searchword);
		 getBingResult(searchword);	
		 removeRepeatItem();
		 getFinalItems();
		 openMetaAggregation();	
		 System.out.println("OnePlus Done!");
	 }
	 public static void  removeRepeatItem()
	  {
		 baiduItemSize = baiduItems.size();
		 bingItemSize = bingItems.size();
		 MetaItem fMetaItem = null;
		 MetaItem sMetaItem = null;
		 
		 
		 for(int i = 0; i < baiduItems.size(); i++)
		 {
		
			 fMetaItem = baiduItems.get(i);
			 for(int k = 0; k < bingItems.size(); k++)
			 {				 
				 sMetaItem = bingItems.get(k);				 
				  if(similarCheck(fMetaItem.getKeyString(), sMetaItem.getKeyString()))
				  {
					  double sorce = fMetaItem.getSorce() * (baiduItemSize - fMetaItem.getSequence())/(fMetaItem.getSequence() + 1) +sMetaItem.getSorce() * (bingItemSize - sMetaItem.getSequence())/(fMetaItem.getSequence() + 1) ;
					  if(fMetaItem.getKeyString().length() >= sMetaItem.getKeyString().length())
					  {	  //�����ٶȵ�����
                         baiduItems.get(i).setSorce(sorce);
						 bingItems.remove(k--);
						 if(k <= 0) k = 0;
//						 System.out.println("bingSize = " + bingItems.size());
					  }
					  else
					  {	  //����bing������
						  bingItems.get(k).setSorce(sorce);
						  baiduItems.remove(i--);
						  if(i <= 0) i = 0;
//							 System.out.println("baiduSize = " + baiduItems.size());
					  }
				  }
				//  baiduItemSize = ba
			 }
		 }
  	}
     public static void getFinalItems()
      {
		 for(int i = 0 ; i < baiduItems.size(); i++)
		 {
			 items.add(baiduItems.get(i));
		 }
		 for(int k = 0; k < bingItems.size(); k++)
		 {
			 items.add(bingItems.get(k));
		 }
      }	
     public static void openMetaAggregation() throws IOException
	  {
    	 
    	 BufferedWriter   bufw = new  BufferedWriter(new FileWriter("result\\meta.html"));
         BufferedReader bufr = new BufferedReader(new FileReader("result\\head.txt"));
         String line = null;
         while( (line = bufr.readLine()) != null)
         {
        	 bufw.append(line);
        	 bufw.flush();
         }
         
         String onePlusString = "<font color=\"#000000\" face=\"��Բ\" size=\"+3\">" + 
        		                 "OnePlus Meta Search :" +  spaceUnit + searchword  + "<br/>" +
        		                 baidu + spaceUnit +  baiduItemSize +  getWhiteSpace(2) + "Redundancy Items " + (baiduItemSize - baiduItems.size()) + "<br/>" +
        		                 bing + spaceUnit + bingItemSize +  getWhiteSpace(2)  + "Redundancy Items " +  (bingItemSize - bingItems.size()) + "<br/>" +
        		                  "</font>" + "<br/>" + "<br/>" + "<br/>";
         bufw.append(onePlusString);
         bufw.flush();
               
		 Iterator<MetaItem> it = items.iterator();
		 int kkkkk = 0;
		 while(it.hasNext())
		 {
			 MetaItem metaItem = it.next();
			 bufw.append("<font color=\"#FF0000\" face=\"��Բ\" size=\"+2\">" + "Rank " + kkkkk + getWhiteSpace(3) +
					 metaItem.getEngine() +   "</font>\n" + metaItem.getContent());
			 kkkkk++;
			 bufw.flush();
		 }		 
         bufr = new BufferedReader(new FileReader("result\\bottom.txt"));
         line = null; 
         while( (line = bufr.readLine()) != null)
         {
        	 bufw.append(line);
        	 bufw.flush();
         }
			try
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Runtime.getRuntime().exec("D:/��װ���/BaiduBrowser/baidubrowser.exe D:/eclipse/wrkspace/WebMining/result/meta.html");
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}		 
    	}	
	 public static String getHtmlByUrl(String url){  
	        String html = null;  
	        HttpGet httpget = new HttpGet(url);//��get��ʽ�����URL  
	        try {  
	            HttpResponse responce = client.execute(httpget);//�õ�responce����  
	            int resStatu = responce.getStatusLine().getStatusCode();//������  
	            if (resStatu==HttpStatus.SC_OK) {//200����  �����Ͳ���  
	                //�����Ӧʵ��  
	                HttpEntity entity = responce.getEntity(); 
	                if (entity!=null) {  
	                    html = EntityUtils.toString(entity);//���htmlԴ����  
	                }  
	            }  
	        } catch (Exception e) {  
	            System.out.println("���ʡ�"+url+"�������쳣!");  
	            e.printStackTrace();  
	        } finally {  
	        	//client.getConnectionManager().shutdown();  
	        }  
	        return html;  
	    }  	 		
     public static void getGoogleResult(String searchword) throws IOException
	    {
	    	String googleURL = "http://www.google.com.hk/search?q="+searchword;	    	
	    	String html = getHtmlByUrl(googleURL);
	    	System.out.println(html);
	    	
	    	Matcher matcher = googlePattern.matcher(html);
	    	int sequence = 0;
	    	while(matcher.find())
	    	{
	    		String sample = matcher.group();
		        removeMatcher = removePattern.matcher(sample);
		        String  keyString =  removeMatcher.replaceAll("");
	    		googleItems.add(new MetaItem(google, sample, sequence,1.2,keyString));
	    		sequence++;
	    	}
	    	
	    }	        
	 public static void getBaiduResult(String searchword) throws IOException
		{
	    	//�ٶȲ�ѯ��URL
	    	String baiduURL = "http://www.baidu.com/s?wd=" + searchword;
	       //��ȡ���ص�html�ļ�
	    	String html = getHtmlByUrl(baiduURL);
	  //  	System.out.println(html);
	    	//ʹ�ðٶȵ�ƥ���㷨�����ٶ�ץȡ����Item��ȡ����
	    	Matcher matcher = baiduPattern.matcher(html);
	    	int sequence = 0;
	    	while(matcher.find())
	    	{
	    		String sample = matcher.group();
	    		if(sequence == 0)	    			
	    		{
	    		  Matcher dealMatcher = baidDealPattern.matcher(sample);
	    		  sample = dealMatcher.replaceAll("");
	    		}
	    		//ʹ��removeģʽ��ÿ��Item�еķ�HTML�޳���ע�⣺����û���޳��ɾ�������ٶ��е�һ��Item�а�����javaScript����
	    		removeMatcher = removePattern.matcher(sample);
	    		//�޳�����Ԫ�غ��ֵ�γɸ�item�Ĺؼ���
	    		String   keyString =  removeMatcher.replaceAll("");
	    		//�������ɵ�Item���浽�ٶ���
	    		baiduItems.add(new MetaItem(baidu,sample,sequence,1.2,keyString));
	    		sequence++;
	    	}
		}
	 public static void getBingResult(String searchword)
		{
	    	//����˵�� ��ͬ getBaiduResult(String searchword) throws IOException
	    	String bingURL = "http://hk.bing.com/search?q=" + searchword;
	    	String html = getHtmlByUrl(bingURL);
//	    	System.err.println(html);
	    	Matcher matcher = bingPattern.matcher(html);
	    	int sequence = 0;
	    	while(matcher.find())
	    	{
	    		String sample = matcher.group();
		        removeMatcher = removePattern.matcher(sample);
		        String   keyString =  removeMatcher.replaceAll("");
	    		bingItems.add(new MetaItem(bing, sample, sequence,1.2,keyString));
	    		sequence++;
	    	}
		}
	  //�������item�Ƿ�����
	 public static boolean similarCheck(String first, String second)  
	  {
		  //��������ԣ����ж��Ƿ��ظ�
		  String shortOne = first;
		  String longOne = second;
		  int firstlength = first.length();
		  int secondlength = second.length();		  
		  if(firstlength > secondlength)
		  {
			  shortOne = second;
			  longOne = first;
		  }
		  int checklength = shortOne.length();
		  int count = 0;
		  for(int i = 0;i < checklength;i++)
		  {
			  for(int k = i+1; k < checklength; k++)
			  {
				 if(longOne.contains(shortOne.subSequence(i, k))) count += k;
			  }
		  }
	     return ((double)count / ((checklength + 1)*checklength /2)) > 2.7 ? true : false;			  
	  }
	 
	 
	 public static String getWhiteSpace(int k)
	 {  
		 String whiteSpace = "";
		 while((k--) > 0)
           whiteSpace += spaceUnit;
		 return whiteSpace;
	 }
}




