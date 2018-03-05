//package com.tp.json;
//
//
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
///**
// *
// System.out.println(jsonStr);
// {"name":"Marty","breed":"whippet","count":1,"price":1.2,"twoFeet":false}
//
// {"price":[1,2,4],"ll":22332222333322333, "images":["abc","def"]	,"dog":[		{"name":"Rufus","breed":"labrador","count":1,"twoFeet":false},		{"name":"Marty","breed":"whippet","count":1,"twoFeet":false}	],	"cat":{"name":"Matilda"} }
//
// */
//public class AutoCreated {
//
//    public static void main(String[] args) throws IOException {
//        System.out.println("abc");
//
//        String jsonStr = " {\"name\":\"Marty\",\"breed\":\"whippet\",\"count\":1,\"price\":1.2,\"twoFeet\":false}\n"; //read();
//
//        jsonStr= " {\"price\":[1,2,4],\"ll\":22332222333322333, \"images\":[\"abc\",\"def\"]\t,\"dog\":[\t\t{\"name\":\"Rufus\",\"breed\":\"labrador\",\"count\":1,\"twoFeet\":false},\t\t{\"name\":\"Marty\",\"breed\":\"whippet\",\"count\":1,\"twoFeet\":false}\t],\t\"cat\":{\"name\":\"Matilda\"} }\n ";
//
//        System.out.println(jsonStr);
//
//        JSONObject jsonObject = new JSONObject(jsonStr);
//
//        JsonBeanCreated result = JsonBeanCreated.createdJavaBean(jsonObject,false,"JavaBean");
//
//        String bean = result.javaBean.toString();
//
//        System.out.println("--------------------result ---------------------");
//
//        System.out.println(bean);
//
//    }
//
//
//    public static String read() throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//        StringBuffer sb = new StringBuffer();
//        String line = null;
//        while (true) {
//            line = br.readLine();
//            if (line == null || "end".equals(line)){
//                return sb.toString();
//            }
//            sb.append(line);
//        }
//    }
//
//
//
//
//}
