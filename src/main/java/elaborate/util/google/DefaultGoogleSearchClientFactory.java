/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.util.google;

import elaborate.util.google.impl.DefaultGoogleSearchClientImpl;

/**
 *
 * @author lendle
 */
public class DefaultGoogleSearchClientFactory implements GoogleSearchClientFactory{

    @Override
    public GoogleSearchClient createGoogleSearchClient() {
        return new DefaultGoogleSearchClientImpl();
    }
    
}
