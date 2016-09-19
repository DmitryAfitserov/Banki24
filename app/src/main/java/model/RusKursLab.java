package model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;


public class RusKursLab {
    private static RusKursLab rusKursLab;
    private ArrayList<KursModelRub> mListRusRub;
    private String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    private List listisshow;


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

    public KursModelRub getItem(int position){
        return mListRusRub.get(position);
    }

    public List getListisshow() {
        return listisshow;
    }

    public void setListisshow(List listisshow) {
        this.listisshow = listisshow;
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
