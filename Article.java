import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Article {

	private String id;
    private String title;
    private String initPage;
    private String endPage;
    private List<Author> authorList = new ArrayList<Author>();


    public Article() {

    }

    public Article(String id, String title, String initPage, String endPage, List<Author> authorList) {
        this.id = id;
        this.title = title;
        this.initPage = initPage;
        this.endPage = endPage;
        this.authorList = authorList;
    }

    public String getId() {
    	return this.id;
    }

    public String getTitle() {
    	return this.title;
    }

    public String getInitPage() {
    	return this.initPage;
    }

    public String getEndPage() {
    	return this.endPage;
    }

    public List<Author> getAuthorList() {
    	return this.authorList;
    }
}