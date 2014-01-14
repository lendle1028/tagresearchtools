/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.tools.mapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author lendle
 */
public class OOSMMapperPopupMenu extends JPopupMenu{
    private List<OOSMMapperPopupMenuListener> listeners=new ArrayList<OOSMMapperPopupMenuListener>();
    private JMenuItem menuInsertBefore=null, menuInsertAfter=null;
    public OOSMMapperPopupMenu() {
        menuInsertBefore=new JMenuItem("Insert Before");
        menuInsertAfter=new JMenuItem("Insert After");
        this.add(menuInsertBefore);
        this.add(menuInsertAfter);
        
        menuInsertBefore.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                for(OOSMMapperPopupMenuListener l : listeners){
                    l.insertBeforeClicked();
                }
            }
        });
        
        menuInsertAfter.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                for(OOSMMapperPopupMenuListener l : listeners){
                    l.insertAfterClicked();
                }
            }
        });
    }
    
    public void addOOSMMapperPopupMenuListener(OOSMMapperPopupMenuListener l){
        this.listeners.add(l);
    }
    
    public void removeOOSMMapperPopupMenuListener(OOSMMapperPopupMenuListener l){
        this.listeners.remove(l);
    }

    public JMenuItem getMenuInsertBefore() {
        return menuInsertBefore;
    }

    public JMenuItem getMenuInsertAfter() {
        return menuInsertAfter;
    }
    
    
}
