package com.jarz.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jarz.game.Platformer;
import com.jarz.game.Sprites.Brick;
import com.jarz.game.Sprites.Coin;

/**
 * Created by Randy on 11/26/2017.
 */

public class WorldCreator
{
    public WorldCreator(World world, TiledMap map)
    {
        //These Are "Bodies" or potential Characters / Objects that Will Eventually Turn into Separate Classes
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Creates Ground Map Hitboxes / Bodies
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect =((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ Platformer.PPM, (rect.getY()+rect.getHeight()/2)/Platformer.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/Platformer.PPM, (rect.getHeight()/2)/Platformer.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Creates Pipe Hitboxes / Bodies
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect =((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/Platformer.PPM, (rect.getY()+rect.getHeight()/2)/Platformer.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2)/Platformer.PPM, (rect.getHeight()/2)/Platformer.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Creates Bricks Hitboxes / Bodies
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect =((RectangleMapObject) object).getRectangle();
            new Brick(world,map,rect);
        }

        //Creates Coins Hitboxes / Bodies
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect =((RectangleMapObject) object).getRectangle();
            new Coin(world,map,rect);
        }
    }
}
