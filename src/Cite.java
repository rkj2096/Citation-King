
import java.util.*;

public class Cite{
	private String author_link;
	private int loop;
	
	public Cite(String al,int l){
		author_link =al;
		loop = l;
	}
	
	//use to generate Paper list
	public Vector<Paper> getPapers()throws java.io.IOException{
		
		Scrap sc = new Scrap(author_link);
		Vector<Paper> pr = new Vector<Paper>();
		
		while(true)
		{
			String next = sc.nextPageLink();
			
			Vector<String> pname =new Vector<String>(); 
			// paper name
			pname = sc.extractPaperName();
			
			Vector<String[]> authors =new Vector<String[]>();
			//authors list
			authors = sc.extractAuthors();
			
			Vector<String> cite_link =new Vector<String>();
			//citation link
			cite_link = sc.extractCiteLink();
			
			Vector<Integer> cites = new Vector<Integer>();
			//citations
			cites = sc.extractCitations();
			
			for(int i=0;i<cites.size();++i)
			{
				pr.add(new Paper(pname.get(i),authors.get(i),cite_link.get(i),cites.get(i)));
			}
			
			if(next.length()==0||cites.size()<pname.size())
			{
				break;
			}
			else
			{
				sc = new Scrap(next);
			}
		}
		return pr;
	}
	
	//use to generate PaperCited object list
	public Vector<PaperCited> getPaperCited(String url)throws java.io.IOException{
		
		CScrap cs = new CScrap(url);
		Vector<PaperCited> pc = new Vector<PaperCited>();
		
		while(true)
		{
			cs.extract();
			String next = cs.nextPageLink();
			
			Vector<String[]> authors =new Vector<String[]>();
			//authors list
			authors = cs.extractAuthors();
			
			Vector<String> cite_link =new Vector<String>();
			//citation link
			cite_link = cs.extractCiteLink();
			
            for(int i=0;i<authors.size();++i)
			{   if(i<cite_link.size())
				pc.add(new PaperCited(authors.get(i),cite_link.get(i))); //array index error
			    else
			    pc.add(new PaperCited(authors.get(i),"Nope"));		
			}
			
			if(next.equals(""))
			{
				break;
			}
			else
			{
			 cs= new CScrap(next);
			}
		}
		return pc;
	}
	
	//run for loop no of iterations

	public int selfCite(Paper p)throws java.io.IOException{
		
		int ss=0; //self citations counter 
		Vector<PaperCited> pc = new Vector<PaperCited>();
		Vector<PaperCited> rem = new Vector<PaperCited>(); //store which are not self cited for further checking
		
		//System.out.println(p.getCiteLink()); //d
		pc = getPaperCited(p.getCiteLink());
		//System.out.println(pc.size()); //d
		
		for(PaperCited ps:pc)
		{
			if(isSelfCite(p,ps))
		    {
				ss++;
			}
            else
			{
			    rem.add(ps);	
			}			
		}
		
		pc = rem;
		int depth = 0;
		Vector<Vector<PaperCited>> vpcs =new Vector<Vector<PaperCited>>();
		// loop 
		while(true)
		{
			if(loop <= depth)
			break;
		    
			if(depth < 1)
			{
              for(int i=0;i<pc.size();i++)
			  {
			    Vector<PaperCited> pds = new Vector<PaperCited>();
				String s = pc.get(i).getCiteLink();
				
				if(s.equals("Nope"))
				break;			
			    pds = getPaperCited(s);
			    
				for(int j=0;j<pds.size();++j)
				{
					if(isSelfCite(p,pds.get(j)))
					{
						ss++;
						break;
					}
                    if( j == pds.size()-1)
                    {
						vpcs.add(pds);
					}						
				}				
			  }
		    }
			else
			{
				Vector<Vector<PaperCited>> tvpcs =new Vector<Vector<PaperCited>>();
				for(int k=0; k<vpcs.size();++k)
				{
				  Vector<PaperCited> ps = new Vector<PaperCited>();
				  boolean tmp = false;
					
					for(int l=0;l<vpcs.get(k).size();++l)
					{
					  Vector<PaperCited> psc = new Vector<PaperCited>();
					  String s = vpcs.get(k).get(l).getCiteLink();
					  
					  if(s.equals("Nope"))
					  break;	  
					  psc = getPaperCited(s);
					  
                        for(int n =0; n<psc.size();n++)
					    {
						   if(isSelfCite(p,psc.get(n)))
						   {
							tmp = true;
							ss ++;
							break;
						   }							
					    }
					  if(tmp)
					  {
						  break;
					  }
					  else
					  {
						ps.addAll(psc);  
					  } 						  
                    }
                  if(!tmp)
                  tvpcs.add(ps);					  
				}
				
			}
			depth++;
		}
		
		return ss;
			
	}
	
	//return true for self citation
	public boolean isSelfCite(Paper p,PaperCited pc)throws java.io.IOException{
        
		String[] pas = p.getAuthors();
		String[] cas = pc.getAuthors();
		
		for(int j=0;j<pas.length;++j)
		{
		  for(int k=0;k<cas.length;++k)
		  {
			   if(pas[j].length() <= cas[k].length())
               {
			     String tmp = cas[k].substring(0,pas[j].length());	
			     if(pas[j].equals(tmp))
			     {
				  return true;		
			     }
			   }				
		  }
        }
		return false;
	}
	
	public String[] info()throws java.io.IOException{
		Vector<Paper> p = new Vector<Paper>();
		
		int tc = 0;
		int tsc = 0;
		String st = "";
		
		p = getPapers();
		
		for(int i=0;i<p.size();++i)
		{   
	        int temp = selfCite(p.get(i));
			tc += p.get(i).getCites();
			tsc += temp;
			st += String.format("\n %34s  %8d  %8d",p.get(i).getP_name(),p.get(i).getCites(),temp);
		}
		
		String rt[] = new String[3];
		rt[0] = String.format("%d",tc);
		rt[1] = String.format("%d",tsc);
		rt[2] = String.format("%s",st);
		return rt;
	}
	public String author()throws java.io.IOException{
		Scrap sc = new Scrap(author_link);
		return sc.author();
	}
	public static void main(String args[])throws java.io.IOException
	{
		Cite c = new Cite("https://scholar.google.co.in/citations?user=omsVC5IAAAAJ&hl=en&oi=ao",0);
		Vector<PaperCited> pcs = new Vector<PaperCited>();
		pcs = c.getPaperCited("https://scholar.google.co.in/scholar?oi=bibs&hl=en&cites=17867205956822112579");
		for(PaperCited pc:pcs)
		{
			for(String s:pc.getAuthors())
			{
				System.out.println(s);
			}
		}
	}
}