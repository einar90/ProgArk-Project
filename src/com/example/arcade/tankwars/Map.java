package com.example.arcade.tankwars;

import android.graphics.Point;
import sheep.collision.Polygon;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 14:43
 */
public class Map {

    private Point displaySize;
    private Polygon mapPolygon;

    public Map(Point displaySize) {
        this.displaySize = displaySize;
        mapPolygon = new Polygon(getMapPointSet());
    }

    public Polygon getMapPolygon() {
        return mapPolygon;
    }

    private float[] getMapPointSet() {
        float[] pointSet = {
                0, displaySize.y / 6 * 5,
                displaySize.x / 12 * 2, displaySize.y / 6 * 5,
                displaySize.x / 12 * 5, displaySize.y / 6 * 4,
                displaySize.x / 12 * 6, displaySize.y / 6 * 3,
                displaySize.x / 12 * 7, displaySize.y / 6 * 4,
                displaySize.x / 12 * 10, displaySize.y / 6 * 5,
                displaySize.x, displaySize.y / 6 * 5,
                displaySize.x, displaySize.y,
                0, displaySize.y
        };
        return pointSet;
    }
}
