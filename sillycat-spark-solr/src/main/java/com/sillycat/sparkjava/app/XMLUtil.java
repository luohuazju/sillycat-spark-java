package com.sillycat.sparkjava.app;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

public class XMLUtil
{

  public static void xmlStreamWriter( String filePath, List<Job> items )
  {

    XMLStreamWriter writer = null;
    try ( OutputStream os =
      Files.newOutputStream( Paths.get( filePath ), StandardOpenOption.CREATE ) )
    {
      XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
      writer = new IndentingXMLStreamWriter(
        outputFactory.createXMLStreamWriter( os, "utf-8" ) );
      writer.writeStartDocument( "utf-8", "1.0" );
      writer.writeStartElement( "jobs" );
      for ( Job item : items )
      {
        writer.writeStartElement( "job" );

        writer.writeStartElement( "id" );
        writer.writeCharacters( item.getId() );
        writer.writeEndElement();

        writer.writeStartElement( "title" );
        writer.writeCData( item.getTitle() );
        writer.writeEndElement();

        writer.writeStartElement( "price" );
        writer.writeCharacters( item.getPrice().toBigInteger().toString() );
        writer.writeEndElement();

        writer.writeEndElement();

      }
      writer.writeEndElement();
      writer.writeEndDocument();
    }
    catch ( IOException | XMLStreamException e )
    {
      e.printStackTrace();
    }
    finally
    {
      if ( writer != null )
      {
        try
        {
          writer.close();
        }
        catch ( XMLStreamException e )
        {
          e.printStackTrace();
        }
      }
    }
  }

}
