import sofia.micro.*;
import java.util.Random;
import java.lang.Math;
import android.graphics.PointF;

//-------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author your name (your-pid)
 *  @version (place the date here, in this format: yyyy.mm.dd)
 */
public class NeuroTransmetter extends Actor
{
    //~ Fields ................................................................
    private double speed;
    private double  degree;
    private boolean isOpt;
    



    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new NeuroTransmetter object.
     */
    public NeuroTransmetter()
    {
        super();
        turn(90);
        speed = (new Random().nextDouble()%1)+ 0.5;
        degree = (new Random().nextDouble()%1)*90;
        isOpt = false;
    }


    //~ Methods ...............................................................

    public void act()
    {
        if(isOpt)
        {
            turnTowards(((Neurosystem)getWorld()).getReuptech());
            move(1);
            if(getGridX() > 150 && getGridX() > 160 &&  getGridY() < 220&& getGridY() > 210)
            {
                this.remove();
            }
            return;
        }
        else if(getGridY() > 540 - 20 && getGridY() < 540 +20)
        {
            if(getGridX() > 345 && getGridX() < 400)
            {     
                ((Neurosystem)getWorld()).hurt(1);
            }
            else if(getGridX() > 190 && getGridX() < 230)
            {
                ((Neurosystem)getWorld()).hurt(2);
            }
            remove();
            return;
        }
        else if((getGridY() > 580 - (70.0/280)*(Math.abs(280-getGridX()))) 
        || getOneIntersectingObject(Scissors.class) != null)
        {
            ((Neurosystem)getWorld()).addPoint();
            remove();
        }
        else
        {
        setRotation((degree++ % 90) + 45);
        move(speed);
        }
    }
    
    public boolean opticate()
    {
        boolean to_return = isOpt;
        isOpt = true;
        return !to_return;
    }
    
}
