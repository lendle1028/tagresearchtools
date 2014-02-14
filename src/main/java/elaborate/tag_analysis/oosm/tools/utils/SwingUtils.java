/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.tools.utils;

import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author DELL
 */
public class SwingUtils {
    public static void repaint(final Component component){
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                component.repaint();
            }
        });
    }
    
    public static boolean isTextFieldEmpty(JTextField tx){
        return tx.getText()==null || tx.getText().length()==0;
    }
}
