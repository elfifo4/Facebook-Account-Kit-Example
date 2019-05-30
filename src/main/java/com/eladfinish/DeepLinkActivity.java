package com.eladfinish;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.deeplinkdispatch.DeepLink;


//@DeepLink({"foo://example.com/deepLink/{expression}", "http://example.com/{expression}"})
@DeepLink("http://israelandroid.com/{expression}")


public class DeepLinkActivity extends Activity {

    private static final String ACTION_DEEP_LINK_METHOD = "deep_link_method";
    private static final String ACTION_DEEP_LINK_COMPLEX = "deep_link_complex";
    private static final String TAG = DeepLinkActivity.class.getSimpleName() + "#";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d(TAG, String.format("%s %s (%s)", key,
                        value.toString(), value.getClass().getName()));
            }
        }

        Log.e(TAG, intent.toString()); //http://example.com/eladfi
        Log.e(TAG, intent.getData().toString()); //http://example.com/eladfi
        Log.e(TAG, "intent.getAction() " + intent.getAction()); //android.intent.action.VIEW
        Log.e(TAG, "intent.getScheme() " + intent.getScheme()); //http


        Bundle parameters = intent.getExtras();
        Log.e(TAG, "Deeplink params: " + parameters);
        Log.e(TAG, "Deeplink params: " + parameters.toString());



        String exp = parameters.getString("expression");
        Log.e(TAG, "exp: " + exp);

        showToast("getBooleanExtra: " + intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false));

        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            Log.e(TAG, "DeepLink.IS_DEEP_LINK");
//            Bundle parameters = intent.getExtras();
            if (parameters != null) {
                String expression = parameters.getString("expression");
                EditText editText = findViewById(R.id.deep_link_edit_text);
                editText.setText(expression);
                showToast(expression);
            }
        }
        else {

        }

//        else if (intent.getBooleanExtra(DeepLink.REFERRER_URI, false)) {
//            Log.e(TAG, "DeepLink.REFERRER_URI");
//
//        }
    }

    private void showToast(String message) {
        Toast.makeText(this, "Deep Link: " + message, Toast.LENGTH_SHORT).show();
    }
}
