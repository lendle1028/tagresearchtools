/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm;

import java.util.List;

/**
 *
 * @author LENDLE
 */
public interface OOSMRule {
    public OOSMElement getHeadingElement();
    public List<OOSMConstruct> getConstructs();
}
