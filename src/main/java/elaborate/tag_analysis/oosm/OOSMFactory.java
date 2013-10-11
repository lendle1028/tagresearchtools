/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm;

import java.io.InputStream;
import java.io.Reader;

/**
 * constructs an OOSM instance from various sources
 * @author LENDLE
 */
public interface OOSMFactory {
    public OOSM createOOSM(InputStream input) throws Exception;
    public OOSM createOOSM(Reader input) throws Exception;
    public OOSM createNew();
}
