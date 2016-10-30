package Business;

/**
 *
 * @author 1050441
 */
public class Friendship 
{
    private final User user;
    private final User friend;
    
    public User User() {
        return user;
    }
    
    public User Friend() {
        return friend;
    }
    
    public Friendship(User user, User friend)
    {
        this.user = user;
        this.friend = friend;
    }
    
    public boolean equals(User user, User friend) {
        return (user != null 
                && friend != null
                && this.user.equals(user) 
                && this.friend.equals(friend));
    }
}
