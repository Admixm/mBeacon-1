package net.admixm.mbeacon;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;


/**
 * Created by MARi on 2017-04-27.
 */

public class PermissionHelper
{
    protected static final String TAG = "PermissionHelper";
    private static Runnable callback;

    public static void activatePermission( final Activity context, Runnable callback )
    {
        PermissionHelper.callback = callback;

        // 단말기에 앱이 필요한 권한들을 요청
        // 사용자가 이전에 한번이라도 권한 요청을 거부했다면,
        // 다시한번 권한 취득을 위해 권한요청 이유를 알리는 AlertDialog 제목/내용을 입력한다.
        int permissionResult = new ADMXPermissionRequester.Builder( context )
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
                                ADMXLog.d( context, TAG, "사용자가 권한 요청을 취소하였습니다." );
                            }
                        } );

        // 이미 모든 권한을 획득한 경우
        // (Android Marshmallow 버전 이상일 경우만 해당)
        if ( permissionResult == ADMXPermissionRequester.ALREADY_GRANTED )
        {
            ADMXLog.d( context, TAG, "PERMISSION_ALREADY_GRANTED" );
            if ( ADMXBeaconAdServiceLib.checkSelfAllPermissions( context, ADMXBeaconAdServiceLib.PERMISSIONS ) )
            {
                callback.run();
            }
        }

        // Android Marshmallow 버전 미만일 경우
        // ('admixmbeacon-release.aar' 라이브러리의 'AndroidManifest.xml'에 필요한 권한들이 미리 설정되어 있다.)
        else if ( permissionResult == ADMXPermissionRequester.NOT_SUPPORTED_VERSION )
        {
            ADMXLog.d( context, TAG, "NOT_SUPPORTED_ANDROID_MARSHMALLOW" );
            callback.run();
        }

        // 권한 취득을 요청한 경우
        // 권한취득 결과(onRequestPermissionsResult())에서 권한 요청에 대한 응답을 받는다.
        // (Android Marshmallow 버전 이상일 경우만 해당)
        else if ( permissionResult == ADMXPermissionRequester.REQUEST_PERMISSION )
        {
            ADMXLog.d( context, TAG, "REQUEST_PERMISSION" );
        }

    }

    public static void onRequestPermissionsResult( Context context, int requestCode, @NonNull int[] grantResults )
    {

        if ( requestCode == ADMXBeaconAdServiceLib.PERMISSION_REQUEST_CODE )
        {
            if ( grantResults.length > 0 )
            {
                boolean haveAllPermissionsGranted = true;

                for ( int grantResult : grantResults )
                {
                    if ( grantResult == PackageManager.PERMISSION_DENIED )
                    {
                        haveAllPermissionsGranted = false;
                    }
                }
                if ( haveAllPermissionsGranted )
                {
                    ADMXLog.d( context, TAG, "사용자가 권한 요청을 허용하였습니다." );

                    if ( ADMXBeaconAdServiceLib.checkSelfAllPermissions( context, ADMXBeaconAdServiceLib.PERMISSIONS ) )
                    {
                        new Handler().post( PermissionHelper.callback);
                    }
                }
                else
                {
                    ADMXLog.d( context, TAG, "사용자가 권한 요청을 거부하였습니다." );
                }
            }
            else
            {
                ADMXLog.e( context, TAG, "권한취득 결과값이 없습니다." );
            }
        }
    }
}
