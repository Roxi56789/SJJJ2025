package Esfe.persistencia;

import java.sql.Connection; // Representa una conexión a la base de datos.
import java.sql.DriverManager; // Gestiona los drivers JDBC y establece conexiones.
import java.sql.SQLException; // Representa errores específicos de la base de datos.

public class ConnectionManager {


    private static final String STR_CONNECTION = "jdbc:sqlserver://LISENA\\MSSQLSERVER01:1433; " +
            "encrypt=true; " +
            "database=SmartNovast; " +
            "trustServerCertificate=true;" +
            "user=LAKJ2025;" +
            "password=LAKJ12345";


    private Connection connection;


    private static ConnectionManager instance;


    private ConnectionManager() {
        this.connection = null;
        try {
            // Carga el driver JDBC de Microsoft SQL Server. Esto es necesario para que Java pueda
            // comunicarse con la base de datos SQL Server.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            // Si el driver no se encuentra, se lanza una excepción indicando el error.
            throw new RuntimeException("Error al cargar el driver JDBC de SQL Server", e);
        }
    }

    /**
     * Este método se encarga de establecer la conexión con la base de datos.
     * Es sincronizado (`synchronized`) para asegurar que solo un hilo a la vez pueda
     * intentar establecer la conexión, lo cual es importante en entornos multihilo.
     *
     * @return La instancia de la conexión a la base de datos.
     * @throws SQLException Si ocurre un error al intentar conectar a la base de datos.
     */
    public synchronized Connection connect() throws SQLException {
        // Verifica si la conexión ya existe y si no está cerrada.
        if (this.connection == null || this.connection.isClosed()) {
            try {
                // Intenta establecer la conexión utilizando la cadena de conexión.
                this.connection = DriverManager.getConnection(STR_CONNECTION);
            } catch (SQLException exception) {
                // Si ocurre un error durante la conexión, se lanza una excepción SQLException
                // con un mensaje más descriptivo que incluye el mensaje original de la excepción.
                throw new SQLException("Error al conectar a la base de datos: " + exception.getMessage(), exception);
            }
        }
        // Retorna la conexión (ya sea la existente o la recién creada).
        return this.connection;
    }

    /**
     * Este método se encarga de cerrar la conexión a la base de datos.
     * También lanza una SQLException si ocurre un error al intentar cerrar la conexión.
     *
     * @throws SQLException Si ocurre un error al intentar cerrar la conexión.
     */
    public void disconnect() throws SQLException {
        // Verifica si la conexión existe (no es nula).
        if (this.connection != null) {
            try {
                // Intenta cerrar la conexión.
                this.connection.close();
            } catch (SQLException exception) {
                // Si ocurre un error al cerrar la conexión, se lanza una excepción SQLException
                // con un mensaje más descriptivo.
                throw new SQLException("Error al cerrar la conexión: " + exception.getMessage(), exception);
            } finally {
                // El bloque finally se ejecuta siempre, independientemente de si hubo una excepción o no.
                // Aquí se asegura que la referencia a la conexión se establezca a null,
                // indicando que ya no hay una conexión activa gestionada por esta instancia.
                this.connection = null;
            }
        }
    }

    /**
     * Este método estático y sincronizado (`synchronized`) implementa el patrón Singleton.
     * Devuelve la única instancia de JDBCConnectionManager. Si la instancia aún no existe,
     * la crea antes de devolverla. La sincronización asegura que la creación de la instancia
     * sea segura en entornos multihilo (que varios hilos no intenten crear la instancia al mismo tiempo).
     *
     * @return La única instancia de JDBCConnectionManager.
     */
    public static synchronized ConnectionManager getInstance() {
        // Verifica si la instancia ya ha sido creada.
        if (instance == null) {
            // Si no existe, crea una nueva instancia de JDBCConnectionManager.
            instance = new ConnectionManager();
        }
        // Retorna la instancia existente (o la recién creada).
        return instance;
    }
}
