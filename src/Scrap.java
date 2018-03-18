//clear testing done !!!
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class Scrap{
	String url;
	public Scrap(String link){
		url=link;
	}
	
	public Document connection()throws java.io.IOException{
		Map<String,String> m = new HashMap<String,String>();
		m.put("GSP","A=4l0n-w:CPTS=1459531293:LM=1459531293:S=r_yniWJzowTyjHA0");
		m.put("NID","78=aM9UUmMcT1g1NxRP0rm2Xt1FrYsafwnFednLFRWcUt4VswitHtwvU9M-Ie5QKQulzAp0gM0nF3kkPWv0j5gnVvLVjK6GWbUaoLod6aabpiv3gDDdYFqueY8m53yOHFt0");
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").followRedirects(false).cookies(m).get();
		return doc;
	}
	public String author()throws java.io.IOException
	{
		return connection().select("div#gsc_prf_in").first().text();
	}
	
	public String nextPageLink()throws java.io.IOException{
		String suf = url.substring(url.length()-11,url.length());
		String next = "";
		if(suf.equals("pagesize=20"))
		{
			int s = Integer.parseInt(url.substring(url.length()-14,url.length()-12));
		    s += 20;
		    String pre = url.substring(0,url.length()-14);
		     next = String.format("%s%d&pagesize=20",pre,s);
		}
		else
		{
			next = String.format("%s&cstart=20&pagesize=20",url);
		}
		Element el= connection().select("button#gsc_bpf_next[disabled class]").first(); //select correction
		if(el == null)
		{
			return next;
		} 
		return "";
	}
	
	public Vector<String> extractPaperName()throws java.io.IOException{
		Vector<String> pn = new Vector<String>();
	    Document doc = connection();
		Elements els = doc.select("td.gsc_a_t > a");
		for(Element e: els){
			pn.add(e.text());
		}
		return pn;
	}
	//correction for ...
	public Vector<String[]> extractAuthors()throws java.io.IOException{
	    Vector<String[]> ath = new Vector<String[]>();
		Document doc = connection();
		Elements els = doc.select("a.gsc_a_at + div.gs_gray");
		Elements elsa = doc.select("td.gsc_a_t > a");
		for(int e=0; e<els.size();++e){
         	String s[] =els.get(e).text().split(", ");
			
			//check if more authors are
			if(s[s.length-1].equals("..."))
			{
			 String nl = "https://scholar.google.co.in" + elsa.get(e).attr("href"); //get link to go to page
			 
			 //connection 
             Map<String,String> m = new HashMap<String,String>();
		     m.put("GSP","A=4l0n-w:CPTS=1459009689:LM=1459009689:S=7p_oz4i-a-ZiPjDn");
		m.put("NID","77=C01Nt3YnMZqBsNdCjoSGlYOFrkvN7DNmVSgm-Isw89tlt6IoDiyAzIqNBlMPYYZXp7l_HyujZASh6MAi7YAOm92Q1FAOeEA9RdI7xCa_uJOuHiUGqnkuhGXjFpWhdGoA");
		     Document doci = Jsoup.connect(nl).userAgent("Mozilla/5.0").cookies(m).get();
			 
			 //extract authors name
             String at[] = doci.select("div.gsc_value").first().text().split(", ");
			 
			 //sort name for matching
             for(int i=0;i<at.length;++i)
			 {   if(i <= s.length-2)
				 {
				  at[i] = s[i];
				  continue;
				 }
				String tm[]=at[i].split(" ");
				at[i] = "";
                for(int j=0;j<tm.length-1;++j)
				{
					at[i]+=tm[j].charAt(0);
				}
                at[i] += " "+tm[tm.length-1];				
			 }
             ath.add(at);			 
			}
			else
			ath.add(s);
		}
		return ath;
	}
	
	public Vector<String> extractCiteLink()throws java.io.IOException{
		Vector<String> clink = new Vector<String>();
		Document doc = connection();
		Elements els = doc.select("td.gsc_a_c > a[href]");
		for(Element e: els){
			if(e.attr("href").length()>0)
			clink.add(e.attr("href"));
		    else 
			break;
		}
		return clink;
	}
	//need to fix - >fixed
	public Vector<Integer> extractCitations()throws java.io.IOException{
		Vector<Integer> cites = new Vector<Integer>();
		Document doc = connection();
		Elements els = doc.select("td.gsc_a_c > a");
		for(Element e: els){
			if(e.attr("href").length()==0){
			 break;	
			}else
			cites.add(Integer.parseInt(e.text()));
		}
		return cites;
	}
	
}