import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import static java.lang.Math.pow;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;


public class Goal1 {

	public static void main(String[] args) throws IOException, SolrServerException {

        String solrUrl = "http://localhost:8983/solr/sigmodrecord";
        HttpSolrClient client = new HttpSolrClient(solrUrl);

        client.setConnectionTimeout(5000);
        client.setSoTimeout(10000);
        client.setDefaultMaxConnectionsPerHost(100);
        client.setMaxTotalConnections(100);
        client.setFollowRedirects(false);
        client.setAllowCompression(true);


    	ReadXMLFile t = new ReadXMLFile();
        List<SigmodRecord> recordList = t.myRecords();

        Collection<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();

        for (int i = 0; i < recordList.size(); i++) {
        	SolrInputDocument tempRecord = new SolrInputDocument();
        	SigmodRecord sigmodRecord = recordList.get(i);
        	tempRecord.addField("id", sigmodRecord.getIssueId());
        	tempRecord.addField("volume", sigmodRecord.getVolume());
        	tempRecord.addField("number", sigmodRecord.getNumber());
        	tempRecord.addField("type", "record");
        	
        	List<Article> articleList = sigmodRecord.getArticleList();
        	for (int j = 0; j < articleList.size(); j++) {
        		SolrInputDocument tempArticle = new SolrInputDocument();
        		Article article = articleList.get(j);
        		tempArticle.addField("id", article.getId());
        		tempArticle.addField("title", article.getTitle());
        		tempArticle.addField("initPage", article.getInitPage());
        		tempArticle.addField("endPage", article.getEndPage());
        		tempArticle.addField("type", "article");

        		List<Author> authorList = article.getAuthorList();
        		for (int k = 0; k < authorList.size(); k++) {
        			SolrInputDocument tempAuthor = new SolrInputDocument();
        			Author author = authorList.get(k);
        			tempAuthor.addField("id", author.getId());
        			tempAuthor.addField("position", parseInt(author.getPosition()));
        			tempAuthor.addField("a_num", authorList.size());
        			tempAuthor.addField("name", author.getName());
        			tempAuthor.addField("type", "author");

        			tempArticle.addChildDocument(tempAuthor);
        		}
        		tempRecord.addChildDocument(tempArticle);
        	}
        	batch.add(tempRecord);
        }
        client.add(batch);
        client.commit();

	}
	public static int parseInt(String position) {
		int sum = 0;
		for (int i = 0; i < position.length(); i++) {
			int number = Character.getNumericValue(position.charAt(i));
			sum += number * Math.pow(10, position.length() - i - 1);
		}
		return sum;
	}

}