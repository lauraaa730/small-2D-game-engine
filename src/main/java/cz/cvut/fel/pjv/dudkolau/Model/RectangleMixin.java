package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class RectangleMixin {
    @JsonIgnore
    public abstract java.awt.geom.Rectangle2D getBounds2D();
}
