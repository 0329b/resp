package pojo;

import java.util.Date;

public class Clock {
    private int clock_id;
    private String employee_no;
    //上班打卡时间
    private Date clock_in_time;
    //下班打卡时间
    private Date clock_off_time;
    //工作日
    private Date clock_date;
    private String order;

    private Date updateTime;
    private int work_date_id;
    private Date work_date;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    public int getWork_date_id() {
        return work_date_id;
    }

    public void setWork_date_id(int work_date_id) {
        this.work_date_id = work_date_id;
    }

    public Date getWork_date() {
        return work_date;
    }

    public void setWork_date(Date work_date) {
        this.work_date = work_date;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public int getClock_id() {
        return clock_id;
    }

    public void setClock_id(int clock_id) {
        this.clock_id = clock_id;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public Date getClock_in_time() {
        return clock_in_time;
    }

    public void setClock_in_time(Date clock_in_time) {
        this.clock_in_time = clock_in_time;
    }

    public Date getClock_off_time() {
        return clock_off_time;
    }

    public void setClock_off_time(Date clock_off_time) {
        this.clock_off_time = clock_off_time;
    }

    public Date getClock_date() {
        return clock_date;
    }

    public void setClock_date(Date clock_date) {
        this.clock_date = clock_date;
    }

    public Clock(int clock_id, String employee_no, Date clock_in_time, Date clock_off_time, Date clock_date) {
        this.clock_id = clock_id;
        this.employee_no = employee_no;
        this.clock_in_time = clock_in_time;
        this.clock_off_time = clock_off_time;
        this.clock_date = clock_date;
    }

    public Clock() {
    }
    //补卡功能
    public Clock(String order,String employee_no, Date clock_date,Date updateTime) {
        this.order=order;
        this.employee_no = employee_no;
        this.clock_date = clock_date;
        this.updateTime=updateTime;
    }
    //添加工作日

    public Clock(String order, Date work_date) {
        this.order = order;
        this.work_date = work_date;
    }
    @Override
    public String toString() {
        return "打卡时间信息->" +
                "打卡id：" + clock_id +
                ",打卡的员工编号：" + employee_no +
                ",上班打卡时间：" + clock_in_time +
                ",下班打卡时间：" + clock_off_time +
                ",当天日期" + clock_date +
                ",备注:" +remark;
    }
}
