/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.tools.mapper;

import java.io.File;
import java.net.URL;

/**
 *
 * @author lendle
 */
public class ProjectConfiguration {
    private File projectLocation=null;
    private File oosmFile=null;
    private URL documentURL=null;
    private String projectName=null;

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
    
    
}
