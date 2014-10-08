/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm;

/**
 * an element behaves as a 'tag' in an html document
 * @author LENDLE
 */
public interface OOSMElement extends OOSMConstruct{
    public static final int TYPE_STRING=0;
    public static final int TYPE_NUMERIC=1;
    
    public int getType();
    public void setType(int type);
}
