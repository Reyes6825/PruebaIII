/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erika pinilla
 */
public class Sql_usuarios extends Conexion {
    
    public boolean registrar (Usuarios user){
      
     PreparedStatement ps= null;
     Connection con = getConexion(); 
     
     String datos = "INSERT INTO usuarios (NOMBRE,APELLIDOS,TIPO_DE_DOCUMENTO,NUMERO,TELEFONO,DEPARTAMENTO,MUNICIPIO,DIRECCION,CORREO,TIPO_DE_USUARIO,USUARIO,CONTRASEÑA)"
             + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
     
      try {
            ps = con.prepareStatement(datos);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellido());
            ps.setString(3, user.getTipodocumento());
            ps.setString(4, user.getDocumento());
            ps.setString(5, user.getTelefono());
            ps.setString(6, user.getDepartamento());
            ps.setString(7, user.getMunicipio());
            ps.setString(8, user.getDireccion());
            ps.setString(9, user.getCorreo());
            ps.setInt(10, user.getTipo());
            ps.setString(11, user.getUsuario());
            ps.setString(12, user.getContraseña());
            ps.execute();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(Sql_usuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
     
    }
      public boolean Iniciar (Usuarios user){    
// se cre el objeto ps(PreparedStatement) que hace una precompilacion de los datos para ser enviados a la base de datos y 
//el objeto con(Connection) para establecer el vinculo entre la base de datos y java
     PreparedStatement ps= null;
     Connection con = getConexion();
     ResultSet rs= null;
     
                   
// se busca el numerodocumento en la base de datos para validar si ya esta registrado          
     String datos = "SELECT  u.ID,u.NOMBRE,u.APELLIDO,u.TIPO_DE_DOCUMENTO,u.NUMERO,u.TELEFONO,u.DEPARTAMENTO,u.MUNICIPIO,u.DIRECCION,u.CORREO,u.TIPO_DE_USUARIO,u.USUARIO,u.CONTRASEÑA,t.tipo_usuario  FROM usuarios "
             + "AS u INNER JOIN TIPO_DE_USUARIO AS t ON u.TIPO_DE_USUARI=t.id_tipo  WHERE usuario=?";
        try {
            ps = con.prepareStatement(datos);
            ps.setString(1, user.getUsuario());     
            rs = ps.executeQuery();
           if(rs.next()){
               
               if(user.getContraseña().equals(rs.getString(7))){
                   String sesion= "UPDATE usuarios SET ultimasesion=? WHERE id=?";
                   ps= con.prepareStatement(sesion);
                   ps.setString(1, user.getUltimasesion());
                   ps.setInt(2, rs.getInt(1));
                   ps.execute();
                   
                   user.setId(rs.getInt(1));
                   user.setNombre(rs.getString(2));
                   user.setApellido(rs.getString(3));
                   user.setTipodocumento(rs.getString(4));
                   user.setDocumento(rs.getString(5));
                   user.setTelefono(rs.getString(6));
                   user.setDepartamento(rs.getString(7));
                   user.setMunicipio(rs.getString(8));
                   user.setDireccion(rs.getString(9));
                   user.setCorreo(rs.getString(10));
                   user.setTipo(rs.getInt(11));
                   user.setUsuario(rs.getString(12));
                   user.setContraseña(rs.getString(13));
                 
                   return true;
               }else{
                   return false;
               }
          
           }
           
           return false;
           
        } catch (SQLException ex) {
            Logger.getLogger(Sql_usuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }
      
       public int existenumero (String numero){    

     PreparedStatement ps= null;
     Connection con = getConexion();
     ResultSet rs= null;
// se busca el numerodocumento en la base de datos para validar si ya esta registrado          
     String datos = "SELECT count(id) FROM usuarios WHERE NUMERO=?";
     
        try {
            ps = con.prepareStatement(datos);
            ps.setString(1, numero);     
            rs = ps.executeQuery();
           if(rs.next()){
               return rs.getInt(1);
           }
           
           return 1;
           
        } catch (SQLException ex) {
            Logger.getLogger(Sql_usuarios.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        } 
    }
      
    }
    
