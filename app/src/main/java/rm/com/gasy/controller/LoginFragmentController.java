package rm.com.gasy.controller;

import android.app.Activity;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rm.androidesentials.controllers.abstracts.AbstractController;

import java.util.Map;

/**
 * Created by oscargallon on 6/14/16.
 */

public class LoginFragmentController extends AbstractController {
    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public LoginFragmentController(Activity activity) {
        super(activity);
    }

    public void createUser(String email, String password) {
        final Firebase firebase = new Firebase("https://boiling-fire-7071.firebaseio.com");
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                Log.i("FIREBASE", stringObjectMap.get("uid").toString());


            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Log.i("FIREBASE", firebaseError.getMessage());
            }
        });
    }
}
