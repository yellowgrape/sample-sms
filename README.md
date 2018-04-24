# sample-sms

1. 기본 리포지토리 추가 (./build.gradle)

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. rebeccapurple 라이브러리 추가 (./app/build.gradle)

- json
- textory android common lib
- textory java common lib

```
dependencies {
    ...
    implementation 'com.google.code.gson:gson:2.8.2'
    implementation 'com.github.rebeccapurple:android:b5a23cc3f6'
    implementation 'com.github.rebeccapurple:java:0d4eed3412'
}
```

3. java 1.8 설정

```
android {
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

3. Application 클래스 재정의

- json 초기화
- log 초기화
- sms 초기화

```
package io.textory.sms;

public class Application extends android.app.Application {

    @Override
    public void onCreate(){
        super.onCreate();

        functional.json.init();                             /** initialize gson lib */

        functional.log.console(false);                      /** remove console out */
        functional.log.depth(5);                            /** set method depth */
        functional.log.date(false);                         /** remove date out */
        functional.log.stacktrace(false);                   /** remove stacktrace  */
        functional.log.add(functional.android.log.get());   /** set android log method */

        functional.android.telephony.sms.init(1024);        /** initialize sms */
    }
}
```

4. AndroidManifest.xml 에 Application 이름을 정의하여 위의 클래스를 사용하도록 설정

```
    <application
        android:name=".Application"
        ... >
        ...
    </Application>
        
```

5. Permission 정의 (AndroidManifest.xml)

```
    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

6. Permission Activity 생성

```
package io.textory.sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class PermissionActivity extends AppCompatActivity {
    private static String[] PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            return;
        }
        permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            return;
        }
        
        /** 다른 ACTIVITY 시작 코드 */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

```

7. 최초 실행 ACTIVITY 를 PERMISSION ACTIVITY 로 변경 (AndroidManifest.xml)

```
 <application
        ...>
        
        <activity android:name=".MainActivity" />
        <activity android:name=".PermissionActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        
    </application>
```