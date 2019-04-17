package com.TiledMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledGameMap extends GameMap {

    //TmxMapLoader tiledMap;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    TiledMapTileLayer layer;



    public TiledGameMap(String tiledMap){
        this.tiledMap = new TmxMapLoader().load(tiledMap);//new TmxMapLoader().load("maps/tiledmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        layer = (TiledMapTileLayer) this.tiledMap.getLayers().get("1");
    }


    @Override
    public void render(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        tiledMap.dispose();
    }

    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col,row);

        if (cell != null){
            TiledMapTile tile = cell.getTile();

            if (tile != null){
                int id = tile.getId();
                return TileType.getTileTypeById(id);
            }
        }
        return null;
    }

    @Override
    public float getWidth() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }

    @Override
    public float getHeight() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }

    @Override
    public int getLayers() {
        return tiledMap.getLayers().getCount();
    }
}
