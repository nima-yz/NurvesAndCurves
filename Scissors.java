import sofia.micro.*;
import android.graphics.PointF;

//-------------------------------------------------------------------------
/**
 *  Represents a Scissors Class
 *
 *  @author Nima Yahyazadeh
 *  @version 2016.05.01
 */
public class Scissors extends Actor
{
    //~ Fields ................................................................
    private int maxHeight;
    private int minHeight;
    private int maxWidth;
    private int minWidth;

    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new Scissors object.
     */
    public Scissors(int maxh, int minh, int maxw, int minw)
    {
        super();
        maxHeight = maxh;
        minHeight = minh;
        minWidth = minw;
        maxWidth = maxw;
    }
    
    //~ Methods ...............................................................
    /**
     * Called when the up button is pressed. Will move the scissor up.
     */
    public void dpadNorthIsDown() {
        if(this.getGridY() > minHeight)
        {
            setPosition(new PointF(getGridX(), getGridY()-2));
        }
    }
    
    /**
     * Called when the down button is pressed. Will move the scissor down.
     */
    public void dpadSouthIsDown() {
        if(this.getGridY() < maxHeight)
        {
            setPosition(new PointF(getGridX(), getGridY()+2));
        }
    }
    
    /**
     * Called when the right button is pressed. Will move the scissor right.
     */
    public void dpadEastIsDown(){
        if(getGridX() < maxWidth){
            move(2);
        }
    }
    
    /**
     * Called when the left button is pressed. Will move the scissor left.
     */
    public void dpadWestIsDown(){
        if(getGridX() > minWidth){
            move(-2);
        }
    }
        
    /**
     * Called when the space button is pressed.
     */
    public void dpadCenterIsDown(){
        ((Neurosystem)getWorld()).useUpt();
    }
    


}
