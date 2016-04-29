package game.GUI;

public class Player {
    String name;
    int id;
    String host;
    int port;
    int status;     //0:NOT PLAYING 1:CONNECTED 2:PLAYING 3:DISCONNECTED
    public boolean isAlive=true;
    boolean isbot;
    public int Lives = 3;

    public int getLives()
    {
        return Lives;
    }

    public Player(int id, String host, int port, int status, boolean isbot,String name) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.status = status;
        this.isbot = isbot;
        this.name=name;
    }

    public Player(int id, String host, int port, int status, boolean isbot) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.status = status;
        this.isbot = isbot;
        this.name="Player "+id;
    }

    public Player() {
        this.id = Main.list.size();
        this.host = "none";
        this.port = 1234;
        status=0;
        isbot=true;
        this.name="Random "+id;
    }


    public int getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getStatus() {
        return status;
    }

    public boolean isbot() {
        return isbot;
    }
    public void updateStatus(int a)
    {
        status=a;
    }
    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id+"&"+host+"&"+port+"&"+status+"&"+isbot+"&"+name+";";
    }
}
