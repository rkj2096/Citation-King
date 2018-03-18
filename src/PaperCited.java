
//clear testing done!!!
public class PaperCited{
	private String authors[];
	private boolean self_cite;
	private String cite_link;
	public PaperCited(String[] authors,String cite_link){
		setAuthors(authors);
		setCiteLink(cite_link);
		self_cite=false;
	}
	public void setAuthors(String[] authors){
		this.authors=authors;
	}
	public void setSelfCite(boolean self_cite){
		this.self_cite=self_cite;
	}
	public void setCiteLink(String cite_link){
		this.cite_link=cite_link;
	}
	public String[] getAuthors(){
		return authors;
	}
	public boolean getSelfCite(){
		return self_cite;
	}
	public String getCiteLink(){
		return cite_link;
	}
}