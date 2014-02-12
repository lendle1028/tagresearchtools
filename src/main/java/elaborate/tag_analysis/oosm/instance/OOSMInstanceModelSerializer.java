/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.instance;

import elaborate.tag_analysis.oosm.OOSM;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author lendle
 */
public interface OOSMInstanceModelSerializer {
    public OOSMInstanceModel createInstanceModel(InputStream input) throws Exception;
    public OOSMInstanceModel createNew(OOSM schema);
    public void save(OOSMInstanceModel model, OutputStream output) throws Exception;
}
