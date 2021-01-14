package com.stuypulse.graphics3d.render;

public interface GlObject extends Comparable<GlObject> {
   
    void destroy();

    /**
     * A higher order GlObject will be destroyed later
     * than a lower order GlObject.
     * 
     * @return the order of this GlObject
     */
    default int getOrder() {
        return 0;
    }

    default int compareTo(GlObject other) {
        return (int) Math.signum(this.getOrder() - other.getOrder());
    }

}
