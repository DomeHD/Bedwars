package de.elwiron.bedwars.utils;

public class Timer {

    int time;
    int scheduler;


    public Timer(int time,int scheduler){
        this.time = time;
        this.scheduler = scheduler;
    }
    public Timer(int time){
        this.time = time;
    }
    public Timer(){
        this.time = 5;
    }

    public void contdown(){
        time--;
    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public int getScheduler() {
        return scheduler;
    }
    public void setScheduler(int scheduler) {
        this.scheduler = scheduler;
    }


}
