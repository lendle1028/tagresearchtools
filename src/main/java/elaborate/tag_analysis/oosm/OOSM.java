/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm;

import java.util.List;

/**
 * an Object-Oriented Schema Model instance
 * @author LENDLE
 */
public interface OOSM extends OOSMConstruct{
    public OOSMElement getRootElement();
    public List<OOSMRule> getRules();
}
