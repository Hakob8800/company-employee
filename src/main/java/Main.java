import db.DBConnectionProvider;
import model.Company;
import model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Connection CONNECTION = DBConnectionProvider.getInstance().getConnection();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final SimpleDateFormat SMD = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            System.out.println("Please choose command");
            System.out.println("Press 0 for exit");
            System.out.println("Press 1 for add company and save in DB");
            System.out.println("Press 2 for add employee and save in DB");
            System.out.println("Press 3 for show all companys");
            int command = Integer.parseInt(SCANNER.nextLine());
            switch (command) {
                case 0:
                    isRun = false;
                    break;
                case 1:
                    Company company = createCompany();
                    saveCompanyToDB(company);
                    System.out.println("Company saved in DB");
                    break;
                case 2:
                    Employee employee = createNewEmployee();
                    saveEmployeeTOBD(employee);
                    System.out.println("Employee saved in DB");
                    break;
                case 3:
                    showAllCompanys();
                    break;
            }

        }

    }

    private static void saveEmployeeTOBD(Employee employee) {
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate("INSERT INTO employee(name,email,registered_date,company_id) values ('" +
                    employee.getName() + "','" + employee.getEmail() + "','" + SMD.format(employee.getRegisteredDate()) + "','" +
                    +employee.getCompanyId() + "')");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Employee createNewEmployee() {
        Employee employee = new Employee();
        System.out.println("Please input employe's data name,email,registered data,companyId");
        String[] employeeDataStr = SCANNER.nextLine().split(",");
        employee.setName(employeeDataStr[0]);
        employee.setEmail(employeeDataStr[1]);
        try {
            employee.setRegisteredDate(SMD.parse(employeeDataStr[2]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employee.setCompanyId(Integer.parseInt(employeeDataStr[3]));
        return employee;
    }

    private static void showAllCompanys() {
        List listOfCompany = getCompanysFromDB();
        for (Object company1 : listOfCompany) {
            System.out.println(company1);
        }
    }

    private static Company createCompany() {
        System.out.println("Please input company data's name, country.");
        String[] dataStr = SCANNER.nextLine().split(",");
        Company company = new Company();
        company.setName(dataStr[0]);
        company.setCountry(dataStr[1]);
        return company;
    }

    private static List getCompanysFromDB() {
        List list = new ArrayList();
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from company");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String country = resultSet.getString(3);
                Company company = new Company(id, name, country);
                list.add(company);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static void saveCompanyToDB(Company company) {
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate("INSERT INTO company(name,country) " +
                    "VALUES ('" + company.getName() + "', '" + company.getCountry() + "')");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
