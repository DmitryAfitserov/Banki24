package com.example.dmitriy.banki24;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;

import model.Date;
import model.RusKursLab;

/**
 * Created by Dmitriy on 21.06.2016.
 */
public class AsyncTaskRus extends AsyncTask<Void, Void, Boolean> {

    private AsyncDelegate delegate;

    public AsyncTaskRus(AsyncDelegate delegate){
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        delegate.clean();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String urlready =  geturlready();
        String urlreadyyesterday = geturlreadyYesterday();


        String todayXMLcp1251 = loadXML(urlready);
        String yesterdayXMLcp1251 = loadXML(urlreadyyesterday);


        if(todayXMLcp1251!=null && yesterdayXMLcp1251!=null) {

            try {
                boolean istrue = createList(todayXMLcp1251, yesterdayXMLcp1251);

                return istrue;
            } catch (IOException e) {
                return false;
            } catch (SAXException e) {
                return false;
            } catch (ParserConfigurationException e) {
                return false;
            }

        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        delegate.asynccompleteBel(aBoolean);
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }



    public boolean createList(String todayXML, String yesterdayXML) throws IOException, SAXException, ParserConfigurationException {

        try {

        ParserXML parser = new ParserXML();
        Document doc1 = parser.parserXML(todayXML, true);
        Document doc2 = parser.parserXML(yesterdayXML, true);
        parser.writeKursRus(doc1, doc2);
        return true;
        } catch (IOException e) {
            return false;
        } catch (SAXException e) {
            return false;
        } catch (ParserConfigurationException e) {
            return false;
        }
    }

    public String loadXML(String url){
        String xml;
        try {

            URLConnection connection = (new URL(url)).openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            InputStream in = connection.getInputStream();
            Charset inputCharset = Charset.forName("cp1251");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, inputCharset));
            StringBuilder builder = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {

                builder.append(line);
            }
            in.close();
            xml = builder.toString();

        } catch (Exception e) {

            return null;
        }


        return xml;
    }

    public String geturlready(){
        String url = RusKursLab.get().getUrl();
        java.util.Date date = Date.get().getDate();
        String datestring = parserDate(date);
        String urlready = createURL(url, datestring);


        return urlready;
    }

    public String geturlreadyYesterday(){
        String url = RusKursLab.get().getUrl();
        java.util.Date dateyesterday = Date.get().getYesterdate();
        String datestring = parserDate(dateyesterday);
        String urlreadyYesterday = createURL(url, datestring);

        return urlreadyYesterday;
    }

    public String createURL(String url, String date){
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append(date);

        return builder.toString();
    }

    public String parserDate(java.util.Date date){
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = DATE_FORMAT.format(date);

        return dateString;
    }
}
