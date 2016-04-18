package zhaoq.hl.hlphonemallmanager.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.utils
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  15:51
 */
public class StateUtils {

        /**
         * 判断网络连接是否可用
         *
         * @param context
         * @return
         */
        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
            } else {
                // 如果仅仅是用来判断网络连接
                // 则可以使用 cm.getActiveNetworkInfo().isAvailable();
                NetworkInfo[] info = cm.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * 判断GPS是否打开
         * @param context
         * @return
         */
        public static boolean isGpsEnabled(Context context) {
            LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
            List<String> accessibleProviders = lm.getProviders(true);
            return accessibleProviders != null && accessibleProviders.size() > 0;
        }

        /**
         * 判断WIFI是否打开
         * @param context
         * @return
         */
        public static boolean isWifiEnabled(Context context) {
            ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
        }

        /**
         * 判断是否是3G网络
         *
         * @param context
         * @return
         */
        public static boolean is3rd(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkINfo = cm.getActiveNetworkInfo();
            if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
            return false;
        }

        /**
         * 判断是wifi还是3g网络
         *
         * @param context
         * @return
         */
        public static boolean isWifi(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkINfo = cm.getActiveNetworkInfo();
            if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
            return false;
        }

        /** 获取设备的IMEI */
        public static String getIMEI(Context context) {
            if (null == context) {
                return null;
            }
            String imei = null;
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
            if (imei == null) {
                // android pad
                imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            return imei;
        }

        /** 获得设备ip地址 */
        public static String getLocalAddress() {
            try {
                Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                while (en.hasMoreElements()) {
                    NetworkInterface intf = en.nextElement();
                    Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                    while (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            } catch (SocketException e) {
                return "无";
            }
            return null;
        }

}
