package com.stuypulse.graphics3d.globject;

import java.util.*;

public interface GlObject extends Comparable<GlObject> {
   
    /***********************
     * GlObject MANAGEMENT *
     ***********************/

    final class Manager {
        private final List<GlObject> objects;
        
        public Manager() {
            this.objects = new ArrayList<>();
        }

        public void addObject(GlObject toAdd) {
            this.objects.add(toAdd);
        }

        public void destroy() {
            Collections.sort(objects);
            for (GlObject obj : objects) {
                obj.destroy();
            }
        }
    }

    /**********************
     * GlObject INTERFACE *
     **********************/

    public void destroy();

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
