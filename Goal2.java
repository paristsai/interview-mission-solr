import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Iterator;
import java.io.IOException;


import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;

public class Goal2 {

    public static void main(String[] args) throws IOException, SolrServerException {

    String solrUrl = "http://localhost:8983/solr/sigmodrecord";
    HttpSolrClient client = new HttpSolrClient(solrUrl);

    client.setConnectionTimeout(5000);
    client.setSoTimeout(10000);
    client.setDefaultMaxConnectionsPerHost(100);
    client.setMaxTotalConnections(100);
    client.setFollowRedirects(false);
    client.setAllowCompression(true);

    Scanner scanner = new Scanner(System.in);
    System.out.println("Please Use quote marks to include the author's name.\nEnter the AUTHOR'S NAME : ");
    String name = scanner.nextLine();

    SolrQuery solrQuery = new SolrQuery();
    solrQuery.setQuery("name:"+ name);
    solrQuery.setParam("rows", "200");

    QueryResponse rsp = client.query( solrQuery );
    SolrDocumentList docs = rsp.getResults();

    int normal_score = 0;
    float weighted_score = 0;
    int total = 0;
    for (SolrDocument doc : docs) {
        int author_num = Integer.parseInt(doc.getFieldValue("a_num").toString());
        int position = Integer.parseInt(doc.getFieldValue("position").toString());
        normal_score += 1;
        weighted_score += (float)(author_num - position) / author_num;
    }
    
    System.out.print("normal_score: " + normal_score);
    System.out.print("\nweighted_score: " + weighted_score + "\n");
    System.out.println( "Query URL:\nhttp://localhost:8983/solr/sigmodrecord/select?" + solrQuery + "\n");
    }

}