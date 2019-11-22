package com.example.ArtifatName.controller;
import com.example.ArtifatName.ArtifatNameApplication;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import static com.example.ArtifatName.ArtifatNameApplication.document;

@RestController
@RequestMapping("data") //http://localhost:8080/GET
public class MessageController {
    @GetMapping
    public Document show()
    {
        ArtifatNameApplication.LOGGER.log(Level.INFO,"Get all users");
        return document;
    }
    @GetMapping("{id}")
    public Element showOne(@PathVariable int id)
    {
        ArtifatNameApplication.LOGGER.log(Level.INFO,"Get "+ id + " user");
        NodeList usersNodeList = document.getElementsByTagName("User");
        Element userElement = (Element) usersNodeList.item(0);
        for(int i = 0; i<id;i++)
        {
            if(usersNodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
                userElement = (Element) usersNodeList.item(i);
            }
        }
        return userElement;
    }
   @PostMapping()
    public Map<String, String> create(@RequestBody Map <String, String> userInfo )
    {
        ArtifatNameApplication.LOGGER.log(Level.INFO,"Add new user");
       Node root = document.getDocumentElement();
        root.appendChild(createNewUser(userInfo));
        try
        {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(ArtifatNameApplication.file);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        }
        catch (TransformerException | IOException e)
        {
            ArtifatNameApplication.LOGGER.log(Level.WARNING,"catch exception in PostMapping! ", e);
        }
        return userInfo;
    }
    @DeleteMapping("{id}")
    public void removeOne(@PathVariable int id)
    {
        ArtifatNameApplication.LOGGER.log(Level.INFO,"Delete "+id+" user");
        Node root = document.getDocumentElement();
        root.removeChild(document.getElementsByTagName("User").item(id-1));
        writeToXml(document);
    }
    @PutMapping("{id}")
    public Map <String, String> update(@PathVariable int id, @RequestBody Map <String, String> userInfo)
    {
        ArtifatNameApplication.LOGGER.log(Level.INFO,"Change "+id+" user");
        Node root = document.getDocumentElement();
        root.replaceChild(createNewUser(userInfo),document.getElementsByTagName("User").item(id-1));
        writeToXml(document);
        return userInfo;
    }
public static void writeToXml(Document document)
{
    ArtifatNameApplication.LOGGER.log(Level.INFO,"Save results to xml file");
    try
    {
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(document);
        FileOutputStream fos = new FileOutputStream(ArtifatNameApplication.file);
        StreamResult result = new StreamResult(fos);
        tr.transform(source, result);
    }
    catch (TransformerException | IOException e)
    {
        ArtifatNameApplication.LOGGER.log(Level.WARNING,"catch exception in 'writeToXml' method! ",e);;
    }
}
private static Node createNewUser(Map <String, String> userInfo)
{
    ArtifatNameApplication.LOGGER.log(Level.INFO,"Create new user");
    Element user = document.createElement("User");
    Element name = document.createElement("Name");
    name.setTextContent(userInfo.get("Name"));
    Element fName = document.createElement("FamilyName");
    fName.setTextContent(userInfo.get("FamilyName"));
    Element birthday = document.createElement("Birthday");
    birthday.setTextContent(userInfo.get("Birthday"));
    user.appendChild(name);
    user.appendChild(fName);
    user.appendChild(birthday);
    return user;
}
}
