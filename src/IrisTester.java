import java.io.*;
import java.util.*;

public class IrisTester {
    private int classifier;

    public IrisTester(int classifier) {
        this.classifier = classifier;
    }

    public void testFile(File file)
    {
        try {
            BufferedReader irisTest = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            double counter=0;
            double correctCounter=0;
            for (String strT; (strT = irisTest.readLine()) != null;counter++) {
                String[] splitedT = strT.split(",");
                String result= testVector(splitedT,true);
                correctCounter+=result.equals(splitedT[splitedT.length-1])?1:0;
            }
            System.out.println("\nTOTAL TEST RESULT: "+correctCounter*100/counter+"%");
        }
        catch(Exception e)
        {e.printStackTrace();}
    }

    public String testVector(String[] strTest,boolean auto){

        try {
            BufferedReader iris=new BufferedReader(new InputStreamReader(new FileInputStream("iris.test.data")));

            List<Double> testVector=new ArrayList<>();
            for (int i = 0; i < strTest.length-1; i++)
                testVector.add(Double.parseDouble(strTest[i]));


            List<IrisNode> nodeList =new ArrayList<>();
            List<String> nameList =new ArrayList<>();
            String result;
            for (String str; (str = iris.readLine()) != null; ) {
                String[] splited = str.split(",");

                List<Double> vector=new ArrayList<>();

                for (int i = 0; i < splited.length-1; i++)
                    vector.add(Double.parseDouble(splited[i]));

                String name = splited[splited.length-1];
                checkSize(vector,testVector);
                double calculation=0;
                for (int i = 0; i < vector.size(); i++) {
                    calculation+=Math.pow(vector.get(i)- testVector.get(i), 2);
                }
                calculation=Math.sqrt(calculation);

                nodeList.add(new IrisNode(name,calculation));
                if(!nameList.contains(name)){
                   // System.out.println(name);
                    nameList.add(name);
                }
            }
            nodeList.sort(Comparator.comparingDouble(o -> o.value));
            //nodeList.forEach(o-> System.out.println(o.name+" " + o.value));
            //System.out.println("SET SIZE: "+ nameList.size());
            int[] counters= new int[nameList.size()];
            Arrays.fill(counters,0);

            int nMax=0;
            for (int i = 0; i < classifier; i++) {
                int n=nameList.indexOf(nodeList.get(0).name);
                counters[n]+=1;
                nMax=counters[n]>counters[nMax]?n:nMax;

                nodeList.remove(0);
            }

            int sum= Arrays.stream(counters).sum();
            result=nameList.get(nMax);
            if(!auto) {
                for (int i = 0; i < nameList.size(); i++) {
                    System.out.println(nameList.get(i) + " = " + counters[i] * 100 / sum + "%");
                }
                System.out.println("\nResult: " + result + " " + counters[nMax] * 100 / sum + "%");
            }
            return result;
        }
        catch(Exception e)
        {e.printStackTrace();}
        return null;
    }

    private void checkSize(List<Double> vector,List<Double> testVector)
    {
        int tvs=testVector.size();
        int vs=vector.size();

        if (vs>tvs)
            for (int i = 0; i < vs - tvs; i++)
                testVector.add(0.0);
        else if (vs<tvs)
            for (int i = 0; i < tvs - vs; i++)
                vector.add(0.0);

    }
}
