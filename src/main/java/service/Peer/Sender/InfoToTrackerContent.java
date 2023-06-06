package service.Peer.Sender;
/*
 * 用于向Tracker发送的信息类
 * type: 用于标识信息类型
 *
 */

import java.io.Serializable;

public class InfoToTrackerContent implements Serializable {
    String type;
    String hash;

}
