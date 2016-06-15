package rm.com.gasy.presentation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import rm.com.gasy.R;
import rm.com.gasy.controller.LoginFragmentController;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private EditText etEmail;

    private EditText etPassword;

    private Button btnLogin;

    private Button btnSignup;

    private LoginFragmentController loginFragmentController;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViewComponents(view);
        loginFragmentController = new LoginFragmentController(getActivity());
        return view;
    }

    private void initViewComponents(View view) {
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnSignup = (Button) view.findViewById(R.id.btn_sign_up);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragmentController.createUser(etEmail.getText().toString(),
                        etPassword.getText().toString());
            }
        });
    }


}
