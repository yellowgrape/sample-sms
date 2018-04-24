package io.textory.sms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** send sms no callback */
        functional.android.telephony.sms.send("01087197281", "hello");
        /** send sms with callback */
        functional.android.telephony.sms.send(this, "01087197281", "hello world", 1024, functional.log::e);
    }
}
