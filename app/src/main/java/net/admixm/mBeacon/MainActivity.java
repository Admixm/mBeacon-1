package net.admixm.mbeacon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.admixm.mbeacon.mbeaconsample.R;
import net.admixm.mbeacon.parameters.ADMXParameters;
import net.admixm.mbeacon.utils.ADMXLog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private String APP_NO = "3";

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
        appCode.setText( APP_NO );

        Button stopButton = ( Button ) findViewById( R.id.button_stop );
        stopButton.setOnClickListener( new View.OnClickListener( )
        {
            @Override
            public void onClick( View v )
            {
                ADMXBeaconAdServiceLib.dispose( MainActivity.this );
            }
        } );
    }

    private void initMBeaconSDK( )
    {
        final ADMXParameters params = new ADMXParameters( );

        // 애드믹스엠 홀딩스에서 발급받은 어플리케이션 코드를 기입합니다.
        params.ApplicationCode = APP_NO;

        // 스캔할 비콘 uuid 리스트를 작성합니다.
        params.BeaconUUIDList = new ArrayList<>( );
        params.BeaconUUIDList.add( "6bed2915-45e9-45fd-885a-7c648112119a" );
        params.BeaconUUIDList.add( "B9407F30-F5F8-466E-AFF9-25556B57FE6D" );
        params.BeaconUUIDList.add( "e2c56db5-dffb-48d2-b060-d0f5a71096e0" );
        params.BeaconUUIDList.add( "7b3f5509-7cf1-4637-a87a-5d44e43cdbd7" );
        params.BeaconUUIDList.add( "73a8edce-1227-9db7-18ff-6243e19db53d" );
        params.BeaconUUIDList.add( "c276ea6f-7de8-444a-9904-7ead820de7d9" );

        ADMXBeaconAdServiceLib.setDebugMode( this, true );

        PermissionHelper.activatePermission( this, new Runnable( )
        {
            @Override
            public void run( )
            {
                // mBeacon SDK를 초기화합니다.
                ADMXBeaconAdServiceLib.init( MainActivity.this, params );
            }
        } );
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults )
    {
        PermissionHelper.onRequestPermissionsResult( this, requestCode, grantResults );
    }

}