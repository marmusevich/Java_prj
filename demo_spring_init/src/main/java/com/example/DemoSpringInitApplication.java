package com.example;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.GenericXmlApplicationContext;

@SpringBootApplication
public class DemoSpringInitApplication {

	public static void main(String[] args) {
		//SpringApplication.run(DemoSpringInitApplication.class, args);

//		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//		XmlBeanDefinitionReader rdr = new XmlBeanDefinitionReader(factory);
//		rdr.loadBeanDefinitions(new FileSystemResource("src/main/java/com/example/xmlBeanFactory.xml"));
//
//		Oracle oracle = factory.getBean("Oracle", Oracle.class);
//
//		System.out.println(oracle.defineMeaningOfLife());


		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:xmlBeanFactory.xml");
		//ctx.load("classpath:src/main/java/com/example/xmlBeanFactory.xml");
		ctx.refresh();

		MessageProvider messageProvider = ctx.getBean("messageProvider", MessageProvider.class);

		System.out.println(messageProvider.getMessage());

	}
}
