package top.focess.qq.plugin.send.data;

import top.focess.qq.api.command.data.DataBuffer;
import top.focess.qq.plugin.send.command.SendCommand;
import top.focess.qq.api.command.data.StringBuffer;

public class MessageTypeBuffer extends DataBuffer<SendCommand.MessageType> {

    private final StringBuffer stringBuffer;

    public MessageTypeBuffer(int size) {
        stringBuffer = StringBuffer.allocate(size);
    }

    public static MessageTypeBuffer allocate(int size) {
        return new MessageTypeBuffer(size);
    }

    @Override
    public void flip() {
        stringBuffer.flip();
    }

    @Override
    public void put(SendCommand.MessageType o) {
        stringBuffer.put(o.name());
    }

    @Override
    public SendCommand.MessageType get() {
        return SendCommand.MessageType.valueOf(stringBuffer.get());
    }
}
