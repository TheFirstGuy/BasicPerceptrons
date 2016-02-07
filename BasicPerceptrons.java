package basicperceptrons;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Urs Mobile
 */
public class BasicPerceptrons {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Instances logicOr = new Instances();
            double[] temp = { 0.0, 0.0 };
            logicOr.add( temp, 0.0);
            temp[1] = 1.0;
            logicOr.add(temp, 1.0);
            temp[0] = 1.0;
            logicOr.add(temp, 1.0);
            temp[1] = 0.0;
            logicOr.add(temp, 1.0);
        } catch (Instances.InstanceNormalizedException ex) {
            Logger.getLogger(BasicPerceptrons.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceMissMatchException ex) {
            Logger.getLogger(BasicPerceptrons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
