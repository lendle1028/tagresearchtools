/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm;

import javax.xml.namespace.QName;

/**
 * An OOSMConstruct is a basic construct in OOSM and
 * can either be an OOSMElement or an OOSMElementList.
 * Here, an OOSMElementList is in fact a repeatable
 * group.
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
