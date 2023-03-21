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
            int counter=0;
            int correctCounter=0;
            for (String strT; (strT = irisTest.readLine()) != null;counter++) {
                String[] splitedT = strT.split(",");
                String result= testVector(splitedT,true);
                correctCounter+=result.equals(splitedT[4])?1:0;
            }
            System.out.println("\nTOTAL TEST RESULT: "+correctCounter*100/counter+"%");
        }
        catch(Exception e)
        {e.printStackTrace();}
    }

    public String testVector(String[] strTest,boolean auto){

        try {
            BufferedReader iris=new BufferedReader(new InputStreamReader(new FileInputStream("iris.test.data")));

            double xT = Double.parseDouble(strTest[0]);
            double yT = Double.parseDouble(strTest[1]);
            double zT = Double.parseDouble(strTest[2]);
            double tT = Double.parseDouble(strTest[3]);

            List<IrisNode> nodeList =new ArrayList<>();
            List<String> nameList =new ArrayList<>();
            String result;
            for (String str; (str = iris.readLine()) != null; ) {
                String[] splited = str.split(",");
                double x = Double.parseDouble(splited[0]);
                double y = Double.parseDouble(splited[1]);
                double z = Double.parseDouble(splited[2]);
                double t = Double.parseDouble(splited[3]);
                String name = splited[4];
                double calculation=Math.sqrt(Math.pow(x-xT,2)+Math.pow(y-yT,2)+Math.pow(z-zT,2)+Math.pow(t-tT,2));
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

}
