package top.focess.qq.plugin.send.command;

import top.focess.command.converter.DataConverterType;
import top.focess.command.converter.ExceptionDataConverter;
import top.focess.qq.plugin.send.data.MessageTypeBuffer;

import java.util.Locale;

public class MessageTypeDataConverter extends ExceptionDataConverter<SendCommand.MessageType> {

    @DataConverterType(buffer = MessageTypeBuffer.class)
    public static final MessageTypeDataConverter MESSAGE_TYPE_DATA_CONVERTER = new MessageTypeDataConverter();

    private MessageTypeDataConverter() {}

    @Override
    public SendCommand.MessageType convert(String arg) {
        return SendCommand.MessageType.valueOf(arg.toUpperCase(Locale.ROOT));
    }

    @Override
    protected Class<SendCommand.MessageType> getTargetClass() {
        return SendCommand.MessageType.class;
    }
}
