

package com.TiledMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameMap extends Actor {

    public abstract void render (OrthographicCamera camera);
    public abstract void update (float delta);
    public abstract void dispose ();

    public TileType getTileTypeByLocation(int layer, float x, float y){
        return this.getTileTypeByCoordinate(layer, (int) (x /TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
    }

    /**
     *
     * @param layer
     * @return
     */

    public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);



    /**
     *
     * @return
     */
    public abstract float getWidth();
    public abstract float getHeight();
    public abstract int getLayers();

}
