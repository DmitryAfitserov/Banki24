package model;

import android.util.Log;

import java.math.BigDecimal;

public class ModelforMetal {
    private String mID;
    private String mNominal;
    private String mRate;
    private String mYesterDayRate;
    private Boolean show;

    public String getmName() {
        return mID;
    }

    public void setmName(String mName) {
        this.mID = mName;
    }

    public String getmNominal() {
        return mNominal;
    }

    public void setmNominal(String mNominal) {
        this.mNominal = mNominal;
    }

    public String getmRate() {
        return mRate;
    }

    public void setmRate(String mRate) {
        this.mRate = mRate;

    }

    public String getmYesterDayRate() {
        return mYesterDayRate;
    }

    public void setmYesterDayRate(String mYesterDayRate) {
        this.mYesterDayRate = mYesterDayRate;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public void deleteComma(boolean isToday){
        String rate;
        if(isToday) {
            rate = getmRate();
        } else rate = getmYesterDayRate();
        if(rate.contains(",")){
            int iend = rate.indexOf(",");
            String rateBefore= rate.substring(0, iend);
            String rateAfter= rate.substring(iend + 1, rate.length());

            StringBuilder newRate = new StringBuilder();
            newRate.append(rateBefore);
            newRate.append(rateAfter);
            rate = newRate.toString();

            if(isToday) {
                setmRate(rate);
            } else setmYesterDayRate(rate);
        }
    }

    public String getGhangeRate(){
        BigDecimal toDay = new BigDecimal(getmRate());

        BigDecimal yesterDay = new BigDecimal(getmYesterDayRate());

        BigDecimal change = toDay.subtract(yesterDay);
        if(change.toString().startsWith("-")){
            return change.toString();
        } else {
            return "+" + change.toString();
        }


    }

}