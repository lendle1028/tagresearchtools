/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.OOSMConstructList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENDLE
 */
public class DefaultOOSMConstructListImpl extends DefaultOOSMConstructImpl implements OOSMConstructList{
    private List<OOSMConstruct> constructs=new ArrayList<OOSMConstruct>();
    @Override
    public List<OOSMConstruct> getConstructs() {
        return constructs;
    }
}
