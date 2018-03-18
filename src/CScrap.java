//clear testing done!!!

import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class CScrap{
	String url;
	String next;
	Vector<String[]> authors= new Vector<String[]>();
	Vector<String> clink = new Vector<String>();
	
	
	public CScrap(String url){
		this.url=url;
		next = "https://scholar.google.co.in";
	}
	
	
	
	public void extract()throws java.io.IOException{
		
		//coonection establishment
		Map<String,String> m = new HashMap<String,String>();
		m.put("GSP","A=4l0n-w:CPTS=1459531293:LM=1459531293:S=r_yniWJzowTyjHA0");
		m.put("NID","78=aM9UUmMcT1g1NxRP0rm2Xt1FrYsafwnFednLFRWcUt4VswitHtwvU9M-Ie5QKQulzAp0gM0nF3kkPWv0j5gnVvLVjK6GWbUaoLod6aabpiv3gDDdYFqueY8m53yOHFt0");
		
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").cookies(m).followRedirects(false).get();
		
		//next
	    Element el = doc.select("td[align=left] > a").first();
		if(el==null)
			next = "";
		else 
			next = next+el.attr("href");
		
		//authors		
		Elements els = doc.select("div.gs_a");
		for(Element e: els ){
			String s[] = e.text().split(" - ")[0].split(", ");
			s[s.length-1] = s[s.length-1].split("…")[0];
			authors.add(s); //need correction
		}
	
		
		//clink
		Elements els1= doc.select("div.gs_fl > a[href^=/scholar?cites]"); //select input - > done
		for(Element e:els1){
			if(e != null)
			clink.add("https://scholar.google.co.in"+e.attr("href"));
		    else
			clink.add(new String(""));	
		}
		
	}
	public String nextPageLink()throws java.io.IOException{
		return next;
	}
	public Vector<String[]> extractAuthors()throws java.io.IOException{
		return authors;
	}
	public Vector<String> extractCiteLink()throws java.io.IOException{
		return clink;
	}
	
}