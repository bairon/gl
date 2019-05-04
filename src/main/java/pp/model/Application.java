package pp.model;

public class Application implements IModel {
    public long id;
    public long userId;
    public String userName;
    public String time;
    public boolean alltypes;
    public int gladlimit;
    public int gladlevel;
    public int totallevel;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
