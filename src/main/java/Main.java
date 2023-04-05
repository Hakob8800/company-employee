import db.DBConnectionProvider;
import model.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{

    private static Connection connection = DBConnectionProvider.getInstance().getConnection();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        System.out.println("Please input company data's name, country.");
//        String[] dataStr = scanner.nextLine().split(",");
//        Company company = new Company();
//        company.setName(dataStr[0]);
//        company.setCountry(dataStr[1]);
//        saveCompanyToDB(company);

        List listOfCompany = getCompanysFromDB();
        for (Object company1 : listOfCompany) {
            System.out.println(company1);
        }

    }

    private static List getCompanysFromDB() {
        List list = new ArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from company");
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String country = resultSet.getString(3);
                Company company = new Company(id,name,country);
                list.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static void saveCompanyToDB(Company company) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO company(name,country) VALUES ('"+company.getName()+"', '"+company.getCountry()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
