package com.swust.emporium.dto;

import com.swust.emporium.enums.HeadLineStateEnum;
import com.swust.emporium.pojo.HeadLine;

import java.util.List;

public class HeadLineExecution {
    private int state;
    private String stateInfo;

    private int count;
    private HeadLine headLine;
    private List<HeadLine> headLineList;


    public HeadLineExecution() {
    }

    public HeadLineExecution(HeadLineStateEnum headLineStateEnum){
        this.state = headLineStateEnum.getState();
        this.stateInfo = headLineStateEnum.getStateInfo();
    }

    public HeadLineExecution(HeadLineStateEnum headLineStateEnum, HeadLine headLine){
        this.state = headLineStateEnum.getState();
        this.stateInfo = headLineStateEnum.getStateInfo();
        this.headLine = headLine;
    }

    public HeadLineExecution(HeadLineStateEnum headLineStateEnum, List<HeadLine> headLineList){
        this.state = headLineStateEnum.getState();
        this.stateInfo = headLineStateEnum.getStateInfo();
        this.headLineList = headLineList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HeadLine getHeadLine() {
        return headLine;
    }

    public void setHeadLine(HeadLine headLine) {
        this.headLine = headLine;
    }

    public List<HeadLine> getHeadLineList() {
        return headLineList;
    }

    public void setHeadLineList(List<HeadLine> headLineList) {
        this.headLineList = headLineList;
    }
}
