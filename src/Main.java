import java.io.*;
import java.util.Scanner;

public class  Main {
    public static void main(String[] args){
        System.out.print("Enter K: ");
        Scanner sc=new Scanner(System.in);
        int q=sc.nextInt();
        IrisTester it=new IrisTester(q);
        for (int i = 0; i < 3; i++) {

            Scanner sc1=new Scanner(System.in);
            System.out.println("Enter vector by your self format: [ float,float,float,float ]  or enter [auto]: ");
            String input=sc1.nextLine();
            if (input.equals("auto"))
            {
                it.testFile(new File("iris.data"));
            }
            else
            {
                it.testVector(input.split(","),false);
            }
        }


    }


}
