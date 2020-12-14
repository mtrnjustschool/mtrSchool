package com.mtr.njusthelper.utils;

import java.net.*;
import java.io.*;
import com.alibaba.fastjson.*;

public class HtmlGetter
{
    public static String getHtml(final String thisUrl, final String cookies, final String arg) {
        PrintWriter out = null;
        BufferedReader in = null;
        final StringBuffer sb = new StringBuffer();
        try {
            final URL url = new URL(thisUrl);
            final HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            if (cookies != null) {
                urlConnection.setRequestProperty("Cookie", cookies);
            }
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            out = new PrintWriter(urlConnection.getOutputStream());
            if (arg != null) {
                out.write(arg);
            }
            out.flush();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
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
        final String result = sb.toString();
        return result;
    }
    
    public static String getFrameHtml(final String thisUrl, final String cookies, final String arg) {
        PrintWriter out = null;
        BufferedReader in = null;
        final StringBuffer sb = new StringBuffer();
        try {
            final URL url = new URL(thisUrl);
            final HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestMethod("POST");
            if (cookies != null) {
                urlConnection.setRequestProperty("Cookie", cookies);
            }
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            out = new PrintWriter(urlConnection.getOutputStream());
            if (arg != null) {
                out.write(arg);
            }
            out.flush();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
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
        final String result = sb.toString();
        return result;
    }
    
    public static JSONObject getJson(final String thisUrl, final String cookies, final Object arg) {
        PrintWriter out = null;
        BufferedReader in = null;
        final StringBuffer sb = new StringBuffer();
        try {
            final URL url = new URL(thisUrl);
            final HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            if (cookies != null) {
                urlConnection.setRequestProperty("Cookie", cookies);
            }
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            out = new PrintWriter(urlConnection.getOutputStream());
            if (arg != null) {
                out.write(arg.toString());
            }
            out.flush();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
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
        final String result = sb.toString();
        final JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }
}
