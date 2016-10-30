package Business;

/**
 *
 * @author 1050441
 */
public class Location {
    private double latitude = 0;
    private double longitude = 0;
    
    public double Latitude() {
        return latitude;
    }

    public double Longitude() {
        return longitude;
    }
    
    public Location(double latitude, double longitude){        
        this.latitude = latitude;
        this.longitude = longitude;
    } 
    
    @Override
    public String toString(){ 
        return "Latitude: " + latitude + " Longitude: " + longitude;  
    } 
    
    public boolean equals(Location location) {
        return (location != null 
                && location.latitude == this.latitude 
                && location.longitude == this.longitude);
    }
}
