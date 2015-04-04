package seven.more.queuesleuth.core;

public class ResponseEvent {

    private Department department;
    private Status status;

    public static ResponseEvent ok(Department department){
        return new ResponseEvent(Status.OK, department);
    }

    public static ResponseEvent error(){
        return new ResponseEvent(Status.ERROR, null);
    }

    private ResponseEvent(Status status, Department department) {
        this.status = status;
        this.department = department;
    }

    public Status getStatus() {
        return status;
    }

    public Department getDepartment() {
        return department;
    }

    public enum Status{
        OK, ERROR
    }

    @Override
    public String toString() {
        if(status == Status.ERROR){
            return "ResponseEvent.ERROR";
        } else{
            return "ResponseEvent.OK with Department ";
        }
    }
}
