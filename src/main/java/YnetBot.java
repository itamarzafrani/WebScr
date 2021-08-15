import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YnetBot extends BaseRobot {
    ArrayList<String> urlList;

    public YnetBot() {
        super("https://www.ynet.co.il/home/0,7340,L-8,00.html");
        urlList = new ArrayList<String>();
        try {
            Document ynet = Jsoup.connect(getRootWebsiteUrl()).get();
            Element mainTitle = ynet.getElementsByClass("textDiv").get(0).child(0);
            urlList.add(mainTitle.attr("href"));
            Element littleTitle = ynet.getElementsByClass("YnetMultiStripRowsComponenta colorBackground").first();
            Elements littleTitleElements = littleTitle.getElementsByClass("textDiv");
            for (Element littleTitleElement : littleTitleElements) {
                urlList.add(littleTitleElement.child(0).attr("href"));
            }
            Element sideTitleElements = ynet.getElementsByClass("withImagePreview coupleItems").first();
            Elements sideElements = sideTitleElements.getElementsByClass("slotTitle medium");
            for (Element sideElement : sideElements) {
                urlList.add(sideElement.child(0).attr("href"));
            }
            Element small = ynet.getElementsByClass("slotList").first();
            Elements smallTitles = small.getElementsByClass("slotTitle small");
            for (Element smallTitle : smallTitles) {
                urlList.add(smallTitle.child(0).attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getWordsStatistics() {
        Map<String, Integer> allWords = new HashMap<String, Integer>();

        for (String url : urlList) {
            try {

                Document site = Jsoup.connect(url).get();
                Element mainTitle = site.getElementsByClass("mainTitleWrapper").first();
                Element subTitle = site.getElementsByClass("subTitleWrapper").first();
                Elements text = site.getElementsByClass("text_editor_paragraph rtl");

                String siteText = mainTitle.text() + " " + subTitle.text() + " " + text.text();
                siteText = siteText.replaceAll("[-–•<>@&_%():,.?0-9]", " ");
                siteText = siteText.replaceAll("\"\\s|\\s\"", " ");
                siteText = siteText.replaceAll("\\s+", " ");
                String[] allText = siteText.split(" ");
                for (String word : allText) {
                    if (allWords.containsKey(word)) {
                        allWords.put(word, allWords.get(word) + 1);
                    } else {
                        allWords.put(word, 1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return allWords;
    }

    public int countInArticlesTitles(String textForSearch) {
        int count = 0;
        try {
            Document ynet = Jsoup.connect(getRootWebsiteUrl()).get();
            Elements titles = ynet.getElementsByClass("slotTitle");
            for (Element main : titles) {
                if (main.text().contains(textForSearch))
                    count++;
            }
            Elements mediumTitles = ynet.getElementsByClass("slotTitle medium");
            for (Element medium : mediumTitles) {
                if (medium.text().contains(textForSearch))
                    count++;
            }
            Elements smallTitles = ynet.getElementsByClass("slotTitle small");
            for (Element small : smallTitles) {
                if (small.text().contains(textForSearch))
                    count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public String getLongestArticleTitle() {
        StringBuilder fullText = new StringBuilder(" ");
        int longestArticle = 0;
        String nameOfTheTitle = " ";
        for (String url : urlList) {
            try {
                Document site = Jsoup.connect(url).get();
                String mainTitle = site.getElementsByClass("mainTitle").text();
                Elements text = site.getElementsByClass("text_editor_paragraph rtl");
                for (Element quote : text) {
                    fullText.append(quote.text());
                }
                if (longestArticle < fullText.length()){
                    longestArticle = fullText.length();
                    nameOfTheTitle = mainTitle;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nameOfTheTitle;
    }


}


//        for (Element container : ynet.getElementsByClass("layoutContainer")) {
//                for (Element title_small : container.getElementsByClass("slotTitle small")) {
//                    if (title_small.text().contains(text)) {
//                        count++;
//                    }
//                }
//                for (Element title_medium : container.getElementsByClass("slotTitle medium")) {
//                    if (title_medium.text().contains(text)) {
//                        count++;
//                    }
//                }
//            }
//            if (ynet.getElementsByClass("slotSubTitle").get(0).text().contains(text)) {
//                count++;
//            }
//            if (ynet.getElementsByClass("slotTitle").get(0).text().contains(text)) {
//                count++;
//            }
//            return count;
