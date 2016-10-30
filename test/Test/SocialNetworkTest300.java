package Test;

import Business.City;
import Business.Location;
import Business.SocialNetwork;
import Business.User;
import Utilities.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author 1050441
 */
public class SocialNetworkTest300 {
    SocialNetwork socialNetwork = new SocialNetwork();
    
    @Before
    public void LoadData() throws IOException
    {
        try
        {
             //Read Cities from file
            socialNetwork.ReadCitiesFromFile("Data/files300/cities300.txt");
             //Read Users from file
            socialNetwork.ReadUsersFromFile("Data/files300/users300.txt");
        } catch (IOException exp) {} 
    }
    
    @Test
    public void testLoadedCities() {
        System.out.println("----------------------------------");
        System.out.println("Loaded Cities from file: ");
        System.out.println("----------------------------------");
        for(City c : socialNetwork.cities)
        {
            System.out.println(" " + c.toString());
        }        
    }
    
    @Test
    public void testLoadedUsers() {
        System.out.println("----------------------------------");
        System.out.println("Loaded Users from file: ");
        System.out.println("----------------------------------");
        for(User u : socialNetwork.users)
        {
            System.out.println("----------------------------------");
            System.out.println("User: ");
            System.out.println(" " + u.toString());
            
            System.out.println("Friends: ");
            socialNetwork.GetUserFriends(u).stream().forEach((f) -> {
                System.out.println(" " + f.toString());
            });
            
            System.out.println("Visited Cities: ");
            u.VisitedCities().stream().forEach((c) -> {
                System.out.println(" " + c.toString());
            });
        }        
    }

    @Test
    public void testInsertUser() {
        System.out.println("----------------------------------");
        System.out.println("Test of insert User");
        System.out.println("----------------------------------");

        User usr1 = new User("9999","mail_9999_@sapo.pt",0);
        
        System.out.println("Current Users List:");
        for (User f : socialNetwork.users) {
            System.out.println(" " + f.toString());
        }
        
        boolean result1 = socialNetwork.InsertUser(usr1);
        
        
        System.out.println("New Users List:");
        for (User f : socialNetwork.users) {
            System.out.println(" " + f.toString());
        }
        
        assertTrue("result should be true", result1==true);
    }
    
    @Test
    public void testInsertFriendship() {
        System.out.println("----------------------------------");
        System.out.println("Test of insert Friendship");
        System.out.println("----------------------------------");

        // User used for test
        User usr = socialNetwork.GetUserByNickName("nick9");
        
        System.out.println("User Tested:");
        System.out.println(" " + usr.toString());
        
        System.out.println("Current user Friends: ");
        
        for (User f : socialNetwork.GetUserFriends(usr)) {
            System.out.println(" " + f.toString());
        }
        
        // Add a new friend
        User friend = socialNetwork.GetUserByNickName("nick2");
                
        // add the friendship
        socialNetwork.InsertFriendship(usr,friend);
        
        System.out.println("new user Friends list: ");
        
        for (User f : socialNetwork.GetUserFriends(usr)) {
            System.out.println(" " + f.toString());
        }

        System.out.println("new Friend Friends list: ");
        for (User f : socialNetwork.GetUserFriends(friend)) {
            System.out.println(" " + f.toString());
        }

        assertTrue("result should be true", socialNetwork.FriendshipExists(usr,friend) == true);
    }
    
    @Test
    public void testRemoveFriendship() {
        System.out.println("----------------------------------");
        System.out.println("Test remove Friendship");
        System.out.println("----------------------------------");

        User usr = socialNetwork.GetUserByNickName("nick9");
        
        System.out.println("User Tested:");
        System.out.println(" " + usr.toString());
        
        User friend = socialNetwork.GetUserByNickName("nick8");
        
        System.out.println("Friend to be removed:");
        System.out.println(" " + friend.toString());
        
        System.out.println("Current user Friends: ");
        
        for (User f : socialNetwork.GetUserFriends(usr)) {
            System.out.println(" " + f.toString());
        }
        
         System.out.println("Current Friend Friends: ");
        
        for (User f : socialNetwork.GetUserFriends(friend)) {
            System.out.println(" " + f.toString());
        }
        
        // Remove Friendship
        socialNetwork.RemoveFriendship(usr, friend);
        
        System.out.println("New user Friends List: ");
        
        for (User f : socialNetwork.GetUserFriends(usr)) {
            System.out.println(" " + f.toString());
        }
        
        System.out.println("Old Friend Friends List: ");
        
        for (User f : socialNetwork.GetUserFriends(friend)) {
            System.out.println(" " + f.toString());
        }
        
        assertTrue("result should be true", socialNetwork.FriendshipExists(usr, friend)==false);
        
    }
    
    @Test
    public void testRemoveUser() {
        System.out.println("----------------------------------");
        System.out.println("Test remove User");
        System.out.println("----------------------------------");
        
        System.out.println("User To Be Removed: ");
        User usr = Lists.getLast(socialNetwork.users);
        System.out.println(" " + Lists.getLast(socialNetwork.users));
                
        System.out.println("Current users List: ");
        
        for (User f : socialNetwork.users) {
            System.out.println(" " + f.toString());
        }
        
        System.out.println("Current user Friends: ");
        
        List<User> userFriends = new ArrayList<>();
        for (User f : socialNetwork.GetUserFriends(Lists.getLast(socialNetwork.users))) {
            userFriends.add(f);
            System.out.println(" " + f.toString());
        }
                
        // Remove Last User from users list
        socialNetwork.RemoveUser(Lists.getLast(socialNetwork.users));
        
        System.out.println("New users List: ");
        
        for (User f : socialNetwork.users) {
            System.out.println(" " + f.toString());
        }
        
        System.out.println("Check ex user friends friends: ");
        
        for (User f : userFriends) {
            System.out.println("Ex friend: ");
            System.out.println(" " + f.toString());
            System.out.println("Ex friend friends: ");
            for (User fr : socialNetwork.GetUserFriends(f)) {
                
                System.out.println(" " + fr.toString());
            }
        }
        assertTrue("result should be true", socialNetwork.UserExists(usr)==false);
    }
    
    @Test
    public void testInsertCity() {
        System.out.println("----------------------------------");
        System.out.println("Test Insert City");
        System.out.println("----------------------------------");
                
        System.out.println("Current Cities List: ");
        
        for (City c : socialNetwork.cities) {
            System.out.println(" " + c.toString());
        }
        
        City c = new City("city999", 65.0, new Location(40.85136, -8.136585));
        
        socialNetwork.InsertCity(c);
        
        System.out.println("New Cities List: ");
        for (City ct : socialNetwork.cities) {
            System.out.println(" " + ct.toString());
        }
        
        assertTrue("result should be true", socialNetwork.CityExists(c)==true);
        
    }
        
    @Test
    public void testSortListByMayorPoints() {
        
        System.out.println("Cities List: ");
        for(City c : socialNetwork.cities)
        {   
            System.out.println(" City Name: " + c.Name() + " Mayor: " + c.Mayor().Nickname() + " Points: " + c.Mayor().AccumulatedPoints());
        }
        
        socialNetwork.SortByCityMayorPoints();
        
        System.out.println("Cities List Sorted: ");
        for(City c : socialNetwork.cities)
        {   
            System.out.println(" City Name: " + c.Name() + " Mayor: " + c.Mayor().Nickname() + " Points: " + c.Mayor().AccumulatedPoints());
        }
    }
    
    @Test
    public void testListUsersAndCitiesCheckedInByOrder() {
        
        System.out.println("List User Points and Cities: ");
        for(User u : socialNetwork.users)
        {   
            System.out.println(" User: " + u.Nickname());
            System.out.println("  Points: " + u.AccumulatedPoints());
            System.out.println("  CheckedIn Cities: ");
            for(City c : u.VisitedCities())
            {   
                System.out.println("   " + c.toString());
            }
        }
    }
    
    @Test
    public void testListUserFriendsByLocation() 
    {
        User usr = Lists.getLast(socialNetwork.users);
        
        Map<Location, List<User>> result = socialNetwork.ListUserFriendsByLocation(usr);
                
        System.out.println(result);
    }
    
    @Test
    public void testListMoreInfluentUsers() 
    {
        System.out.println("Test List the top 5 users:");
        
        for(User u : socialNetwork.users)
        {   
            System.out.println(" User: " + u.Nickname() + " Number of Friends: " + socialNetwork.GetUserFriends(u).size());
        }
        
        System.out.println("List top 5 users:");
        
        for(User u : socialNetwork.ListMoreInfluentUsers(5))
        {   
            System.out.println(" User: " + u.Nickname());
        }
    }
}
