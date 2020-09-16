package ru.data_base;

import ru.general.User;
import ru.general.basic_classes.Car;
import ru.general.basic_classes.Coordinates;
import ru.general.basic_classes.Mood;
import ru.general.basic_classes.WeaponType;
import ru.general.human_being_controller.HumanBeing;
import ru.general.human_being_controller.HumanBeingMap;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataBaseController {
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    public static final String DATA_BASE_URL = "jdbc:postgresql://pg:5432/studs";
    public static final String USER = "*****";
    public static final String PASSWORD = "*****";
    public static int maxKey = 0;
    public static void createTable() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
            System.out.println("Connection successful!");
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = "CREATE SEQUENCE trotskyy_users_sequence INCREMENT BY 1;" +
                "CREATE SEQUENCE trotskyy_human_beings_sequence INCREMENT BY 1;" +
                "CREATE TABLE trotskyy_users (ID int PRIMARY KEY NOT NULL, USERNAME TEXT NOT NULL, PASSWORD TEXT);" +
                "CREATE TABLE trotskyy_human_beings " +
                    "(KEY int PRIMARY KEY  NOT NULL," +
                    "ID            int     NOT NULL," +
                    "NAME          TEXT    NOT NULL," +
                    "COORDINATE_X  FLOAT   NOT NULL," +
                    "CREATION_DATE TIMESTAMP       ," +
                    "COORDINATE_Y  FLOAT   NOT NULL," +
                    "REAL_HERO     boolean NOT NULL," +
                    "HAS_TOOTHPICK boolean NOT NULL," +
                    "IMPACT_SPEED  FLOAT   NOT NULL," +
                    "WEAPON_TYPE   TEXT    NOT NULL," +
                    "MOOD          TEXT    NOT NULL," +
                    "CAR_NAME      TEXT            ," +
                    "CAR_COOL      boolean         ," +
                    "USER_ID       int     NOT NULL)";
            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
            connection.close();
            System.out.println("Disconnection successful");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PostgreSQL JDBC Driver not found");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQL exception");
        }
    }

    public static void createUser(User user) {
        try {
            lock.writeLock().lock();
            user.setId(getUserId());
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO trotskyy_users (ID, USERNAME, PASSWORD) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            System.out.println("User added");
            preparedStatement.close();

            connection.commit();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PosgreSQL JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static User getUser(String username) {
        User user = null;
        try{
            lock.readLock().lock();
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM trotskyy_users WHERE username LIKE ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
            preparedStatement.close();

            connection.commit();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PosgreSQL JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        } finally {
            lock.readLock().unlock();
        }
        return user;
    }
    private static int getUserId() {
        int id = 0;
        try {
            lock.readLock().lock();
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "SELECT nextval('trotskyy_users_sequence')";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            preparedStatement.close();

            connection.commit();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PosgreSQL JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        } finally {
            lock.readLock().unlock();
        }
        return id;
    }

    public static HumanBeingMap getDataByUser(User user) {
        HumanBeingMap humanBeingMap = new HumanBeingMap();
        System.out.println("Getting data for user : " + user.getUsername());
        user = getUser(user.getUsername());
        int userID = user.getId();
        try {
            lock.readLock().lock();
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM trotskyy_human_beings WHERE USER_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet =   preparedStatement.executeQuery();
            HumanBeing humanBeing;

            while (resultSet.next()) {
                int key = resultSet.getInt("KEY");
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                double coordinateX = resultSet.getDouble("COORDINATE_X");
                double coordinateY = resultSet.getDouble("COORDINATE_Y");
                ZonedDateTime creationDate = resultSet.getTimestamp("CREATION_DATE").toLocalDateTime().atZone(ZoneId.of("Europe/Moscow"));
                boolean isRealHero = resultSet.getBoolean("REAL_HERO");
                boolean hasToothpick = resultSet.getBoolean("HAS_TOOTHPICK");
                double impactSpeed = resultSet.getDouble("IMPACT_SPEED");
                WeaponType weaponType = WeaponType.valueOf(resultSet.getString("WEAPON_TYPE"));
                Mood mood = Mood.valueOf(resultSet.getString("MOOD"));
                String carName = resultSet.getString("CAR_NAME");
                boolean carCool = resultSet.getBoolean("CAR_COOL");

                Coordinates coordinates = new Coordinates(coordinateX, coordinateY);
                Car car = carName.equals("") ? null : new Car(carName, carCool);

                humanBeing = new HumanBeing(name, coordinates, isRealHero, hasToothpick, impactSpeed, weaponType, mood, car);
                humanBeing.setCreationDate(creationDate);
                humanBeing.setId(id);

                humanBeingMap.addHumanBeing(key, humanBeing);
            }
            preparedStatement.close();

            connection.commit();

//            sql = "DELETE FROM trotskyy_human_beings WHERE USER_ID = ?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, userID);
//            preparedStatement.executeUpdate();
//            preparedStatement.close();
//            connection.commit();

            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PosgreSQL JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        } finally {
            lock.readLock().unlock();
        }
        return humanBeingMap;
    }
    public static void saveDataByUser(User user, HumanBeingMap humanBeingMap) {
        user = getUser(user.getUsername());
        int userID = user.getId();
        Set<Integer> oldKeys= getKeysbyUser(userID);
        TreeMap<Integer, HumanBeing> currentMap = humanBeingMap.getHumanBeingTreeMap();
        try {
            lock.writeLock().lock();
            for (int key : currentMap.keySet()) {
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
                connection.setAutoCommit(false);
                String sql;
                PreparedStatement preparedStatement;
                HumanBeing humanBeing = currentMap.get(key);
                if (oldKeys.contains(key)) {
//                    sql = "UPDATE trotskyy_human_beings SET" +
//                            " ID            = ?," +
//                            " NAME          = ?," +
//                            " COORDINATE_X  = ?," +
//                            " COORDINATE_Y  = ?," +
//                            " CREATION_DATE = ?," +
//                            " REAL_HERO     = ?," +
//                            " HAS_TOOTHPICK = ?," +
//                            " IMPACT_SPEED  = ?," +
//                            " WEAPON_TYPE   = ?, " +
//                            " MOOD          = ?," +
//                            " CAR_NAME      = ?," +
//                            " CAR_COOL      = ?" +
//                            " WHERE KEY     = ?;";
                    sql = "INSERT INTO trotskyy_human_beings" +
                            "(KEY, ID, NAME, COORDINATE_X, COORDINATE_Y, CREATION_DATE, " +
                            "REAL_HERO, HAS_TOOTHPICK, IMPACT_SPEED, WEAPON_TYPE, MOOD, CAR_NAME, CAR_COOL, USER_ID)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, key);
                    preparedStatement.setLong(2, humanBeing.getId());
                    preparedStatement.setString(3, humanBeing.getName());
                    preparedStatement.setDouble(4, humanBeing.getCoordinates().getX());
                    preparedStatement.setDouble(5, humanBeing.getCoordinates().getY());
                    preparedStatement.setTimestamp(6, Timestamp.valueOf(humanBeing.getCreationDate().toLocalDateTime()));
                    preparedStatement.setBoolean(7, humanBeing.getRealHero());
                    preparedStatement.setBoolean(8, humanBeing.getHasToothpick());
                    preparedStatement.setDouble(9, humanBeing.getImpactSpeed());
                    preparedStatement.setString(10, humanBeing.getWeaponType().toString());
                    preparedStatement.setString(11, humanBeing.getMood().toString());
                    Car car = humanBeing.getCar();
                    preparedStatement.setString(12, car != null ? car.getName() : "");
                    preparedStatement.setBoolean(13, car != null ? car.getCool() : false);
                    preparedStatement.setInt(14, userID);
                } else {
                    sql = "INSERT INTO trotskyy_human_beings" +
                            "(KEY, ID, NAME, COORDINATE_X, COORDINATE_Y, CREATION_DATE, " +
                            "REAL_HERO, HAS_TOOTHPICK, IMPACT_SPEED, WEAPON_TYPE, MOOD, CAR_NAME, CAR_COOL, USER_ID)" +
                            "VALUES(nextval('trotskyy_human_beings_sequence'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                    maxKey++;
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setLong(1, humanBeing.getId());
                    preparedStatement.setString(2, humanBeing.getName());
                    preparedStatement.setDouble(3, humanBeing.getCoordinates().getX());
                    preparedStatement.setDouble(4, humanBeing.getCoordinates().getY());
                    preparedStatement.setTimestamp(5, Timestamp.valueOf(humanBeing.getCreationDate().toLocalDateTime()));
                    preparedStatement.setBoolean(6, humanBeing.getRealHero());
                    preparedStatement.setBoolean(7, humanBeing.getHasToothpick());
                    preparedStatement.setDouble(8, humanBeing.getImpactSpeed());
                    preparedStatement.setString(9, humanBeing.getWeaponType().toString());
                    preparedStatement.setString(10, humanBeing.getMood().toString());
                    Car car = humanBeing.getCar();
                    preparedStatement.setString(11, car != null ? car.getName() : "");
                    preparedStatement.setBoolean(12, car != null ? car.getCool() : false);
                    preparedStatement.setInt(13, userID);
                }
                preparedStatement.executeUpdate();
                preparedStatement.close();
                connection.commit();
                System.out.println(preparedStatement);
                connection.close();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PosgreSQL JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static Set<Integer> getKeysbyUser(int userID) {
        Set<Integer> Keys = new HashSet<>();
        try {
            lock.writeLock().lock();
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM trotskyy_human_beings WHERE USER_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Keys.add(resultSet.getInt("KEY"));
            }
            preparedStatement.close();

            connection.commit();
//            lock.readLock().unlock();
//            lock.writeLock().lock();

            sql = "DELETE FROM trotskyy_human_beings WHERE USER_ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();

            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PosgreSQL JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        } finally {
//            lock.readLock().unlock();
            lock.writeLock().unlock();
        }
        return Keys;
    }

    public static void clear() {
        try {
            lock.writeLock().lock();
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "DROP TABLE trotskyy_users, trotskyy_human_beings;\n" +
                    "DROP SEQUENCE trotskyy_users_sequence, trotskyy_human_beings_sequence;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PosgreSQL JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        } finally {
            lock.writeLock().unlock();
        }
    }
    public static boolean existingKey(int key) {
        boolean answer = false;
        try {
            lock.readLock().lock();
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DATA_BASE_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            String sql = "SELECT EXISTS(SELECT * FROM trotskyy_human_beings WHERE KEY = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                answer = resultSet.getBoolean("exists");
            }
            preparedStatement.close();
            connection.commit();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PosgreSQL JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL exception");
        } finally {
            lock.readLock().unlock();
        }
        return answer;
    }
}
