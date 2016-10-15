package com.blingbling.sivant.blingbling;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bella on 10/15/2016.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://blingbling.netne.net/Register.php";
    private Map<String,String> params;

    public RegisterRequest(String first_name, String last_name,String user_name,String password,String email,int radios,boolean popup_coupons,Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params= new HashMap<>();
        params.put("first_name",first_name);
        params.put("last_name",last_name);
        params.put("user_name",user_name);
        params.put("password",password);
        params.put("email",email);
        params.put("radios", radios+"");
        params.put("popup_coupons", popup_coupons+"");
    }

    // @Override
    public Map<String, String> getParams() {
        return params;
    }
}
