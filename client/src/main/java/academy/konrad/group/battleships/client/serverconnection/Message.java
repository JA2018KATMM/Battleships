package academy.konrad.group.battleships.client.serverconnection;

public class Message {
    private String content;

    Message(String content) {

        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
