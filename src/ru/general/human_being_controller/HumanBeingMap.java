package ru.general.human_being_controller;

import ru.general.basic_classes.*;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс для дерева с объектами HumanBeing и его управлением
 */

public class HumanBeingMap {
    /** Поле initializationDate */
    private ZonedDateTime initializationDate;
    /** Поле collectionType*/
    private String collectionType;
    /** Поле lastId*/
    private Long lastId;
    /**Поле humanBeingTreeMap, ключи - integer, значения - HumanBeing*/
    private TreeMap<Integer, HumanBeing> humanBeingTreeMap;

    /**
     * Стандартный конструктор для коллекции
     */

    public HumanBeingMap() {
        this.lastId = 0L;
        this.collectionType = "TreeMap";
        this.initializationDate = ZonedDateTime.now();
        this.humanBeingTreeMap = new TreeMap<>();
    }
    /**
     * Читает данные из файла @param pathName, и заполняет ими коллекцию, id генерируются с единицы в порядке возрастания
     */
    public HumanBeingMap(String pathName) {
        this.lastId = 0L;
        this.collectionType = "TreeMap";
        this.initializationDate = ZonedDateTime.now();
        this.humanBeingTreeMap = new TreeMap<>();
        try{
            File xmlFile = new File(pathName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList list = document.getElementsByTagName("HumanBeing");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    HumanBeing humanBeing = new HumanBeing();
                    humanBeing.setId(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()));
                    humanBeing.setName(element.getElementsByTagName("name").item(0).getTextContent());
                    humanBeing.setCoordinates(new Coordinates(Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent()), Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent())));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
                    humanBeing.setCreationDate(ZonedDateTime.parse(element.getElementsByTagName("creationDate").item(0).getTextContent(), formatter));
                    humanBeing.setRealHero(Boolean.parseBoolean(element.getElementsByTagName("realHero").item(0).getTextContent()));
                    humanBeing.setHasToothpick(Boolean.parseBoolean(element.getElementsByTagName("hasToothpick").item(0).getTextContent()));
                    humanBeing.setImpactSpeed(Double.parseDouble(element.getElementsByTagName("impactSpeed").item(0).getTextContent()));
                    humanBeing.setWeaponType(WeaponType.valueOf(element.getElementsByTagName("weaponType").item(0).getTextContent()));
                    humanBeing.setMood(Mood.valueOf(element.getElementsByTagName("mood").item(0).getTextContent()));
                    humanBeing.setCar(new Car(element.getElementsByTagName("carName").item(0).getTextContent(), Boolean.parseBoolean(element.getElementsByTagName("cool").item(0).getTextContent())));
                    this.addHumanBeing(Integer.parseInt(element.getAttribute("key")), humanBeing);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Геттер initializationDate
     * @return initializationDate
     */
    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }

    /**
     * Сеттер initializationDate
     * @param initializationDate initializationDate
     */

    public void setInitializationDate(ZonedDateTime initializationDate) {
        this.initializationDate = initializationDate;
    }

    /**
     * Геттер collectionType
     * @return collectionType
     */

    public String getCollectionType() {
        return collectionType;
    }

    /**
     * Сеттер collectionType
     * @param collectionType collectionType
     */

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    /**
     * Геттер lastId
     * @return lastId
     */

    private Long getLastId() {
        return lastId;
    }

    /**
     * Сеттер lastId
     * @param lastId lastId
     */

    private void setLastId(Long lastId) {
        this.lastId = lastId;
    }
    /**
     * Геттер humanBeingTreeMap
     * @return humanBeingTreeMap
     */

    public TreeMap<Integer, HumanBeing> getHumanBeingTreeMap() {
        return humanBeingTreeMap;
    }

    /**
     * Сеттер humapBeingTreeMap
     * @param humanBeingTreeMap humanBeingTreeMap
     */

    public void setHumanBeingTreeMap(TreeMap<Integer, HumanBeing> humanBeingTreeMap) {
        this.humanBeingTreeMap = humanBeingTreeMap;
    }

    /**
     * Добавляет в коллекцию элемент по ключу
     * @param key ключ
     * @param humanBeing humanBeing
     */
    public void addHumanBeing(Integer key, HumanBeing humanBeing){
        setLastId(getLastId() + 1);
        humanBeing.setId(this.getLastId());
        this.humanBeingTreeMap.put(key, humanBeing);
    }

    /**
     * Возвращает ключ элемента с данным id
     * @param id - id
     * @return key
     */

    public Integer getKeyById(Long id){
        for (int key : humanBeingTreeMap.keySet()){
            if (Objects.equals(humanBeingTreeMap.get(key).getId(), id)){
                return key;
            }
        }
        return null;
    }

    /**
     * Обновляет значение HumanBeing с данным id и ключом
     * @param id id
     * @param key ключ
     * @param humanBeing humanBeing
     */
    public void updateHumanBeing(Long id, Integer key, HumanBeing humanBeing){
        humanBeing.setId(id);
        humanBeingTreeMap.replace(key, humanBeing);
    }

    /**
     * Удаляет элемент по ключу
     * @param key ключ
     */
    public void removeHumanBeing(Integer key){
        humanBeingTreeMap.remove(key);
    }

    /**
     * Очищает карту
     */
    public void clearHumanBeingMap(){
        humanBeingTreeMap.clear();
        this.setLastId(0L);
    }

    /**
     * Сохраняет данные в файл SavedCollection.xml
     */
    public void saveHumanBeingMapInFile(){
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter("SavedCollection.xml"));
            writer.writeStartDocument("1.0");
            writer.writeStartElement("HumanBeingCollection");
            for (int key : humanBeingTreeMap.keySet()){

                // Starting HumanBeing tag
                writer.writeStartElement("HumanBeing");
                writer.writeAttribute("key", String.valueOf(key));

                //id tag
                writer.writeStartElement("id");
                writer.writeCharacters(humanBeingTreeMap.get(key).getId().toString());
                writer.writeEndElement();

                //name tag
                writer.writeStartElement("name");
                writer.writeCharacters(humanBeingTreeMap.get(key).getName());
                writer.writeEndElement();

                //Coordinates tag
                writer.writeStartElement("coordinates");
                writer.writeStartElement("x");
                writer.writeCharacters(String.valueOf(humanBeingTreeMap.get(key).getCoordinates().getX()));
                writer.writeEndElement();
                writer.writeStartElement("y");
                writer.writeCharacters(String.valueOf(humanBeingTreeMap.get(key).getCoordinates().getY()));
                writer.writeEndElement();
                writer.writeEndElement();

                //CreationDate tag
                writer.writeStartElement("creationDate");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
                writer.writeCharacters(humanBeingTreeMap.get(key).getCreationDate().format(formatter));
                writer.writeEndElement();

                //realHero tag
                writer.writeStartElement("realHero");
                writer.writeCharacters(String.valueOf(humanBeingTreeMap.get(key).getRealHero()));
                writer.writeEndElement();

                //hasToothpick tag
                writer.writeStartElement("hasToothpick");
                writer.writeCharacters(String.valueOf(humanBeingTreeMap.get(key).getHasToothpick()));
                writer.writeEndElement();

                //impactSpeed tag
                writer.writeStartElement("impactSpeed");
                writer.writeCharacters(humanBeingTreeMap.get(key).getImpactSpeed().toString());
                writer.writeEndElement();

                //weaponType tag
                writer.writeStartElement("weaponType");
                writer.writeCharacters(humanBeingTreeMap.get(key).getWeaponType().toString());
                writer.writeEndElement();

                //mood tag
                writer.writeStartElement("mood");
                writer.writeCharacters(humanBeingTreeMap.get(key).getMood().toString());
                writer.writeEndElement();

                //car tag
                writer.writeStartElement("car");
                if (humanBeingTreeMap.get(key).getCar() == null){
                    writer.writeCharacters("");
                }else {
                    writer.writeStartElement("carName");
                    writer.writeCharacters(humanBeingTreeMap.get(key).getCar().getName());
                    writer.writeEndElement();
                    writer.writeStartElement("cool");
                    writer.writeCharacters(String.valueOf(humanBeingTreeMap.get(key).getCar().getCool()));
                    writer.writeEndElement();
                }
                writer.writeEndElement();

                // Closing HumanBeing tag
                writer.writeEndElement();
            }
            writer.writeEndDocument();
            writer.flush();
        } catch (XMLStreamException | IOException ex){
            ex.printStackTrace();
        }
    }

    public Set<Map.Entry<Integer, HumanBeing>> entrySet() {
        return humanBeingTreeMap.entrySet();
    }

    public boolean isEmpty() {
        return humanBeingTreeMap.isEmpty();
    }

    public HumanBeing get(int key) {
        return this.humanBeingTreeMap.get(key);
    }

    public Set<Integer> keySet() {
        return this.humanBeingTreeMap.keySet();
    }

    /**
     * Переопределение метода toString
     * @return Строковое представление класса
     */
    @Override
    public String toString() {
        return "{Тип коллекции: " + collectionType +
                ", дата инициализации: " + initializationDate +
                ", количество элементов: " + humanBeingTreeMap.size() +
                "}\n";
    }
}