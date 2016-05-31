import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Scanner;
import java.util.Iterator;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.client.solrj.response.TermsResponse;

import org.json.JSONObject;
import org.json.JSONArray;


public class Goal3 {

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
        System.out.println("Please enter the query string for TITLE field. Default string is *");
        String input = scanner.nextLine();

        if (input.isEmpty()) {
            input = "*";
        }
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("title:" + input);
        solrQuery.setParam("qt", "/tvrh");
        solrQuery.setParam("rows", "50");
        solrQuery.setParam("tv.tf_idf", true);

        QueryResponse queryResponse = client.query(solrQuery);
        
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("max row: 50;  total row: " + solrDocumentList.size());

        String queryString = "http://localhost:8983/solr/sigmodrecord/tvrh?indent=on&q=title:" + input + "&rows=20&tv.tf_idf=true&wt=json";
        URL url = new URL(queryString);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String jsonResult = "";
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                jsonResult += strTemp;
            }

            JSONObject obj = new JSONObject(jsonResult);
            JSONArray arr = obj.getJSONArray("termVectors");
            for (int i = 0; i < arr.length(); i = i + 2) {
                System.out.println(arr.get(i)+ "\n" + arr.get(i + 1) + "\n");
            }
            System.out.println("Query URL: " + queryString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}