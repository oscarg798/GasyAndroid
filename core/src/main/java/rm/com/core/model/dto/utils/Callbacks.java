package rm.com.core.model.dto.utils;

import java.io.Serializable;

/**
 * Created by oscargallon on 6/6/16.
 */

public class Callbacks {

    public interface SendSerializableToFragment{
        void sendSerializableData(Serializable serializable);
    }
}
