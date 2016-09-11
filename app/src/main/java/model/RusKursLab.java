package model;

import java.util.ArrayList;


public class RusKursLab {
    private static RusKursLab rusKursLab;
    private ArrayList<KursModelRub> mListRusRub;
    private String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";


    public static RusKursLab get(){
        if(rusKursLab == null){
            rusKursLab = new RusKursLab();
        }
        return rusKursLab;
    }

    private RusKursLab(){
        mListRusRub = new ArrayList<>();
    }

    public void add(KursModelRub kursModelRub){
        mListRusRub.add(kursModelRub);
    }

    public String getUrl() {
        return url;
    }


    public ArrayList<KursModelRub> getmListRusRub() {
        return mListRusRub;
    }

    public void cleanList(){
        mListRusRub.clear();
    }

    public void sortListRus(){
        ArrayList<KursModelRub> list = new ArrayList<>();
        for(int i =0; i<  mListRusRub.size(); i++){
            if(mListRusRub.get(i).getShow()){
                list.add(mListRusRub.get(i));
            }
        }
        for(int i =0; i<  mListRusRub.size(); i++){
            if(!mListRusRub.get(i).getShow()){
                list.add(mListRusRub.get(i));
            }

        }
        mListRusRub.clear();
        mListRusRub = list;


    }

}
