package seven.more.queuesleuth.core;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Department{

    private String mDepartmentName;
    private String mQueryDepartment;

    @SerializedName("result")
    private Result result;
    private ArrayList<Result.Group> groups;


public static class Result {

    private int id;
    private String name;

    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    @SerializedName("grupy")
    private ArrayList<Group> groups;

    public static class Group{
        @SerializedName("idGrupy")
        private String id;
        @SerializedName("literaGrupy")
        private String letter;
        @SerializedName("nazwaGrupy")
        private String name;
        @SerializedName("aktualnyNumer")
        private String currentNumber;
        @SerializedName("liczbaKlwKolejce")
        private String queueLength;
        @SerializedName("liczbaCzynnychStan")
        private String openStandsCount;
        @SerializedName("czasObslugi")
        private String avargeServiceTime;
        @SerializedName("status")
        private String status;
        @SerializedName("lp")
        private String lp;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCurrentNumber() {
            return currentNumber;
        }

        public void setCurrentNumber(String currentNumber) {
            this.currentNumber = currentNumber;
        }

        public String getQueueLength() {
            return queueLength;
        }

        public void setQueueLength(String queueLength) {
            this.queueLength = queueLength;
        }

        public String getOpenStandsCount() {
            return openStandsCount;
        }

        public void setOpenStandsCount(String openStandsCount) {
            this.openStandsCount = openStandsCount;
        }

        public String getAvargeServiceTime() {
            return avargeServiceTime;
        }

        public void setAvargeServiceTime(String avargeServiceTime) {
            this.avargeServiceTime = avargeServiceTime;
        }
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
}


    public void setName(String name){
        result.setName(name);
    }

    public ArrayList<Result.Group> getGroups(){
        return result.getGroups();
    }
    public void setGroups(ArrayList<Result.Group> groups) {
        result.setGroups(groups);
    }

    public String getDate(){
        return result.getDate();
    }

    public String getName(){
        return result.getName();
    }
    public void setDate(String date) {
        result.setDate(date);
    }

    public String getDepartmentName(){
        return mDepartmentName;
    }

    public void setDepartmentName(String name){
        mDepartmentName = name;
    }
    public String getQueryDepartment(){
        return mQueryDepartment;
    }

    public void setQueryDepartment(String name){
        mQueryDepartment = name;
    }

}
