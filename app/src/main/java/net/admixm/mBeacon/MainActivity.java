package net.admixm.mbeacon;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import net.admixm.mbeacon.mbeaconsample.R;
import net.admixm.mbeacon.utils.ADMXLog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    //applicationCode 는 '애드믹스엠 홀딩스'에서 지정해준 앱 넘버로 설정
    private String applicationCode = "3";

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initLayout( );
        initMBeaconSDK( );
    }

    protected void initLayout( )
    {
        TextView versionName = ( TextView ) findViewById( R.id.text_view_version_name );
        versionName.setText( ADMXBeaconAdServiceLib.SDK_VERSION );

        TextView appCode = ( TextView ) findViewById( R.id.text_view_app_no );
        appCode.setText( applicationCode );
    }

    private void initMBeaconSDK( )
    {
        // 스캔할 비콘 uuid 리스트를 작성합니다.
        final ArrayList beaconUuidList = new ArrayList( );
        beaconUuidList.add( "6bed2915-45e9-45fd-885a-7c648112119a" );
        beaconUuidList.add( "B9407F30-F5F8-466E-AFF9-25556B57FE6D" );
        beaconUuidList.add( "e2c56db5-dffb-48d2-b060-d0f5a71096e0" );
        beaconUuidList.add( "7b3f5509-7cf1-4637-a87a-5d44e43cdbd7" );
        beaconUuidList.add( "73a8edce-1227-9db7-18ff-6243e19db53d" );
        beaconUuidList.add( "c276ea6f-7de8-444a-9904-7ead820de7d9" );


        // 광고 수신 결과를 서버로 보낼지 여부를 설정합니다.
        // false로 설정하면 광고를 계속해서 받을 수 있습니다. 디버그 할 때만 사용해주세요.
        ADMXBeaconAdServiceLib.setSendingAdResult( this, true );

        // mBeacon 로그를 사용할지 여부를 설정합니다.
        ADMXBeaconAdServiceLib.setDebugMode( this, true );

        // 화면이 꺼진 채로 광고를 받았을 때 광고를 바로 표시할지 여부를 설정합니다.
        ADMXBeaconAdServiceLib.enableAutoDisplayOnScreenOff( this, true );


        PermissionHelper.activatePermission( this, new Runnable( )
        {
            @Override
            public void run( )
            {
                ADMXBeaconAdServiceLib.init( MainActivity.this, beaconUuidList, applicationCode );
            }
        } );
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults )
    {
        PermissionHelper.onRequestPermissionsResult( this, requestCode, grantResults );
    }

}