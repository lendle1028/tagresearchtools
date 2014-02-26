/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.instance.binding;

import elaborate.tag_analysis.oosm.OOSMElement;
import java.util.List;

/**
 * an EvaluatedObject is an object result from evaluating
 * all bindings of a OOSMNodeInstance tree
 * @author DELL
 */
public interface EvaluatedObject {
    public OOSMElement getRoot();
    public Object getRootValue();
    public Object getProperty(OOSMElement name);
    public List<OOSMElement> getPropertyNames();
}
