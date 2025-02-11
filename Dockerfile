# Usa una imagen oficial de Tomcat 9
FROM tomcat:9.0

# Copia el archivo WAR al directorio webapps de Tomcat
COPY dist/SEMPER_PARATUS.war C:\apache-tomcat-9.0.98\webapps\SEMPER_PARATUS.war

# Expone el puerto 808
EXPOSE 8080

# Inicia Tomcat
CMD ["catalina.sh", "run"]