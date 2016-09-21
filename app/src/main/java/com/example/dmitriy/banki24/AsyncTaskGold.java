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

import model.Date;
import model.MetalLab;

/**
 * Created by Dmitry on 16.07.2016.
 */
public class AsyncTaskGold extends AsyncTask<Void, Void, Boolean> {

    private AsyncDelegate delegate;
    private boolean istrue;

    public AsyncTaskGold(AsyncDelegate fragment) {
        this.delegate = fragment;

    }

    @Override
    protected void onPreExecute() {
        delegate.clean();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String urlGoldToday = createUrl(true);
        String urlGoldYesterday = createUrl(false);


        String xmlToday = loadXML(urlGoldToday);
        String xmlYesterday = loadXML(urlGoldYesterday);

        istrue = false;
        if(xmlToday!=null && xmlYesterday!=null) {
            try {

                istrue = createList(xmlToday, xmlYesterday);

                for(int i = 0; i<100; i++){
                    if(!MetalLab.get().getListForChange().isEmpty()){
                        break;
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

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

        Boolean f =  MetalLab.get().changeCurrency();
        if(!f){
            return f;
        }


        return istrue;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        delegate.asynccompleteBel(true);
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
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

    public boolean createList(String todayXML, String yessterdayXML) throws IOException, SAXException, ParserConfigurationException {
        ParserXML parser = new ParserXML();
        Document doc1 = parser.parserXML(todayXML, false);
        Document doc2 = parser.parserXML(yessterdayXML, false);
        parser.writeListGold(doc1, doc2);
        return true;
    }


    private String createUrl(boolean isToday){
        String urlGold = MetalLab.get().getUrlGold();
        java.util.Date date;
        if(isToday ){
            date = Date.get().getDate();
        } else date = Date.get().getYesterdate();

        String dateString = parserDate(date);


        return unionString(urlGold,dateString);
    }

    private String parserDate(java.util.Date date){
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = DATE_FORMAT.format(date);
        return dateString;
    }

    private String unionString(String url, String dateString){
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append(dateString);

        return builder.toString();
    }


}


