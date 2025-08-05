package se.su.ovning4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Exercise4 implements Comparator<Record> {
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
         System.out.println(graph.toString());
         reader.close();


      }catch(FileNotFoundException e){
         System.err.println("File " + fileName + " could not be found");
      }catch(IOException e){
         System.err.println("Problems reading the file " + fileName);
      }
    }

    public SortedMap<Integer, SortedSet<Record>> getAlsoLiked(Record item) {
        Set<Node> listeners = new HashSet<>();
        Set<Node> connections = new HashSet<>();
        SortedMap<Integer, SortedSet<Record>> map = new TreeMap<>(Comparator.reverseOrder());
        for (Edge<Node> e : graph.getEdgesFrom(item)) {
            listeners.add(e.getDestination());
        }
        for (Node n : listeners){
            graph.getEdgesFrom(n).forEach(e -> connections.add(e.getDestination()));
        }
        for (Node n : connections) {
            int popularity;
            if(n instanceof Record r){
                popularity = getPopularity(r);
                map.putIfAbsent(popularity, new TreeSet<Record>(this));
                map.get(popularity).add(r);
            }
        }
        return Collections.unmodifiableSortedMap(map);
    }

    public int getPopularity(Record item) {
        return graph.getEdgesFrom(item).size();

    }

    public SortedMap<Integer, Set<Record>> getTop5() {
        SortedMap<Integer, Set<Record>> popularityMap = new TreeMap<>(Comparator.reverseOrder());
        SortedMap<Integer, Set<Record>> top5Map = new TreeMap<>(Comparator.reverseOrder());
        for(Node n : graph.getNodes()){
            if (n instanceof Record r){
                popularityMap.putIfAbsent(getPopularity(r), new TreeSet<Record>(this));
                popularityMap.get(getPopularity(r)).add(r);
            }
        }
        if (popularityMap.size() < 5) {
            top5Map = popularityMap;
        }
        else {
            for(Map.Entry<Integer, Set<Record>> entry : popularityMap.entrySet()){
                if(top5Map.size() >= 5) break;
                top5Map.put(entry.getKey(), entry.getValue());
            }
        }
        return Collections.unmodifiableSortedMap(top5Map);
    }

    public void loadRecommendationGraph(String fileName) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line = reader.readLine();
            while(line != null){
                String[] split = line.split(";");
                Person p = new Person(split[0]);
                Record r = new Record(split[1], split[2]);
                graph.add(p);
                graph.add(r);
                graph.connect(p, r, null, 0);
                line = reader.readLine();
            }
        }catch (FileNotFoundException e){
            System.err.println("File " + fileName + " could not be found");
        }catch (IOException e){
            System.err.println("Problems reading the file " + fileName);
        }
    }

    @Override
    public int compare(Record o1, Record o2) {
        if (o1.getName().compareTo(o2.getName()) < 0){
            return -1;
        }
        if (o1.getName().compareTo(o2.getName()) > 0){
            return 1;
        }
        return 0;
        }
}
