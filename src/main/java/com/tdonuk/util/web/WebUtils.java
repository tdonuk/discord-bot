package com.tdonuk.util.web;

import com.tdonuk.util.BaseUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebUtils extends BaseUtils {

    public static Document getDocument(final String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public static Elements get(final String cssQuery, Document doc) {
        return doc.select(cssQuery);
    }

    public static String selectOwnById(final String id, Document doc) {
        return doc.getElementById(id).ownText();
    }

    public static String selectTextById(final String id, Document doc) {
        return doc.getElementById(id).text();
    }

}
