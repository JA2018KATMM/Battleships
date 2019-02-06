package academy.konrad.group.battleships.client.communication;

public class Message {
    private final String content;

    public Message(String content) {

        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
