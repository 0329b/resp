package pojo;

public class TurnOver {
    private String start_time;
    private String end_time;
    private String order;

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

    public TurnOver(String order, String start_time, String end_time) {
        this.order=order;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getOrder() {
        return order;
    }

    public TurnOver() {
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public TurnOver(String order, String start_time) {
        this.order=order;
        this.start_time = start_time;
    }
}
