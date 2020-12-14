package com.mtr.njusthelper.service;

import org.springframework.stereotype.*;
import com.alibaba.fastjson.*;
import java.net.*;
import java.io.*;
import org.jsoup.*;
import java.util.regex.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

@Service
public class UnifieldSystemService
{
    public JSONObject getAesKey() {
        final JSONObject jsonObject = new JSONObject();
        String aeskey = null;
        final String urlString = "http://ids.njust.edu.cn/authserver/login";
        final String arg = null;
        final String cookie1 = "";
        String cookie2 = "";
        PrintWriter out = null;
        final String domain = null;
        final String name = null;
        final String ssuccess = null;
        BufferedReader in = null;
        final StringBuffer sb = new StringBuffer();
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
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
            cookie2 = urlConnection.getHeaderField("Set-Cookie");
            System.out.println(cookie2);
            urlConnection.getHeaderField("Location");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
        if (out != null) {
            out.close();
        }
        final Pattern pattern = Pattern.compile("<script type=\"text/javascript\">.*?var pwdDefaultEncryptSalt = \"([\\s\\S]*?)\";.*?</script>");
        final Matcher matcher = pattern.matcher(sb.toString());
        if (matcher.find()) {
            aeskey = matcher.group(1);
            System.out.println(aeskey);
        }
        final Document document = Jsoup.parse(sb.toString());
        final Elements elements = document.select("#casLoginForm");
        final String lt = elements.select("input:nth-child(6)").attr("value");
        final String dllt = elements.select("input:nth-child(7)").attr("value");
        final String execution = elements.select("input:nth-child(8)").attr("value");
        final String _eventId = elements.select("input:nth-child(9)").attr("value");
        final String rmShown = elements.select("input:nth-child(10)").attr("value");
        jsonObject.put("aeskey", (Object)aeskey);
        jsonObject.put("lt", (Object)lt);
        jsonObject.put("dllt", (Object)dllt);
        jsonObject.put("execution", (Object)execution);
        jsonObject.put("_eventId", (Object)_eventId);
        jsonObject.put("rmShown", (Object)rmShown);
        jsonObject.put("cookie", (Object)cookie2);
        return jsonObject;
    }
    
    public JSONObject getVerification(final String username, final String password, final String lt, final String dllt, final String execution, final String _eventId, final String rmShown, final String cookies) {
        final JSONObject jsonObject = new JSONObject();
        final String urlString = "http://ids.njust.edu.cn/authserver/login";
        final String arg = "username=" + username + "&password=" + password + "&lt=" + lt + "&dllt=" + dllt + "&execution" + execution + "&_eventId" + _eventId + "&rmShown" + rmShown;
        String cookie1 = "";
        String cookie2 = "";
        PrintWriter out = null;
        final String domain = null;
        final String name = null;
        final String ssuccess = null;
        BufferedReader in = null;
        final StringBuffer sb = new StringBuffer();
        try {
            final URL url = new URL(urlString);
            final HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            if (cookies != null) {
                urlConnection.setRequestProperty("Cookie", cookies);
            }
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(false);
            out = new PrintWriter(urlConnection.getOutputStream());
            if (arg != null) {
                out.write(arg);
            }
            out.flush();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
            cookie1 = urlConnection.getHeaderField("SET-COOKIE");
            cookie2 = urlConnection.getHeaderField("Set-Cookie");
            System.out.println(String.valueOf(cookie1) + "     " + cookie2);
            urlConnection.getHeaderField("Location");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
        if (out != null) {
            out.close();
        }
        return jsonObject;
    }
}
