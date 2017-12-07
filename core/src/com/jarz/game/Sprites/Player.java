package com.jarz.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jarz.game.Platformer;
import com.jarz.game.Screens.PlayScreen;

/**
 * Created by Randy on 11/26/2017.
 */

public class Player extends Sprite
{
    public World world;
    public Body b2body;
    private TextureRegion MC_Stand;

    public enum State{FALLING, JUMPING, STANDING, RUNNING}

    public State currentState;
    public State previousState;

    private Animation <TextureRegion> MC_Run;
    private Animation <TextureRegion> MC_Jump;
    private Animation <TextureRegion> MC_Standing;
    private float stateTimer;
    private boolean runningRight;

    public Player(World world, PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("Jack0"));
        this.world = world;
        definePlayer();
        MC_Stand = new TextureRegion(getTexture(), 0,0, 32, 32 );
        setBounds(0,0, 32/Platformer.PPM, 32/Platformer.PPM);
        setRegion(MC_Stand);

        currentState = State.STANDING;
        previousState = State.STANDING;

        stateTimer = 0;
        runningRight = true;

        //Add Animations
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 36, 36, 32, 32));
        frames.add(new TextureRegion(getTexture(), 2, 70, 32, 32));
        MC_Standing = new Animation <TextureRegion>(0.50f, frames); //Standing Animation Added
        frames.clear();

        frames.add(new TextureRegion(getTexture(), 0, 2, 32, 32));
        frames.add(new TextureRegion(getTexture(), 1*34, 2, 32, 32));
        frames.add(new TextureRegion(getTexture(), 2*34, 2, 32, 32));
        frames.add(new TextureRegion(getTexture(), 1*34, 2, 32, 32));
        MC_Run = new Animation <TextureRegion>(0.1f, frames); //Running Animation Added
        frames.clear();

        frames.add(new TextureRegion(getTexture(), 2, 36, 32, 32));
        MC_Jump = new Animation <TextureRegion>(0.50f, frames);//Jumping Animation Added
        frames.clear();
    }

    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2 + .071f );
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();

        TextureRegion region;
        switch(currentState) //Switch Between Different Animations based on Player State
        {
            case JUMPING:
                region = MC_Jump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = MC_Run.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
                region = MC_Standing.getKeyFrame(stateTimer, true);
                break;
            default:
               region = MC_Stand;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight )&& !region.isFlipX()) //Checks if the Player is Running Right or Left
        {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight )&& region.isFlipX())
        {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState()
    {
        if(b2body.getLinearVelocity().y > 0|| (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0 )
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }
    public void definePlayer()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / Platformer.PPM , 32/Platformer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 /Platformer.PPM);
        fdef.shape = shape;

        b2body.createFixture(fdef);

        //Create Feet to Prevent Clipping Glitches when Running on Blocks
        FixtureDef fdef2 = new FixtureDef();
        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-2/Platformer.PPM, -4.5f / Platformer.PPM), new Vector2(2/Platformer.PPM, -4.5f/Platformer.PPM));
        fdef2.shape = feet;
        b2body.createFixture(fdef2);
    }
}
