import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SigmodRecord {

	private String id;

	private int volume;

	private String number;
	
	private List<Article> articleList = new ArrayList<Article>();

	public SigmodRecord(){
		
	}
	
	// public SigmodRecord(String title, int initPage, int endPage, String authors) {
	// 	this.title = title;
	// 	this.initPage = initPage;
	// 	this.endPage  = endPage;
	// 	this.authors = authors;
		
	// }

	public String getIssueId() {
		return id;
	}

	public void setIssueId(String id) {
		this.id = id;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(String id, String title, String initPage, String endPage, List<Author> authorList) {
		this.articleList.add(new Article(id, title, initPage, endPage, authorList));
	}
}