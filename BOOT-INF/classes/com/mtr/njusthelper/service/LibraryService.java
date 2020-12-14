package com.mtr.njusthelper.service;

import org.springframework.stereotype.*;
import com.alibaba.fastjson.*;
import com.mtr.njusthelper.utils.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.*;
import java.net.*;
import java.io.*;

@Service
public class LibraryService
{
    public JSONObject search(final String keyword) {
        final JSONObject argJson = new JSONObject();
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("fieldCode", "");
        item.put("fieldValue", keyword);
        final List fieldList = new ArrayList();
        fieldList.add(item);
        item = new HashMap<String, Object>();
        item.put("fieldList", fieldList);
        final List searchWords = new ArrayList();
        searchWords.add(item);
        argJson.put("searchWords", (Object)searchWords);
        argJson.put("filters", (Object)new JSONArray());
        argJson.put("limiter", (Object)new JSONArray());
        argJson.put("sortField", (Object)"relevance");
        argJson.put("sortType", (Object)"desc");
        argJson.put("pageSize", (Object)500);
        argJson.put("pageCount", (Object)1);
        argJson.put("locale", (Object)"");
        argJson.put("first", (Object)true);
        JSONObject jsonObject = new JSONObject();
        if (keyword == "" || keyword == null) {
            jsonObject.put("total", (Object)0);
            jsonObject.put("content", (Object)null);
            return jsonObject;
        }
        jsonObject = HtmlGetter.getJson("http://202.119.83.14:8080/opac/ajax_search_adv.php", (String)null, (Object)argJson);
        return jsonObject;
    }
    
    public JSONObject detail(final String marcRecNo) {
        final JSONObject jsonObject = new JSONObject();
        final String html = HtmlGetter.getHtml("http://202.119.83.14:8080/opac/item.php?marc_no=" + marcRecNo, (String)null, (String)null);
        final Document document = Jsoup.parse(html);
        final List<Map<String, String>> item_detail = new ArrayList<Map<String, String>>();
        final Elements elements = document.select("dl.booklist");
        for (final Element item : elements) {
            final String dt = item.select("dt").text();
            final String dd = item.select("dd").text();
            if (!dt.equals("") && dt != null && !dt.equals("\u8c46\u74e3\u7b80\u4ecb\uff1a")) {
                final Map<String, String> temp = new HashMap<String, String>();
                temp.put("head", dt);
                temp.put("body", dd);
                item_detail.add(temp);
            }
        }
        jsonObject.put("detail", (Object)item_detail);
        final List<Map<String, String>> gcInfo = new ArrayList<Map<String, String>>();
        final Elements elements2 = document.select("tr.whitetext");
        for (final Element item2 : elements2) {
            final String shh = item2.select("td:nth-child(1)").text();
            final String tmh = item2.select("td:nth-child(2)").text();
            final String njq = item2.select("td:nth-child(3)").text();
            final String gcd = item2.select("td:nth-child(4)").text();
            final String skzt = item2.select("td:nth-child(7)").text();
            final Map<String, String> temp2 = new HashMap<String, String>();
            temp2.put("shh", shh);
            temp2.put("tmh", tmh);
            temp2.put("njq", njq);
            temp2.put("gcd", gcd);
            temp2.put("skzt", skzt);
            gcInfo.add(temp2);
        }
        jsonObject.put("gcInfo", (Object)gcInfo);
        return jsonObject;
    }
    
    public JSONObject iborrowed(final String username, final String password) {
        final JSONObject resultJson = new JSONObject();
        PrintWriter out = null;
        final BufferedReader in = null;
        String cookie = "";
        try {
            String urlStr = "http://m.5read.com/njust";
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            final List<String> cookies = headers.get("Set-Cookie");
            for (final String ele : cookies) {
                cookie = String.valueOf(cookie) + ele.split(";")[0] + ";";
            }
            urlStr = "http://mc.m.5read.com/irdUser/login/opac/opacLogin.jspx";
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestProperty("Host", "mc.m.5read.com");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0");
            urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            urlConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            urlConnection.setRequestProperty("Referer", "http://mc.m.5read.com/user/login/showLogin.jspx?backurl=%2Fuser%2Fuc%2FshowUserCenter.jspx");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", "110");
            urlConnection.setRequestProperty("Upgrade-Insecure-Requests", "1");
            urlConnection.setRequestProperty("Connection", "keep-alive");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.setRequestProperty("Cookie", cookie);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            try {
                out = new PrintWriter(urlConnection.getOutputStream());
            }
            catch (Exception e) {
                resultJson.put("result", (Object)"0");
                System.out.println("\u5bc6\u7801\u9519\u8bef");
                return resultJson;
            }
            final String arg = "schoolid=528&&userType=0&username=" + username + "&password=" + password;
            out.write(arg);
            out.flush();
            headers = urlConnection.getHeaderFields();
            cookie = cookie.substring(39, cookie.length() - 59);
            for (final String ele2 : headers.get("Set-Cookie")) {
                cookie = String.valueOf(cookie) + ele2.split(";")[0] + ";";
            }
            urlStr = "http://mc.m.5read.com/cmpt/opac/opacLink.jspx?stype=1";
            final String html = HtmlGetter.getHtml(urlStr, cookie, (String)null);
            if (html.contains("\u62b1\u6b49")) {
                resultJson.put("result", (Object)"0");
                return resultJson;
            }
            final Document document = Jsoup.parse(html);
            final Elements elements = document.select(".boxBd").select("div:nth-child(1)").select(".sheet").select("table");
            final List<Map> content = new ArrayList<Map>();
            for (final Element element : elements) {
                final Map<String, Object> item = new HashMap<String, Object>();
                final Elements elements2 = element.select("tbody:nth-child(1)").select("tr");
                final List<Map<String, String>> detail = new ArrayList<Map<String, String>>();
                String renewParam = "";
                for (int i = 0; i < elements2.size(); ++i) {
                    final Element element2 = (Element)elements2.get(i);
                    if (element2.select("th").size() > 0) {
                        final Map<String, String> borrowDetail = new HashMap<String, String>();
                        if (i == 0) {
                            String td = element2.select("td").text();
                            td = "\u300a" + td + "\u300b";
                            item.put("title_author", td);
                        }
                        else {
                            final String th = element2.select("th").text();
                            final String td2 = element2.select("td").text();
                            borrowDetail.put("th", th);
                            borrowDetail.put("td", td2);
                            detail.add(borrowDetail);
                        }
                    }
                    else {
                        renewParam = element2.select("td").text();
                    }
                }
                item.put("detail", detail);
                item.put("renewParam", renewParam);
                content.add(item);
            }
            resultJson.put("content", (Object)content);
        }
        catch (MalformedURLException ex) {}
        catch (IOException ex2) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex3) {}
        }
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        catch (IOException ex4) {}
        return resultJson;
    }
}
