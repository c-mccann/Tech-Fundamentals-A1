import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by carlmccann2 on 16/10/2016.
 */
public class MarkovChainy {

    public MyLinkedHashTable prefixesAndFollowersSelection(String[] words, int prefixLength){
        System.out.println("Constructing prefixes and followers table...");
        MyLinkedHashTable prefixesAndFollowers = new MyLinkedHashTable();
        for (int i = 0; i < words.length - prefixLength; i++) {
            String key = "";
            for (int j = 0; j < prefixLength ; j++) {
                key += words[i+j];
                if(j < prefixLength - 1){
                    key += " ";
                }
            }
            prefixesAndFollowers.insert(key,words[i+prefixLength]);

        }
        System.out.println("Table Constructed!");
        return prefixesAndFollowers;
    }

    public String[] constructText(MyLinkedHashTable prefixesAndFollowers,int prefixLength, int outputWordCount){
        System.out.println("Constructing Text...");

        // the whole text we are building
        List<String> text = new ArrayList<String>();

        // a list of prefixes for ease of use
        List<String> prefixes = new ArrayList<String>();
        // pulls all keys/prefixes into a list
        MyLinkedList[] nodes = prefixesAndFollowers.getNodes();
        for (int i = 0; i < prefixesAndFollowers.getNodes().length; i++) {
            if(nodes[i] != null){
                prefixes.add(nodes[i].getKey());
            }
        }

        // choose random prefix
        String currentPrefix = prefixes.get(ThreadLocalRandom.current().nextInt(0,prefixes.size()));

        // for updating currentPrefix, I split and store. this allows access to last and second last words in list etc
        // rather than 2 words in one string stored being accessed as well as its follower.
        String[] brokenDownPrefix = currentPrefix.split(" ");
        for(String s: brokenDownPrefix){
            text.add(s);
        }
        // while we still want to add text dependant on the specified word count
        while(text.size() < outputWordCount){
            // pulls all followers/values into list for ease of use
            ArrayList<String> followers;

            //update 04/11/2016, this little while loop
            // if currentPrefix doesn't have any followers, pick a new one to ensure we keep adding to text
            // not sure if this is ever the case but coded it to be sure.
            while(!prefixesAndFollowers.contains(currentPrefix)){
                currentPrefix = prefixes.get(ThreadLocalRandom.current().nextInt(0,prefixes.size()));
            }

            followers = new ArrayList<>(Arrays.asList(prefixesAndFollowers.retrieve(currentPrefix)));
            if(followers.size() > 0){
                String randomFollower = followers.get(ThreadLocalRandom.current().nextInt(0, followers.size()));
                text.add(randomFollower);
            }

            currentPrefix = "";
            for (int i = 0; i < prefixLength ; i++) {
                if(i < prefixLength-1){
                    currentPrefix += text.get(text.size() - (prefixLength - i)) + " ";
                }
                else{
                    currentPrefix += text.get(text.size() - (prefixLength - i));
                }
            }
        }
        System.out.println("Text Constructed!");
        return text.toArray(new String[text.size()]);
    }

    public void prettyPrinter(String[] text){
        System.out.println("Printing to Screen:\n");
        for (int i = 0; i < text.length ; i++) {
            System.out.print(text[i] + " ");
            if(i % 15 == 0 && i > 0) System.out.print("\n");
        }
    }

    public static void main(String[] args){
//        String fileToOpen = "src/stateoftheunionaddress1864.txt";
        String fileToOpen = "src/big.txt";
//        String fileToOpen = "src/Trump_Speech.txt";
//        String fileToOpen = "src/sample.txt";
        int prefixLength = 2;
        int outputWordCount = 500;

        if(args.length == 3){
            try{
                fileToOpen = args[0];
                prefixLength = Integer.parseInt(args[1]);
                outputWordCount = Integer.parseInt(args[2]);

                FileIO fio = new FileIO();
                MarkovChainy mc = new MarkovChainy();

                String[] words = fio.fileReader(fileToOpen, false);
                MyLinkedHashTable prefixesAndFollowers = mc.prefixesAndFollowersSelection(words, prefixLength);
//        prefixesAndFollowers.printDetails();
                String[] text = mc.constructText(prefixesAndFollowers, prefixLength, outputWordCount);
                mc.prettyPrinter(text);

//        System.out.println(Arrays.toString(text));
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("Incorrect input, please try again!");
            }
        }
    }
}
