package com.angelmusic.student.core.music;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by DELL on 2017/2/8.
 */

public class PULLMusicParser {
//
//    public List<Book> parse(InputStream is) throws Exception {
//
//        List<Book> booksList = null;
//        Book book = null;
//
//        // 由android.util.Xml创建一个XmlPullParser实例
//        XmlPullParser parser = Xml.newPullParser();
//        // 设置输入流 并指明编码方式
//        parser.setInput(is, "UTF-8");
//        // 产生第一个事件
//        int eventType = parser.getEventType();
//
//        while (eventType != XmlPullParser.END_DOCUMENT) {
//
//            switch (eventType) {
//                // 判断当前事件是否为文档开始事件
//                case XmlPullParser.START_DOCUMENT:
//                    booksList = new ArrayList<Book>(); // 初始化books集合
//                    break;
//
//                // 判断当前事件是否为标签元素开始事件
//                case XmlPullParser.START_TAG:
//
//                    if (parser.getName().equals("book")) { // 判断开始标签元素是否是book
//                        book = new Book();
//                    } else if (parser.getName().equals("id")) {
//                        eventType = parser.next();
//                        // 得到book标签的属性值，并设置book的id
//                        book.setId(Integer.parseInt(parser.getText()));
//                    } else if (parser.getName().equals("name")) { // 判断开始标签元素是否是book
//                        eventType = parser.next();
//                        book.setName(parser.getText());
//                    } else if (parser.getName().equals("price")) { // 判断开始标签元素是否是price
//                        eventType = parser.next();
//                        book.setPrice(Float.parseFloat(parser.getText()));
//                    }
//                    break;
//
//                // 判断当前事件是否为标签元素结束事件
//                case XmlPullParser.END_TAG:
//                    if (parser.getName().equals("book")) { // 判断结束标签元素是否是book
//                        booksList.add(book); // 将book添加到books集合
//                        book = null;
//                    }
//                    break;
//            }
//            // 进入下一个元素并触发相应事件
//            eventType = parser.next();
//        }
//        return booksList;
//    }
//
//    /**
//     * @param books
//     * @return writer.toString()
//     */
//    @Override
//    public String serialize(List<Book> books) throws Exception {
//
//        // 由android.util.Xml创建一个XmlSerializer实例
//        XmlSerializer serializer = Xml.newSerializer();
//        StringWriter writer = new StringWriter();
//        // 设置输出方向为writer
//        serializer.setOutput(writer);
//        serializer.startDocument("UTF-8", true);
//        serializer.startTag("", "books");
//
//        for (Book book : books) {
//            serializer.startTag("", "book");
//            serializer.attribute("", "id", book.getId() + "");
//
//            serializer.startTag("", "name");
//            serializer.text(book.getName());
//            serializer.endTag("", "name");
//
//            serializer.startTag("", "price");
//            serializer.text(book.getPrice() + "");
//            serializer.endTag("", "price");
//
//            serializer.endTag("", "book");
//        }
//        serializer.endTag("", "books");
//        serializer.endDocument();
//
//        return writer.toString();
//    }

}
