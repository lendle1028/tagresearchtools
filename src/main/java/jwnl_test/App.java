/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jwnl_test;

import java.io.FileInputStream;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;
import net.didion.jwnl.dictionary.MorphologicalProcessor;

/**
 *
 * @author lendle
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        JWNL.initialize(new FileInputStream("file_properties.xml"));
        Dictionary dictionary=Dictionary.getInstance();
        MorphologicalProcessor morph=dictionary.getMorphologicalProcessor();
        IndexWord indexWord=morph.lookupBaseForm(POS.NOUN, "dogs");
        System.out.println(indexWord.getLemma());
        indexWord=dictionary.getIndexWord(POS.NOUN, indexWord.getLemma());
        System.out.println(indexWord.getSenseCount());
        for(int i=0; i<indexWord.getSenseCount(); i++){
            Synset sense=indexWord.getSenses()[i];
            for(int j=0; j<sense.getWordsSize(); j++){
                System.out.println(sense.getWord(j).getLemma());
            }
            System.out.println("==========================================");
        }
        /*Synset sense=indexWord.getSenses()[0];
        System.out.println(sense.getWord(2).getLemma());*/
        dictionary.close();
    }
}
