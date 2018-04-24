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
    implementation 'com.github.rebeccapurple:android:0d4eed3412'
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