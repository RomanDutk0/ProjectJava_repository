package gui;

import base.plane.Plane;

import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbHandler {

    static Logger logger = LoggerFactory.getLogger(DbHandler.class);
    public static Connection getConnect() throws ClassNotFoundException, SQLException{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aviacompany", "postgres", "2005");
        //logger.info("Connected successfully!");
        System.out.println("Connected successfully!");
        return connection;
    }
    public static void createAviacompany(String name) {
        try {
            String query = "INSERT INTO company\n" +
                    "(company_name)\n" +
                    "VALUES(?)";
            PreparedStatement prStatement = getConnect().prepareStatement(query);
            prStatement.setString(1, name);
            prStatement.executeUpdate();
            prStatement.close();
            logger.info("Aviacompany '{}' created successfully", name);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Помилка при створенні авіакомпанії");
            logger.error("Error creating aviacompany '{}'", name, e);
            e.printStackTrace();
        }
    }

    public static int getID(String comp_name) {
        try {
            String query = "SELECT company_id\n" +
                    "FROM company\n" +
                    "WHERE company_name = " + "'" + comp_name + "'";
            PreparedStatement prStatement = getConnect().prepareStatement(query);
            ResultSet result = prStatement.executeQuery();
            int id = 0;
            if(result.next()) {
                id = result.getInt(1);
            }
            logger.info("ID retrieved successfully for company '{}'", comp_name);
            return id;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error getting ID for company '{}'", comp_name, e);
            System.out.println("Помилка при доставанні ID");
            e.printStackTrace();
        }
        return 0;
    }
    public static void addPlane(Plane model, String name, int company_id) {
        try {
            String query = "INSERT INTO planes\n" +
                    "(plane_name, plane_type, plane_model, flight_distance, fuel_consumption, cargo_capacity, passenger_capacity, company_id)\n" +
                    "VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement prStatement = getConnect().prepareStatement(query);

            prStatement.setString(1, name);
            prStatement.setString(2, model.getClass().getSuperclass().getSimpleName());
            prStatement.setString(3, model.getClass().getSimpleName());
            prStatement.setInt(4, model.getFlightDist());
            prStatement.setInt(5, model.getFuelConsumption());
            prStatement.setInt(6, model.getCargoCapacity());
            prStatement.setInt(7, model.getPassCapacity());
            prStatement.setInt(8, company_id);
            prStatement.executeUpdate();
            prStatement.close();
            logger.info("Plane '{}' added successfully for company with ID '{}'", name, company_id);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error adding plane '{}'", name, e);
            System.out.println("Помилка при додаванні літака");
            e.printStackTrace();
        }
    }

    public static boolean checkIfExist(String name, String column_name, String tableName) {
        String query = "SELECT * FROM " + tableName +
                " WHERE " + column_name + " = " + "'" + name + "'";
        try {
            PreparedStatement prStatement = getConnect().prepareStatement(query);
            ResultSet resultSet = prStatement.executeQuery();
            if(resultSet.next())

                return true;
            return false;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deletePlane(String name) {
        try {
            String query = "DELETE FROM planes\n" +
                    "WHERE plane_name = " + "'" + name + "'";
            PreparedStatement prStatement = getConnect().prepareStatement(query);

            prStatement.executeUpdate();
            prStatement.close();
            logger.info("Plane '{}' deleted successfully", name);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error deleting plane '{}'", name, e);
            System.out.println("Помилка при видалені літака");
            e.printStackTrace();
        }
    }

    public static void deleteAviacompany(int company_id) {
        try {
            String delPlanes = "DELETE FROM planes\n" +
                    "WHERE company_id = " + "'" + company_id + "'";
            PreparedStatement delAll = getConnect().prepareStatement(delPlanes);
            delAll.executeUpdate();
            delAll.close();

            String query = "DELETE FROM company\n" +
                    "WHERE company_id = " + "'" + company_id + "'";
            PreparedStatement prStatement = getConnect().prepareStatement(query);

            prStatement.executeUpdate();
            prStatement.close();
            logger.info("Aviacompany with ID '{}' deleted successfully", company_id);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error deleting aviacompany with ID '{}'", company_id, e);
            System.out.println("Помилка при видалені авіакомпанії!");
            e.printStackTrace();
        }
    }
    public static int calculateCapacity(int company_id) {
        try {
            String query = "SELECT SUM(cargo_capacity + passenger_capacity)\n" +
                    "FROM planes\n" +
                    "WHERE company_id = " + company_id;
            PreparedStatement prStatement = getConnect().prepareStatement(query);
            ResultSet result = prStatement.executeQuery();
            int sum = 0;
            if(result.next()) {
                sum = result.getInt(1);
            }
            logger.info("Capacity calculated for company with ID '{}': {}", company_id, sum);
            return sum;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error calculating capacity for company with ID '{}'", company_id, e);
            System.out.println("Помилка при знаходженні місткості");
            e.printStackTrace();
        }
        return 0;
    }

    public static String printAviacompany(int company_id) {
        try {
            String query = "SELECT plane_name, flight_distance\n" +
                    "FROM planes\n" +
                    "WHERE company_id = " + company_id + "\n" +
                    "ORDER BY flight_distance";
            PreparedStatement prStatement = getConnect().prepareStatement(query);
            ResultSet result = prStatement.executeQuery();
            String text = "Літаки в обраній авіакомпанії:\n";
            while(result.next()) {
                text += result.getString("plane_name") + " | Дальність польоту: "
                        + result.getString("flight_distance") + "\n";
            }
            logger.info("Printed aviacompany with ID '{}'", company_id);
            return text;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error printing aviacompany with ID '{}'", company_id, e);
            System.out.println("Помилка при знаходженні місткості");
            e.printStackTrace();
        }
        return null;
    }

    public static String printPlane(String name) {
        try {
            String query = "SELECT \n" +
                    "plane_name, plane_type, plane_model, flight_distance," +
                    "fuel_consumption, cargo_capacity, passenger_capacity, company_name\n" +
                    "FROM planes\n" +
                    "JOIN company ON company.company_id = planes.company_id\n" +
                    "WHERE plane_name = " + "'" + name + "'";
            PreparedStatement prStatement = getConnect().prepareStatement(query);
            ResultSet result = prStatement.executeQuery();
            String text = "";
            while(result.next()) {
                String plane_name = result.getString(1);
                String plane_type = result.getString(2);
                String plane_model = result.getString(3);
                int flight_distance = result.getInt(4);
                int fuel_consumption = result.getInt(5);
                int cargo_capacity = result.getInt(6);
                int passenger_capacity = result.getInt(7);
                String company_name = result.getString(8);
                text = "Назва літака: " + plane_name + "|Тип: " + plane_type + "|Модель: " + plane_model + "\n" +
                        "Дальність польоту: " + flight_distance + "\n" +
                        "Витрати палива (літр/год): " + fuel_consumption + "\n" +
                        "Місткість вантажу: " + cargo_capacity + "\n" +
                        "Кількість пасажирських місць: " + passenger_capacity + "\n" +
                        "Авіакомпанія, якій належить: " + company_name;
            }
            logger.info("Printed plane '{}'", name);
            return text;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error printing plane '{}'", name, e);
            System.out.println("Помилка при знаходженні місткості");
            e.printStackTrace();
        }
        return null;
    }
    public static String findPlane(String min, String max) {
        try {
            String query = "SELECT plane_name, fuel_consumption\n" +
                    "FROM planes\n" +
                    "WHERE fuel_consumption BETWEEN " + min + " AND " + max;
            PreparedStatement prStatement = getConnect().prepareStatement(query);
            ResultSet result = prStatement.executeQuery();
            String text = "";
            while(result.next()) {
                String plane_name = result.getString(1);
                int fuel_consumption = result.getInt(2);
                text += "Назва літака: " + plane_name + "\n" +
                        "Витрати палива (літр/год): " + fuel_consumption + "\n";
            }
            logger.info("Found planes with fuel consumption between '{}' and '{}'", min, max);
            return text;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error finding planes with fuel consumption between '{}' and '{}'", min, max, e);
            System.out.println("Помилка при знаходженні місткості");
            e.printStackTrace();
        }
        return null;
    }
    public static boolean countComps() {
        try {
            String query = "SELECT COUNT(company_id)\n" +
                    "FROM company";
            PreparedStatement prStatement = getConnect().prepareStatement(query);
            ResultSet resultSet = prStatement.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) == 0)
                return true;
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error counting companies", e);
            throw new RuntimeException(e);
        }
    }
    public static void addOwner(String type, String firstName, String lastName, String countryName) {
        try {
            if (type.equals("person")) {
                String query = "INSERT INTO owner\n" +
                        "(type_owner, first_name, last_name)\n" +
                        "VALUES(?,?,?)";
                PreparedStatement prStatement = getConnect().prepareStatement(query);
                prStatement.setString(1, type);
                prStatement.setString(2, firstName);
                prStatement.setString(3, lastName);
                prStatement.executeUpdate();
                prStatement.close();
                logger.info("Added owner '{} {}' of type '{}'", firstName, lastName, type);
            } else if (type.equals("state")) {
                String query2 = "INSERT INTO owner\n" +
                        "(type_owner, country)\n" +
                        "VALUES(?,?)";
                PreparedStatement prStatement = getConnect().prepareStatement(query2);
                prStatement.setString(1, type);
                prStatement.setString(2, countryName);
                prStatement.executeUpdate();
                prStatement.close();
                logger.info("Added owner '{}' of type 'state'", countryName);
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error adding owner", e);
            System.out.println("Помилка при додаванні літака");
            e.printStackTrace();
        }
    }

    public static int getOwnerID(String type, String firstName, String lastName, String countryName) {
        try {
            int id = 0;
            if(type.equals("person")) {
                String query = "SELECT owner_id\n" +
                        "FROM owner\n" +
                        "WHERE first_name = " + "'" + firstName + "'\n" +
                        "AND last_name = " + "'" + lastName + "'";
                PreparedStatement prStatement = getConnect().prepareStatement(query);
                ResultSet result = prStatement.executeQuery();
                if (result.next()) {
                    id = result.getInt(1);
                }
                logger.info("Retrieved owner ID '{}' for '{} {}'", id, firstName, lastName);
                return id;
            } else if(type.equals("state")) {
                String query2 = "SELECT owner_id\n" +
                        "FROM owner\n" +
                        "WHERE country = " + "'" + countryName + "'";
                PreparedStatement prStatement = getConnect().prepareStatement(query2);
                ResultSet result = prStatement.executeQuery();
                if (result.next()) {
                    id = result.getInt(1);
                }
                logger.info("Retrieved owner ID '{}' for state '{}'", id, countryName);
                return id;
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error getting owner ID", e);
            System.out.println("Помилка при доставанні ID");
            e.printStackTrace();
        }
        return 0;
    }

    public static void deleteOwner(int ownerID) {
        try {
            String selectCompanies = "SELECT company_id\n" +
                    "FROM company\n" +
                    "WHERE owner_id = " + ownerID;
            PreparedStatement selectAll = getConnect().prepareStatement(selectCompanies);
            ResultSet result = selectAll.executeQuery();
            while(result.next()) {
                DbHandler.deleteAviacompany(result.getInt(1));
            }
            String delOwner = "DELETE FROM owner\n" +
                    "WHERE owner_id = " + ownerID;
            PreparedStatement deleteOwner = getConnect().prepareStatement(delOwner);
            deleteOwner.executeUpdate();
            deleteOwner.close();
            logger.info("Owner with ID '{}' deleted successfully", ownerID);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error deleting owner with ID '{}'", ownerID, e);
            System.out.println("Помилка при видалені авіакомпанії!");
            e.printStackTrace();
        }
    }

}
