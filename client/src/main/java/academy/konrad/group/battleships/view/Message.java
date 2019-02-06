package academy.konrad.group.battleships.view;

class Message {
    private String content;

    Message(String content) {

        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
