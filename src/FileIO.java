import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlmccann2 on 16/10/2016.
 */
public class FileIO {
    public String[] fileReader(String fileToOpen, boolean splitOnHyphen){
        System.out.println("Reading File \"" + fileToOpen + "\"...");
        List<String> words = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileToOpen));
            String currentLine;
            while((currentLine = br.readLine()) != null){
                String[] tempWords = currentLine.split(" ");
                for(String s: tempWords){
                    if(splitOnHyphen){
                        String[] hyphenSplit = s.split("-");
                        for (int i = 0; i < hyphenSplit.length ; i++) {
                            words.add(hyphenSplit[i]);
                            if(i < hyphenSplit.length - 1){
                                words.add("-");
                            }
                        }
                    }
                    else{
                        words.add(s);
                    }
                }
            }
            System.out.println("File Read Succesfully!");
            return words.toArray(new String[words.size()]);
        }
        catch(Exception e){
            System.out.println("Couldn't open file");
            System.exit(0);
        }

        return null;
    }

    public void fileWriter(String fileToWrite, String[] words){
        System.out.println("Writing File \"" + fileToWrite + "\"...");
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileToWrite));
            for (int i = 0; i < words.length ; i++) {
                // some formatting where hyphenated words were spaced out weirdly, from code in the else statement
                // which was originally the only word writing functionality
                int lookAhead = 1;
                if(i == words.length - 1){
                    lookAhead = 0;

                }
                if(words[i].equals("-") || words[i+lookAhead].equals("-")){
                    bw.write(words[i]);
                }
                else{
                    bw.write(words[i] + " ");
                }

                if(i % 15 == 0 && i > 0) bw.write("\n");
            }
            System.out.println("File Written Successfully!");
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Couldn't write file");
            System.exit(0);
        }
    }
}
