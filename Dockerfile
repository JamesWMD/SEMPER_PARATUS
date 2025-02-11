# Usa una imagen oficial de Tomcat 9
FROM tomcat:9.0

# Copia el archivo WAR al directorio webapps de Tomcat
COPY dist/tu-proyecto.war /usr/local/tomcat/webapps/

# Expone el puerto 8080
EXPOSE 8080

# Inicia Tomcat
CMD ["catalina.sh", "run"]