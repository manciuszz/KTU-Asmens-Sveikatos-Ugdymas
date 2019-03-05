package app.asu;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.TypedValue;
import android.widget.Toast;
import java.util.UUID;

public class StaticMethods {
    public static final long DAY_LONG = 86400000;
    public static final long WEEK_LONG = 604800000;

    public static boolean isNetworkConnected(Context ctx) {
        ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService("connectivity");
        if (connectivity.getActiveNetworkInfo() == null || !connectivity.getActiveNetworkInfo().isConnected()) {
            return false;
        }
        return true;
    }

    public static boolean isGpsConnecter(Context ctx) {
        if (((LocationManager) ctx.getSystemService("location")).isProviderEnabled("gps")) {
            return true;
        }
        Toast.makeText(ctx, "Norėdami naudotis šia funkcija, reikia įjungti GPS", 0).show();
        return false;
    }

    public static void showGPSDisabledAlertToUser(final Context ctx) {
        Builder alertDialogBuilder = new Builder(ctx);
        alertDialogBuilder.setMessage("Norint naudotis šia funkcija, reikia įjungti GPS. Ar norite jį įjungti?").setCancelable(false).setPositiveButton("Eiti į nustatymus", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ctx.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        });
        alertDialogBuilder.setNegativeButton("Atšaukti", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }

    public static int toDp(Context ctx, int px) {
        return (int) TypedValue.applyDimension(1, (float) px, ctx.getResources().getDisplayMetrics());
    }

    public static String getUniquePsuedoID() {
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        try {
            return new UUID((long) m_szDevIDShort.hashCode(), (long) Build.class.getField("SERIAL").get(null).toString().hashCode()).toString();
        } catch (Exception e) {
            return new UUID((long) m_szDevIDShort.hashCode(), (long) "serial".hashCode()).toString();
        }
    }
}
