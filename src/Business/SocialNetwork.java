
package Business;

import Utilities.Lists;
import Utilities.Utilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author 1050441
 */
public class SocialNetwork {
    
    // Attributes  
    public List<City> cities;
    public List<User> users;
    public List<Friendship> friendships;
    
    public SocialNetwork(){        
        this.cities = new ArrayList<>(); 
        this.users = new ArrayList<>(); 
        this.friendships = new ArrayList<>(); 
    } 

    public void ReadCitiesFromFile(String FilePath) throws IOException {
        try
        {
            for (String row : Utilities.readFile(FilePath)) {
                
                String[] parts = row.split(","); 
                
                if (parts.length == 4)
                {
                    String name = parts[0];
                    
                    if(name.isEmpty())
                    {
                        System.out.println("Invalid city name");
                    }
                    else
                    {
                        Double points = Double.parseDouble(parts[1]);
                        Double latitude = Double.parseDouble(parts[2]);
                        Double longitude = Double.parseDouble(parts[3]);
                    
                        cities.add(new City(name, points, new Location(latitude, longitude)));
                    }
                }
                else
                {
                    System.out.println("Invalid line, one of the city properties are missing.");
                }
            } 
        } catch (IOException exp) {
            System.out.println(exp.getMessage());
        } 
    }
    
    public void ReadUsersFromFile(String FilePath) throws IOException {
        try
        {
            //Get all Lines from file
            List<String> lines = Utilities.readFile(FilePath);
            
            // Add Users
            int i = 0;
            while (i < lines.size()) {
                
                String[] usr = lines.get(i).split(",");
                
                String nickName = usr[0];
                String Email = usr[1];
                
                User user = new User(nickName, Email, 0);
                
                // Add user visited cities
                for(int y=2; y<usr.length; y++) {
                    user.CheckIn(GetCityByName(usr[y]));
                }
                
                // Add User to Network
                users.add(user);
                i=i+2;
            }
            
            // Add User Friends
            int x = 0;
            while (x < lines.size()) {
                
                //Get User Information
                String[] userLine = lines.get(x).split(",");
                
                User user = GetUserByNickName(userLine[0]);
                
                // Get User Friends
                String[] usrFriends = lines.get(x+1).split(",");
                
                for(int y=0; y<usrFriends.length; y++) 
                {
                    InsertFileFriendship(new Friendship(user, GetUserByNickName(usrFriends[y])));
                }
                x+=2;
            }
            
            DefineMayors();
        } catch (IOException exp) {} 
    }
    
    private void DefineMayors()
    {
        for(City c : cities)
        {
            for(User u : users)
            {
                if(c.Mayor() == null)
                {
                    c.setMayor(u);
                }
                else if(c.Mayor().GetTotalCityPoints(c) < u.GetTotalCityPoints(c))
                {
                    c.setMayor(u);
                }
            }
        }
    }
        
    public List<City> SortByCityMayorPoints()
    {
        Collections.sort(cities);
        return cities;
    }
        
    public City GetCityByName(String cityName)
    {
        for (City c : cities) {
            if (c.Name().equals(cityName))
                return c;
        }
        return null;
    }
    
    public void CheckIn(User user, City city)
    {
        if(!Lists.getLast(cities).equals(city))
            user.CheckIn(city);
        else
            System.out.println("The user can not checkin on the same city two times in a row.");
        
        DefineMayors();
    }
    
    public boolean CityExists(City city)
    {
        for (City c : cities) {
            if (c.equals(city))
                return true;
        }
        return false;
    }

     public boolean InsertCity(City city){
        //Don't allow two users with the same name and emmail
        if (!cities.stream().noneMatch((c) -> (c.equals(city)))) {
            return false;
        }
        else
        {
            cities.add(city);
            return true;
        }
    }
     
    public User GetUserByNickName(String nickname)
    {
        for (User u : users) {
            if (u.Nickname().equals(nickname))
                return u;
        }
        return null;
    }
 
    public boolean UserExists(User user)
    {
        for (User u : users) {
            if (u.equals(user))
                return true;
        }
        return false;
    }
    
    public boolean InsertUser(User user){
        //Don't allow two users with the same name and emmail
        if (!users.stream().noneMatch((u) -> (u.equals(user)))) {
            return false;
        }
        else
        {
            users.add(user);
            return true;
        }
    }
    
    public void InsertFileFriendship(Friendship friendship)
    {
        boolean friendshipExists = false;
         
        for(Friendship f : friendships)
        {
            if(f.equals(friendship))
                friendshipExists = true;
        }
        
        if(!friendshipExists)
            friendships.add(friendship);
    }
       
    public void InsertFriendship(User user1, User user2)
    {
        // Create Friendship object
        Friendship fr1 = new Friendship(user1, user2);
        // Reverse Friendship
        Friendship fr2 = new Friendship(user2, user1);
        
        boolean friendship1 = false;
        boolean friendship2 = false;
         
        for(Friendship f : friendships)
        {
            if(f.equals(fr1))
                friendship1 = true;
            
            if(f.equals(fr2))
                friendship2 = true;
        }
        
        if(!friendship1)
            friendships.add(fr1);
        
        if(!friendship2)
            friendships.add(fr2);
    }
    
    public List<User> GetUserFriends(User user)
    {
        List<User> friends = new ArrayList<>();
        
        friendships.stream().filter((f) -> (f.User().equals(user))).forEach((f) -> {
            friends.add(f.Friend());
        });
        
        return friends;
    }
    
    public boolean FriendshipExists(User user1, User user2)
    {        
        boolean existsFriendship1 = false;
        boolean existsFriendship2 = false;
        
        for(Friendship f : friendships)
        {
            if(f.User()== user1 && f.Friend() == user2)
                existsFriendship1 = true;
        }
        
        for(Friendship f : friendships)
        {
            if(f.User()== user2 && f.Friend() == user1)
                existsFriendship2 = true;
        }
        
        return existsFriendship1 && existsFriendship2;
    }
        
    public void RemoveFriendship(User user1, User user2)
    {         
        List<Friendship> friendshipsToRemove = new ArrayList<>();
        
        if (FriendshipExists(user1, user2) || FriendshipExists(user2, user1))
        {
            for(Friendship f : friendships)
            {
                if(f.User() == user1 && f.Friend() == user2)
                    friendshipsToRemove.add(f);
                
                if(f.User() == user2 && f.Friend() == user1)
                    friendshipsToRemove.add(f);
            }   
        }
        
        for(Friendship f : friendshipsToRemove)
        {
            friendships.remove(f);
        } 
    }
    
    public void RemoveAnUserFriendships(User user)
    {         
        List<Friendship> friendshipsToRemove = new ArrayList<>();
        
        if (UserExists(user))
        {
            for(Friendship f : friendships)
            {
                if(f.User() == user)
                    friendshipsToRemove.add(f);
                
                if(f.Friend() == user)
                    friendshipsToRemove.add(f);
            }   
        }
        
        for(Friendship f : friendshipsToRemove)
        {
            friendships.remove(f);
        } 
    }
    
     public void RemoveUserFromMayors(User user)
    {   
        if (UserExists(user))
        {
            for(City c : cities)
            {
                if(c.Mayor() == user)
                    c.setMayor(null);
            }   
            DefineMayors();
        }
    }
     
    public void RemoveUser(User user)
    {   
        // check if the user exists
        if(UserExists(user))
        {
            //in first place we need to remove the user friendships
            RemoveAnUserFriendships(user);
            RemoveUserFromMayors(user);
            users.remove(user);
        }
        else
        {
            System.out.println("The user can't be removed because isn't part of the social network");
        }
    }   
    
    public Map<Location, List<User>> ListUserFriendsByLocation(User usr) 
    {
        return GetUserFriends(usr).stream().collect(Collectors.groupingBy(User::GetCurrentLocation));
    }
    
    public List <User> ListMoreInfluentUsers(int NumberOfTopUsers) 
    {      
        Comparator<User> byNumberOfFriends = (User e1, User e2) -> Integer
                .compare(GetUserFriends(e2).size(), GetUserFriends(e1).size());
        
        return users.stream().sorted(byNumberOfFriends).limit(NumberOfTopUsers).collect(Collectors.toList());
    }
}
