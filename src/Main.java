import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        input=in.nextLine().toLowerCase().toCharArray()[0];
        
        char[] vowels={ 'a', 'e', 'i', 'o', 'u', 'y'};
        boolean firstVowel = false;
        boolean result = true;
        for (int i = 0; i < input.length; i++){
            if (i == 0){
                char x = Array.get(input, i);
                if (x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u' || x == 'y'){
                    firstVowel = true;
                }
                else {
                    firstVowel = false;
                }
            }
            else {
                char x = Array.get(input, i);
                if (firstVowel == true){
                    if(i % 2 == 0 ){
                        if (x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u' || x == 'y'){
                            result = false;
                        }
                    }
                    else {
                        if (x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u' || x == 'y'){
                        }
                        else {
                        result = false;
                        }
                    }
                }
                else {
                    if(i % 2 == 0 ){
                        if (x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u' || x == 'y'){
                            
                        }
                        else {
                        result = false;
                        }

                    }
                    else {
                        if (x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u' || x == 'y'){
                            result = false;

                    }
                }
                
            }
        }
        in.close();
    }
}