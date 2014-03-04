/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.mapper;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lendle
 */
public class ProjectConfiguration implements Cloneable{

    private File projectLocation = null;
    private File oosmFile = null;
    private URL documentURL = null;
    private String projectName = null;

    public File getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(File projectLocation) {
        this.projectLocation = projectLocation;
    }

    public File getOosmFile() {
        return oosmFile;
    }

    public void setOosmFile(File oosmFile) {
        this.oosmFile = oosmFile;
    }

    public URL getDocumentURL() {
        return documentURL;
    }

    public void setDocumentURL(URL documentURL) {
        this.documentURL = documentURL;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public static ProjectConfiguration load(File file) throws IOException{
        try(FileInputStream input=new FileInputStream(file); XMLDecoder decoder=new XMLDecoder(input);){
            return (ProjectConfiguration) decoder.readObject();
        }
    }
    
    public void save(File file) {
        try (FileOutputStream output = new FileOutputStream(file); XMLEncoder encoder = new XMLEncoder(output);) {
            
            encoder.setPersistenceDelegate(URL.class, new PersistenceDelegate() {

                @Override
                protected Expression instantiate(Object oldInstance, Encoder out) {
                    return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{((URL) oldInstance).toString()});
                }
            });
            encoder.setPersistenceDelegate(File.class, new PersistenceDelegate() {

                @Override
                protected Expression instantiate(Object oldInstance, Encoder out) {
                    return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{((File) oldInstance).getAbsolutePath()});
                }
            });
            encoder.writeObject(this);
        } catch (Exception ex) {
            Logger.getLogger(NewProjectDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
