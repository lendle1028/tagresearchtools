/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.instance.binding;

import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.OOSMElement;
import java.util.List;

/**
 * an EvaluatedObject is an object result from evaluating
 * all bindings of a OOSMNodeInstance tree
 * @author DELL
 */
public interface EvaluatedObject {
    public OOSMConstruct getRoot();
    public List getRootValue();
    public EvaluatedObject getProperty(OOSMConstruct name);
    public List<OOSMConstruct> getPropertyNames();
}
