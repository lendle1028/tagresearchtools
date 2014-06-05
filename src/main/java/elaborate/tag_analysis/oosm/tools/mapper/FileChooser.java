/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.mapper;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileView;

/**
 *
 * @author lendle
 */
public class FileChooser extends JFileChooser {
    private Icon icon=null;
    public FileChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);
        try {
            icon=new ImageIcon(this.getClass().getClassLoader().getResource("elaborate/tag_analysis/oosm/tools/mapper/resources/lucky_shamrock_24.png").toURI().toURL());
        } catch (Exception ex) {
            Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setFileView(new FileView(){

            @Override
            public Icon getIcon(File f) {
                if(new File(f, ".project").exists()){
                    try {
                        return icon;
                    } catch (Exception ex) {
                        Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return super.getIcon(f); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    @Override
    public boolean accept(File file) {
        return file.isDirectory()/* && new File(file, ".project").exists()*/;
    }

    //a customized file chooser that only displays project folders
    @Override
    public void approveSelection() {
        if (new File(getSelectedFile(), ".project").exists() == false) {
            // beep
            return;
        } else {
            super.approveSelection();
        }
    }
}
