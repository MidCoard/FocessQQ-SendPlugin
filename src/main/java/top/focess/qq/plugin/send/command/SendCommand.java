package top.focess.qq.plugin.send.command;

import com.google.common.collect.Lists;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.LightApp;
import org.jetbrains.annotations.NotNull;
import top.focess.qq.FocessQQ;
import top.focess.qq.api.command.*;
import top.focess.qq.api.util.InputTimeoutException;

import java.util.List;

@CommandType( name = "send")
public class SendCommand extends Command {

    @Override
    public void init() {
        this.addExecutor((sender,data,ioHandler) ->{
            if (sender.isAdministrator() || sender.isConsole()) {
                long id = data.getLong();
                Group group = FocessQQ.getGroup(id);
                if (group == null) {
                    ioHandler.outputLang("send-command-group-not-found",id);
                    return CommandResult.REFUSE;
                }
                MessageType messageType = data.get(MessageType.class);
                if (messageType == MessageType.APP)
                    group.sendMessage(new LightApp(data.get()));
                else if (messageType == MessageType.TEXT)
                    group.sendMessage(data.get());
                else if (messageType == MessageType.MIRAI)
                    group.sendMessage(MiraiCode.deserializeMiraiCode(data.get()));
                return CommandResult.ALLOW;
            }
            return CommandResult.REFUSE;
        },CommandArgument.ofLong(),CommandArgument.of(MessageTypeDataConverter.MESSAGE_TYPE_DATA_CONVERTER),CommandArgument.ofString());
        this.addExecutor((sender,data,ioHandler) ->{
            MessageType messageType = data.get(MessageType.class);
            if(sender.isMember()) {
                Group group = sender.getMember().getGroup();
                if (messageType == MessageType.APP)
                    group.sendMessage(new LightApp(data.get()));
                else if (messageType == MessageType.TEXT)
                    group.sendMessage(data.get());
                else if (messageType == MessageType.MIRAI)
                    group.sendMessage(MiraiCode.deserializeMiraiCode(data.get()));
                return CommandResult.ALLOW;
            }
            else if (sender.isFriend()) {
                Friend friend = sender.getFriend();
                if (messageType == MessageType.APP)
                    friend.sendMessage(new LightApp(data.get()));
                else if (messageType == MessageType.TEXT)
                    friend.sendMessage(data.get());
                else if (messageType == MessageType.MIRAI)
                    friend.sendMessage(MiraiCode.deserializeMiraiCode(data.get()));
                return CommandResult.ALLOW;
            }
            else return CommandResult.REFUSE;
        },CommandArgument.of(MessageTypeDataConverter.MESSAGE_TYPE_DATA_CONVERTER),CommandArgument.ofString());
        this.addExecutor((commandSender, dataCollection, ioHandler) -> {
            if (commandSender.isMember()) {
                Group group = commandSender.getMember().getGroup();
                if (dataCollection.get(MessageType.class) == MessageType.MIRAI){
                    ioHandler.outputLang("send-command-input-one-message");
                    ioHandler.hasInput(true);
                    try {
                        group.sendMessage(ioHandler.input());
                    } catch (InputTimeoutException ignored) {}
                } else return CommandResult.ARGS;
                return CommandResult.ALLOW;
            } else if (commandSender.isFriend()) {
                Friend friend = commandSender.getFriend();
                if (dataCollection.get(MessageType.class) == MessageType.MIRAI){
                    ioHandler.outputLang("send-command-input-one-message");
                    ioHandler.hasInput(true);
                    try {
                        friend.sendMessage(ioHandler.input());
                    } catch (InputTimeoutException ignored) {}
                } else return CommandResult.ARGS;
                return CommandResult.ALLOW;
            }
            return CommandResult.REFUSE;
        },CommandArgument.of(MessageTypeDataConverter.MESSAGE_TYPE_DATA_CONVERTER));
    }

    @NotNull
    @Override
    public List<String> usage(CommandSender commandSender) {
        List<String> list = Lists.newArrayList();
        if (commandSender.isConsole() || commandSender.isAdministrator()) {
            list.add("Use: send <group-id> TEXT <text>");
            list.add("Use: send <group-id> APP <app-json>");
        }
        list.add("Use: send TEXT <text>");
        list.add("Use: send APP <app-json>");
        list.add("Use: send MIRAI");
        return list;
    }

    public enum MessageType {

        TEXT,APP,MIRAI;

    }


}
