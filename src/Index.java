import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

public class Index {
    Map<Integer, String> sources;
    HashMap<String, HashSet<Integer>> index;

    Index(){
        sources = new HashMap<Integer,String>();
        index = new HashMap<String, HashSet<Integer>>();
    }

    public void buildIndex(String[] files){
        String path = "F:/GIT/INGIN/Files/";
        int i=0;
        for(String fileName: files){

            try(BufferedReader file = new BufferedReader(new FileReader(path+fileName))){
                sources.put(i, fileName);
                String ln;
                while((ln = file.readLine())!=null){
                    String[] words = ln.split("\\W+");  //split by words(or non words) confused
                    for(String word: words){
                        word = word.toLowerCase();
                        if(!index.containsKey(word)){
                            index.put(word,new HashSet<Integer>());
                            index.get(word).add(i);
                        }
                    }
                }
            }
            catch (IOException e){
                System.out.println("File "+fileName+" not found \n");
            }
            i++;
        }
    }

    public void find(String query){
        String[] words = query.split("\\W+");
        HashSet<Integer> res = new HashSet<>(index.get(words[0].toLowerCase()));
        for(String word: words){
            res.retainAll(index.get(word));
        }
        if(res.size()==0){
            System.out.println("not found");
        }
        System.out.println("found in: ");
        for(int num: res){
            System.out.println("\t" +sources.get(num));
        }
    }

    public static void main(String[] args) throws IOException {
        Index index = new Index();
        index.buildIndex(new String[]{"file1.txt","file2.txt", "file3.txt"});

        System.out.println("Print search phrase: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String phrase = in.readLine();

        index.find(phrase);
    }
}
