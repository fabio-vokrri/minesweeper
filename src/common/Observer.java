package common;

import common.message.Message;

public interface Observer {
    void update(Message message);
}
