import java.util.*; 
 class Document { 
    private String id; 
    private String content; 
    public Document(String id, String content) { 
        this.id = id; 
        this.content = content.toLowerCase(); // convert to lowercase for case-insensitive search 
    } 
    public String getId() { 
        return id; 
    } 
    public String getContent() { 
        return content; 
    } 
} 
class SearchEngine { 
    private Map<String, List<String>> invertedIndex;  // word -> list of document IDs 
    private Map<String, Document> documentStore;      // document ID -> document content 
    public SearchEngine() { 
        invertedIndex = new HashMap<>(); 
        documentStore = new HashMap<>(); 
    } 
    public void addDocument(Document doc) { 
        String[] words = doc.getContent().split("\\W+");  // split by non-word characters 
        documentStore.put(doc.getId(), doc);              // store document in the map 
        for (String word : words) { 
            invertedIndex.computeIfAbsent(word, k -> new ArrayList<>()).add(doc.getId()); 
        } 
    } 
    public List<String> search(String query) { 
        String[] words = query.toLowerCase().split("\\W+"); 
        Map<String, Integer> relevance = new HashMap<>(); 
        for (String word : words) { 
            if (invertedIndex.containsKey(word)) { 
                for (String docId : invertedIndex.get(word)) { 
                    relevance.put(docId, relevance.getOrDefault(docId, 0) + 1); 
                } 
            } 
        } 
        List<String> sortedDocs = new ArrayList<>(relevance.keySet()); 
        sortedDocs.sort((a, b) -> relevance.get(b) - relevance.get(a)); 
 
        return sortedDocs; 
    } 
    public void displayDocument(String docId) { 
        if (documentStore.containsKey(docId)) { 
            System.out.println("Document ID: " + docId); 
            System.out.println(documentStore.get(docId).getContent()); 
        } else { 
            System.out.println("Document not found."); 
        } 
    } 
} 
public class BasicSearchEngineApp { 
    public static void main(String[] args) { 
        SearchEngine searchEngine = new SearchEngine(); 
        searchEngine.addDocument(new Document("1", "Java is a high-level programming 
language.")); 
        searchEngine.addDocument(new Document("2", "Python is widely used for data 
science.")); 
        searchEngine.addDocument(new Document("3", "The Java Virtual Machine allows Java 
to run anywhere.")); 
        searchEngine.addDocument(new Document("4", "Java and Python are both popular 
programming languages.")); 
        searchEngine.addDocument(new Document("5", "Search engines use algorithms to rank 
web pages.")); 
        Scanner scanner = new Scanner(System.in); 
        while (true) { 
            System.out.println("\nEnter your search query (or type 'exit' to quit): "); 
            String query = scanner.nextLine(); 
            if (query.equalsIgnoreCase("exit")) break; 
            List<String> results = searchEngine.search(query); 
            if (results.isEmpty()) { 
                System.out.println("No documents found matching your query."); 
            } else { 
                System.out.println("Documents matching your query:"); 
                for (String docId : results) { 
                    System.out.println("- Document ID: " + docId); 
                    searchEngine.displayDocument(docId); 
                    System.out.println(); 
                } 
            } 
        } 
        scanner.close(); 
    } 
} 
