package rm.com.core.model.dto.utils;

import android.view.View;

import java.io.Serializable;

/**
 * Created by oscargallon on 6/6/16.
 */

public class Callbacks {

    public static final String EDIT_ACTION ="EDIT";
    public static final String DELETE_ACTION = "DELETE";

    public interface SendSerializableToFragment{
        void sendSerializableData(Serializable serializable);
    }

    public interface IAdapterListeners{
        void onAdapterClickListener(View view, Object object, String action);
    }
}
