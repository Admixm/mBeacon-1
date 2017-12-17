# mBeacon
mBeacon SDK는 지하철에 설치된 비콘에서의 Request를 받아 AD를 띄우게 하는 것입니다

이 문서는 안드로이드를 기준으로 작성되었습니다

[개발자 센터](http://www.lubetr.com/mbeacon/mbeacon/)

[서비스 소개](http://www.mbeacon.kr)
</br>
</br>
</br>
</br>
## 시작하기

mBeacon SDK는 jCenter를 통해 배포되고 있습니다

앱 수준의 build.gradle파일에 아래와 같은 의존성을 추가합니다


```Gradle
dependencies {
    compile 'net.admixm.mBeacon:general:+'
}
```
</br>
</br>
</br>
</br>


### Proguard

mBeacon SDK는 Android 공식 proguard를 지원합니다

만약, proguard를 적용중이라면 아래와 같이 proguard-rules.pro에 추가합니다

```Gradle
-keep class net.admixm.mbeacon.** {*;}
-ignorewarnings
```
</br>
</br>
</br>
</br>
### Init

MainActivity.java에 아래와 같은 메쏘드를 추가하고 onCreate()에서 호출합니다

context가 만료되는 환경에서 init을 호출해서는 안됩니다.(: 스플래시 화면 등)

```Android
import net.admixm.mbeacon.ADMXBeaconAdServiceLib;
import net.admixm.mbeacon.parameters.ADMXParameters;

private void initADMXLibrary( )
{
    ADMXParameters params = new ADMXParameters();

    // 애드믹스엠 홀딩스에서 발급받은 어플리케이션 코드를 기입합니다.
    params.ApplicationCode =  앱_코드;

    // mBeacon SDK를 초기화합니다.
    ADMXBeaconAdServiceLib.init( MainActivity.this, params, new ADMXLocationAgreementListener( )
    {
        @Override
        public void onSuccess( )
        {
        	Log.d("ADMX","비콘 서비스 시작!");
        }

        @Override
        public void onDecline( )
        {
            Log.e("ADMX","사용자가 권한에 동의하지 않았습니다.");
        }
    }  );
}

```
</br>
</br>
</br>
</br>


## API

### ADMXLocationAgreementListener

ADMXLocationAgreementListener는 두개의 메쏘드를 위임하는 Interface로써 사용자 권한 동의 여부를 콜백합니다
</br>
</br>
### onSuccess

사용자가 모든 권한에 동의 할 경우 호출 됩니다

```Android
		@Override
        public void onSuccess( )
        {
        }
```
</br>
</br>
### onDecline

사용자가 모든 권한에 동의하지 않을 경우 호출됩니다


```Android
		@Override
        public void onDecline( )
        {
        }
```

</br>
</br>
</br>
</br>

### ADMXBeaconAdServiceLib

ADMXBeaconAdServiceLib는 세개의 static 메쏘드를 사용 할 수 있습니다
</br>
</br>
### init


init 메쏘드는 시작하기에서 사용했던 메쏘드입니다

ADMXLocationAgreementListener는 @nullable 어노테이션이 붙어 있으며 null값으로 넘겨주어도 작동합니다
```Android
ADMXBeaconAdServiceLib.init( Activity, ADMXParameters, @nullable ADMXLocationAgreementListener ); 
```
</br>
</br>
### dispose

dispose 메쏘드는 서비스를 종료시킬 수 있는 메쏘드입니다.

경우에 따라 우리 서비스와 충돌이 나는 경우가 있다면, 사용하시면 됩니다.

```Android
ADMXBeaconAdServiceLib.dispose( Context );
```
</br>
</br>
### checkSelfAllPermissions

checkSelfAllPermissions 메쏘드는 사용자가 안드로이드 퍼미션에 동의하였는지를 검사하여 재 동의를 받게합니다

```Android
ADMXBeaconAdServiceLib.checkSelfAllPermissions( Context, ADMXBeaconAdServiceLib.PERMISSIONS );
```

</br>
감사합니다.
