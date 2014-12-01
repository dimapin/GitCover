package de.pfeufferweb.gitcover;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;

import static java.lang.Integer.parseInt;

public class JacocoCoverageBuilder
{
    public Coverage computeAll(File directory) throws Exception
    {
        Coverage overallCoverage = new Coverage();
        Collection<File> files = FileUtils.listFiles(directory, new IOFileFilter()
        {

            @Override
            public boolean accept(File dir, String name)
            {
                return false;
            }

            @Override
            public boolean accept(File file)
            {
                return file.getName().equals("jacoco.xml");
            }
        }, new IOFileFilter()
        {

            @Override
            public boolean accept(File dir, String name)
            {
                return false;
            }

            @Override
            public boolean accept(File file)
            {
                return true;
            }
        });
        for (File file : files)
        {
            overallCoverage.addAll(compute(file));
        }
        return overallCoverage;
    }

    public Coverage compute(File file) throws Exception
    {
        Coverage result = new Coverage();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new EntityResolver()
        {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
            {
                if (systemId.contains("JACOCO"))
                {
                    return new InputSource(new StringReader(""));
                }
                else
                {
                    return null;
                }
            }
        });
        Document doc = builder.parse(file);
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression classExpr = xpath.compile("//sourcefile/@name");
        NodeList foundFileNodes = (NodeList) classExpr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < foundFileNodes.getLength(); ++i)
        {
            String fileName = foundFileNodes.item(i).getNodeValue();
            result.addFile(fileName);
            XPathExpression linesExpr = xpath.compile("//sourcefile[@name='" + fileName + "']//line");
            NodeList foundLineNodes = (NodeList) linesExpr.evaluate(doc, XPathConstants.NODESET);
            for (int j = 0; j < foundLineNodes.getLength(); ++j)
            {
                NamedNodeMap attributes = foundLineNodes.item(j).getAttributes();
                int line = parseInt(attributes.getNamedItem("nr").getNodeValue());
                int hits = parseInt(attributes.getNamedItem("ci").getNodeValue());
                result.addLine(fileName, line, hits);
            }
        }
        return result;
    }
}
