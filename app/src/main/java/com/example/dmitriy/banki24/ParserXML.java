package com.example.dmitriy.banki24;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.BelKursLab;
import model.MetalLab;
import model.KursModelRub;
import model.ModelforMetal;
import model.RusKursLab;

/**
 * Created by Dmitriy on 18.03.2016.
 */
public class ParserXML {

    KursModelRub specimenBelRub;
    ModelforMetal modelforMetal;


    public Document parserXML(String stringXML, boolean ifcp1251) throws ParserConfigurationException, IOException, SAXException {
        Document document = null;
        try {
            ByteArrayInputStream stream;
            if(ifcp1251){
                stream = new ByteArrayInputStream(stringXML.getBytes("cp1251"));
            } else{
                stream = new ByteArrayInputStream(stringXML.getBytes());
            }
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(stream);

            return document;
        }
        catch (Exception e) {

            return document = null;

        }

    }
    public void writeKursBel(Document documentToDay, Document documentYesterDay){
        NodeList mList1 = documentToDay.getElementsByTagName("Currency");
        NodeList mList2 = documentYesterDay.getElementsByTagName("Currency");


        BelKursLab.get().cleanListBel();


        for (int temp = 0; temp < mList1.getLength(); temp++) {

            Node mNode1 = mList1.item(temp);
            Node mNode2 = mList2.item(temp);


            if (mNode1.getNodeType() == Node.ELEMENT_NODE && mNode2.getNodeType() == Node.ELEMENT_NODE) {
                specimenBelRub = new KursModelRub();
                Element element1 = (Element) mNode1;
                Element element2 = (Element) mNode2;
                specimenBelRub.setmId(temp);
                specimenBelRub.setmCharCode(element1.getElementsByTagName("CharCode").item(0).getTextContent());

                specimenBelRub.setmName(element1.getElementsByTagName("Name").item(0).getTextContent());
                specimenBelRub.setmRate(element1.getElementsByTagName("Rate").item(0).getTextContent());

                specimenBelRub.setmYesterDayRate(element2.getElementsByTagName("Rate").item(0).getTextContent());

                specimenBelRub.setmNominale(element1.getElementsByTagName("Scale").item(0).getTextContent());

                if(element1.getElementsByTagName("CharCode").item(0).getTextContent().equals("RUB")||
                        element1.getElementsByTagName("CharCode").item(0).getTextContent().equals("EUR")||
                        element1.getElementsByTagName("CharCode").item(0).getTextContent().equals("USD")
                        ){
                    specimenBelRub.setShow(true);
                } else specimenBelRub.setShow(false);

                BelKursLab.get().add(specimenBelRub);

            }
        }
        BelKursLab.get().sortListBel();

    }

    public void writeKursRus(Document documentToDay, Document documentYesterDay){
        NodeList mList1 = documentToDay.getElementsByTagName("Valute");
        NodeList mList2 = documentYesterDay.getElementsByTagName("Valute");



        for (int temp = 0; temp < mList1.getLength(); temp++) {

            Node mNode1 = mList1.item(temp);
            Node mNode2 = mList2.item(temp);


            if (mNode1.getNodeType() == Node.ELEMENT_NODE && mNode2.getNodeType() == Node.ELEMENT_NODE) {
                specimenBelRub = new KursModelRub();
                Element element1 = (Element) mNode1;
                Element element2 = (Element) mNode2;
                specimenBelRub.setmId(temp);
                specimenBelRub.setmCharCode(element1.getElementsByTagName("CharCode").item(0).getTextContent());
           //     Log.d("RRR", "запись    " + element1.getElementsByTagName("CharCode").item(0).getTextContent());

                specimenBelRub.setmName(element1.getElementsByTagName("Name").item(0).getTextContent());
                specimenBelRub.setmRate(element1.getElementsByTagName("Value").item(0).getTextContent());
          //      Log.d("RRR", "запись    " + element1.getElementsByTagName("Value").item(0).getTextContent());
                specimenBelRub.setmYesterDayRate(element2.getElementsByTagName("Value").item(0).getTextContent());

                specimenBelRub.setmNominale(element1.getElementsByTagName("Nominal").item(0).getTextContent());

                //      Log.d("RRR", "запись    " + element1.getElementsByTagName("Name").item(0).getTextContent());
                if(element1.getElementsByTagName("CharCode").item(0).getTextContent().equals("EUR")||
                        element1.getElementsByTagName("CharCode").item(0).getTextContent().equals("USD")
                        ){
                    specimenBelRub.setShow(true);
                } else specimenBelRub.setShow(false);
                specimenBelRub.changeTopoint();

                RusKursLab.get().add(specimenBelRub);

            }
        }
        RusKursLab.get().sortListRus();

    }

    public void writeListGold(Document documentToDay, Document documentYesterDay){
        NodeList mList1 = documentToDay.getElementsByTagName("IngotsPrices");
        NodeList mList2 = documentYesterDay.getElementsByTagName("IngotsPrices");


        MetalLab.get().cleanList();

        for (int temp = 0; temp < mList1.getLength(); temp++) {

            Node mNode1 = mList1.item(temp);
            Node mNode2 = mList2.item(temp);


            if (mNode1.getNodeType() == Node.ELEMENT_NODE && mNode2.getNodeType() == Node.ELEMENT_NODE) {
                modelforMetal = new ModelforMetal();
                Element element1 = (Element) mNode1;
                Element element2 = (Element) mNode2;
                modelforMetal.setmName(MetalLab.get().getNameMetal(element1.getAttribute("MetalId")));

                modelforMetal.setmNominal(element1.getAttribute("Nominal"));

                modelforMetal.setmRate(element1.getElementsByTagName("EntitiesRubles").item(0).getTextContent());

                modelforMetal.setmYesterDayRate(element2.getElementsByTagName("EntitiesRubles").item(0).getTextContent());

                modelforMetal.setShow(true);
                modelforMetal.deleteComma(true);
                modelforMetal.deleteComma(false);








                MetalLab.get().additem(modelforMetal);

            }
        }
        Log.d("EEE", "запись   gold ");

    }
}
