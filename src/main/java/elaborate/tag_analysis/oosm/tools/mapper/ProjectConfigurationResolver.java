/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.mapper;

import java.io.File;

/**
 * 
 * @author lendle
 */
public class ProjectConfigurationResolver {
    /**
     * resolve project dependencies: schema/document against the given root directory
     * @param conf
     * @param rootDirectory
     * @return
     * @throws Exception 
     */
    public static ProjectConfiguration resolveAgainstRootDirectory(ProjectConfiguration conf, File rootDirectory) throws Exception{
        if(conf.getProjectLocation().equals(rootDirectory)){
            //the project is kept in the same location
            return conf;
        }else{
            //prioritize local dependency
            ProjectConfiguration newConf=(ProjectConfiguration) conf.clone();
            newConf.setProjectLocation(rootDirectory);
            newConf.setOosmFile(new File(rootDirectory, conf.getOosmFile().getName()));
            if(newConf.getDocumentURL().getProtocol().startsWith("file")){
                newConf.setDocumentURL(new File(rootDirectory, conf.getDocumentURL().getFile().substring(conf.getDocumentURL().getFile().lastIndexOf("/")+1)).toURI().toURL());
            }
            newConf.save(new File(rootDirectory, ".project"));
            return newConf;
        }
    }
}
