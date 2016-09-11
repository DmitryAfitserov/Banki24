package model;

public class Date {

    private static Date mDate;

    private java.util.Date date;
    private java.util.Date yesterdate;


   private Date(){
       date = new java.util.Date();
       createYesterdaydate(date);
   }

    public static Date get(){
        if(mDate == null){
            mDate = new Date();
        }
        return mDate;
    }



    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
        createYesterdaydate(date);
    }

    public java.util.Date getYesterdate() {
        return yesterdate;
    }

    private void setYesterdate(java.util.Date yesterdate) {
        this.yesterdate = yesterdate;
    }

    private void createYesterdaydate(java.util.Date date){
        java.util.Date yesterdaydate = new java.util.Date();
        yesterdaydate.setTime(date.getTime() - 24 * 60 * 60 * 1000);
        setYesterdate(yesterdaydate);

    }



}
