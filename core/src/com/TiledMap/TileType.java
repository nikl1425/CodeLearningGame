package com.TiledMap;

import java.util.HashMap;

public enum TileType {


    GRASS(302, true, "Grass"),
    FOIL(367, true, "foil"),
    ROCKGROUND(98, true, "Rockground"),
    STUFF1(368, true, "stuff"),
    WATER(125, false, "Water"),

    WATERTL(463, false, "WaterTL"),
    WATERT(464, false, "WaterT"),
    WATERTR(465, false, "WaterTR"),
    WATERL(495, false, "waterL"),
    WATERM(496, false, "waterM"),
    WATERR(497, false, "waterR"),
    WATERDL(527, false, "waterDL"),
    WATERD(528, false, "waterD"),
    WATERDR(529, false, "waterDR"),

    BRUSH1(262, false, "bush"),
    BRUSH2(263, false, "bush"),
    BRUSH3(264, false, "bush"),

    BRUSH4(294, false, "bush"),
    BRUSH5(295, false, "bush"),
    BRUSH6(296, false, "bush"),

    BRUSH7(327, false, "bush"),
    BRUSH8(328, false, "bush"),
    BRUSH9(329, false, "bush");


    public static final int TILE_SIZE = 32;

    private int id;
    private boolean collidable;
    private String name;
    private float damage;

    TileType(int id, boolean collidable, String name){
        this(id,collidable,name,0);
    }

    TileType(int id, boolean collidable, String name, float damage){
        this.id = id;
        this.collidable = collidable;
        this.name = name;
        this.damage = damage;
    }

    public int getId() {
        return id;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public String getName() {
        return name;
    }

    public float getDamage() {
        return damage;
    }

    private static HashMap<Integer, TileType> tileMap;

    static {
        tileMap = new HashMap<Integer, TileType>();
        for (TileType tileType : TileType.values()){
            tileMap.put(tileType.getId(), tileType);
        }
    }

    public static TileType getTileTypeById (int id) {
        return tileMap.get(id);
    }
}

