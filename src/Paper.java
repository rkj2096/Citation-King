//clear testing done!!!
//class paper to store paper of all authors

public class Paper{
	private String p_name;
	private String []authors;
    private String cite_link;
	private int cites;
	
	public Paper(String p_name,String []authors,String cite_link,int cites){
		setP_name(p_name);
		setAuthors(authors);
		setCite_link(cite_link);
		setCites(cites);
	}
	
	public void setP_name(String p_name){
		this.p_name=p_name;
	}
	public void setAuthors(String []authors){
		this.authors=authors;
	}
	public void setCite_link(String cite_link){
		this.cite_link=cite_link;
	}
	public void setCites(int cites){
		this.cites=cites;
	}
	public String getP_name(){
		return p_name;
	}
	public String[] getAuthors(){
		return authors;
	}
	public String getCiteLink(){
		return cite_link;
	}
	public int getCites(){
		return cites;
	}
}