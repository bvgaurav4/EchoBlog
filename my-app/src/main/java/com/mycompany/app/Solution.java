import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
class Sequence
{
    public int getNthNumber(){
        return 0;
    }  
}
class Fibonacci extends Sequence{
    int a;
    int b;
    Fibonacci()
    {
        this.a=0;
        this.b=1;
    }
    public int getNthNumber(int N)
    {
        if(N==1)
        {
            return a;
        }
        if(N==2)
        {
            return b;
        }
        int c=0;
        for(int i=3;i<=N;i++)
        {
            c=a+b;
            a=b;
            b=c;
        }
        return c;
    }
}
class ArithmeticProgression{
    int start;
    int diff;
    ArithmeticProgression(int a, int b)
    {
        start =a;
        diff=b;
    }
    public int getNthNumber(int N)
    {
        int nth=start;
        for(int i=0;i<N-1;i++)
        {
            nth+=diff;
        }
        return nth;
    }

} 
class WholeNumbers extends ArithmeticProgression{
    WholeNumbers()
    {
        super(0,1);
    }    
    }
class MultiplesOfFive extends ArithmeticProgression{
    MultiplesOfFive()
    {
        super(0,5);
    }
}
public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] somthig=scanner.nextLine().split(" ");
        // for(int i=0;i<somthig.length;i++)
        // {
        //     System.out.println(somthig[i]);
        // }
        if(somthig[0].equals("whole"))
        {
            WholeNumbers lol= new WholeNumbers();

            System.out.println(lol.getNthNumber(Integer.parseInt(somthig[1])));
        }
        if(somthig[0].equals("five"))
        {
            MultiplesOfFive lol= new MultiplesOfFive();
            System.out.println(lol.getNthNumber(Integer.parseInt(somthig[1])));
        }
        if(somthig[0].equals("ap"))
        {
            ArithmeticProgression lol= new ArithmeticProgression(Integer.parseInt(somthig[2]),Integer.parseInt(somthig[3]));
            System.out.println(lol.getNthNumber(Integer.parseInt(somthig[1])));
        }
        if(somthig[0].equals("fibonacci"))
        {
            Fibonacci lol= new Fibonacci();
            System.out.println(lol.getNthNumber(Integer.parseInt(somthig[1])));
        }
    }
}