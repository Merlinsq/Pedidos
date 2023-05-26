package com.example.pedi2.clases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.FragmentActivity;

import com.example.pedi2.clases.Cliente;
import com.example.pedi2.clases.Pedido;
import com.example.pedi2.clases.Producto;
import com.example.pedi2.clases.TipoPedido;
import com.example.pedi2.clases.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MiBaseDeDatosHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pedidos.db";
    private static final int DATABASE_VERSION = 1;

    public MiBaseDeDatosHelper(FragmentActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Crear tabla usuario
        database.execSQL("CREATE TABLE usuario (_id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, password TEXT);");

        // Insertar datos por defecto en la tabla usuario
        ContentValues userValues = new ContentValues();
        userValues.put("user", "msandi");
        userValues.put("password", "msandi2023");
        database.insert("usuario", null, userValues);

        // Crear tabla cliente
        database.execSQL("CREATE TABLE cliente (_id INTEGER PRIMARY KEY AUTOINCREMENT, codigo TEXT, nombre TEXT, direccion TEXT, CL TEXT);");

        // Crear tabla TipoPedido
        database.execSQL("CREATE TABLE TipoPedido (_id INTEGER PRIMARY KEY AUTOINCREMENT, Contado TEXT, Credito TEXT);");

        // Crear tabla productos
        database.execSQL("CREATE TABLE productos (_id INTEGER PRIMARY KEY AUTOINCREMENT, codigo TEXT, descripcion TEXT, cantidad INTEGER, PedidoMT TEXT, PedidoMB TEXT);");

        // Crear tabla pedidos
        database.execSQL("CREATE TABLE pedidos (_id INTEGER PRIMARY KEY AUTOINCREMENT, cliente_id INTEGER, tipo_pedido_id INTEGER, producto_id INTEGER, Cantidad INT, FOREIGN KEY(cliente_id) REFERENCES cliente(_id), FOREIGN KEY(tipo_pedido_id) REFERENCES TipoPedido(_id), FOREIGN KEY(producto_id) REFERENCES productos(_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // Aquí se colocarían las sentencias SQL para actualizar la base de datos
        // en caso de que se cambiara la versión de la misma
    }

    public List<Usuario> obtenerUsuariosDesdeBD() {
        List<Usuario> usuarios = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM usuario", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("user"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));

                Usuario usuario = new Usuario(id, user, password);
                usuarios.add(usuario);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return usuarios;
    }

    public int actualizarUsuarioEnBD(int id, String nuevoUser, String nuevaPassword) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user", nuevoUser);
        values.put("password", nuevaPassword);

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.update("usuario", values, whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }
    public int eliminarUsuarioDeBD(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.delete("usuario", whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }

    public long insertarClienteEnBD(String codigo, String nombre, String direccion, String cl) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("codigo", codigo);
        values.put("nombre", nombre);
        values.put("direccion", direccion);
        values.put("CL", cl);
        long clienteId = database.insert("cliente", null, values);
        database.close();
        return clienteId;
    }

    public List<Cliente> obtenerClientesDesdeBD() {
        List<Cliente> clientes = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM cliente", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                @SuppressLint("Range") String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
                @SuppressLint("Range") String cl = cursor.getString(cursor.getColumnIndex("CL"));

                Cliente cliente = new Cliente(id, codigo, nombre, direccion, cl);
                clientes.add(cliente);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return clientes;
    }

    public int actualizarClienteEnBD(int id, String nuevoCodigo, String nuevoNombre, String nuevaDireccion, String nuevoCl) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("codigo", nuevoCodigo);
        values.put("nombre", nuevoNombre);
        values.put("direccion", nuevaDireccion);
        values.put("CL", nuevoCl);

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.update("cliente", values, whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }

    public int eliminarClienteDeBD(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.delete("cliente", whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }

    public long insertarTipoPedidoEnBD(String contado, String credito) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Contado", contado);
        values.put("Credito", credito);

        long tipoPedidoId = database.insert("TipoPedido", null, values);

        database.close();

        return tipoPedidoId;
    }

    public List<TipoPedido> obtenerTiposPedidoDesdeBD() {
        List<TipoPedido> tiposPedido = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM TipoPedido", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String contado = cursor.getString(cursor.getColumnIndex("Contado"));
                @SuppressLint("Range") String credito = cursor.getString(cursor.getColumnIndex("Credito"));

                TipoPedido tipoPedido = new TipoPedido(id, contado, credito);
                tiposPedido.add(tipoPedido);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return tiposPedido;
    }

    public int actualizarTipoPedidoEnBD(int id, String nuevoContado, String nuevoCredito) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Contado", nuevoContado);
        values.put("Credito", nuevoCredito);

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.update("TipoPedido", values, whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }

    public int eliminarTipoPedidoDeBD(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.delete("TipoPedido", whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }


    public long insertarProductosEnBD(String codigo, String descripcion) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("codigo", codigo);
        values.put("descripcion", descripcion);

        long productosId = database.insert("productos", null, values);
        database.close();
        return productosId;
    }

    public List<Producto> obtenerProductosDesdeBD() {
        List<Producto> productos = new ArrayList<>();



        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM productos", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));


                Producto producto = new Producto(id, codigo, descripcion);
                productos.add(producto);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return productos;
    }

    public int actualizarProductoEnBD(int id, String nuevoCodigo, String nuevaDescripcion) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("codigo", nuevoCodigo);
        values.put("descripcion", nuevaDescripcion);


        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.update("productos", values, whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }


    public int eliminarProductoDeBD(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.delete("productos", whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }

    public long insertarPedidoEnBD(int clienteId, int tipoPedidoId, int productoId, int cantidad) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cliente_id", clienteId);
        values.put("tipo_pedido_id", tipoPedidoId);
        values.put("producto_id", productoId);
        values.put("cantidad_id", cantidad);

        long pedidoId = database.insert("pedidos", null, values);

        database.close();

        return pedidoId;
    }
    public List<Pedido> obtenerPedidosDesdeBD() {
        List<Pedido> pedidos = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM pedidos", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") int clienteId = cursor.getInt(cursor.getColumnIndex("cliente_id"));
                @SuppressLint("Range") int tipoPedidoId = cursor.getInt(cursor.getColumnIndex("tipo_pedido_id"));
                @SuppressLint("Range") int productoId = cursor.getInt(cursor.getColumnIndex("producto_id"));
                @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));

                Pedido pedido = new Pedido(id, clienteId, tipoPedidoId, productoId, cantidad);
                pedidos.add(pedido);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return pedidos;
    }

    public int actualizarPedidoEnBD(int id, int nuevoClienteId, int nuevoTipoPedidoId, int nuevoProductoId, int nuevoCantidad) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cliente_id", nuevoClienteId);
        values.put("tipo_pedido_id", nuevoTipoPedidoId);
        values.put("producto_id", nuevoProductoId);
        values.put("cantidad", nuevoCantidad);

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.update("pedidos", values, whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }

    public int eliminarPedidoDeBD(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = database.delete("pedidos", whereClause, whereArgs);

        database.close();

        return rowsAffected;
    }


    public void borrarTablaClientes() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("cliente", null, null);
        database.close();
    }

    public void borrarTablaProductos() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("productos", null, null);
        database.close();
    }

}

