package com.qyoub.speedtest.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PingTest extends Thread {

    HashMap<String, Object> result = new HashMap<>();
    String server = "";
    int count;
    double instantRtt = 0;
    double avgRtt = 0.0;
    boolean finished = false;
    boolean started = false;

    public PingTest(String serverIpAddress, int pingTryCount) {
        this.server = serverIpAddress;
        this.count = pingTryCount;
    }

    public PingTest(String serverIpAddress) {
        this.server = serverIpAddress;

    }

    public double getAvgRtt() {
        return avgRtt;
    }

    public double getInstantRtt() {
        return instantRtt;
    }

    public boolean isFinished() {
        return finished;
    }

    List<String> list = new ArrayList<>();

    @Override
    public void run() {
        try {
            String pn = " /system/bin/ping -c " + " " + count + " " + this.server;
            Process p = Runtime.getRuntime().exec(pn);
            ProcessBuilder ps = new ProcessBuilder("/system/bin/ping", "-c " + count, this.server);
            //Runtime r=Runtime.getRuntime();
            ps.redirectErrorStream(true);
            Process pr = ps.start();
            //  Process p=r.exec(pn);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            String output = "";
            while ((line = in.readLine()) != null) {
               // list.add(line);
                if (line.contains("icmp_seq")) {
                    instantRtt = Double.parseDouble(line.split(" ")[line.split(" ").length - 2].replace("time=", ""));

                }
                if (line.startsWith("rtt ")) {
                    avgRtt = Double.parseDouble(line.split("/")[4]);
                    break;
                }

            }
           // pr.waitFor();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        finished = true;
    }

    public List<String> getList() {
        return list;
    }


   /*
    long startTime = 0;
    long endTime = 0;

    @Override
    public void run() {
        try {
            final URL url = new URL("http://" + this.server);
            final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds
            startTime = System.currentTimeMillis();
            urlConn.connect();
            endTime = System.currentTimeMillis();

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                avgRtt=endTime-startTime;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finished = true;
    }*/
}


