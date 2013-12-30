/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.impl.gson;

import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.impl.DefaultOOSMElementImpl;

/**
 *
 * @author lendle
 */
public class OOSMElementInterfaceDeserializer extends BaseInterfaceDeserializer<OOSMElement> {

    public OOSMElementInterfaceDeserializer() {
        super(DefaultOOSMElementImpl.class);
    }

}
