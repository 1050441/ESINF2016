package Business;

/**
 *
 * @author 1050441
 */
public class City implements Comparable<City> {
    
    // Attributes
    private String name = "";
    private double points = 0;
    private Location location = null;
    private User mayor = null;
    
    // Properties
    public String Name() {
        return name;
    }

    public double Points() {
        return points;
    }

    public Location Location() {
        return location;
    }

    public User Mayor() {
        return mayor;
    }

    public void setMayor(User mayor) {
        this.mayor = mayor;
    }
    
    public City(String name, double points, Location location){        
        this.name = name;
        this.points = points;
        this.location = location;
    } 
    
    @Override
    public String toString(){ 
        return "Name: " + name + " Points: " + points + " " + location.toString();  
    } 
    
    public boolean equals(City city) {
        return (city != null && city.name.equals(this.name));
    }
    
    @Override
    public int compareTo(City c) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
    
        if (this.Mayor().AccumulatedPoints() == c.Mayor().AccumulatedPoints()) 
            return EQUAL;
        
        if (this.Mayor().AccumulatedPoints() > c.Mayor().AccumulatedPoints())
            return BEFORE;
        
        if (this.Mayor().AccumulatedPoints() < c.Mayor().AccumulatedPoints()) 
            return AFTER;
        
        return 0;
    }
}
