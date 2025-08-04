package se.su.ovning4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.SortedMap;

public class Exercise4 {
    private Graph<Node> graph = new ListGraph<>();

    public void loadLocationGraph(String fileName){
      try{

         BufferedReader reader = new BufferedReader(new FileReader(fileName));

         //load nodes
         String[] locationSplit = reader.readLine().split(";");
         for(int i = 0; i < locationSplit.length/3; i++) {
            graph.add(new Location(locationSplit[i * 3], Double.parseDouble(locationSplit[i * 3 + 1]), Double.parseDouble(locationSplit[i * 3 + 2])));
         }
        //load edges
         String line = reader.readLine();
         while(line != null){
            String[] edgeSplit = line.split(";");
            Node from = null;
            Node to = null;
            for(Node n : graph.getNodes()){
                if(n.getName().equals(edgeSplit[0])){
                    from = n;
                }
            }
            for (Node n : graph.getNodes()){
                if(n.getName().equals(edgeSplit[1])){
                    to = n;
                }
            }
            graph.connect(from, to, edgeSplit[2], Integer.parseInt(edgeSplit[3]) );
            line = reader.readLine();
         }
         
      }catch(FileNotFoundException e){
         System.err.println("File " + fileName + " could not be found");
      }catch(IOException e){
         System.err.println("Problems reading the file " + fileName);
      }
    }

    public SortedMap<Integer, SortedSet<Record>> getAlsoLiked(Record item) {
       return null;
    }

    public int getPopularity(Record item) {
       return -1;
    }

    public SortedMap<Integer, Set<Record>> getTop5() {
       return null;
    }

    public void loadRecommendationGraph(String fileName) {
    }

}
