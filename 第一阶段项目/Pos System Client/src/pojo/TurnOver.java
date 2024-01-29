package pojo;

public class TurnOver {
    private String start_time;
    private String end_time;
    private String order;
    //打印季度营业额
    private String quarter;
    private Double amount;

    @Override
    public String toString() {
        return "季度：" + quarter +
                ", 营业额：" + amount ;
    }


    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public TurnOver() {
    }

    public TurnOver(String order, String start_time, String end_time) {
        this.order = order;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public TurnOver(String order, String start_time) {
        this.order = order;
        this.start_time = start_time;
    }
}
