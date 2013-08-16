/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis;

/**
 *
 * @author lendle
 */
public class TagStdevRatioPair implements Comparable{

    private Tag tag;
    private double stdevRatio;

    public TagStdevRatioPair(Tag tag, double stdev) {
        this.tag = tag;
        this.stdevRatio = stdev;
    }

    
    /**
     * Get the value of stdev
     *
     * @return the value of stdev
     */
    public double getStdevRatio() {
        return stdevRatio;
    }

    /**
     * Set the value of stdev
     *
     * @param stdev new value of stdev
     */
    public void setStdevRatio(double stdevRatio) {
        this.stdevRatio = stdevRatio;
    }

    /**
     * Get the value of tag
     *
     * @return the value of tag
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Set the value of tag
     *
     * @param tag new value of tag
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.stdevRatio) ^ (Double.doubleToLongBits(this.stdevRatio) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TagStdevRatioPair other = (TagStdevRatioPair) obj;
        if (Double.doubleToLongBits(this.stdevRatio) != Double.doubleToLongBits(other.stdevRatio)) {
            return false;
        }
        return true;
    }


    @Override
    public int compareTo(Object t) {
        if(!(t instanceof TagStdevRatioPair)){
            return 1;
        }
        TagStdevRatioPair _t=(TagStdevRatioPair) t;
        if(Math.abs(this.stdevRatio)<Math.abs(_t.stdevRatio)){
            return -1;
        }
        else if(Math.abs(this.stdevRatio)==Math.abs(_t.stdevRatio)){
            return 0;
        }
        else{
            return 1;
        }
    }
}
