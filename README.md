# mBeacon
mBeacon SDK는 지하철에 설치된 비콘에서의 Request를 받아 AD를 띄우게 하는 것입니다.

이 문서는 안드로이드를 기준으로 작성되었습니다.

[개발자 센터](https://m-beacon-rrtt96.c9users.io/)







### 시작하기

mBeacon SDK는 jCenter를 통해 배포되고 있습니다.

앱 수준의 build.gradle파일에 아래와 같은 의존성을 추가합니다.


```Gradle
dependencies {
    compile 'net.admixm.mBeacon:general:+'
}
```


### Proguard

mBeacon SDK는 Android 공식 proguard를 지원합니다.
만약, proguard를 적용중이라면 아래와 같이 proguard-rules.pro에 추가합니다.

```Gradle
-keep class net.admixm.mbeacon.** {*;}
-ignorewarnings
```
