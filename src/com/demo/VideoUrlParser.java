package com.demo;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class VideoUrlParser {

    /**
     * Converts the url to the downloadable url
     * 
     * @param url
     * @return downloadable url or null
     */
    public String parse(String url) throws Exception
    {
    	String strKejFlvUrl = "http://kej.tw/flvretriever/youtube.php?videoUrl=" + url;
    	
    	Document doc = Jsoup.connect(strKejFlvUrl).get();
    	String title = doc.title();
    	System.out.println("title is " + title);
//    	for(Element element : doc.getAllElements())
//    	{
//    		System.out.println(element.nodeName());
//    	}
    	
    	Element elmtResultArea = doc.getElementById("resultarea");
    	
//    	for (Element elementAhref : elmtResultArea.getElementsByTag("a"))
//    	{
//    		System.out.println(elementAhref.html());
//    	}
    	
    	String strFlvInfoHref = elmtResultArea.getElementsMatchingOwnText("¤U¸ü¦¹ÀÉ®×").attr("href");
    	
    	InputStream is = new URL(strFlvInfoHref).openConnection().getInputStream();
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	byte[] buffer = new byte[1024];
        for (int length; (length = is.read(buffer)) > 0; baos.write(buffer, 0, length));
        is.close();
        
        String strVideoInfo = new String(baos.toByteArray());
    	System.out.println("flv info : " + strVideoInfo);
    	
    	
		Map<String,String> allFields = new HashMap<String,String>();
		allFields.put("videoInfo", strVideoInfo);
    	Response response = Jsoup.connect(strKejFlvUrl).userAgent(System.getProperty("http.agent")).data(allFields).followRedirects(true).execute();
    	System.out.println(response.body());
//    	Element elmtResultDiv = docResult.getElementById("result_div");
//    	for(Element element : elmtResultDiv.getAllElements())
//    	{
//    		System.out.println(element.text());
//    	}
        
        return null;
    }

}
