/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicperceptrons;

import java.util.ArrayList;

/**
 *
 * @author Urs Mobile
 */
class InstanceMissMatchException extends Exception {

    public InstanceMissMatchException(Instances aThis, double[] instance) {
        super("Attempted to add instance of size: " + instance.length + " to "
                + "Instance object with instance size: " + aThis);
    }

    InstanceMissMatchException(String string) {
        super(string);
    }
    
}
