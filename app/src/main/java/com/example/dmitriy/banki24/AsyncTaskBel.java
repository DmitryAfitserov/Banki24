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
import java.text.SimpleDateFormat;
import javax.xml.parsers.ParserConfigurationException;
import model.BelKursLab;
import model.Date;
import model.MetalLab;

/**
 * Created by Dmitriy on 15.04.2016.
 */
public class AsyncTaskBel extends AsyncTask<Void, Void, Boolean> {


    private AsyncDelegate delegate;

    public AsyncTaskBel(AsyncDelegate delegate) {
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

        String todayXML = loadXML(urlready);
        String yesterdayXML = loadXML(urlreadyyesterday);


        boolean istrue = Boolean.parseBoolean(null);
        if(todayXML!=null && yesterdayXML!=null) {
            try {
                istrue = createList(todayXML, yesterdayXML);

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (SAXException e) {
                e.printStackTrace();
                return false;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                return false;
            }

        }


        return istrue;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        MetalLab.get().setListForChange(BelKursLab.get().getListBel());
        delegate.asynccompleteBel(s);

        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    public boolean createList(String todayXML, String yessterdayXML) throws IOException, SAXException, ParserConfigurationException {
        ParserXML parser = new ParserXML();
        Document doc1 = parser.parserXML(todayXML, false);
        Document doc2 = parser.parserXML(yessterdayXML, false);
        parser.writeKursBel(doc1, doc2);
        return true;
    }

    public String loadXML(String url){
        String xml;
        try {

            URLConnection connection = (new URL(url)).openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
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
        String url = BelKursLab.get().getUrl();
        java.util.Date date = Date.get().getDate();
        String datestring = parserDate(date);
        String urlready = createURL(url, datestring);


        return urlready;
    }

    public String geturlreadyYesterday(){
        String url = BelKursLab.get().getUrl();
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
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = DATE_FORMAT.format(date);

        return dateString;
    }


}
