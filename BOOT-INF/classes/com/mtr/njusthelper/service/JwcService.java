package com.mtr.njusthelper.service;

import org.springframework.stereotype.*;
import com.mtr.njusthelper.dao.*;
import org.springframework.beans.factory.annotation.*;
import com.alibaba.fastjson.*;
import java.util.regex.*;
import java.net.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import com.mtr.njusthelper.utils.*;
import java.util.*;
import com.mtr.njusthelper.entity.*;
import java.security.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import net.sourceforge.tess4j.*;

@Service
public class JwcService
{
    private StartdateMapper startdateMapper;
    
    @Autowired
    public JwcService(final StartdateMapper startdateMapper) {
        this.startdateMapper = startdateMapper;
    }
    
    public String testLogin(final String cookies) {
        final JSONObject jsonObject = new JSONObject();
        final int length = cookies.length();
        final String domain = cookies.substring(length - 14);
        final String html = HtmlGetter.getHtml("http://" + domain + ":9080/njlgdx/framework/main.jsp", cookies, (String)null);
        final Pattern pattern = Pattern.compile("<div id=\"Top1_divLoginName\".*?>[\\s\\S]*?</div>");
        final Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return "1";
        }
        return "0";
    }
    
    public JSONObject getVerification(final String username, final String password) {
        final JSONObject jsonObject = new JSONObject();
        final String passwordMd5 = this.encryption(password);
        final String urlString = "http://202.119.81.113:9080/njlgdx/xk/LoginToXk";
        final String arg = "method=verify&USERNAME=" + username + "&PASSWORD=" + passwordMd5;
        String cookie = "";
        PrintWriter out = null;
        String domain = null;
        String name = null;
        String ssuccess = null;
        Label_0365: {
            try {
                final URL url = new URL(urlString);
                final HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setInstanceFollowRedirects(false);
                out = new PrintWriter(urlConnection.getOutputStream());
                if (arg != null) {
                    out.write(arg);
                }
                out.flush();
                cookie = urlConnection.getHeaderField("Set-Cookie");
                final String location = urlConnection.getHeaderField("Location");
                if (location != null) {
                    domain = location.substring(7, 21);
                    cookie = String.valueOf(cookie) + ";domain=" + domain;
                    final String html = HtmlGetter.getHtml("http://" + domain + ":9080/njlgdx/framework/main.jsp", cookie, (String)null);
                    final Pattern pattern = Pattern.compile("<div id=\"Top1_divLoginName\".*?>([\\s\\S]*?)</div>");
                    final Matcher matcher = pattern.matcher(html);
                    if (matcher.find()) {
                        final String result = matcher.group(1);
                        name = result.substring(0, result.length() - 14);
                    }
                    ssuccess = "1";
                    jsonObject.put("cookie", (Object)cookie);
                    jsonObject.put("name", (Object)name);
                    jsonObject.put("username", (Object)username);
                    jsonObject.put("password", (Object)password);
                    break Label_0365;
                }
                ssuccess = "0";
            }
            catch (MalformedURLException ex) {}
            catch (IOException ex2) {}
            finally {
                if (out != null) {
                    out.close();
                }
            }
        }
        if (out != null) {
            out.close();
        }
        jsonObject.put("success", (Object)ssuccess);
        return jsonObject;
    }
    
    public JSONObject getExam(final String cookies) {
        final JSONObject jsonObject = new JSONObject();
        final int length = cookies.length();
        final String domain = cookies.substring(length - 14);
        final String html = HtmlGetter.getHtml("http://" + domain + ":9080/njlgdx/xsks/xsksap_query?Ves632DSdyV=NEW_XSD_KSBM", cookies, (String)null);
        final Pattern pattern = Pattern.compile("<option selected.*?>([\\s\\S]*?)</option>");
        final Matcher matcher = pattern.matcher(html);
        String xq = "";
        while (matcher.find()) {
            xq = matcher.group(1);
        }
        final String html2 = HtmlGetter.getHtml("http://" + domain + ":9080/njlgdx/xsks/xsksap_list", cookies, "xnxqid=" + xq);
        final Document document = Jsoup.parse(html2);
        final Elements elements = document.select("#dataList").select("tbody:nth-child(1)").select("tr");
        final List<Map<String, String>> examList = new ArrayList<Map<String, String>>();
        for (int i = 1; i < elements.size(); ++i) {
            final Elements elements2 = ((Element)elements.get(i)).getElementsByTag("td");
            final Map<String, String> elemMap = new HashMap<String, String>();
            elemMap.put("name", ((Element)elements2.get(3)).text());
            elemMap.put("time", ((Element)elements2.get(4)).text());
            elemMap.put("address", ((Element)elements2.get(5)).text());
            elemMap.put("num", ((Element)elements2.get(6)).text());
            examList.add(elemMap);
        }
        jsonObject.put("exams", (Object)examList);
        jsonObject.put("success", (Object)"1");
        return jsonObject;
    }
    
    public JSONObject getGrade(final String cookies) {
        final JSONObject jsonObject = new JSONObject();
        final int length = cookies.length();
        final String domain = cookies.substring(length - 14);
        final String html = HtmlGetter.getHtml("http://" + domain + ":9080/njlgdx/kscj/cjcx_list", cookies, "kksj=&kcxz=&kcmc=&xsfs=max");
        final List<Map> list = new ArrayList<Map>();
        final Pattern pattern = Pattern.compile("<table id=\"dataList\".*?>[\\s\\S]*?<\\/table>");
        final Matcher matcher = pattern.matcher(html);
        String table = "";
        if (matcher.find()) {
            table = matcher.group();
        }
        final Pattern pattern2 = Pattern.compile("<tr.*?>[\\s\\S]*?<\\/tr>");
        final Matcher matcher2 = pattern2.matcher(table);
        int count = 0;
        while (matcher2.find()) {
            if (count == 0) {
                count = 1;
            }
            else {
                final Map<String, String> resMap = new HashMap<String, String>();
                final Pattern pattern3 = Pattern.compile("<td.*?>[\\s\\S]*?<\\/td>");
                final Matcher matcher3 = pattern3.matcher(matcher2.group());
                int num = 1;
                while (matcher3.find()) {
                    if (num == 2) {
                        resMap.put("kkxq", matcher3.group().replace("<td>", "").replace("</td>", ""));
                    }
                    if (num == 4) {
                        resMap.put("kcmc", matcher3.group().replace("<td align=\"left\">", "").replace("</td>", ""));
                    }
                    if (num == 5) {
                        final Pattern pattern4 = Pattern.compile("<td.*?>");
                        final Matcher matcher4 = pattern4.matcher(matcher3.group());
                        if (matcher4.find()) {
                            final String grade = matcher4.group();
                            if (grade.equals("<td style=\" \">")) {
                                resMap.put("grade", matcher3.group().replace("<td style=\" \">", "").replace("</td>", ""));
                            }
                            else if (grade.equals("<td>")) {
                                resMap.put("grade", matcher3.group().replace("<td>", "").replace("</td>", ""));
                            }
                            else {
                                resMap.put("grade", matcher3.group().replace("<td style=\" color:red;\">", "").replace("</td>", ""));
                            }
                        }
                        resMap.put("GP", this.caculateGP(resMap.get("grade")));
                    }
                    if (num == 7) {
                        resMap.put("xf", matcher3.group().replace("<td>", "").replace("</td>", ""));
                    }
                    if (num == 10) {
                        resMap.put("kcsz", matcher3.group().replace("<td>", "").replace("</td>", ""));
                    }
                    ++num;
                }
                list.add(resMap);
            }
        }
        final List<Map> resultList = new ArrayList<Map>();
        final List<List<Map>> middleList = new ArrayList<List<Map>>();
        String flag = "";
        int index = 0;
        for (int i = list.size() - 1; i >= 0; --i) {
            if (list.get(i).get("kkxq").equals(flag)) {
                middleList.get(index - 1).add(list.get(i));
            }
            else {
                flag = list.get(i).get("kkxq");
                final List<Map> tempList = new ArrayList<Map>();
                tempList.add(list.get(i));
                middleList.add(tempList);
                ++index;
            }
        }
        for (int i = 0; i < middleList.size(); ++i) {
            Map<String, Object> everyXq = new HashMap<String, Object>();
            everyXq.put("xq", middleList.get(i).get(0).get("kkxq"));
            everyXq.put("data", middleList.get(i));
            everyXq = this.summarize(middleList.get(i), everyXq);
            resultList.add(everyXq);
        }
        jsonObject.put("success", (Object)"1");
        jsonObject.put("list", (Object)resultList);
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap = this.summarize(list, tempMap);
        jsonObject.put("summarizing", (Object)tempMap);
        return jsonObject;
    }
    
    public String caculateGP(String cj) {
        String jd = "";
        if (cj.equals("\u4f18\u79c0")) {
            cj = "90";
        }
        if (cj.equals("\u514d\u4fee")) {
            cj = "89";
        }
        if (cj.equals("\u826f\u597d")) {
            cj = "80";
        }
        if (cj.equals("\u4e2d\u7b49")) {
            cj = "70";
        }
        if (cj.equals("\u53ca\u683c")) {
            cj = "60";
        }
        if (cj.equals("\u901a\u8fc7")) {
            cj = "60";
        }
        if (cj.equals("\u5408\u683c")) {
            cj = "60";
        }
        if (cj.equals("\u4e0d\u53ca\u683c")) {
            cj = "0";
        }
        if (cj.equals("\u4e0d\u5408\u683c")) {
            cj = "0";
        }
        if (cj.equals("\u4e0d\u901a\u8fc7")) {
            cj = "0";
        }
        if (cj.equals("\u8bf7\u8bc4\u6559")) {
            cj = "0";
        }
        final Double cjNum = Double.parseDouble(cj);
        if (cjNum >= 60.0) {
            jd = "1.0";
        }
        else {
            jd = "0.0";
        }
        if (cjNum >= 64.0) {
            jd = "1.5";
        }
        if (cjNum >= 68.0) {
            jd = "2.0";
        }
        if (cjNum >= 72.0) {
            jd = "2.3";
        }
        if (cjNum >= 75.0) {
            jd = "2.7";
        }
        if (cjNum >= 78.0) {
            jd = "3.0";
        }
        if (cjNum >= 82.0) {
            jd = "3.3";
        }
        if (cjNum >= 85.0) {
            jd = "3.7";
        }
        if (cjNum >= 90.0) {
            jd = "4.0";
        }
        return jd;
    }
    
    public JSONObject getCourse(final String cookies) {
        final JSONObject jsonObject = new JSONObject();
        final List<List<List<Map<String, String>>>> everyWeek = new ArrayList<List<List<Map<String, String>>>>();
        final int length = cookies.length();
        final String domain = cookies.substring(length - 14);
        final String startdate = this.getStart_date();
        final String term = this.getTerm();
        for (int i = 0; i < 25; ++i) {
            final String html = HtmlGetter.getHtml("http://" + domain + ":9080/njlgdx/xskb/xskb_list.do?Ves632DSdyV=NEW_XSD_PYGL", cookies, "zc=" + (i + 1) + "&xnxq01id=" + term);
            final Document document1 = Jsoup.parse(html);
            final Elements elements1 = document1.select("#kbtable").select("tbody:nth-child(1)").select("tr");
            final List<List<Map<String, String>>> everyClass = new ArrayList<List<Map<String, String>>>();
            for (int w = 1; w < elements1.size() - 1; ++w) {
                final List<Map<String, String>> courseM = new ArrayList<Map<String, String>>();
                final Elements elements2 = ((Element)elements1.get(w)).getElementsByAttributeValue("style", "display: none;");
                for (int j = 0; j < elements2.size(); ++j) {
                    final Map<String, String> temp = new HashMap<String, String>();
                    final String value = ((Element)elements2.get(j)).text();
                    final String[] courseZip = value.split(" ");
                    if (courseZip.length > 1 && courseZip != null) {
                        temp.put("name", courseZip[0]);
                        String teacher = null;
                        if (((Element)elements2.get(j)).getElementsByAttributeValue("title", "\u8001\u5e08").size() != 0) {
                            teacher = ((Element)((Element)elements2.get(j)).getElementsByAttributeValue("title", "\u8001\u5e08").get(0)).text();
                        }
                        String week = null;
                        if (((Element)elements2.get(j)).getElementsByAttributeValue("title", "\u5468\u6b21(\u8282\u6b21)").size() != 0) {
                            week = ((Element)((Element)elements2.get(j)).getElementsByAttributeValue("title", "\u5468\u6b21(\u8282\u6b21)").get(0)).text();
                        }
                        String address = null;
                        if (((Element)elements2.get(j)).getElementsByAttributeValue("title", "\u6559\u5ba4").size() != 0) {
                            address = ((Element)((Element)elements2.get(j)).getElementsByAttributeValue("title", "\u6559\u5ba4").get(0)).text();
                        }
                        if (teacher != null) {
                            temp.put("teacher", teacher);
                        }
                        else {
                            temp.put("teacher", "\u672a\u77e5");
                        }
                        if (week != null) {
                            temp.put("week", week);
                        }
                        else {
                            temp.put("week", "\u672a\u77e5");
                        }
                        if (address != null) {
                            temp.put("address", address);
                        }
                        else {
                            temp.put("address", "\u672a\u77e5");
                        }
                        courseM.add(temp);
                    }
                    else {
                        courseM.add(null);
                    }
                }
                everyClass.add(courseM);
            }
            everyWeek.add(everyClass);
        }
        jsonObject.put("start_date", (Object)startdate);
        jsonObject.put("course", (Object)everyWeek);
        jsonObject.put("success", (Object)"1");
        return jsonObject;
    }
    
    public JSONObject getCengke(final String cookies, final String name, final String teacher) {
        final JSONObject jsonObject = new JSONObject();
        final int length = cookies.length();
        final String domain = cookies.substring(length - 14);
        final String term = this.getTerm();
        final String html = HtmlGetter.getFrameHtml("http://" + domain + ":9080/njlgdx/kbcx/kbxx_kc_ifr", cookies, "xnxqh=" + term + "&skyx=&kkyx=&zzdKcSX=&kc=" + name + "&zc1=&zc2=&jc1=&jc2=");
        final Document document = Jsoup.parse(html);
        final Elements elements = document.select("#kbtable").select("tbody").select("tr");
        final List<Map<String, String>> courses = new ArrayList<Map<String, String>>();
        for (int i = 2; i < elements.size(); ++i) {
            final String courseName = ((Element)elements.get(i)).select("td:nth-child(1)").select("nobr").text();
            final Elements elements2 = ((Element)elements.get(i)).select("td");
            for (int j = 1; j < elements2.size(); ++j) {
                final String courseContent = ((Element)elements2.get(j)).select("nobr").text();
                if (courseContent.contains("\u5468")) {
                    final int weekNum = j / 6 + 1;
                    String week = null;
                    switch (weekNum) {
                        case 1: {
                            week = "\u661f\u671f\u4e00";
                            break;
                        }
                        case 2: {
                            week = "\u661f\u671f\u4e8c";
                            break;
                        }
                        case 3: {
                            week = "\u661f\u671f\u4e09";
                            break;
                        }
                        case 4: {
                            week = "\u661f\u671f\u56db";
                            break;
                        }
                        case 5: {
                            week = "\u661f\u671f\u4e94";
                            break;
                        }
                        case 6: {
                            week = "\u661f\u671f\u516d";
                            break;
                        }
                        case 7: {
                            week = "\u661f\u671f\u65e5";
                            break;
                        }
                    }
                    final int jc = j % 6;
                    final Map<String, String> courseMap = new HashMap<String, String>();
                    courseMap.put("name", courseName);
                    courseMap.put("coursecontent", courseContent);
                    courseMap.put("week", week);
                    courseMap.put("jc", "\u7b2c" + jc + "\u5927\u8282");
                    if (teacher != null && teacher != "") {
                        if (courseContent.contains(teacher)) {
                            courses.add(courseMap);
                        }
                    }
                    else {
                        courses.add(courseMap);
                    }
                }
            }
        }
        jsonObject.put("courses", (Object)courses);
        return jsonObject;
    }
    
    public JSONObject getClassroom(final String cookies, final String jxlbh, final String zc, final String xq, final List<String> jcList) {
        final JSONObject jsonObject = new JSONObject();
        final List<String> crList = new ArrayList<String>();
        String xnxqh = "";
        String startJc = "";
        String endJc = "";
        for (int m = 0; m < jcList.size(); ++m) {
            final String jc = jcList.get(m);
            if (jc.equals("0")) {
                startJc = "01";
                endJc = "03";
            }
            else if (jc.equals("1")) {
                startJc = "04";
                endJc = "05";
            }
            else if (jc.equals("2")) {
                startJc = "06";
                endJc = "07";
            }
            else if (jc.equals("3")) {
                startJc = "08";
                endJc = "10";
            }
            else if (jc.equals("4")) {
                startJc = "11";
                endJc = "12";
            }
            final List<String> thisList = new ArrayList<String>();
            final int length = cookies.length();
            final String domain = cookies.substring(length - 14);
            String html = HtmlGetter.getHtml("http://" + domain + ":9080/njlgdx/kbxx/jsjy_query", cookies, (String)null);
            final Pattern pattern = Pattern.compile("<select id=\"xnxqh\".*?>.*?<option.*?selected.*?>([\\s\\S]*?)</option>");
            final Matcher matcher = pattern.matcher(html);
            while (matcher.find()) {
                xnxqh = matcher.group(1);
            }
            final Document document2 = Jsoup.parse(html);
            final Elements elements2 = document2.select("#xnxqh").select("option");
            for (final Element element : elements2) {
                if (element.getElementsByAttribute("selected").size() != 0) {
                    xnxqh = element.text();
                }
            }
            final String arg = "typewhere=jszq&xnxqh=" + xnxqh + "&xqbh=01&jxqbh=&jxlbh=" + jxlbh + "&jsbh=&bjfh=%3D&rnrs=&jszt=5&zc=" + zc + "&zc2=" + zc + "&xq=" + xq + "&xq2=" + xq + "&jc=" + startJc + "&jc2=" + endJc;
            html = HtmlGetter.getHtml("http://" + domain + ":9080/njlgdx/kbxx/jsjy_query2", cookies, arg);
            final Document document3 = Jsoup.parse(html);
            final Elements elements3 = document3.select("#dataList").select("tbody:nth-child(1)").select("tr");
            for (int i = 2; i < elements3.size() - 2; ++i) {
                final Elements elements4 = ((Element)elements3.get(i)).select("td:nth-child(1)");
                final String tempResult = elements4.text();
                final Pattern pattern2 = Pattern.compile("(.*?)\\(");
                final Matcher matcher2 = pattern2.matcher(tempResult);
                while (matcher2.find()) {
                    final String classroom = matcher2.group(1);
                    thisList.add(classroom);
                    if (m == 0) {
                        crList.add(classroom);
                    }
                }
            }
            for (int i = 0; i < crList.size(); ++i) {
                final String classroomA = crList.get(i);
                if (!thisList.contains(classroomA)) {
                    crList.remove(classroomA);
                }
            }
        }
        crList.sort(new MapKeyComparator());
        jsonObject.put("classrooms", (Object)crList);
        return jsonObject;
    }
    
    public Map<String, Object> summarize(final List<Map> list, final Map<String, Object> map) {
        double sumAllXf = 0.0;
        double sumBxXf = 0.0;
        double countAll = 0.0;
        double countBx = 0.0;
        double avgAllGrade = 0.0;
        double avgBxGrade = 0.0;
        double countAllGP = 0.0;
        double countBxGP = 0.0;
        double avgAllGP = 0.0;
        double avgBxGP = 0.0;
        for (int j = 0; j < list.size(); ++j) {
            String tempCj = list.get(j).get("grade");
            if (tempCj.equals("\u826f\u597d")) {
                tempCj = "80";
            }
            if (tempCj.equals("\u4f18\u79c0")) {
                tempCj = "90";
            }
            if (tempCj.equals("\u514d\u4fee")) {
                tempCj = "89";
            }
            if (tempCj.equals("\u4e2d\u7b49")) {
                tempCj = "70";
            }
            if (tempCj.equals("\u53ca\u683c")) {
                tempCj = "60";
            }
            if (tempCj.equals("\u901a\u8fc7")) {
                tempCj = "60";
            }
            if (tempCj.equals("\u5408\u683c")) {
                tempCj = "60";
            }
            if (tempCj.equals("\u4e0d\u53ca\u683c")) {
                tempCj = "0";
            }
            if (tempCj.equals("\u4e0d\u5408\u683c")) {
                tempCj = "0";
            }
            if (tempCj.equals("\u4e0d\u901a\u8fc7")) {
                tempCj = "0";
            }
            if (tempCj.equals("\u8bf7\u8bc4\u6559")) {
                tempCj = "0";
            }
            sumAllXf += Double.parseDouble(String.valueOf(list.get(j).get("xf")));
            countAll += Double.parseDouble(String.valueOf(list.get(j).get("xf"))) * Double.parseDouble(tempCj);
            countAllGP += Double.parseDouble(String.valueOf(list.get(j).get("GP"))) * Double.parseDouble(String.valueOf(list.get(j).get("xf")));
            if (String.valueOf(list.get(j).get("kcsz")).equals("\u5fc5\u4fee")) {
                sumBxXf += Double.parseDouble(String.valueOf(list.get(j).get("xf")));
                countBx += Double.parseDouble(String.valueOf(list.get(j).get("xf"))) * Double.parseDouble(tempCj);
                countBxGP += Double.parseDouble(String.valueOf(list.get(j).get("GP"))) * Double.parseDouble(String.valueOf(list.get(j).get("xf")));
            }
        }
        avgAllGrade = countAll / sumAllXf;
        avgBxGrade = countBx / sumBxXf;
        avgAllGP = countAllGP / sumAllXf;
        avgBxGP = countBxGP / sumBxXf;
        map.put("sumAllXf", new StringBuilder(String.valueOf(sumAllXf)).toString());
        map.put("sumBxXf", new StringBuilder(String.valueOf(sumBxXf)).toString());
        map.put("avgAllGrade", String.format("%.2f", avgAllGrade));
        map.put("avgBxGrade", String.format("%.2f", avgBxGrade));
        map.put("avgAllGP", String.format("%.2f", avgAllGP));
        map.put("avgBxGP", String.format("%.2f", avgBxGP));
        return map;
    }
    
    public String getStart_date() {
        final StartdateExample startdateExample = new StartdateExample();
        startdateExample.or().andStartdateIsNotNull();
        final List<Startdate> startdates = this.startdateMapper.selectByExample(startdateExample);
        final String startdate = startdates.get(0).getStartdate();
        return startdate;
    }
    
    public String getTerm() {
        final StartdateExample startdateExample = new StartdateExample();
        startdateExample.or().andStartdateIsNotNull();
        final List<Startdate> startdates = this.startdateMapper.selectByExample(startdateExample);
        final String term = startdates.get(0).getTerm();
        return term;
    }
    
    public String encryption(final String plainText) {
        String re_md5 = new String();
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            final byte[] b = md.digest();
            final StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString().toUpperCase();
        }
        catch (NoSuchAlgorithmException ex) {}
        return re_md5;
    }
    
    public void downloadPic(final String url, final String path, final String cookie) {
        try {
            final URL u = new URL(url);
            final HttpURLConnection urlConnection = (HttpURLConnection)u.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Cookie", cookie);
            final DataInputStream dataInputStream = new DataInputStream(urlConnection.getInputStream());
            final FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void removeBackground(final String imgUrl, final String resUrl) {
        final int threshold = 300;
        try {
            final BufferedImage img = ImageIO.read(new File(imgUrl));
            final int width = img.getWidth();
            final int height = img.getHeight();
            for (int i = 1; i < width; ++i) {
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        final Color color = new Color(img.getRGB(x, y));
                        final int num = color.getRed() + color.getGreen() + color.getBlue();
                        if (num >= threshold) {
                            img.setRGB(x, y, Color.WHITE.getRGB());
                        }
                    }
                }
            }
            for (int i = 1; i < width; ++i) {
                final Color color2 = new Color(img.getRGB(i, 1));
                final int num2 = color2.getRed() + color2.getGreen() + color2.getBlue();
                for (int x2 = 0; x2 < width; ++x2) {
                    for (int y2 = 0; y2 < height; ++y2) {
                        final Color color3 = new Color(img.getRGB(x2, y2));
                        final int num3 = color3.getRed() + color3.getGreen() + color3.getBlue();
                        if (num3 == num2) {
                            img.setRGB(x2, y2, Color.BLACK.getRGB());
                        }
                        else {
                            img.setRGB(x2, y2, Color.WHITE.getRGB());
                        }
                    }
                }
            }
            final File file = new File(resUrl);
            if (!file.exists()) {
                final File dir = file.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                try {
                    file.createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImageIO.write(img, "jpg", file);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public void cuttingImg(final String imgUrl) {
        try {
            final File newfile = new File(imgUrl);
            BufferedImage bufferedimage = ImageIO.read(newfile);
            final int width = bufferedimage.getWidth();
            final int height = bufferedimage.getHeight();
            if (width > 54) {
                bufferedimage = this.cropImage(bufferedimage, (width - 54) / 2, 0, width - (width - 54) / 2, height);
                if (height > 16) {
                    bufferedimage = this.cropImage(bufferedimage, 0, (height - 16) / 2, 52, height - (height - 16) / 2);
                }
            }
            else if (height > 16) {
                bufferedimage = this.cropImage(bufferedimage, 0, (height - 16) / 2, width, height - (height - 16) / 2);
            }
            ImageIO.write(bufferedimage, "jpg", new File(imgUrl));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public BufferedImage cropImage(final BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        if (startX == -1) {
            startX = 0;
        }
        if (startY == -1) {
            startY = 0;
        }
        if (endX == -1) {
            endX = width - 1;
        }
        if (endY == -1) {
            endY = height - 1;
        }
        final BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                final int rgb = bufferedImage.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }
    
    public String executeTess4J(final String imgUrl) {
        String ocrResult = "";
        try {
            final ITesseract instance = (ITesseract)new Tesseract();
            final File imgDir = new File(imgUrl);
            ocrResult = instance.doOCR(imgDir);
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }
        return ocrResult;
    }
}
