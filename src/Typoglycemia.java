import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by C12508463 on 13/10/2016.
 */
public class Typoglycemia {

    public String[] wordJumbler(String[] words){
        System.out.println("Jumbling Words...");
        // loops through each word
        for (int i = 0; i < words.length ; i++) {

            // skips words that can't be jumbled
            if (words[i].length() <= 3){ continue;}
            char[] jumbledWord = words[i].toCharArray();

            // Originally if statements used, but only captured single occurences of punctuation at start and end
            // i.e. "..." not dealt with properly

            // found case where quotations were rendered: "John" -> "ojhn", found after wordEndingMarker solution
            int wordStartMarker = 0;
            int count = 0;
            while(!Character.isLetter(jumbledWord[count])){
                wordStartMarker++;
                count++;
                if(count == jumbledWord.length) break;  // stops cases such as this: [(, #, 1, 5] , found in big.txt
            }

            // avoids swapping last letter of word when word in question is a sentence finisher with punctuation at end
            // should account for ellipsis, now
            count = jumbledWord.length - 1;
            int wordEndingMarker = 1;
            while(!Character.isLetter(jumbledWord[count])){
                wordEndingMarker++;
                count--;
                if(count == 0) break;                   // stops cases such as this: [(, #, 1, 5] , found in big.txt
            }

            if(words[i].contains("'")){
                wordEndingMarker = words[i].length() - 1 - words[i].indexOf("'");
            }

            // iterate over inner letters of the word
            for (int j = 1 + wordStartMarker; j < jumbledWord.length - wordEndingMarker; j++) {
                // stops swapping of punctuation when it is current character to swap
                if (!Character.isLetter(jumbledWord[j])) {
                    continue;
                }
                int random = ThreadLocalRandom.current().nextInt(j,jumbledWord.length - wordEndingMarker);
                // stops swapping of punctuation when it is randomly selectled
                while(!Character.isLetter(jumbledWord[random])){
                    random = ThreadLocalRandom.current().nextInt(j,jumbledWord.length - wordEndingMarker);
                }
                // swapping
                char temp = jumbledWord[j];
                jumbledWord[j] = jumbledWord[random];
                jumbledWord[random] = temp;

            }
            words[i] = new String(jumbledWord);
        }
        System.out.println("Words Jumbled!");
        return words;
    }

    public static void main(String[] args){
        String fileToOpen;
        String fileToWrite;

        if(args.length == 2){
            fileToOpen = args[0];
            fileToWrite = args[1];
            Typoglycemia t = new Typoglycemia();
            FileIO fio = new FileIO();

            fio.fileWriter(fileToWrite,t.wordJumbler(fio.fileReader(fileToOpen, true)));
        }
        else {
            System.out.println("Bad input, please specify an input and output file");
            return;
//            fileToOpen = "big.txt";
//            fileToWrite = "out.txt";
        }
    }
}
