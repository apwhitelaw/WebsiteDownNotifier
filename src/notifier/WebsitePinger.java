package notifier;

import java.net.HttpURLConnection;
import java.net.URL;

public class WebsitePinger {

    private String website = "";
    private int downCount = 0;

    public boolean ping() {
        int responseCode = 0;

        try {
            URL url = new URL(website);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 10 second timeout, lower timeout causes false positives
            connection.setConnectTimeout(10000);
            connection.connect();

            responseCode = connection.getResponseCode();

            // if failure, add 1 to downCount, otherwise reset to 0
            if (responseCode == 200) {
                downCount = 0;
                return true;
            } else {
                downCount++;
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public int getDownCount() {
        return downCount;
    }
}
