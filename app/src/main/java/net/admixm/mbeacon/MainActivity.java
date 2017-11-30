package net.admixm.mbeacon;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.admixm.mbeacon.mbeaconsample.R;

import net.admixm.mbeacon.parameters.ADMXParameters;

public class MainActivity extends AppCompatActivity
{
    private String APP_NO = "23";

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

        PermissionHelper.activatePermission( this, new Runnable( )
        {
            @Override
            public void run( )
            {

                // mBeacon SDK를 초기화합니다.
                ADMXBeaconAdServiceLib.init( MainActivity.this, params, new ADMXLocationAgreementListener( )
                {
                    @Override
                    public void onSuccess( )
                    {
                        Log.i( "TAG", "비콘 서비스가 시작됩니다." );
                    }

                    @Override
                    public void onDecline( )
                    {
                        Log.i( "TAG", "사용자가 비콘 서비스에 동의하지 않았습니다." );
                    }
                } );
            }
        } );
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, String[] permissions, int[] grantResults )
    {
        PermissionHelper.onRequestPermissionsResult( this, requestCode, grantResults );
    }

}