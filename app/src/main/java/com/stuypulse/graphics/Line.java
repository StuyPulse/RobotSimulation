package com.stuypulse.graphics;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;

public class Line {
    
    public final Vector2D a;
    public final Vector2D b;
    
    public Line(Vector2D a, Vector2D b) {
        this.a = a;
        this.b = b;
    }

    public Line transform(Vector2D offset, Angle angle) {
        return new Line(a.rotate(angle).add(offset), b.rotate(angle).add(offset));
    }

    public void draw() {
        StdDraw.line(a.x, a.y, b.x, b.y);
    }

}
