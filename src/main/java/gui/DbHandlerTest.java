package gui;

import base.plane.Plane;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.slf4j.Logger;

import java.sql.*;

import static org.mockito.Mockito.*;

class DbHandlerTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    @Mock
    private Logger mockLogger;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        DbHandler.logger = mockLogger;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(DbHandler.getConnect()).thenReturn(mockConnection);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockConnection.close();
    }

    @Test
    void testCreateAviacompany() throws SQLException {
        String companyName = "TestCompany";
        DbHandler.createAviacompany(companyName);
        verify(mockPreparedStatement, times(1)).setString(1, companyName);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockLogger, times(1)).info("Aviacompany '{}' created successfully", companyName);
    }

    @Test
    void testGetID() throws SQLException {
        String companyName = "TestCompany";
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        int companyId = DbHandler.getID(companyName);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockLogger, times(1)).info("ID retrieved successfully for company '{}'", companyName);
        Assertions.assertEquals(1, companyId);
    }

    @Test
    void testAddPlane() throws SQLException {
        Plane mockPlane = mock(Plane.class);
        when(mockPlane.getFlightDist()).thenReturn(1000);
        when(mockPlane.getFuelConsumption()).thenReturn(500);
        when(mockPlane.getCargoCapacity()).thenReturn(2000);
        when(mockPlane.getPassCapacity()).thenReturn(150);

        DbHandler.addPlane(mockPlane, "TestPlane", 1);

        verify(mockPreparedStatement, times(1)).setString(1, "TestPlane");
        verify(mockPreparedStatement, times(1)).setString(2, mockPlane.getClass().getSuperclass().getSimpleName());
        verify(mockPreparedStatement, times(1)).setString(3, mockPlane.getClass().getSimpleName());
        verify(mockPreparedStatement, times(1)).setInt(4, mockPlane.getFlightDist());
        verify(mockPreparedStatement, times(1)).setInt(5, mockPlane.getFuelConsumption());
        verify(mockPreparedStatement, times(1)).setInt(6, mockPlane.getCargoCapacity());
        verify(mockPreparedStatement, times(1)).setInt(7, mockPlane.getPassCapacity());
        verify(mockPreparedStatement, times(1)).setInt(8, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockLogger, times(1)).info("Plane '{}' added successfully for company with ID '{}'", "TestPlane", 1);
    }

    @Test
    void testCheckIfExist() throws SQLException {
        String name = "TestName";
        String columnName = "test_column";
        String tableName = "test_table";
        when(mockResultSet.next()).thenReturn(true);

        boolean exists = DbHandler.checkIfExist(name, columnName, tableName);
        verify(mockPreparedStatement, times(1)).executeQuery();
        Assertions.assertTrue(exists);
    }

    @Test
    void testDeletePlane() throws SQLException {
        String planeName = "TestPlane";
        DbHandler.deletePlane(planeName);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockLogger, times(1)).info("Plane '{}' deleted successfully", planeName);
    }

    @Test
    void testDeleteAviacompany() throws SQLException {
        int companyId = 1;
        DbHandler.deleteAviacompany(companyId);
        verify(mockPreparedStatement, times(2)).executeUpdate();
        verify(mockPreparedStatement, times(2)).close();
        verify(mockLogger, times(1)).info("Aviacompany with ID '{}' deleted successfully", companyId);
    }

    @Test
    void testCalculateCapacity() throws SQLException {
        int companyId = 1;
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(3500);

        int capacity = DbHandler.calculateCapacity(companyId);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockLogger, times(1)).info("Capacity calculated for company with ID '{}': {}", companyId, 3500);
        Assertions.assertEquals(3500, capacity);
    }

    @Test
    void testPrintAviacompany() throws SQLException {
        int companyId = 1;
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("plane_name")).thenReturn("TestPlane");
        when(mockResultSet.getString("flight_distance")).thenReturn("1000");

        String result = DbHandler.printAviacompany(companyId);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockLogger, times(1)).info("Printed aviacompany with ID '{}'", companyId);
        Assertions.assertEquals("Літаки в обраній авіакомпанії:\nTestPlane | Дальність польоту: 1000\n", result);
    }

    @Test
    void testPrintPlane() throws SQLException {
        String planeName = "TestPlane";
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString(1)).thenReturn("TestPlane");
        when(mockResultSet.getString(2)).thenReturn("TestType");
        when(mockResultSet.getString(3)).thenReturn("TestModel");
        when(mockResultSet.getInt(4)).thenReturn(1000);
        when(mockResultSet.getInt(5)).thenReturn(500);
        when(mockResultSet.getInt(6)).thenReturn(2000);
        when(mockResultSet.getInt(7)).thenReturn(150);
        when(mockResultSet.getString(8)).thenReturn("TestCompany");

        String result = DbHandler.printPlane(planeName);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockLogger, times(1)).info("Printed plane '{}'", planeName);
        Assertions.assertEquals("Назва літака: TestPlane|Тип: TestType|Модель: TestModel\n" +
                "Дальність польоту: 1000\n" +
                "Витрати палива (літр/год): 500\n" +
                "Місткість вантажу: 2000\n" +
                "Кількість пасажирських місць: 150\n" +
                "Авіакомпанія, якій належить: TestCompany", result);
    }

    @Test
    void testFindPlane() throws SQLException {
        String min = "100";
        String max = "500";
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString(1)).thenReturn("TestPlane");
        when(mockResultSet.getInt(2)).thenReturn(300);

        String result = DbHandler.findPlane(min, max);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockLogger, times(1)).info("Found planes with fuel consumption between '{}' and '{}'", min, max);
        Assertions.assertEquals("Назва літака: TestPlane\nВитрати палива (літр/год): 300\n", result);
    }

    @Test
    void testCountComps() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(0);

        boolean result = DbHandler.countComps();
        verify(mockPreparedStatement, times(1)).executeQuery();
        Assertions.assertTrue(result);
    }

    @Test
    void testAddOwnerPerson() throws SQLException {
        String type = "person";
        String firstName = "John";
        String lastName = "Doe";

        DbHandler.addOwner(type, firstName, lastName, null);
        verify(mockPreparedStatement, times(1)).setString(1, type);
        verify(mockPreparedStatement, times(1)).setString(2, firstName);
        verify(mockPreparedStatement, times(1)).setString(3, lastName);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockLogger, times(1)).info("Added owner '{} {}' of type '{}'", firstName, lastName, type);
    }

    @Test
    void testAddOwnerState() throws SQLException {
        String type = "state";
        String countryName = "TestCountry";

        DbHandler.addOwner(type, null, null, countryName);
        verify(mockPreparedStatement, times(1)).setString(1, type);
        verify(mockPreparedStatement, times(1)).setString(2, countryName);
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockLogger, times(1)).info("Added owner 'TestCountry' of type 'state'");
    }
}
