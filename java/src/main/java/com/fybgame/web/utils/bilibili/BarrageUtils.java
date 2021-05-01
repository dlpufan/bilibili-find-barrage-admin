package com.fybgame.web.utils.bilibili;

import com.fybgame.web.entity.Barrage;
import com.fybgame.web.entity.MyColor;
import com.fybgame.web.utils.MyTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:fyb
 * @Date: 2021/3/5 1:15
 * @Version:1.0
 */
public class BarrageUtils {
    public static boolean match(String s,String regex){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }
    public static List<com.fybgame.web.entity.Barrage> getBarrages(int cid) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://comment.bilibili.com/"+cid+".xml").get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Elements elements = doc.getElementsByTag("d");
        List<com.fybgame.web.entity.Barrage> barrages = new ArrayList<>(elements.size());
        for (int i = 0; i < elements.size(); i++) {
            String s = elements.get(i).toString();
            Barrage barrage = new Barrage();
            String[] bgInfo = s.split(",");
            barrage.setSendTimeInVideo(bgInfo[0].split("\"")[1]);
            barrage.setType(Integer.parseInt(bgInfo[1]));
            barrage.setSize(Integer.parseInt(bgInfo[2]));
            barrage.setColor(new MyColor(Integer.parseInt(bgInfo[3])));
            barrage.setSendTime(MyTime.timeStamp(bgInfo[4]));
            barrage.setBarragePool(Integer.parseInt(bgInfo[5]));
            barrage.setUid(bgInfo[6]);
            barrage.setRowId(bgInfo[7].split("\">")[0]);
            barrage.setValue(bgInfo[7].split("\">")[1].replaceAll("</d>","").replaceAll("\n","").replaceFirst(" ",""));
            barrages.add(barrage);
        }
        return barrages;

    }
    private static long CRCPOLYNOMIAL = 3988292384L;
    private static long[] arrays = new long[256];

    private static void create_table(){
        for (int i = 0; i < arrays.length; i++) {
            long crcreg = i;
            for (int j = 0 ; j < 8 ; j ++){
                if((crcreg & 1)!=0){
                    crcreg = CRCPOLYNOMIAL ^ crcreg>>1;
                }
                else{
                    crcreg = crcreg >> 1;
                }
            }
            arrays[i] = crcreg;
        }

    }
    private static long crc32(Long s){
        long crcstart = 4294967295L;
        for (int i = 0; i < s.toString().length(); i++) {
            long index = (crcstart ^ s.toString().charAt(i)) & 255;
            crcstart = (crcstart >> 8) ^ arrays[(int)index];
        }
        return crcstart;
    }
    private static long crc32_last_index(String s){
        long crcstart = 4294967295L;
        long index = 0;
        for (int i = 0; i < s.length(); i++) {
            index = (crcstart ^ s.charAt(i)) & 255;
            crcstart = (crcstart >> 8) ^ arrays[(int)index];
        }
        return index;
    }
    private static int get_crc_index(long t){
        for (int i = 0; i < 256; i++) {
            if(arrays[i]>>24 == t){
                return i;
            }
        }
        return -1;
    }
    private static long[] deep_check(long i,int[] index){
        String s = "";
        long[] re = {0};
        long tc = 0L;
        long hashcode = crc32(i);
        tc = hashcode & 0xff ^ index[2];
        for(int j = 3 ; j > 0 ; j --){
            if(!(tc<=57 && tc>=48)){
                return re;
            }
            s += Long.toString(tc-48);
            hashcode = arrays[index[j-1]] ^ (hashcode >> 8);
            if(j == 1){
                long[] result = {1,Long.valueOf(s)};
                return result;
            }
            tc = hashcode & 0xff ^ index[j-2];

        }
        return  re;
    }
    public static String crc32bCrack(String s){
        create_table();
        int[] index = {0,0,0,0};
        int i = 0;
        long ht = Long.parseLong(s,16) ^ 4294967295L;
        for(i = 3 ; i > -1 ; i --){
            index[3-i] = get_crc_index(ht >> (i*8));
            long snum = arrays[index[3-i]];
            ht ^= snum >> ((3-i)*8);
        }

        long [] deepCheckData = {};
        for (i = 0; i < 100000000; i++) {
            long lastindex = crc32_last_index(Integer.valueOf(i).toString());

            if(lastindex == index[3]){
                deepCheckData = deep_check(i,index);
                if(deepCheckData[0] != 0){
                    break;
                }
            }
        }
        if(i == 100000000){
            return "-1";
        }
        return i+""+deepCheckData[1];
    }
}
