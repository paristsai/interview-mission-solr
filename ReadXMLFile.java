import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReadXMLFile {

    public List<SigmodRecord> myRecords() {

        List<SigmodRecord> recordList = new ArrayList<SigmodRecord>();

        try {

            File fXmlFile = new File("/home/ubuntu/search_engine/SigmodRecord.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            FileInputStream in = new FileInputStream(fXmlFile);

            Document doc = dBuilder.parse( in );

            doc.getDocumentElement().normalize();

            // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("issuesTuple");

            // System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                // System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    SigmodRecord tempRecord = new SigmodRecord();

                    Element eElement = (Element) nNode;
                    int volume = Integer.parseInt(eElement.getElementsByTagName("volume").item(0).getTextContent());
                    String number = eElement.getElementsByTagName("number").item(0).getTextContent();

                    tempRecord.setIssueId("issue_" + temp);
                    tempRecord.setVolume(volume);
                    tempRecord.setNumber(number);
                    
                    NodeList articles = eElement.getElementsByTagName("articlesTuple");
                    for (int i = 0; i < articles.getLength(); i++) {
                        Element article = (Element) articles.item(i);
                                                
                        List<Author> authorList = new ArrayList<Author>();
                        
                        NodeList authors = article.getElementsByTagName("author");
                        for (int j= 0; j < authors.getLength(); j++) {
                            Element author = (Element) authors.item(j);
                            String position = author.getAttribute("AuthorPosition");
                            String name = author.getTextContent();
                            authorList.add(new Author("author_" + j, position, name));
                        }   
                        String title = article.getElementsByTagName("title").item(0).getTextContent();
                        String initPage = article.getElementsByTagName("initPage").item(0).getTextContent();
                        String endPage = article.getElementsByTagName("endPage").item(0).getTextContent();

                        tempRecord.setArticleList("article_" + i, title, initPage, endPage, authorList);
                    }
                    recordList.add(tempRecord);
                }
            }
            System.out.println("Parse XML file Succeeded!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recordList;
    }
}