package model;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MetalLab {

    private static MetalLab metalLab;
    private ArrayList<ModelforMetal> mListGold;

    private String urlGold = "http://www.nbrb.by/Services/XmlIngots.aspx?onDate=";
    private List<String> listwithNames;
    private ArrayList<KursModelRub> listForChange;
    private String namecurrency;



    private MetalLab(){
        mListGold = new ArrayList<>();
        listwithNames = new ArrayList<>();
        listForChange = new ArrayList<>();
        listwithNames.add("Золото");
        listwithNames.add("Серебро");
        listwithNames.add("Платина");

    }

   static public MetalLab get(){
        if(metalLab == null){
            metalLab = new MetalLab();
        }
       return metalLab;
    }

    public ArrayList<String> getListwithNames() {
        return (ArrayList<String>) listwithNames;
    }

    public void setListForChange(ArrayList<KursModelRub> listForChange) {
        this.listForChange = listForChange;
    }

    public String getUrlGold() {
        return urlGold;
    }


    public ArrayList<ModelforMetal> getmListGold() {
        return mListGold;
    }

    public void additem(ModelforMetal modelforMetal){
        mListGold.add(modelforMetal);
    }

    public void cleanList(){
        mListGold.clear();
    }

    public String getNameMetal(String id){
        int i = Integer.parseInt(id);
        return listwithNames.get(i);
    }

    public boolean changeCurrency(String charCod){
        setNameCurrency(charCod);
        if(charCod.equals("BYN")){
            listForChange.clear();
            return true;
        }
        String rate =null;
        String nominal = null;

        for(int i = 0; i < listForChange.size(); i++){
            if(charCod.equals(listForChange.get(i).getmCharCode())){
                rate = listForChange.get(i).getmRate();
                nominal = listForChange.get(i).getmNominale();
                listForChange.clear();
                break;
            }
        }
        if(rate == null && nominal == null){
            return false;
        }
        BigDecimal rateBD = new BigDecimal(rate);
        BigDecimal nominalBD = new BigDecimal(nominal);
        BigDecimal index = rateBD.divide(nominalBD, 5, BigDecimal.ROUND_HALF_UP);

        for(int i = 0; i < mListGold.size(); i++){
            String oldRateString = mListGold.get(i).getmRate();
            BigDecimal oldRate = new BigDecimal(oldRateString);
            BigDecimal newRate = oldRate.divide(index, 2, BigDecimal.ROUND_HALF_UP);
            mListGold.get(i).setmRate(newRate.toString());

            String oldRateYString = mListGold.get(i).getmYesterDayRate();
            BigDecimal oldRateY = new BigDecimal(oldRateYString);
            BigDecimal newRateY = oldRateY.divide(index, 2, BigDecimal.ROUND_HALF_UP);
            mListGold.get(i).setmYesterDayRate(newRateY.toString());


            Log.d("EEE",  "work new!! " + newRateY.toString());

        }

        return true;
    }

    public ArrayList<KursModelRub> getListForChange() {
        return listForChange;
    }

    public String getNameCurrency() {

        return namecurrency;
    }

    public void setNameCurrency(String nameCurrency) {
        this.namecurrency = nameCurrency;
    }
}