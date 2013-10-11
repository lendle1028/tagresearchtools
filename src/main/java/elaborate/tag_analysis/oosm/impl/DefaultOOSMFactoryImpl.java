/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMFactory;
import java.io.InputStream;
import java.io.Reader;

/**
 *
 * @author LENDLE
 */
public class DefaultOOSMFactoryImpl implements OOSMFactory{

    @Override
    public OOSM createOOSM(InputStream input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OOSM createOOSM(Reader input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OOSM createNew() {
        return new DefaultOOSMImpl();
    }
    
}
