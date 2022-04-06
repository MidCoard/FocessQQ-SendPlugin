package top.focess.qq.plugin.send.data;

import org.jetbrains.annotations.NotNull;
import top.focess.command.data.DataBuffer;
import top.focess.qq.plugin.send.command.SendCommand;
import top.focess.command.data.StringBuffer;

public class MessageTypeBuffer extends DataBuffer<SendCommand.MessageType> {

    private final StringBuffer stringBuffer;

    public MessageTypeBuffer(int size) {
        stringBuffer = StringBuffer.allocate(size);
    }

    @Override
    public void flip() {
        stringBuffer.flip();
    }

    @Override
    public void put(SendCommand.MessageType o) {
        stringBuffer.put(o.name());
    }

    @NotNull
    @Override
    public SendCommand.MessageType get() {
        return SendCommand.MessageType.valueOf(stringBuffer.get());
    }

    @NotNull
    @Override
    public SendCommand.MessageType get(int i) {
        return SendCommand.MessageType.valueOf(stringBuffer.get(i));
    }
}
