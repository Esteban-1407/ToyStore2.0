package Main;

import config.DatabaseConnection;
import customExceptions.ToyStoreException;
import mapping.dtos.*;
import model.category;
import model.cliente;
import model.employees;
import services.ToyStorelmpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class View {

    private static ToyStorelmpl toyStore;

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getInstance()){
            toyStore = new ToyStorelmpl();
            Scanner scanner = new Scanner(System.in);
            int choice = -7;

            while (choice != 0) {
                System.out.println("Bienvenido a la tienda de juguetes");
                System.out.println("1. Mostrar lista de juguetes");
                System.out.println("2. Buscar juguete por ID");
                System.out.println("3. Agregar nuevo juguete");
                System.out.println("4. Actualizar stock de un juguete");
                System.out.println("5. Obtener total de stock");
                System.out.println("6. Obtener valor total de inventario");
                System.out.println("7. Obtener tipo con más juguetes");
                System.out.println("8. Obtener tipo con menos juguetes");
                System.out.println("9. Obtener juguetes con valor mayor a cierto monto");
                System.out.println("10. Ordenar juguetes por cantidad de stock");
                System.out.println("11. Mostrar lista de ventas y sus detalles");
                System.out.println("12. Mostrar empleados");
                System.out.println("13. Mostrar Clientes Activos");
                System.out.println("14. Mostrar Ventas registradas");
                System.out.println("15. Nueva venta");
                System.out.println("16. Nuevo Empleado");
                System.out.println("17. Nuevo cliente");
                System.out.println("0. Salir");
                System.out.print("Ingrese su elección: ");

                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 1:
                            CompletableFuture<List<ToyDTO>> future = CompletableFuture.supplyAsync(() -> {
                                List<ToyDTO> list = toyStore.listToys();
                                if (!list.isEmpty()) {
                                    for (ToyDTO toys : list) {
                                        System.out.println(toys);
                                        System.out.println("Loading...");
                                        try {
                                            Thread.sleep(3000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    System.out.println("There are no toys on the list");
                                }
                                return list;
                            });
                            future.join();
                            System.out.println("The task is completed");
                            break;
                        case 2:
                            searchToy();
                            break;
                        case 3:
                            addNewToy(scanner);
                            break;
                        case 4:
                            updateStock(scanner);
                            break;
                        case 5:
                            getTotalStock();
                            break;
                        case 6:
                            getTotalValue();
                            break;
                        case 7:
                            getTypeWithMostToys();
                            break;
                        case 8:
                            getTypeWithLeastToys();
                            break;
                        case 9:
                            getToysWithValueGreaterThan(scanner);
                            break;
                        case 10:
                            orderByStockQuantity();
                            break;
                        case 11:
                            listSaleDetails();
                            break;
                        case 12:
                            listEmployees();
                            break;
                        case 13:
                            listCustomers();
                            break;
                        case 14:
                            listSales();
                            break;
                        case 15:
                            newSale(scanner);
                            break;
                        case 16:
                            addEmployee(scanner);
                            break;
                        case 17:
                            addCustomer(scanner);
                            break;
                        case 0:
                            System.out.println("Gracias por visitar la tienda de juguetes. ¡Hasta luego!");
                            break;
                        default:
                            System.out.println("Opción no válida. Inténtelo de nuevo.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, ingrese un número válido.");
                    scanner.nextLine();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ToyStoreException.ToyNotFoundException e) {
                    System.out.println("Error: Juguete no encontrado - " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchToy() throws SQLException, ToyStoreException.ToyNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del juguete a buscar: ");
        int id = scanner.nextInt();
        ToyDTO toy = toyStore.search(id);
        if (toy != null) {
            System.out.println("Juguete encontrado:");
            System.out.println(toy);
        } else {
            System.out.println("No se encontró ningún juguete con el ID proporcionado.");
        }
    }

    private static void addNewToy(Scanner scanner) throws SQLException {
        System.out.print("Ingrese el nombre del juguete: ");
        String name = scanner.nextLine();
        System.out.print("Ingrese el precio del juguete: ");
        int price = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la cantidad en stock del juguete: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la categoría del juguete: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el tipo de la categoría del juguete");
        String type = scanner.nextLine();
        category c = new category(categoryId,type);
        ToyDTO toyDTO = new ToyDTO( name, price, c, stock);
        toyStore.addToy(toyDTO);
        System.out.println("Juguete agregado con éxito.");
    }

    private static void addCustomer(Scanner scanner) throws SQLException {
        System.out.println("Ingrese el nombre del cliente");
        String name = scanner.nextLine();
        System.out.println("Ingrese el ID number del usuario");
        String IDnumber = scanner.nextLine();
        System.out.println("Ingrese la fecha de nacimiento del cliente");
        Date fecha = Date.valueOf(scanner.nextLine());
        ClienteDTO clienteDTO = new ClienteDTO(name, IDnumber, fecha);
        toyStore.addCliente(clienteDTO);
        System.out.println("Usuario agregado con exito");
    }

    private static void addEmployee(Scanner scanner) throws SQLException {
        System.out.println("Ingrese su usuario");
        String user = scanner.nextLine();
        System.out.println("Ingrese su contraseña");
        String password = scanner.nextLine();
        System.out.println("Ingrese su fecha de ingreso");
        Date date = Date.valueOf(scanner.nextLine());
        EmployeesDTO employeesDTO = new EmployeesDTO(user,password,date);
        toyStore.addEmployees(employeesDTO);
    }

    private static void newSale(Scanner scanner) throws SQLException {
        System.out.println("Ingrese el id del cliente");
        int Id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("ingrese el id del empleado");
        int employeeId = scanner.nextInt();
        System.out.println("Ingrese el nombre del cliente");
        String clientId = scanner.nextLine();
        System.out.println("Ingrese el ID number del cliente");
        String ID = scanner.nextLine();
        System.out.println("Ingrese la  fecha de nacimiento");
        Date date = Date.valueOf(scanner.nextLine());
        System.out.println("Ingrese el user");
        String user = scanner.nextLine();
        System.out.println("Ingrese la contraseña");
        String password = scanner.nextLine();
        System.out.println("Ingrese fecha de inicio");
        Date startDate = Date.valueOf(scanner.nextLine());
        cliente cliente = new cliente(Id,clientId,ID,date);
        employees employees = new employees(employeeId,user,password,startDate);
        SaleDTO saleDTO = new SaleDTO(cliente,employees);
        toyStore.addSale(saleDTO);
    }






private static void updateStock(Scanner scanner) {
        System.out.print("Ingrese el ID del juguete para actualizar el stock: ");
        int toyId = scanner.nextInt();
        System.out.print("Ingrese la cantidad de cambio de stock (positivo para agregar, negativo para restar): ");
        int quantityChange = scanner.nextInt();
        toyStore.updateStock(toyId, quantityChange);
        System.out.println("Stock actualizado con éxito.");
    }

    private static void getTotalStock() {
        int totalStock = toyStore.getTotalStock();
        System.out.println("El total de stock en la tienda es: " + totalStock);
    }

    private static void getTotalValue() {
        double totalValue = toyStore.getTotalValue();
        System.out.println("El valor total del inventario en la tienda es: " + totalValue);
    }

    private static void getTypeWithMostToys() {
        String typeWithMostToys = toyStore.getTypeWithMostToys();
        System.out.println("El tipo con más juguetes es: " + typeWithMostToys);
    }

    private static void getTypeWithLeastToys() {
        String typeWithLeastToys = toyStore.getTypeWithLeastToys();
        System.out.println("El tipo con menos juguetes es: " + typeWithLeastToys);
    }

    private static void getToysWithValueGreaterThan(Scanner scanner) {
        System.out.print("Ingrese el valor mínimo para los juguetes que desea buscar: ");
        int value = scanner.nextInt();
        List<ToyDTO> toys = toyStore.getToysWithValueGreaterThan(value);
        displayToys(toys);
    }

    private static void orderByStockQuantity() {
        List<ToyDTO> toys = toyStore.orderByStockQuantity();
        displayToys(toys);
    }
    private static void listEmployees() {
        List<EmployeesDTO> employees = toyStore.listEmployees();
        displayEmployees(employees);
    }
    private static void listSaleDetails() {
        List<SaleDetailsDTO> saleDetails = toyStore.listSaleDetails();
        displaySaleDetails(saleDetails);
    }
    private static void listSales() {
        List<SaleDTO> sales = toyStore.listSales();
        displaySales(sales);
    }
    private static void listCustomers(){
        List<ClienteDTO> clients = toyStore.listCustomers();
        displayClients(clients);
    }



    private static void displayToys(List<ToyDTO> toys) {
        if (toys.isEmpty()) {
            System.out.println("No hay juguetes disponibles en la tienda.");
        } else {
            System.out.println("Lista de juguetes:");
            for (ToyDTO toy : toys) {
                System.out.println(toy);
            }
        }
    }
    private static void displayEmployees(List<EmployeesDTO> employees) {
        if (employees.isEmpty()) {
            System.out.println("No hay empleados disponibles.");
        } else {
            System.out.println("Lista de empleados:");
            for (EmployeesDTO employ : employees) {
                System.out.println(employ);
            }
        }
    }
    private static void displaySaleDetails(List<SaleDetailsDTO> saleDetails) {
        if (saleDetails.isEmpty()) {
            System.out.println("No hay ventas y detalles registradas.");
        } else {
            System.out.println("Lista de ventas y sus detalles:");
            for (SaleDetailsDTO saleDetailsDTO : saleDetails) {
                System.out.println(saleDetailsDTO);
            }
        }
    }
    private static  void displaySales(List<SaleDTO> sales){
        if (sales.isEmpty()){
            System.out.println("No hay ventas registradas.");
        } else {
            System.out.println("Lista de ventas");
            for (SaleDTO sale : sales) {
                System.out.println(sale);
            }
        }
    }
    private static void displayClients(List<ClienteDTO> clients){
        if (clients.isEmpty()){
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("Lista de clientes");
            for (ClienteDTO client : clients) {
                System.out.println(client);
            }
        }

    }
}

