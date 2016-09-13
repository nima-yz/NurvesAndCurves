import sofia.micro.*;
import java.util.Random;
import sofia.graphics.TextShape;

//-------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Nima Yahyazadeh
 *  @version 2016.05.01
 */
public class Neurosystem extends World
{
    //~ Fields ................................................................
    private  final int textOffsetY = 10; 
    private final int textOffsetX = 90;
    private CellBody post;
    private Axon pre;
    private Scissors cutter;
    private Reuptech reupt;
    private int counter;
    private int level;
    private int timeInterval;
    private int reUptCounter;
    private int medicineCounter;
    private int health;
    private int pill;
    private int upt;
    private int points;

    /* Text Shapes on score board */
    private TextShape healthShape;
    private TextShape pillNumberText;
    private TextShape UptechAvail;
    private TextShape levelText;
    private TextShape pointText;

    /* Locations */
    private final int firstX = 360;
    private final int firstY = 540;
    private final int secondX = 220;
    private final int secondY = 540;

    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new Neurosystem object.
     */
    public Neurosystem()
    {
        super(500, 700, 1);

        init();
        addElementToWorld();       
    }

    //~ Methods ...............................................................
    /**
     * Will initialize all the objects and score board.
     */
    private void init()
    {
        health = 10;
        pill = 2;
        level = 1;
        counter = 400;
        upt = 10;
        points = 0;

        pre = new Axon();
        post = new CellBody();
        cutter = new Scissors(480, 270, 480, 90);
        reupt = new Reuptech();

        levelText = new TextShape("Level = "+level, textOffsetX , textOffsetY);
        healthShape = new TextShape("Health: "+health, textOffsetX ,textOffsetY+20);
        pillNumberText = new TextShape("Antagonist: "+pill, textOffsetX , textOffsetY+40);
        UptechAvail = new TextShape("UptechAvailable: "+ upt,textOffsetX , textOffsetY+60);  
        pointText = new TextShape("Points: "+ points, textOffsetX , textOffsetY+80);

        levelText.setTypeSize(20);
        healthShape.setTypeSize(20);
        pillNumberText.setTypeSize(20);
        UptechAvail.setTypeSize(20);
        pointText.setTypeSize(20);
    }

    private void addElementToWorld()
    {
        add(reupt, 160, 210);
        add(pre, 310, 73);
        add(post, 289,600);
        add(cutter, 90, 480);
        add(levelText);
        add(healthShape);
        add(pillNumberText);
        add(UptechAvail);
        add(pointText);
    }

    public Reuptech getReuptech()
    {
        return reupt;
    }

    public void act()
    {
        this.populate();
    }

    private void populate()
    {
        if(counter > 0)
        {
            counter --;
        }
        else
        {
            counter = new Random().nextInt(100) +  (5-level)*50 + 100; 
            for(int i = 0; i < new Random().nextInt(4); i++)
            {
                add(new NeuroTransmetter(), new Random().nextInt(270)+149, 226);
            }
        }

        reUptCounter++;
        if(reUptCounter == 3000)
        {
            upt++;
            reUptCounter = 0;
        }

        medicineCounter++;
        if(medicineCounter == 7000)
        {
            pill++;
            medicineCounter = 0;
        }

        resetScoreBoard();
    }

    /**
     * [hurt description]
     * @param whichRecept [description]
     */
    public void hurt(int whichRecept)
    {
        if((whichRecept == 1 && getOneObjectAt(firstX, firstY, Pill.class) != null ) || 
        (whichRecept == 2 && getOneObjectAt(secondX, secondY, Pill.class) != null)){
            points++;
            return;
        }
        health --;
        
        if(health == 0)
        {
            TextShape lost = new TextShape("You Lost",250,350);
            lost.setTypeSize(75);
            add(lost);
            stop();
        }
    }

    /**
     * [useUpt description]
     */
    public void useUpt()
    {
        boolean some_reopted = false;
        if(upt > 0)
        {
            for(NeuroTransmetter nm :getObjects(NeuroTransmetter.class))
            {
                some_reopted |= nm.opticate();
            }
            if(some_reopted) upt--;
        }
        resetScoreBoard();
    }

    /**
     * [resetScoreBoard description]
     */
    public void resetScoreBoard()
    {
        healthShape.setText("Health: "+health);
        levelText.setText("Level = "+level);
        pillNumberText.setText("Antagonist: "+pill);
        UptechAvail.setText("UptechAvailable: "+ upt);
        pointText.setText("Points: "+ points);
    }

    /**
     * [onTap description]
     */
    public void onTap()
    {
        if(pill > 0)usePill();
    }

    /**
     * [usePill description]
     */
    private void usePill()
    {
        int num = new Random().nextInt(2);
        switch(num)
        {
            case 0:
            if(getOneObjectAt(firstX, firstY, Pill.class) == null)
            {
                this.add(new Pill(), firstX, firstY);
                pill--;
            }
            else if(getOneObjectAt(secondX, secondY, Pill.class) == null)
            {
                this.add(new Pill(), secondX, secondY);
                pill--;
            }
            break;
            case 1:
            if(getOneObjectAt(secondX, secondY, Pill.class) == null)
            {
                add(new Pill(), secondX, secondY);
                pill--;
            }
            else if(getOneObjectAt(firstX, firstY, Pill.class) == null)
            {
                add(new Pill(), firstX, firstY);
                pill--;
            } 
            break;
        }
    }

    /**
     * Adds the point to the existing points.
     */
    public void addPoint() 
    {
        points++;
        /* When to add to go to next level */
        if(points == (level-1)*10+5)
        {
            level++;
            /* Last level of the game */
            if(level == 6)
            {
                TextShape won = new TextShape("You Won",250,350);
                won.setTypeSize(75);
                add(won);
                stop();
            }
        }
        /* Update the ScoreBoard after changes */
        resetScoreBoard();
    }
}