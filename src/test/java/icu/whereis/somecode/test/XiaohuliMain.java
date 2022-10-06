package icu.whereis.somecode.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 小狐狸app请求
 */
public class XiaohuliMain {
    public static void main(String[] args) throws IOException {
        var url = "https://dfpjmsvl.shdkw1o.com/OpenAPI/v1/anchor/hot?page=1&size=50&order=time&isPk=0";
        //url = "https://dfpjmsvl.shdkw1o.com/OpenAPI/v1/user/personal";

        getAnchorHot(url);
    }

    public static void getAnchorHot(String url) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept-encoding", "gzip");
        headers.put("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJzdWIiOiI0OTg2Mjg0NSIsInVzZXJuYW1lIjoiMTUxNzcxMjI1NDEiLCJyb2xlIjowLCJsb2IiOjEsImlhdCI6MTY1NjA2NzE1OSwiZXhwIjoxNjU4NjU5MTU5fQ.FFdlYLp7qEvY7bMbFQ5ARs8PiDgV-JEioaNCE7M1LGVu23mptC6Tqo7dTGzzzf2g0-fYhFl92nJL3K2EAn-phg");
        headers.put("user-agent", "okhttp/4.9.2");
        headers.put("x-live-butter2", "4L0HHMyx728WLb9kOmYL2uzpIuwgqj6EeRwlJDDIcxNxQhO00KTdm9Kq1w/x/0JArMLY3dptRBvwYHs1QGItVC5TYterbEneffODKLkMCtehGKBkb3UMwC+Ruvy8hTBnDDLyosaufuLSGxMRgNS3WMRKupT+KiLnAvy2rOn9Z4hyYeqlNoDoFK3x0gpiaZz7");
        headers.put("x-accept-puzzle", "cola,tiger,tiger2,panda");
        headers.put("knockknock", "synergy");
        headers.put("x-live-pretty", "spring");

        Document document = Jsoup.connect(url).headers(headers).ignoreContentType(true).get();
        System.out.println(document);
    }
}
