package com.mabeijianxi.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabejianxi on 2016/2/24.
 */
public class UrlData {
    private static List<String> bigUrlList;
    private static List<String> smallUrlList;

    public static List<String> getBigUrlList() {

        if (bigUrlList == null) {
            bigUrlList = new ArrayList<String>();

            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/B9/ezn_AVap8DmADopDAAGtRCAjGyg590.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/B9/ezn_AVap8DqAGZaMAAIph8Hbkkc704.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/AE/ezn_AVakohmAYRmyAAM7gHEZQ6E7321244");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/AE/ezn_AVakohuAdopSAAMEOzh_wJw5987101");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/AE/ezn_AVakoiCARxC4AAopJ3rIoOI7316136");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/9E/ezn_AVaWSGuAQpl4AADF0XsTEV4226.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/9E/ezn_AVaWSGuAAmg7AADEFr8Dh6w199.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/9E/ezn_AVaWSHeAZ_yeABQqj6h1CHk251.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/95/ezn_AVaPs2uAPVQGAGVemZJlc_Y025.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/95/ezn_AVaPsFGAX088ABT3YQOXRnM856.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/95/ezn_AVaPsGCAdMoSABuhQceEgHM418.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbA6AdwWoAAweVmr9D9Q220.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbBWAdsS9AA7TkMj-kQk489.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbB-AfTDYABJHssImasw811.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbCOAdKy6AAjyM566mjI856.jpg");
            bigUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbCeAVcowAAdAYtPGnmg475.jpg");
        }
        return bigUrlList;
    }

    public static List<String> getSmallList() {
        if (smallUrlList == null) {
            smallUrlList = new ArrayList<String>();

            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/B9/ezn_AVap8DmAL311AAAPHa33rQk099.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/B9/ezn_AVap8DqAAYFGAAAS3i4eSTw000.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/AE/ezn_AVakohiAcfl-AAAitaXeQuw6423012");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/AE/ezn_AVakohmAavUFAAAZYqlQ5CY1043354");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/AE/ezn_AVakohuAaJHIAAAhc_qz4Yc6851197");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/9E/ezn_AVaWSGqAQ_EjAAAf0v0VzIw821.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/9E/ezn_AVaWSGuATIqkAAAfRMbgsfE579.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/9E/ezn_AVaWSGyAF8uVAAAene8Z9Oc613.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/95/ezn_AVaPsziAd-XWAAAeJVm2DHk713.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/95/ezn_AVaPsEaAcoV-AAAQ4HQ9gi8362.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/95/ezn_AVaPsFGAClE8AAARxrUTX8M503.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbAiAeZ43AAAbLTkFJpA581.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbA6AOJ5zAAAlNeUR3nU386.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbBaAOhw4AAAtUKfb01c382.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbB-ASqfLAAAZtGteT2U083.jpg");
            smallUrlList.add("http://123.57.255.1:8068/group1/M00/02/96/ezn_AVaQbCSALDDuAAAbyKTuYq4456.jpg");
        }
        return smallUrlList;
    }
}
