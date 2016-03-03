package guoyuan.webmining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpService;
import org.apache.http.util.EntityUtils;


public class Aggrgation{
	
	public static final String UTF_8 = "UTF-8";
	
	public static HttpClient client = new DefaultHttpClient();
	
	public static TreeSet<MetaItem>  items = new TreeSet<MetaItem>();
	public static ArrayList<MetaItem> googleItems = new ArrayList<MetaItem>();
	public static ArrayList<MetaItem> baiduItems = new ArrayList<MetaItem>();
	public static ArrayList<MetaItem> bingItems = new ArrayList<MetaItem>();
	
	public static String google = "GOOGLE";
	public static String baidu = "Baidu";
	public static String bing = "Bing";
	public static String googleReg = "<li class=\"g\"><h3 class=\"r\">.+?</li>";
	public static String baiduReg = "<table.+?><tr><td.+?><h3.+?>.+?</div></td></tr></table>";
	public static String bingReg = "<li class=\"sa_wr.+?\">.+?</div></div></li>";
//	<li class=\"record.+?\">.+?<><h3 class=\"title\">.+?</li>
	
	public static String baiduDealReg = "<table .+?><tr><td .+?>.+?<div id=\"content_left\">";
	public static Pattern baidDealPattern = Pattern.compile(baiduDealReg,Pattern.DOTALL);
	public static Pattern googlePattern = Pattern.compile(googleReg,Pattern.DOTALL);
	public static Pattern baiduPattern = Pattern.compile(baiduReg,Pattern.DOTALL);
	public static Pattern bingPattern = Pattern.compile(bingReg,Pattern.DOTALL);
	public static String  removeHtml = "<.+?>";
	public static Matcher removeMatcher = null;
	public static Pattern removePattern = Pattern.compile(removeHtml, Pattern.DOTALL);
    public static  int baiduItemSize = 0;
	public static  int bingItemSize = 0;
    public static String spaceUnit = "&nbsp;";
    public static String searchword = "";
   

	

}
