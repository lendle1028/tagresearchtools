/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm;

import java.util.List;

/**
 * an element behaves as a 'tag' in an html document
 * @author LENDLE
 */
public interface OOSMElementList extends OOSMConstruct{
    public List<OOSMElement> getElements();
}
