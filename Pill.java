import sofia.micro.*;

//-------------------------------------------------------------------------
/**
 *  Representing a Pill object in the game
 *
 *  @author nimayz
 *  @version 2016.05.01
 */
public class Pill extends Actor
{
    //~ Fields ................................................................
    private int life;


    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new Pill object.
     */
    public Pill()
    {
        super();
        life = 1000;
    }
    
    //~ Methods ...............................................................
    public void act()
    {
        life--;
        if(life == 0)
        {
            remove();
        }
    }
}
