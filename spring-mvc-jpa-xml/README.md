# Spring 5 MVC, Spring Data JPA, Configuração XML

Devido a dificuldade que muitos desenvolvedores encontram ao configurar o Spring 5 por meio de configuração XML, é apresentado neste exemplo o passo a passo de como realizar a configuração XML de um projeto Spring 5 MVC com Spring Data JPA.

O objetivo do artigo não é discutir qual é a melhor forma de configurar um projeto Spring 5, e sim apresentar um modelo que pode ser útil em diversos projetos.

## POM.XML

É utilizado o Maven 3 para gerenciar as dependências: 

```XML
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-webmvc</artifactId>
  <version>5.3.19</version>
</dependency>

<dependency>
  <groupId>org.springframework.data</groupId>
  <artifactId>spring-data-jpa</artifactId>
  <version>2.6.4</version>
</dependency>

<dependency>
  <groupId>org.hibernate</groupId>
  <artifactId>hibernate-core</artifactId>
  <version>5.6.7.Final</version>
</dependency>

<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.13.2.2</version>
</dependency>

<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <version>2.1.212</version>
  <scope>runtime</scope>
</dependency>

<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>javax.servlet-api</artifactId>
  <version>4.0.1</version>
  <scope>provided</scope>
</dependency>

<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>jstl</artifactId>
  <version>1.2</version>
</dependency>
```

## Spring MVC

Para o Spring MVC é configurado o DispatcherServlet e o WebApplicationContext.   

### DispatcherServlet (web.xml)

O DispatcherServlet recebe todas as requisições HTTP e direciona para o controlador.  

```XML
<servlet>
  <servlet-name>dispatcher</servlet-name>
  <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  <init-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/dispatcher-context.xml</param-value>
  </init-param>
  <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
  <servlet-name>dispatcher</servlet-name>
  <url-pattern>/</url-pattern>
</servlet-mapping>
```
* Com a tag `<servlet />` é declarado o DispatcherServlet
* Com a tag `<servlet-mapping />` é configurada a URL que será mapeada para o DispatcherServlet. 

### Dispatcher Context (dispatcher-context.xml)

O WebApplicationContext é o contexto Spring específico para aplicações Web.

```XML
<context:component-scan base-package="br.com.eof.examples" />

<mvc:annotation-driven />
```

* A tag `<context:component-scan />` instrui para que seja detectado automaticamente as classes com anotações Spring. Habilita implicitamente `<context:annotation-config />` 
* A tag `<mvc:annotation-driven />` habilita a configuração do Spring MVC

## Spring Data JPA (persistence-context.xml)

É utilizado o Hibernate como provedor de persistência padrão do JPA.

### DataSource

No DataSource é configurado a fonte de dados com o banco de dados embutido H2: 

```XML
<jdbc:embedded-database id="dataSource" type="H2" />
```

* A tag `<jdbc:embedded-database />` cria o banco de dados embutido e disponibiliza para o container Spring como um bean do tipo javax.sql.DataSource 

### EntityManagerFactory

Para utlizar o JPA no Spring é preciso configurar o EntityManagerFactory. Fazendo uma anologia grosseira, o EntityManagerFactory é equivalente ao SessionFactory em uma configuração pura do Hibernate.

Neste exemplo é utilizado o LocalContainerEntityManagerFactoryBean que suporta a injeção de um DataSource:

```XML
<bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
  <property name="dataSource" ref="dataSource" />
  <property name="packagesToScan" value="br.com.eof.examples.entities"/>
  <property name="jpaVendorAdapter">
    <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"/>
  </property>
  <property name="jpaProperties">
    <props>
      <prop key="hibernate.dialect">org.hibernate.dialect.H2Dialect</prop>
      <prop key="hibernate.hbm2ddl.auto">create-drop</prop>
      <prop key="hibernate.hbm2ddl.import_files">data.sql</prop>
      <prop key="hibernate.show_sql">true</prop>
    </props>
  </property>
</bean>
```
* `dataSource:` Configura o bean DataSource
* `packagesToScan:` Configura a localização das entidades 
* `jpaVendorAdapter:` Configura o Hibernate como o provedor de persistência padrão JPA
* `jpaProperties:` Configura detalhes para o provedor de pesistência Hibernate

### TransactionManager

O EntityManagerFactory requer um gerenciador de transações. O Spring fornece o JpaTransactionManager para gerenciar as transações:

```XML
<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
  <property name="entityManagerFactory" ref="entityManagerFactory"/>
</bean>

<tx:annotation-driven />
```
A tag `<tx:annotation-driven>` permite o uso de anoatações no código Java para  a demarcação de transações.

### Spring Data JPA Repository

A tag `<jpa:repositories>` configura a localização dos repositórios Spring Data JPA que serão instanciados:

```XML
<jpa:repositories base-package="br.com.eof.examples.repositories"/>
```
