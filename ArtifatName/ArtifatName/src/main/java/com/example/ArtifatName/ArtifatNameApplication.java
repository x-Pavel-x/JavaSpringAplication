package com.example.ArtifatName;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SpringBootApplication
public class ArtifatNameApplication {
	public static LinkedList<User> users= new LinkedList();
	public static Document document;
	public static File file;

	static public Logger LOGGER;
	static
	{
		try(FileInputStream ins = new FileInputStream("src\\main\\java\\com\\example\\ArtifatName\\log.config"))
		{
			LogManager.getLogManager().readConfiguration(ins);
			LOGGER = Logger.getLogger(ArtifatNameApplication.class.getName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException
	{
		SpringApplication.run(ArtifatNameApplication.class, args);
		file = new File("src\\main\\java\\com\\example\\ArtifatName\\file.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		LOGGER.log(Level.INFO,"Parsing xml-file");
		document = builder.parse(file);
		NodeList usersNodeList = document.getElementsByTagName("User");

		int size = usersNodeList.getLength();
		for(int i = 0; i<size;i++)
		{
			if(usersNodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				Element userElement = (Element) usersNodeList.item(i);
				User user = new User();
				NodeList childNodes = userElement.getChildNodes();
				for(int j = 0; j<childNodes.getLength(); j++)
				{
					if(childNodes.item(j).getNodeType() == Node.ELEMENT_NODE)
					{
						Element childElement = (Element) childNodes.item(j);
						switch (childElement.getNodeName())
						{
							case "Name":
							{
								user.setName(childElement.getTextContent());
							}
							break;
							case "FamilyName":
							{
								user.setFamilyName(childElement.getTextContent());
							}
							break;
							case "Birthday":
							{
								user.setBirthday(childElement.getTextContent());
							}
						}
					}
				}
				LOGGER.log(Level.INFO,"Add User to collection");
				users.add(user);
			}
		}


















	}

}
