package net.admixm.mbeacon.mbeaconsample;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import net.admixm.mbeacon.ADMXBeaconAdServiceLib;
import net.admixm.mbeacon.utils.ADMXLog;
import net.admixm.mbeacon.utils.ADMXPermissionRequester;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    protected static final String TAG = "MainActivity";

    // 비콘 UUID 리스트
    private List< String > beaconUuidList = null;

    //applicationCode 는 '애드믹스엠 홀딩스'에서 지정해준 앱 넘버로 설정
    private String applicationCode = "발급받은 앱 코드 입력";


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        /**
         * 'null'로 설정하면 디폴트 UUID를 가진 비콘만 스캔한다.
         * (디폴트 UUID: e2c56db5-dffb-48d2-b060-d0f5a71096e0)
         * 추가로 다른 비콘도 스캔하고 싶을 때만 ADMXBeaconAdServiceLib.init()시에 파라미터로 넣어준다.
         */
        beaconUuidList = new ArrayList<>( );
        beaconUuidList.add( "6bed2915-45e9-45fd-885a-7c648112119a" );
        beaconUuidList.add( "B9407F30-F5F8-466E-AFF9-25556B57FE6D" );
        beaconUuidList.add( "e2c56db5-dffb-48d2-b060-d0f5a71096e0" );
        beaconUuidList.add( "7b3f5509-7cf1-4637-a87a-5d44e43cdbd7" );
        beaconUuidList.add( "73a8edce-1227-9db7-18ff-6243e19db53d" );
        beaconUuidList.add( "c276ea6f-7de8-444a-9904-7ead820de7d9" );

        // 광고확인 요청 유무 설정
        // 'false'로 설정하면 광고를 계속해서 수신한다.
        ADMXBeaconAdServiceLib.setSendingAdResult( this, true );

        // 로그캣 정보표시 유무 설정
        // 'true'로 설정하면 로그캣에 mBeacon 라이브러리 로그를 출력한다.
        ADMXBeaconAdServiceLib.setDebugMode( this, false );


        // 단말기에 앱이 필요한 권한들을 요청
        // 사용자가 이전에 한번이라도 권한 요청을 거부했다면,
        // 다시한번 권한 취득을 위해 권한요청 이유를 알리는 AlertDialog 제목/내용을 입력한다.
        int permissionResult = new ADMXPermissionRequester.Builder( MainActivity.this )
                .setTitle( "권한 요청" )
                .setMessage( "앱의 원활한 기능 사용을 위해 단말기의 특정 권한이 필요합니다. 계속 진행하시겠습니까?" )
                .setPositiveButtonName( "네" )
                .setNegativeButtonName( "아니요" )
                .create( )
                .request( ADMXBeaconAdServiceLib.PERMISSIONS, ADMXBeaconAdServiceLib.PERMISSION_REQUEST_CODE,
                        new ADMXPermissionRequester.OnClickDenyButtonListener( )
                        {
                            @Override
                            public void onClick( Activity activity )
                            {
                                ADMXLog.d( MainActivity.this, TAG, "사용자가 권한 요청을 취소하였습니다." );
                            }
                        } );

        // 이미 모든 권한을 획득한 경우
        // (Android Marshmallow 버전 이상일 경우만 해당)
        if ( permissionResult == ADMXPermissionRequester.ALREADY_GRANTED )
        {
            ADMXLog.d( this, TAG, "PERMISSION_ALREADY_GRANTED" );
            // 단말기가 앱에 대한 모든 권한을 갖고 있는지 검사
            if ( ADMXBeaconAdServiceLib.checkSelfAllPermissions( this, ADMXBeaconAdServiceLib.PERMISSIONS ) )
            {
                // 비콘 UUID 리스트와 앱 고유번호 값을 파라미터로 설정하고, 비콘 스캔 서비스 시작
                ADMXBeaconAdServiceLib.init( this, beaconUuidList, applicationCode );
            }
        }

        // Android Marshmallow 버전 미만일 경우
        // ('admixmbeacon-release.aar' 라이브러리의 'AndroidManifest.xml'에 필요한 권한들이 미리 설정되어 있다.)
        else if ( permissionResult == ADMXPermissionRequester.NOT_SUPPORTED_VERSION )
        {
            ADMXLog.d( this, TAG, "NOT_SUPPORTED_ANDROID_MARSHMALLOW" );
            // 비콘 UUID 리스트와 앱 고유번호 값을 파라미터로 설정하고, 비콘 스캔 서비스 시작
            // 앱 고유번호는 '애드믹스엠 홀딩스'에서 지정해준 숫자로 설정
            ADMXBeaconAdServiceLib.init( this, beaconUuidList, applicationCode );
        }

        // 권한 취득을 요청한 경우
        // 권한취득 결과(onRequestPermissionsResult())에서 권한 요청에 대한 응답을 받는다.
        // (Android Marshmallow 버전 이상일 경우만 해당)
        else if ( permissionResult == ADMXPermissionRequester.REQUEST_PERMISSION )
        {
            ADMXLog.d( this, TAG, "REQUEST_PERMISSION" );
        }
    }


    /**
     * @param int      requestCode 요청코드
     * @param String[] permissions 단말기에 요청한 권한 리스트(배열)
     * @param int[]    grantResults 권한 요청에 대한 결과값 리스트(배열/인덱스별로 매칭)
     * @return void
     * @throws
     * @brief 권한 요청에 대한 응답을 받는 함수
     * @details
     */
    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults )
    {
        ADMXLog.i( this, TAG, "OnRequestPermissionsResult" );

        if ( requestCode == ADMXBeaconAdServiceLib.PERMISSION_REQUEST_CODE )
        {
            if ( grantResults.length > 0 )
            {
                boolean haveAllPermissionsGranted = true;

                // 사용자가 요청한 모든 권한을 수락했는지 체크
                for ( int grantResult : grantResults )
                {
                    if ( grantResult == PackageManager.PERMISSION_DENIED )
                    {
                        haveAllPermissionsGranted = false;
                    }
                }
                // 사용자가 권한 요청을 모두 허용한 경우
                if ( haveAllPermissionsGranted )
                {
                    ADMXLog.d( this, TAG, "사용자가 권한 요청을 허용하였습니다." );
                    // 단말기가 앱에 대한 모든 권한을 갖고 있는지 검사
                    if ( ADMXBeaconAdServiceLib.checkSelfAllPermissions( this, ADMXBeaconAdServiceLib.PERMISSIONS ) )
                    {
                        // 비콘 UUID 리스트와 앱 고유번호 값을 파라미터로 설정하고, 비콘 스캔 서비스 시작
                        ADMXBeaconAdServiceLib.init( this, beaconUuidList, applicationCode );
                    }
                }
                else
                {
                    ADMXLog.d( this, TAG, "사용자가 권한 요청을 거부하였습니다." );
                }
            }
            else
            {
                ADMXLog.e( this, TAG, "권한취득 결과값이 없습니다." );
            }
        }
    }
}
