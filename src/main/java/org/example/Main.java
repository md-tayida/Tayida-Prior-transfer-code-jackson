
package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Main{
    public static void main(String[] args) {
        try {
            String url = "https://storage.googleapis.com/maoz-event/rawdata.txt";
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode nodeBased = objectMapper.readTree(new URL(url));
            // JsonNode nodeBased = objectMapper.readTree(url);
            JsonNode edges = nodeBased.path("edges");
            JsonNode nodes = nodeBased.path("nodes");

            ArrayList<String> nodeAll = new ArrayList<>();
            ArrayList<Object> addressInAll = new ArrayList<>();
            ArrayList<Object> addressOutAll = new ArrayList<>();

            Map<String,String> nodeData = new LinkedHashMap<>();
            Map<String, String> sortedNode = new LinkedHashMap<>();
            //---------- 1.  เก็บ data ของ node ไว้ใน map: nodedata-----------------/////
            //String startNode = "";
            for (JsonNode node: nodes) {
                String id = node.path("id").asText();
                String type = node.path("type").asText();
                //เก็บ nooe data ไว้ด้วย
                nodeData.put(id,type);
            }
/// ////////////////////////////////////////////////////////////////////////////////////////////////////
/// ////------------ 2. หา node เริ่มต้น เรียง node ก่อน node นี้ไปหา node ไหน--------------///////////
            String nodeCur = "";
            // System.out.println(nodeData)
            while (nodeCur != null) {  // เดินตามโหนดไปเรื่อย ๆ  ถ้า มี
                List<String> nextNodes = new ArrayList<>();  // เก็บ node ถัดไป
                for(JsonNode edge : edges){
                    String target = edge.path("target").asText();
                    String source = edge.path("source").asText();
                    if (source.equals(nodeCur)) {  // ถ้า node current เริ่มต้นจาก sourvce ไหน//   System.out.println("TYPE: " + type
                        String nextTargets = target; // node ที่ id == source ี targets คือ
                        if (nextTargets != null) {
                            nextNodes.add(nextTargets);  // เพิ่มทุก target เข้า nextNodes
                        }
                    }else if(nodeCur.equals("")){
                        nodeCur = source;
                        String nextTargets = target; // node ที่ id == source ี targets คือ เส้นออกคือ target
                        if (nextTargets != null) {
                            nextNodes.add(nextTargets);  // เพิ่มทุก target เข้า nextNodes
                        }
                        sortedNode.put(nodeCur,nodeData.get(nodeCur)); //nodeเริ่มต้น
                        // System.out.println("index"+sortedNode);
                        addressInAll.add("''");
                        //  addressOutAll2.add(nextTargets);
                    }
                }
                // ถ้ามี target ถัดไป
                if (!nextNodes.isEmpty()) {
                    for (String nextNode : nextNodes) {  // วนลูปผ่านทุก target
                        nodeCur = nextNode;  // ให้ค่าา nodeCur เป็นตำแหน่ง node ถัดไป
                        sortedNode.put(nodeCur,nodeData.get(nodeCur));
                        addressInAll.add(nodeCur);
                    }
                } else {
                    nodeCur = null;  // ถ้าไม่มี target ให้หยุด
                }
            }
            //เก็บ node ที่เรียงแล้ว
            sortedNode.forEach((key, value) -> {
                nodeAll.add("'" + value + "'");
            });
            /// ///////////////////////////////////////////////////////////////////////////
            //---------- 1. เก็บ source-target ที่ index เดียวกัน -----------------/////

            Map<String, Object> sourceToTarget = new LinkedHashMap<>();  //in to out
            Map<String, Object> test = new LinkedHashMap<>();
            for (JsonNode edge : edges) {
                String source = edge.path("source").asText();
                String target = edge.path("target").asText();
                // sT.put(source,target);
                //  keyยังไม่ contain sorce  ท
                if (!sourceToTarget.containsKey(source)) { //// sourceToTargets.put(source, new ArrayList<>()); // ถ้ายังไม่มี key ให้สร้าง List ใหม่
                    sourceToTarget.put(source,null);
                }
                if(sourceToTarget.get(source)==null){ //ถ้ามี key แล้ว มี value ไหม ไม่มีมี เพิ่ม
                    sourceToTarget.put(source, target);

                }else{  //ถ้าไม่เท่ากับnull คือ source นั้น มี targetของมันอยู่แล้ว
                    //  System.out.println("target in::  "+target);
                    Object previousTarget = sourceToTarget.get(source); //สร้าง obj มาเก็บไว้
                    if (previousTarget instanceof String) {  //ตtarget ตัวแรกเป็น String
                        sourceToTarget.put(source, Collections.singletonList((String) previousTarget)); //แปลงเป็น LIST
                        List<String> allTargets = new ArrayList<>();

                        allTargets.add((String) previousTarget); // เพิ่มค่าเดิมที่เป็น String เข้าไปก่อน line
                        allTargets.add(target); // แล้วค่อยเพิ่มค่าใหม่ face
                        sourceToTarget.put(source, allTargets); // อัปเดตค่าใน Map
                    }
                    else{ //ถ้า target เป็น list แล้ว
                        if (!sourceToTarget.containsKey(source)) {
                            sourceToTarget.put(source, new ArrayList<>());
                        }
                        ((List<String>) sourceToTarget.get(source)).add(target);
                    }// System.out.println("newMap EnD: "+newMap);
                }
            }

            /// ---------------4. หา adressOut -----------------//// ต้องเอา node ที่มันเรียงแล้วเว้ย
            for (String id : sortedNode.keySet()) {
                //boolean isOut = true;
                if (sourceToTarget.containsKey(id)) {
                    addressOutAll.add("'"+sourceToTarget.get(id)+"'");
                }
                else{
                    addressOutAll.add("''");
                }
            }
            System.out.println("Nodes = "+nodeAll);
            System.out.println("addressIn = " + addressInAll);
            System.out.println("addressOut = " + addressOutAll);

        } catch (Exception e) {
            System.out.println(e);
        }
    }}
