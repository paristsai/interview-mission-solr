import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Author {

	private String id;
    private String position;
    private String name;


    public Author() {

    }

    public Author(String id, String position, String name) {
        this.id = id;
        this.position = position;
        this.name = name;
    }

    public String getId() {
    	return this.id;
    }

    public String getPosition() {
    	return this.position;
    }

    public String getName() {
    	return this.name;
    }
}