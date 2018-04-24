package com.wedevol.xmpp.util;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONValue;
import com.wedevol.xmpp.bean.CcsInMessage;
import com.wedevol.xmpp.bean.CcsOutMessage;

/**
 * Mapper for the transformation of JSON messages to attribute maps and vice versa in the XMPP
 * Server
 */

public class MessageMapper {

  /**
   * Creates a JSON from a FCM outgoing message attributes
   */
  public static String createJsonOutMessage(CcsOutMessage outMessage) {
    return createJsonMessage(createAttributeMap(outMessage));
  }

  /**
   * Creates a JSON encoded ACK message for a received upstream message
   */
  public static String createJsonAck(String to, String messageId) {
    final Map<String, Object> map = new HashMap<String, Object>();
    map.put("message_type", "ack");
    map.put("to", to);
    map.put("message_id", messageId);
    return createJsonMessage(map);
  }

  public static String createJsonMessage(Map<String, Object> jsonMap) {
    return JSONValue.toJSONString(jsonMap);
  }

  /**
   * Creates a MAP from a FCM outgoing message attributes
   */
  public static Map<String, Object> createAttributeMap(CcsOutMessage msg) {
    final Map<String, Object> map = new HashMap<String, Object>();
    if (msg.getTo() != null) {
      map.put("to", msg.getTo());
    }
    if (msg.getMessageId() != null) {
      map.put("message_id", msg.getMessageId());
    }
    map.put("data", msg.getDataPayload());
    if (msg.getCondition() != null) {
      map.put("condition", msg.getCondition());
    }
    if (msg.getCollapseKey() != null) {
      map.put("collapse_key", msg.getCollapseKey());
    }
    if (msg.getPriority() != null) {
      map.put("priority", msg.getPriority());
    }
    if (msg.isContentAvailable() != null && msg.isContentAvailable()) {
      map.put("content_available", true);
    }
    if (msg.getTimeToLive() != null) {
      map.put("time_to_live", msg.getTimeToLive());
    }
    if (msg.isDeliveryReceiptRequested() != null && msg.isDeliveryReceiptRequested()) {
      map.put("delivery_receipt_requested", true);
    }
    if (msg.isDryRun() != null && msg.isDryRun()) {
      map.put("dry_run", true);
    }
    if (msg.getNotificationPayload() != null) {
      map.put("notification", msg.getNotificationPayload());
    }
    return map;
  }

  /**
   * Creates an incoming message according the bean
   */
  @SuppressWarnings("unchecked")
  public static CcsInMessage createCcsInMessage(Map<String, Object> jsonMap) {
    String from = null;
    String category = null;
    String messageId = null;
    Map<String, String> dataPayload = null;

    if (jsonMap.get("from") != null) {
      from = jsonMap.get("from").toString();
    }

    // Package name of the application that sent this message
    if (jsonMap.get("category") != null) {
      category = jsonMap.get("category").toString();
    }

    // Unique message id
    if (jsonMap.get("message_id") != null) {
      messageId = jsonMap.get("message_id").toString();
    }

    if (jsonMap.get("data") != null) {
      dataPayload = (Map<String, String>) jsonMap.get("data");
    }

    final CcsInMessage msg = new CcsInMessage(from, category, messageId, dataPayload);
    return msg;
  }

}