module org.artembogomolova.demo.webapp {
  requires org.hibernate.orm.core;
  requires spring.boot.autoconfigure;
  requires spring.boot;
  requires java.sql;
  requires spring.context;
  requires com.fasterxml.jackson.module.jaxb;
  requires java.xml.bind;
  requires java.activation;
  requires com.fasterxml.jackson.databind;
  opens org.artembogomolova.demo.webapp;
}