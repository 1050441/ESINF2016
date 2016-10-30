package Business;

import Utilities.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 1050441
 */
public class User { 
    
    // Attributes
    private String nickname = "";
    private String email = "";
    private double accumulatedPoints = 0;
    private List<City> visitedCities;
    
    // Properties
    public String Nickname() {
        return nickname;
    }

    public String Email() {
        return email;
    }

    public double AccumulatedPoints() {
        return accumulatedPoints;
    }

    public void AddPoints(double points){
        accumulatedPoints += points; 
    }

    public List<City> VisitedCities() {
        return visitedCities;
    }
   
    public void CheckIn(City city)
    {
        visitedCities.add(city);
        AddPoints(city.Points());
    }
            
    public City GetCurrentCity(){
        return Lists.getLast(visitedCities);
    }
    
    public Location GetCurrentLocation(){
        return Lists.getLast(visitedCities).Location();
    }
    
    
    public double GetTotalCityPoints(City city)
    {
        double totalCityPoints = 0;
        
        for(City c : visitedCities)
        {
            if(c.equals(city))
            {
                totalCityPoints+=c.Points();
            }
        }
        return totalCityPoints;
    }
    
    public User(){ 
        visitedCities = new ArrayList<>();
    } 
    
    public User( String Nickname, String Email, double AccumulatedPoints){ 
        nickname = Nickname;
        email = Email;
        accumulatedPoints = AccumulatedPoints;
        visitedCities = new ArrayList<>();
    }
    
    @Override
    public String toString(){ 
        return "Nickname: " + nickname + " Email: " + email + " Points: " + accumulatedPoints;  
    } 
    
    public boolean equals(User user) {
        return (user != null 
                && user.nickname.equals(nickname) 
                && user.email.equals(email));
    }
}
