package model;

import java.math.BigDecimal;


public class KursModelRub {
    private String mCharCode;
    private String mName;
    private String mRate;
    private String mYesterDayRate;
    private String mNominale;
    private Integer mId;
    private Boolean show;

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public String getmCharCode() {
        return mCharCode;
    }

    public void setmCharCode(String mCharCode) {
        this.mCharCode = mCharCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
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

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmNominale() {
        return mNominale;
    }

    public void setmNominale(String mNominale) {
        this.mNominale = mNominale;
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

    public void changeTopoint(){
        String rate = getmRate();
        int iend = rate.indexOf(",");
        String rateBefore= rate.substring(0, iend);
        String rateAfter= rate.substring(iend + 1, rate.length());

        StringBuilder newRate = new StringBuilder();
        newRate.append(rateBefore);
        newRate.append(".");
        newRate.append(rateAfter);
     //   Log.d("RRR", newRate.toString());
        setmRate(newRate.toString());

        rate = getmYesterDayRate();
        iend = rate.indexOf(",");
        rateBefore= rate.substring(0, iend);
        rateAfter= rate.substring(iend + 1, rate.length());

        StringBuilder newRate1 = new StringBuilder();
        newRate1.append(rateBefore);
        newRate1.append(".");
        newRate1.append(rateAfter);
      //  Log.d("RRR", newRate1.toString());
        setmYesterDayRate(newRate1.toString());


    }
}
