package app.asu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutostartReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.i("Autostart", "AutostartReceiver - onReceive");
        context.startService(new Intent(context, ErgonomicsService.class));
    }
}
