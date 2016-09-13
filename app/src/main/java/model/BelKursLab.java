package model;

import java.util.ArrayList;


public class BelKursLab {
    private static BelKursLab mBelKursLab;
    private ArrayList<KursModelRub> mListBelrub;
    private String url = "http://www.nbrb.by/Services/XmlExRates.aspx?ondate=";


    private BelKursLab(){

        mListBelrub = new ArrayList<>();


    }

    public static BelKursLab get(){
        if(mBelKursLab == null){
            mBelKursLab = new BelKursLab();


        }
        return mBelKursLab;
    }


    public String getUrl() {
        return url;
    }

    public void add(KursModelRub kursModelRub){
        mListBelrub.add(kursModelRub);
    }

    public ArrayList<KursModelRub> getListBel(){
        return mListBelrub;
    }

    public KursModelRub getItem(int position){
        return mListBelrub.get(position);
    }





    public void sortListBel(){
        ArrayList<KursModelRub> list = new ArrayList<>();
        for(int i =0; i<  mListBelrub.size(); i++){
            if(mListBelrub.get(i).getShow()){
                list.add(mListBelrub.get(i));
            }
        }
        for(int i =0; i<  mListBelrub.size(); i++){
            if(!mListBelrub.get(i).getShow()){
                list.add(mListBelrub.get(i));
            }

        }
        mListBelrub.clear();
        mListBelrub = list;


    }
    public void cleanListBel(){
        mListBelrub.clear();
    }


}
