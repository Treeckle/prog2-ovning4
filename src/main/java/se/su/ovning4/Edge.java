package se.su.ovning4;

public class Edge<T> {

  private int weight;
  private final T destination;
  private final String name;

  public Edge(int weight, T destination, String name) {
    this.weight = weight;
    this.destination = destination;
    this.name = name;
  }

  public int getWeight(){
    return weight;
  }

  public void setWeight(int weight) {
    if (weight < 0) {
      throw new IllegalArgumentException("Weight cannot be negative");
    }
    this.weight = weight;
  }

  public T getDestination(){
    return destination;
  }

  public String getName(){
    return name;
  }

  public String toString(){
    return "till " + getDestination() + " med " + getName() + " tar 3 ";
  }
}
