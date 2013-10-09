/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm;

import javax.xml.namespace.QName;

/**
 * An OOSMConstruct is a basic construct in OOSM.
 * 
 * @author LENDLE
 */
public interface OOSMConstruct {
    /**
     * @return the name of the construct
     */
    public QName getName();
    /**
     * 
     * @return the description of the construct
     */
    public String getDescription();
}
