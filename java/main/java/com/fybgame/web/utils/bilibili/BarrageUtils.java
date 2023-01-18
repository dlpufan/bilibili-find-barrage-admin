package com.fybgame.web.utils.bilibili;

import com.alibaba.fastjson.JSONObject;
import com.fybgame.web.entity.Barrage;
import com.fybgame.web.entity.MyColor;
import com.fybgame.web.protobuf.Danmaku;
import com.fybgame.web.utils.LogUtils;
import com.fybgame.web.utils.MyTime;
import com.google.gson.JsonObject;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.util.JsonFormat;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public static String oldGetAllBarrage(int cid,int index){
        String res = "";
        String urlOld = "https://api.bilibili.com/x/v2/dm/web/seg.so?type=1&oid="+cid+"&segment_index="+index;
        try {
            URL u =new URL(urlOld);
            HttpURLConnection conn=(HttpURLConnection)u.openConnection();
            InputStream is =conn.getInputStream();
            Danmaku.DmSegMobileReply danmakuElem = Danmaku.DmSegMobileReply.parseFrom(is);
            res = JsonFormat.printer().print(danmakuElem);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.info("采用旧网址");
        return res;
    }
    public static List<Barrage> getAllBarrages(int cid){
//        String res = "";
//        String urlOld = "https://api.bilibili.com/x/v2/dm/web/seg.so?type=1&oid="+cid+"&segment_index=1";
//        String url = "https://api.bilibili.com/x/v2/dm/web/history/seg.so?type=1&oid="+cid+"&date="+MyTime.getCurrentTimeDate();
//        InputStream is = null;
//            try {
//                URL u =new URL(url);
//                HttpURLConnection conn=(HttpURLConnection)u.openConnection();
//                conn.setInstanceFollowRedirects(true);
//                conn.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
//                conn.setRequestProperty("cokie","LIVE_BUVID=AUTO6515665221242292; DedeUserID=3876453; DedeUserID__ckMd5=a683ade7c4ab4483; blackside_state=1; buvid_fp_plain=undefined; buvid4=8EF684D8-F2AA-3AEA-733E-F0D16F08C00B65299-022012016-kqdVRu7dkfp1TyhW0qbhDQ%3D%3D; CURRENT_BLACKGAP=0; i-wanna-go-back=-1; hit-dyn-v2=1; nostalgia_conf=-1; fingerprint3=796696a59cebbd36ec3f5c766b5363a7; buvid3=DB1282F8-65B1-BE0D-99D7-7116F52289DE60267infoc; _uuid=7DE415E2-E8610-4AD1-5C3F-339FBC28A93828619infoc; b_nut=100; Hm_lvt_a69e400ba5d439df060bf330cd092c0d=1664829903,1665297779,1665470713,1666388881; Hm_lvt_6ab26a3edfb92b96f655b43a89b9ca70=1664829903,1665297779,1665470713,1666388881; hit-new-style-dyn=0; rpdid=|(u~JkkkulYY0J'uYY)l~m~)m; go_old_video=1; CURRENT_QUALITY=80; CURRENT_FNVAL=4048; b_ut=5; fingerprint=751b059d7771e1b473a70d6711631987; buvid_fp=751b059d7771e1b473a70d6711631987; SESSDATA=cb212ff6%2C1689504695%2C9f936%2A12; bili_jct=ba65ff856a9faf8deb3d1b038eea2ab8; bp_video_offset_3876453=752208434847285200; b_lsid=EEE1FF96_185C3CA3927; innersign=1; sid=p6u2cp68; PVID=1");
//                is =conn.getInputStream();
//                Danmaku.DmSegMobileReply danmakuElem = Danmaku.DmSegMobileReply.parseFrom(is);
//
//                LogUtils.info("采用新网址");
//                res = JsonFormat.printer().print(danmakuElem);
//                System.out.println(res);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally {
//                if(is!=null){
//                    IOUtils.closeQuietly(is);
//                }
//            }
//
//            JSONObject jsonObject = JSONObject.parseObject(res);
        String resArr = "";
        JSONObject jsonObject = null;
        int index = 1;
            if(jsonObject==null){

                while(true){
                    jsonObject = JSONObject.parseObject(oldGetAllBarrage(cid,index));
                    if(jsonObject==null){
                        break;
                    }
                    if(jsonObject.toString().equals("{}")){
                        break;
                    }
                    else{
                        index++;
                        String elems = jsonObject.get("elems").toString().substring(1);
                        resArr += elems.substring(0,elems.length()-1)+",";

                    }
                }


            }

            String[] split = resArr.split("},");
            List<Barrage> barrages = new ArrayList<>(split.length);
            for(int i = 1 ; i < split.length-1 ; i ++){
                JSONObject j = JSONObject.parseObject(split[i]+"}");
                Barrage barrage = new Barrage();
                String time = j.getString("progress");
                if(time!=null){
                    Integer progress = Integer.parseInt(time) / 1000;
                    barrage.setSendTimeInVideo(progress.toString());
                }
                barrage.setSendTime(MyTime.timeStamp(j.getString("ctime")));
                String weight = j.getString("weight");
                barrage.setBarrageLevel(weight==null?"1":weight);
                barrage.setRowId(j.getString("idStr"));
                barrage.setType(j.getInteger("mode"));
                String color = j.getString("color");
                if(color!=null){
                    barrage.setColor(new MyColor(Integer.parseInt(color)));
                }
                barrage.setValue(j.getString("content"));
                barrage.setSize(j.getInteger("fontsize"));
                barrage.setUid(j.getString("midHash"));
                barrages.add(barrage);
            }
            System.out.println(barrages);
            return barrages;
    }

    public static List<Barrage> getBarrages(int cid) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://comment.bilibili.com/"+cid+".xml").get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Elements elements = doc.getElementsByTag("d");
        List<Barrage> barrages = new ArrayList<>(elements.size());
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
            barrage.setRowId(bgInfo[7]);
            barrage.setBarrageLevel(bgInfo[8].split("\">")[0]);
            barrage.setValue(bgInfo[8].split("\">")[1].replaceAll("</d>","").replaceAll("\n","").replaceFirst(" ",""));
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
